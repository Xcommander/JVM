/**
 * 新生代区域GC，为Minor GC
 **/

public class MinorGC {
    private static final int _1MB = 1024 * 1024;

    public static void testAllocation() {
        /**
         * VM参数:-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
         * **/
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];//这里发生了MinorGC
        /**以下是GC的结果，其中from和to就是那两个survivor，eden:to=8:1
         * [GC (Allocation Failure) [DefNew: 7632K->573K(9216K), 0.0042795 secs] 7632K->6717K(19456K), 0.0162859 secs] [Times: user=0.02 sys=0.00, real=0.02 secs]
         Heap
         def new generation   total 9216K, used 4833K [0x03e00000, 0x04800000, 0x04800000)
         eden space 8192K,  52% used [0x03e00000, 0x042290f8, 0x04600000)
         from space 1024K,  56% used [0x04700000, 0x0478f5f8, 0x04800000)
         to   space 1024K,   0% used [0x04600000, 0x04600000, 0x04700000)
         tenured generation   total 10240K, used 6144K [0x04800000, 0x05200000, 0x05200000)
         the space 10240K,  60% used [0x04800000, 0x04e00030, 0x04e00200, 0x05200000)
         Metaspace       used 209K, capacity 2280K, committed 2368K, reserved 4480K
         *
         *
         * 现在逐一分析，首先我们要明白，这个表示执行到allocation4的时候，系统发现内存不足，然后进行MinorGC的结果，
         * 还没有给allocation4分配内存。
         * 所以针对GC的结果是指发生GC，并且GC完成，打印的log，还没给allocation4分配内存
         * (1)DefNew: 7632K->573K(9216K) 这个是指发生GC前，新生区占用内存为7632K，完成GC后，内存占用为573K，总的内存为9216K
         * (2) 7632K->6717K(19456K)这个是整个堆栈区，7632K 指GC前的堆栈使用量，6717K是指GC后的堆栈区的使用量，19456K这个是指总的
         * 堆栈区可使用的量
         *
         *
         * heap的具体情况，是指GC完成后且对象分配完成后，新生区和老年区的各个情况。
         * 由上面可以看到，由于新生代无法为allocation4分配4M的内存，此时就是将其余3个（存活不能回收）给移到survivor，但是survivor只有1M，没办法
         * 只能让老年区进行担保，此时就会把其他三个（共6M的数据转移到老年区），所以就可以看到新生区为4M，老年区为6M。
         * **/

    }

    public static void testPretenureSizeThreshold() {
        /**
         * 测试大对象直接进入老年区
         * VM参数: -verbose:gc
         * -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
         * **/
        byte[] allocation1 = new byte[4 * _1MB];
        /**
         * Heap
         def new generation   total 9216K, used 1488K [0x03e00000, 0x04800000, 0x04800000)
         eden space 8192K,  18% used [0x03e00000, 0x03f74240, 0x04600000)
         from space 1024K,   0% used [0x04600000, 0x04600000, 0x04700000)
         to   space 1024K,   0% used [0x04700000, 0x04700000, 0x04800000)
         tenured generation   total 10240K, used 4096K [0x04800000, 0x05200000, 0x05200000)
         the space 10240K,  40% used [0x04800000, 0x04c00010, 0x04c00200, 0x05200000)
         Metaspace       used 205K, capacity 2280K, committed 2368K, reserved 4480K
         *
         * 从上面看到PretenureSizeThreshold=3145728，限制对象大小为3M，当分配的内存大小为4M，直接分配到老年代分区
         * the space 10240K,  40% used
         *
         * **/
    }

    public static void testTenuringThreshold() {
        /**
         * 长期存活的对象进入老年区，也就是survivor 中国对象的age大约一定阈值，就会被晋升到老年区，默认为15，
         * 可以通过-XX:MaxTenuringThreshold来设置
         * **/
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[3 * _1MB];
        allocation3 = new byte[4 * _1MB];//进入gc，allocation1 在survivor的age +1
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];//第二次gc
        /**
         * [GC (Allocation Failure) [DefNew: 5676K->858K(9216K), 0.0039704 secs] 5676K->4954K(19456K), 0.0040115 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
         [GC (Allocation Failure) [DefNew: 5117K->0K(9216K), 0.0011045 secs] 9213K->4953K(19456K), 0.0011350 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         Heap
         def new generation   total 9216K, used 4260K [0x03e00000, 0x04800000, 0x04800000)
         eden space 8192K,  52% used [0x03e00000, 0x042290e8, 0x04600000)
         from space 1024K,   0% used [0x04600000, 0x04600050, 0x04700000)
         to   space 1024K,   0% used [0x04700000, 0x04700000, 0x04800000)
         tenured generation   total 10240K, used 4953K [0x04800000, 0x05200000, 0x05200000)
         the space 10240K,  48% used [0x04800000, 0x04cd6770, 0x04cd6800, 0x05200000)
         Metaspace       used 218K, capacity 2280K, committed 2368K, reserved 4480K

         从这份log中，我们可以看出虽然我们设置了MaxTenuringThreshold=15，按道理survivor（from）不应该是为0，这时候我们应该想起
         动态对象年龄判断，如果from中的对象相同年龄的值的总和大于from的一半，那么这个大于或者等于该对象的年龄则搬到老年区去。
         也就是说survivor中from的晋升，是先看动态对象判断，再看MaxTenuringThreshold的值。
         *
         * **/


    }

    public static void testHandlePromotion() {
        /**
         * 新生区在进行Minor gc的时候，先看老年区的担保，如果老年区的可用最大连续空间大于新生区对象大小或者历代晋升的平均
         * 大小，那么就认为可以担保，可以进行Minor GC
         *
         * **/
        byte[] a1, a2, a3, a4, a5, a6, a7, a8, a9, a10;
        a1 = new byte[2 * _1MB];
        a2 = new byte[2 * _1MB];
        a3 = new byte[2 * _1MB];
        a1 = null;
        a4 = new byte[2 * _1MB];//第一次gc，先看担保，再进行minor gc
        a5 = new byte[2 * _1MB];
        a6 = new byte[2 * _1MB];
        a4 = null;
//        a5=null;
//        a6=null;
        a7 = new byte[2 * _1MB];//第二次gc，先看担保，再看minor gc，不行的话，进行full gc
        a8 = new byte[2 * _1MB];
        a10 = new byte[4 * _1MB];//第三次gc，会导致full gc
    }

    public static void main(String[] args) {
//        MinorGC minorGC=new MinorGC();
        /**注意类的static方法不属于类的对象，所以不能通过object.static方法来获取，
         * static方法属于整个项目的
         * 所以用的时候只需要类.static方法即可
         * **/
//        MinorGC.testAllocation();
        MinorGC.testHandlePromotion();
        /**
         * 总结:在Hotspot中，内存分配在新生区中（优先以Eden为主，其中可以通过参数PretenureSizeThreshold来改些大于某个阈值，
         * 直接分配在老年区），
         * 当新生区内存不足时，要准备进行Minor GC的时候
         * 首先会判断老年区是否能为新生区做担保，担保的条件是老年区的最大连续空间是否大于新生区的对象的总和或者历代晋升的平均水平
         * 如果条件成立，则进行Minor GC，否则进入full GC
         * 当进入Minor GC的时候，按照复制算法gc，gc完成后，查看survivor（from）区域，根据动态年龄判断，是否有对象晋升。
         * 接下来，再根据年龄判断，是否有对象晋升。
         * 这样搞下来，算是一个完整的Minor GC过程
         *
         * **/


    }

}
