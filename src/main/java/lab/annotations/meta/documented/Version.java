package lab.annotations.meta.documented;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @Author: kkyeer
 * @Description: 版本注解
 * @Date:Created in 22:08 2019/4/13
 * @Modified By:
 */
@Documented
@Target(ElementType.TYPE)
@interface Version {
    String value();
}
