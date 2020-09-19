package lab.annotations.meta.repeatable;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author: kkyeer
 * @Description: 可重复注解
 * @Date:Created in 22:36 2019/4/13
 * @Modified By:
 */
@Repeatable(InfoArray.class)
@Retention(RetentionPolicy.RUNTIME)
@interface Info {
    String value();
}
