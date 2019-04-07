package taste.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 22:44 2019/3/8
 * @Modified By:
 */
@Target({ElementType.PARAMETER,ElementType.TYPE_USE})
public @interface NonNull {
    String value() default "";
}
