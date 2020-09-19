package lab.jdksrc.java.util.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: kkyeer
 * @Description: List<Map> 转Map
 * @Date:Created in 2020.01.26
 * @Modified By:
 */

public class ListConverter {
    // 假设一种场景，对接对方接口，对方返回的结果是Map数组，其中每个map均保存一个二元值，key为目录，value为值，需要转换为目录与值数组的对应关系
    // 源值：
//        K   V
//        1   2
//        1   3
//        2   1
//        2   4
//     期望的聚合结果:Map<K,List<V>>
//    {1=[2, 3], 2=[1, 4]}
    public static void main(String[] args) throws Exception {
        List<Map<Integer, Integer>> sqlResult = new ArrayList<>();
        packIntoList(1, 2, sqlResult);
        packIntoList(1, 3, sqlResult);
        packIntoList(2, 1, sqlResult);
        packIntoList(2, 4, sqlResult);
        Map<Integer,List<Integer>> result = sqlResult.stream().collect(
                Collectors.toMap(
                        (map) -> map.entrySet().iterator().next().getKey(),
                        (map) -> Collections.singletonList(map.entrySet().iterator().next().getValue()),
                        (val1,val2)->{
                            val1.addAll(val2);
                            return val1;
                        }
                )
        );
        System.out.println(result);
    }

    private static void packIntoList(Integer key, Integer value,List<Map<Integer,Integer>> target) {
        Map<Integer, Integer> result = new HashMap<>();
        result.put(key, value);
        target.add(result);
    }
}
