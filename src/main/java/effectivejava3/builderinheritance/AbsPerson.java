package effectivejava3.builderinheritance;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/1 17:49
 */
public class AbsPerson {
    int age;
    String name;
    String id;

    public static  class AbsBuilder<T> {
        int  age;
        String name;
        String id;

        public AbsBuilder(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public T id(String id) {
            this.id = id;
            return (T)this;
        }

        /**
         * build方法
         * @author kkyeer
         * @date 2018/10/1 18:11
         * @return
         */
        AbsPerson build(){
            return new AbsPerson(this);
        }
    }

    AbsPerson(AbsBuilder<?> absBuilder) {
        this.age = absBuilder.age;
        this.name = absBuilder.name;
        this.id = absBuilder.id;
    }
}
