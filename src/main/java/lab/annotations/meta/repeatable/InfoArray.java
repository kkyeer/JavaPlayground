package lab.annotations.meta.repeatable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author: kkyeer
 * @Description: 包裹注解
 * @Date:Created in 22:36 2019/4/13
 * @Modified By:
 */
@Retention(RetentionPolicy.RUNTIME)
@interface InfoArray {
    Info[] value();
    String desc() default "a";
}
