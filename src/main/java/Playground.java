import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 21:00 2018/12/18
 * @Modified By:
 */
public class Playground {
    public static void main(String[] args) {
        List<Map<String, Object>> source = new ArrayList<>(2);

        Map<String, Object> param1 = new HashMap<>(3);
        param1.put("paramId", "p1");
        param1.put("paramName", "name");
        param1.put("paramValue", "Johnson");

        Map<String, Object> param2 = new HashMap<>(3);
        param2.put("paramId", "p1");
        param2.put("paramName", "age");
        param2.put("paramValue", 12);


        Map<String, Object> param3 = new HashMap<>(3);
        param3.put("paramId", "p1");
        param3.put("paramName", "grade");
        param3.put("paramValue", "high");
        
        
//        p2
        Map<String, Object> param4 = new HashMap<>(3);
        param4.put("paramId", "p2");
        param4.put("paramName", "name");
        param4.put("paramValue", "Mike");

        Map<String, Object> param5 = new HashMap<>(3);
        param5.put("paramId", "p2");
        param5.put("paramName", "age");
        param5.put("paramValue", 20);


        Map<String, Object> param6 = new HashMap<>(3);
        param6.put("paramId", "p2");
        param6.put("paramName", "grade");
        param6.put("paramValue", "middle");

        source.add(param1);
        source.add(param2);
        source.add(param3);
        source.add(param4);
        source.add(param5);
        source.add(param6);
        System.out.println(hoToVer(source));
    }

    private static List<Map<String, Object>> hoToVer(List<Map<String, Object>> source){
        List<Map<String, Object>> result = new ArrayList<>(12);
        List<Map<String, Object>> result2 = source.stream().collect(
            new Collector<Map<String, Object>, Map<String,Object>, List<Map<String, Object>>>() {
                @Override
                public Supplier<Map<String, Object>> supplier() {
                    return HashMap::new;
                }

                @Override
                public BiConsumer<Map<String, Object>, Map<String, Object>> accumulator() {
                    return (resultMap,sourceMap)->{
                        System.out.println("accu:"+resultMap+sourceMap);
                        resultMap.put("paramId", sourceMap.get("paramId"));
                        resultMap.put((String) sourceMap.get("paramName"), sourceMap.get("paramValue"));
                    };
                }

                @Override
                public BinaryOperator<Map<String, Object>> combiner() {
                    return (result1,result2)->{
                        System.out.println("combiner:"+result1+result2);
                        result1.putAll(result2);
                        return result1;
                    };
                }

                @Override
                public Function<Map<String, Object>, List<Map<String, Object>>> finisher() {
                    return (map)->{
                        result.add(map);
                        return result;
                    };
                }

                @Override
                public Set<Characteristics> characteristics() {
                    return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));
                }
            }
        );
        System.out.println("result2:"+result2);
        return result;
    }
}
