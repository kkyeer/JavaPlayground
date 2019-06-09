package design.pattern.decorator;

/**
 * @Author: kkyeer
 * @Description: 猫科动物
 * @Date:Created in 20:45 2019/6/5
 * @Modified By:
 */
abstract class CatLike implements Animal {
    @Override
    public void eat() {
        System.out.println("Eat with front claws");
    }
}
