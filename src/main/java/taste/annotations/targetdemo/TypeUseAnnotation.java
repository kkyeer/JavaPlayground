package taste.annotations.targetdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 15:16 2019/3/25
 * @Modified By:
 */
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeUseAnnotation {
    String value();
}
