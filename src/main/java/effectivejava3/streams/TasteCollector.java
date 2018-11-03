package effectivejava3.streams;

import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 12:45 2018/11/3
 * @Modified By:
 */
public class TasteCollector {
    private static <R,T> R simuCollect(Stream<T> stream, Supplier<R> supplier, BiConsumer<R,T> accumulator,BiConsumer<R,R> combiner){
//        获得一个R类型的Result，此方法执行不确定次
        R midResult1 = supplier.get();
        R midResult2 = supplier.get();
//        遍历stream,填充到result中，此方法遍历到哪些stream内部元素，取决于实现方法
        stream.forEach((element)->accumulator.accept(midResult1, element));
        stream.forEach((element)->accumulator.accept(midResult2, element));
//        将多个R类型的result合并以生成最终结果
        combiner.accept(midResult1,midResult2);
        return midResult1;
    }

    static void simpleCollector(){
        Stream<String> words = Stream.of("GNU","not","Unix","GNU");
        HashMap<String,Integer> count = words.collect(
                HashMap::new,
                (map,word)->map.merge(word,1,(ori,newVal)->ori+newVal),
                Map::putAll
        );
        System.out.println(count);
    }

    static void tasteToSet(){
        // toList
        System.out.println(Stream.of("GNU","not","Unix","GNU").collect(Collectors.toList()));
        // toSet
        System.out.println(Stream.of("GNU","not","Unix","GNU").collect(Collectors.toSet()));
    }

    static void tasetToMap(){
        Stream<String> words = Stream.of("GNU","not","Unix","GNU");
//        统计各单词出现次数
        Map<String, Integer> count = words.collect(Collectors.toMap(
                Function.identity(),
                (word)->1,
                (x,y)->x+y)
        );
        System.out.println(count);
    }

    static void tasteSumming(){
        Stream<String> words = Stream.of("GNU","not","Unix","GNU");
//        统计字母数出现次数
        int count = words.collect(Collectors.summingInt(String::length));
        System.out.println(count);
        //13
    }

    static void tasteSummarizing(){
//        统计字母数出现次数数据
        Stream<String> words = Stream.of("GNU","not","Unix","GNU");
        IntSummaryStatistics summaryStatistics = words.collect(Collectors.summarizingInt(String::length));
        System.out.println(summaryStatistics);//IntSummaryStatistics{count=4, sum=13, min=3, average=3.250000, max=4}
    }

    static void tasteJoin(){
//        统计字母数出现次数数据
        Stream<String> words = Stream.of("GNU","not","Unix","GNU");
        System.out.println(words.collect(Collectors.joining("---","Result is:","。")));

    }



    public static void main(String[] args) {
//        tasetToMap();
//        tasteSumming();
        tasteSummarizing();
        tasteJoin();
    }
}
