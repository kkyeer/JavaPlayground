package lab.jdk;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: kkyeer
 * @Description: 试试ToArray的问题
 * @Date:Created in 上午11:09 2021/4/23
 * @Modified By:
 */
public class ToArrayBugExperiment {
    public static void main(String[] args) {
        Child[] childArray = {new Child(), new Child()};
//        使用List<Object>接响应
        List<Object> arr = Arrays.asList(childArray);
        try {
            // 此处报错1
            arr.set(0, new Object());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        正确的方法
        List<Child> childArrayList = Arrays.asList(childArray);
//        调用带参的toArray方法
        Object[] withParam = childArrayList.toArray(new Object[0]);
        System.out.println("带参方法的返回数组类型:"+withParam.getClass());
        withParam[0] = new Object();
//        调用无参的toArray方法
        Object[] withoutParam = childArrayList.toArray();
        System.out.println("无参方法的返回数组类型:"+withoutParam.getClass());
        try {
            // 此处报错2
            withoutParam[0] = new Object();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Child extends Parent{}

    private static class Parent{}
}

