package taste.annotaions.try2use;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: kkyeer
 * @Description: 模拟Spring的AutoWire注解
 * @Date:Created in 23:49 2019/4/6
 * @Modified By:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowire {

}
