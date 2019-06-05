package design.pattern.decorator;

/**
 * @Author: kkyeer
 * @Description: 老虎
 * @Date:Created in 20:47 2019/6/5
 * @Modified By:
 */
class Tiger implements Animal {
    private Animal wrappedAnimal;

    public Tiger(Animal wrappedAnimal) {
        this.wrappedAnimal = wrappedAnimal;
    }


    @Override
    public void eat() {
        this.wrappedAnimal.eat();
    }

    public void hunt(){
        System.out.println("a tiger is hunting");
    }
}
