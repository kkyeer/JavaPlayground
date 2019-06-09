package design.pattern.decorator;

/**
 * @Author: kkyeer
 * @Description: 家猫
 * @Date:Created in 20:52 2019/6/5
 * @Modified By:
 */
class HomeCat extends CatLike{
    private Animal wrappedAnimal;

    HomeCat(Animal wrappedAnimal) {
        this.wrappedAnimal = wrappedAnimal;
    }


    @Override
    public void eat() {
        this.wrappedAnimal.eat();
    }

    public void sleep(){
        System.out.println("a cat is sleeping");
    }
}
