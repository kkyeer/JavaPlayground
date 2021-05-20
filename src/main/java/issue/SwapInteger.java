package issue;

import java.lang.reflect.Field;

/**
 * @Author: kkyeer
 * @Description: 数字交换
 * @Date:Created in 21:00 2018/12/18
 * @Modified By:
 */
class SwapInteger {

    public static void main(String[] args) {
        System.out.println(2>>>1);
        Integer a = 1;
        Integer b = 2;
        Integer c = 1;
        System.out.println("a=" + a + ",b=" + b);
        // 应该输出 a=1,b=2
        swap(a, b);
        System.out.println("a=" + a + ",b=" + b);
        // 应该输出 a=2,b=1
        System.out.println("c="+c);
        // 应该输出 c=1
    }

    private static void swap(Integer a,Integer b){
        if (a > 127 && b > 127) {
            Class<?> aClass = a.getClass();
            try {
                Field field = aClass.getDeclaredField("value");
                field.setAccessible(true);
                int temp = a;
                field.setInt(a,b);
                field.setInt(b,temp);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("......");
        }

    }
}
