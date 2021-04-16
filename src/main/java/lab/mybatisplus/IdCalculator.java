package lab.mybatisplus;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

/**
 * @Author: kkyeer
 * @Description: 计算雪花算法ID
 * @Date:Created in 下午5:47 2021/4/16
 * @Modified By:
 */
public class IdCalculator {
    public static void main(String[] args) throws UnknownHostException, ExecutionException, InterruptedException {
        printDataCenterIdAndWorkerId(-126,-111,2645473);
        printDataCenterIdAndWorkerId(-79,-95, 2147074);
    }

    static void printDataCenterIdAndWorkerId(long mac1,long mac2,long pid){
        long dataCenterId = getDatacenterId(mac1,mac2,31);
        long workerId = getMaxWorkerId(dataCenterId, 31, pid);
        System.out.println(String.format("DataCenterId:%d,WorkerId:%d",dataCenterId,workerId));
    }

    static long getDatacenterId(long mac1,long mac2,long maxDatacenterId) {
        long id = 0L;
        id = ((0x000000FF & (long) mac1) | (0x0000FF00 & (((long) mac2) << 8))) >> 6;
        id = id % (maxDatacenterId + 1);
        return id;
    }


    static long getMaxWorkerId(long datacenterId, long maxWorkerId,long pid) {
        StringBuilder mpid = new StringBuilder();
        mpid.append(datacenterId);
        mpid.append(pid);
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }
}
