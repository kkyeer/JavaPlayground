package design.pattern.decorator;

/**
 * @Author: kkyeer
 * @Description: 测试用例
 * @Date:Created in 20:50 2019/6/5
 * @Modified By:
 */
class TestCase {
    public static void main(String[] args) {
        Animal animal = new CatLike();
        Tiger tiger = new Tiger(animal);
        tiger.eat();
        tiger.hunt();
        HomeCat homeCat = new HomeCat(animal);
        homeCat.eat();
        homeCat.sleep();
    }
}
