package taste.annotations.try2use.ioccontainer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: kkyeer
 * @Description: 模拟Spring的Bean注解
 * @Date:Created in 22:45 2019/4/6
 * @Modified By:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Bean {
    
}
