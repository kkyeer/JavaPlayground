package design.pattern.singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * @Author: kkyeer
 * @Description: 枚举类来实现单例模式
 * @Date:Created in 15:09 2019/6/24
 * @Modified By:
 */
enum LazyLoadWithEnum implements Singleton{
    INSTANCE;
    Object object;
    LazyLoadWithEnum(){
        try {
            // 耗时的初始化操作
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        object  = new Singleton() {
            @Override
            public int hashCode() {
                return new Random().nextInt(1000);
            }
        };
    }

    Singleton getInstance(){
        return INSTANCE;
    }
}
