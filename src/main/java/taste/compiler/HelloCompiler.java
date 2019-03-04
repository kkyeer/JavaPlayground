package taste.compiler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 19:02 2019/3/4
 * @Modified By:
 */
public class HelloCompiler {
    private static void basic() throws FileNotFoundException {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        File sourceCodeFile = new File("src/main/resources/HelloCompiler.java");
        OutputStream outputStream = System.out;
        OutputStream errOut = System.out;
        int result = javaCompiler.run(null, outputStream, errOut,sourceCodeFile.getAbsolutePath());
    }

    public static void main(String[] args) throws Exception{
        basic();
    }
}
