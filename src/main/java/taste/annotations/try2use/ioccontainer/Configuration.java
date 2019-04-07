package taste.annotations.try2use.ioccontainer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: kkyeer
 * @Description: 模拟Spring的Configuration注解
 * @Date:Created in 11:44 2019/4/7
 * @Modified By:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {

}
