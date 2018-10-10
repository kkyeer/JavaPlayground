package effectivejava3.builderinheritance;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/1 17:49
 */
public abstract class AbsPerson {
    int age;
    String name;
    String id;

    public abstract static  class Builder<T extends Builder<T>> {
        int  age;
        String name;
        String id;

        public Builder(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public T id(String id) {
            this.id = id;
            return self();
        }

        /**
         * build方法
         * @author kkyeer
         * @date 2018/10/1 18:11
         * @return
         */
        abstract AbsPerson build();

        protected abstract T self();
    }

    AbsPerson(Builder<?> builder) {
        this.age = builder.age;
        this.name = builder.name;
        this.id = builder.id;
    }
}
