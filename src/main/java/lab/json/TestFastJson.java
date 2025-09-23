package lab.json;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import share.TestLombokFluent;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 14:31 2025/8/29
 * @Modified By:
 */
public class TestFastJson {
    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(TestLombokFluent.testData()));
        System.out.println(com.alibaba.fastjson2.JSON.toJSONString(TestLombokFluent.testData()));
        System.out.println(new Gson().toJson(TestLombokFluent.testData()));
//        fastjson1 2.0.58:  {"id":1,"name":"name"}
//        fastjson1 1.2.83:  {}
//        fastjson2 2.0.58:  {"id":1,"name":"name"}
//        gson            :  {"id":1,"name":"name","sub":{"id":2,"name":"subName"}}
    }
}
