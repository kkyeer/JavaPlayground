package chapter3;

//    -verbose:gc -Xms20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:+PrintTenuringDistribution  -XX:+UseSerialGC
// 动态对象年龄：Survivor中相同年龄对象大小超过Survivor一半，则大于等于此年龄的全部晋升老生代
public class PromoteToTenuredGenByBiggerThanHalfSurvivor {
    private static final int _1MB = 1024*1024;

    public static void main(String[] args) {
        byte[] allo1,allo2,allo3,allo4;
        allo1 = new byte[_1MB / 4];
        allo2 = new byte[_1MB / 4];
//        试图在Eden区划分空间给allo3指向的对象，由于allo1-3加起来有8.25M，Eden区实际为8192k ,空间不足，触发新生代minorGC
        allo3 = new byte[_1MB * 4];
        allo4 = new byte[_1MB * 4];
        allo3 = null;
        allo4 = new byte[_1MB * 4];
//        此时Eden区有原allo3,allo4指向的对象，若想再分配新空间给allo4新指向的对象，则需共计8M空间，Eden区空间不足，触发MinorGC

//[GC[DefNew
//Desired survivor size 524288 bytes, new threshold 1 (max 15)
//- age   1:     852136 bytes,     852136 total
//: 5958K->832K(9216K), 0.0073270 secs] 5958K->4928K(19456K), 0.0073670 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
//[GC[DefNew
//Desired survivor size 524288 bytes, new threshold 15 (max 15)
//: 4928K->0K(9216K), 0.0058450 secs] 9024K->9024K(19456K), 0.0058610 secs] [Times: user=0.00 sys=0.01, real=0.00 secs]
//Heap
// def new generation   total 9216K, used 4262K [0x000000008ee00000, 0x000000008f800000, 0x000000008f800000)
//  eden space 8192K,  52% used [0x000000008ee00000, 0x000000008f229928, 0x000000008f600000)
//  from space 1024K,   0% used [0x000000008f600000, 0x000000008f600000, 0x000000008f700000)
//  to   space 1024K,   0% used [0x000000008f700000, 0x000000008f700000, 0x000000008f800000)
// tenured generation   total 10240K, used 9024K [0x000000008f800000, 0x0000000090200000, 0x00000000fae00000)
//   the space 10240K,  88% used [0x000000008f800000, 0x00000000900d00c8, 0x00000000900d0200, 0x0000000090200000)
// compacting perm gen  total 21248K, used 3305K [0x00000000fae00000, 0x00000000fc2c0000, 0x0000000100000000)
//   the space 21248K,  15% used [0x00000000fae00000, 0x00000000fb13a420, 0x00000000fb13a600, 0x00000000fc2c0000)
//No shared spaces configured.
    }
}
