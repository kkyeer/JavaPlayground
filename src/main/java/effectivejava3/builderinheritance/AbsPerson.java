package effectivejava3.builderinheritance;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/1 17:49
 */
public abstract class AbsPerson {
    int age;
    String name;
    String id;

    AbsPerson(AbsBuilder absBuilder) {
        this.age = absBuilder.age;
        this.name = absBuilder.name;
        this.id = absBuilder.id;
    }

    protected abstract static class AbsBuilder<T,B extends AbsBuilder<T,B>> {
        int  age;
        String name;
        String id;

        public AbsBuilder(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @SuppressWarnings("unchecked")
        public B id(String id) {
            this.id = id;
            return self();
        }

        protected abstract B self();

        abstract T build();
    }
}
