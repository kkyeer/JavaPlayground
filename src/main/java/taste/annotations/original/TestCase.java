package taste.annotations.original;

import javax.annotation.Generated;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author: kkyeer
 * @Description: 试用一下一些不常用的注解
 * @Date:Created in 22:44 2019/4/11
 * @Modified By:
 */
@Generated(value = "com.kkyeer.taste", date = "2019-01-04T12:00:00")
class TestCase {
    @PreDestroy
    public void test1(){
        System.out.println("before destroy");

    }

    @PostConstruct
    public void test2(){
        System.out.println("after construct");
    }

    public static void main(String[] args) {
        new TestCase();
    }
}
