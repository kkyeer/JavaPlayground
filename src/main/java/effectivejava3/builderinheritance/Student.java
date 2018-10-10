package effectivejava3.builderinheritance;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/2 21:29
 */
public class Student extends AbsPerson {
    private int classNo;

    Student(Builder builder) {
        super(builder);
        this.classNo = builder.classNo;
    }

    public static class Builder extends AbsPerson.Builder<Builder> {
        int classNo;

        public Builder(int age, String name) {
            super(age, name);
        }

        public Builder classNo(int classNo) {
            this.classNo = classNo;
            return this;
        }

        @Override
        Student build() {
            return new Student(this);
        }


        @Override
        protected Builder self() {
            return this;
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "classNo=" + classNo +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
