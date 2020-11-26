package lab.annotations;

import java.lang.annotation.Annotation;

/**
 * @Author: kkyeer
 * @Description: 注解equals方法的试验
 * @Date:Created in 21:18 2019/3/20
 * @Modified By:
 */
public class EqualsTest {

    @Charger(name = "aa")
    public void method1(){}

    @Charger(name = "bb")
    public void method2(){}

    @Charger(name = "aa")
    public void method3(){}

    @Charger(name = "aa", hobby = "daa")
    public void method4(){}

    public static void main(String[] args) throws NoSuchMethodException {
        Class<? extends EqualsTest> clazz = EqualsTest.class;
        Annotation charger1 = clazz.getMethod("method1").getAnnotation(Charger.class);
        Annotation charger2 = clazz.getMethod("method2").getAnnotation(Charger.class);
        Annotation charger3 = clazz.getMethod("method3").getAnnotation(Charger.class);
        Annotation charger4 = clazz.getMethod("method4").getAnnotation(Charger.class);
        System.out.println("1=2?"+charger1.equals(charger2));
        // false
        System.out.println("1=3?" + charger1.equals(charger3));
        // true
        System.out.println("1=4?" + charger1.equals(charger4));
        // false
    }
}
