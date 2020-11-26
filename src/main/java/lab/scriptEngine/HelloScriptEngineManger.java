package lab.scriptEngine;

import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.script.*;
import java.util.List;

/**
 * @Author: kkyeer
 * @Description: Java内部有提供一套脚本语言执行的接口，同时JavaSE8自带了一个js引擎-nashorn
 * @Date:Created in 18:34 2019/3/4
 * @Modified By:
 */
public class HelloScriptEngineManger {
    private static NashornScriptEngine jsEngine;
    private static ScriptEngineFactory scriptEngineFactory;

    /**
     * 初始化
     */
    private static void init(){
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        List<ScriptEngineFactory> scriptEngineFactories = scriptEngineManager.getEngineFactories();
        // now only nashorn engine is involved
        scriptEngineFactories.forEach(
                scriptEngineFactory -> System.out.println(scriptEngineFactory.getEngineName())
        );
        scriptEngineFactory = scriptEngineFactories.get(0);
        // print multi thread safe info
        System.out.println("Threading:"+scriptEngineFactory.getParameter("THREADING"));
        // output:null ,not thread safe
        jsEngine = (NashornScriptEngine) scriptEngineFactory.getScriptEngine();
    }

    /**
     * 基本用法
     */
    private static void basic(){
        try {
            jsEngine.eval("var n = 288;");
            Object result = jsEngine.eval("n+1");
            System.out.println(result);

            jsEngine.put("k", 232);
            System.out.println(jsEngine.eval("k+1"));

        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用函数
     */
    private static void invokeFunc() throws ScriptException, NoSuchMethodException {
        jsEngine.eval("function add(a,b){return a+b;}");
        System.out.println("Add result:" + jsEngine.invokeFunction("add", 1, 2));
    }


    public static void main(String[] args) throws ScriptException, NoSuchMethodException {
        init();
//        basic();
        invokeFunc();
    }
}
