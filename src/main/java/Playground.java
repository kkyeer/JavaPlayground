import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 21:00 2018/12/18
 * @Modified By:
 */
public class Playground {
    private static void testHoriToVertical(){
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
    private static Map<String, Map<String, Object>> hoToVer(List<Map<String, Object>> source) {
        return source.stream().collect(
                Collectors.groupingBy(
                        (inMap) -> (String) inMap.get("paramId"),
                        Collector.of(
                                HashMap::new,
                                (resultMap, sourceMap) -> resultMap.put((String) sourceMap.get("paramName"), sourceMap.get("paramValue")),
                                (map1, map2) -> map1
                        )
                )
        );
    }


    public static void main(String[] args) {
        testHoriToVertical();
    }
}
