package taste.scriptEngine;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 18:34 2019/3/4
 * @Modified By:
 */
public class HelloScriptEngineManger {
    public static void main(String[] args) {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        List<ScriptEngineFactory> scriptEngineFactories = scriptEngineManager.getEngineFactories();
        // now only nashorn engine is involved
        scriptEngineFactories.forEach(
                scriptEngineFactory -> System.out.println(scriptEngineFactory.getEngineName())
        );
        NashornScriptEngineFactory nashornScriptEngineFactory = (NashornScriptEngineFactory) scriptEngineFactories.get(0);
        // print multi thread safe info
        System.out.println(nashornScriptEngineFactory.getParameter("THREADING"));
        // output:null ,not thread safe
        ScriptEngine jsEngine = nashornScriptEngineFactory.getScriptEngine();

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
}
