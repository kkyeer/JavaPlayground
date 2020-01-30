package design.pattern.singleton;

/**
 * @Author: kkyeer
 * @Description: 枚举类来实现单例模式
 * @Date:Created in 15:09 2019/6/24
 * @Modified By:
 */
enum LazyLoadWithEnum implements Singleton{
    INSTANCE;
    Singleton getInstance(){
        return INSTANCE;
    }
}
