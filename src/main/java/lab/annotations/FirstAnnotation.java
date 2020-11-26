package lab.annotations;

import share.Person;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 20:25 2019/3/4
 * @Modified By:
 */
// @Target is meta-annotation
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
// 保留方案
@Retention(RetentionPolicy.SOURCE)
public @interface FirstAnnotation {
    enum Status {UNCONFIRMED, CONFIRMED}

    // 下面是可能的返回值类型
    boolean isValid() default true;

    String name() default "[none]";

    Class resultType() default String.class;

    Class<? extends Person> career() default Person.class;

    Status forStatus() default Status.UNCONFIRMED;

    ActionListenerFor anno() default @ActionListenerFor(source = "demo");

    String[] cadidates();

    // marker annotation  全部都有默认值，且使用的时候也不指定值的，类似，@Marker,叫marker annotation,
    // value annotation 只有一个元素就是value的情况下，使用时可以忽略value=，类似@Name("John")
    // 注解值为数组时，使用大括号来包裹，类似@Names({"N1","N2"})
    //
}
