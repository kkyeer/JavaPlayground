package taste.annotations;

import java.util.List;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 20:21 2019/3/4
 * @Modified By:
 */
public class HelloAnnotation {
    public static void main(String[] args) {

    }

    //  targetdemo:parameter
    public String getId(@NonNull String userName) {
        // targetdemo:type_use
        List<@NonNull String> stringList;
        return null;
    }
}
