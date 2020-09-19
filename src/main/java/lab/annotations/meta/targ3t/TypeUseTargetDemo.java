package lab.annotations.meta.targ3t;

import java.lang.reflect.*;
import java.util.AbstractList;
import java.util.List;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 15:19 2019/3/25
 * @Modified By:
 */
@TypeUseAnnotation("Before class")
public class TypeUseTargetDemo  extends @TypeUseAnnotation("Before super class") AbstractList<String> {

    @TypeUseAnnotation("Before field")
    private  String @TypeUseAnnotation("Annotated an array") [] box;


    public @TypeUseAnnotation("Return type") String trapOperation(List<@TypeUseAnnotation("Type parameter") String> param) throws @TypeUseAnnotation("Exception annotation") Exception{
        if (param != null) {
            throw new Exception("You are trapped");
        }

        return null;
    }

    private static void getAllAboveAnnotationValue() throws NoSuchFieldException, NoSuchMethodException, ClassNotFoundException {
        Class<TypeUseTargetDemo> clazz = TypeUseTargetDemo.class;

        // 类定义前
        TypeUseAnnotation beforeClass = clazz.getAnnotation(TypeUseAnnotation.class);
        System.out.println(beforeClass.value());

        // extends 后父类前的注解
        TypeUseAnnotation beforeSuperClass = clazz.getAnnotatedSuperclass().getAnnotation(TypeUseAnnotation.class);
        System.out.println(beforeSuperClass.value());

        // todo 父类形参列表中形参的注解
//        System.out.println(clazz.getTypeParameters().length);

        // todo 成员变量前的注解
        Field field = clazz.getDeclaredField("box");
        field.setAccessible(true);
//        System.out.println(field.getAnnotatedType().getType());

        // 数组类型的注解
        System.out.println(field.getAnnotatedType().getAnnotation(TypeUseAnnotation.class).value());

        // todo 参数列表中形参的注解
        Method method = clazz.getMethod("trapOperation", List.class);
        Parameter parameter = method.getParameters()[0];
        ParameterizedType type = (ParameterizedType) parameter.getParameterizedType();
        String actualType = type.getActualTypeArguments()[0].getTypeName();
        Class actualTypeClass = Class.forName(actualType);
        System.out.println(actualTypeClass.getAnnotations().length);
        System.out.println(((TypeUseAnnotation)actualTypeClass.getAnnotation(TypeUseAnnotation.class)).value());

        // 异常列表中的注解
        System.out.println(method.getAnnotatedExceptionTypes()[0].getAnnotation(TypeUseAnnotation.class).value());

        // 返回值中的注解
        System.out.println(method.getAnnotatedReturnType().getAnnotation(TypeUseAnnotation.class).value());
    }

    public static void main(String[] args) throws Exception {
        getAllAboveAnnotationValue();
    }


    /**
     * {@inheritDoc}
     *
     * @param index
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public @TypeUseAnnotation("") String get(int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
