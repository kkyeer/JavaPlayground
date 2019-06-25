package design.pattern.facade;

/**
 * @Author: kkyeer
 * @Description: 简化的外观类
 * @Date:Created in 20:20 2019/6/25
 * @Modified By:
 */
class Simple {
    private Complex complex;
    void simpleInvoke(){
        complex.fun1();
        complex.fun2();
        complex.fun3();
    }
}
