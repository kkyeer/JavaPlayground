package taste.annotations.meta.targ3t;

import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: kkyeer
 * @Description: 注解不同Target的使用与值的获取
 * @Date:Created in 22:07 2019/3/21
 * @Modified By:
 */

// 类注解
@VariousTarget.TypeAnnotation("This is before class declaration")
public class VariousTarget {
    // 构造器注解，内部是注解的注解
    @ConstructorAnnotation(value="This is a constructor annotation",ref=@AnnotationTypeAnnotation("This is a annotation of a annotation"))
    public VariousTarget(){}

    // 成员变量注解
    @FieldAnnotation("This is a field annotation")
    private String[] prop;

    // 方法注解，参数列表中是参数注解
    @MethodAnnotation("This is a method annotation")
    private void printSomething(@ParameterAnnotation("parameter annotation") String content) {
        // 本地变量注解，仅在源码中有效，不会被编译到class文件中
        @LocalVariableAnnotation("This ia a local variable annotation")
        String prefix = "-------";
    }


    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
        Class<VariousTarget> clazz = VariousTarget.class;
        System.out.println("TYPE:" + clazz.getAnnotation(TypeAnnotation.class).value());

        Constructor<VariousTarget> constructor = clazz.getConstructor();
        ConstructorAnnotation constructorAnnotation = constructor.getAnnotation(ConstructorAnnotation.class);
        System.out.println("CONSTRUCTOR:" + constructorAnnotation.value());
        System.out.println("ANNOTATION TYPE:"+constructorAnnotation.ref().value());
        Field field = clazz.getDeclaredField("prop");
        // 此处需要更改访问权限来获取注解值
        field.setAccessible(true);
        System.out.println("FIELD:" + field.getAnnotation(FieldAnnotation.class).value());

        Method method = clazz.getDeclaredMethod("printSomething", String.class);
        System.out.println("METHOD:" + method.getAnnotation(MethodAnnotation.class).value());
        Annotation[][] parameterAnnos = method.getParameterAnnotations();
        System.out.println("PARAMETER:" + ((ParameterAnnotation) parameterAnnos[0][0]).value());

        Package pkg = Package.getPackage("taste.annotations.meta.targ3t");
        System.out.println("PACKAGE:" + pkg.getAnnotation(PackageAnnotation.class).value());
    }

    /**
     * Target为TYPE的注解
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TypeAnnotation {
        String value();
    }

    /**
     * Target为Constructor的注解
     */
    @Target(ElementType.CONSTRUCTOR)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface  ConstructorAnnotation{
        String value();
        AnnotationTypeAnnotation ref();
    }

    /**
     * Target为Field的注解
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface  FieldAnnotation{
        String value();
    }

    /**
     * Target为Method的注解
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface  MethodAnnotation{
        String value();
    }

    /**
     * Target为Parameter的注解
     */
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface  ParameterAnnotation{
        String value();
    }

    /**
     * Target为AnnotationType的注解，也就是给注解作注解的注解
     */
    @Target(ElementType.ANNOTATION_TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface  AnnotationTypeAnnotation{
        String value();
    }

    /**
     * Target为Parameter的注解
     */
    @Target(ElementType.LOCAL_VARIABLE)
    @Retention(RetentionPolicy.SOURCE)
    public @interface  LocalVariableAnnotation{
        String value();
    }


    /**
     * Target为Parameter的注解
     */
    @Target(ElementType.PACKAGE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface  PackageAnnotation{
        String value();
    }
}
