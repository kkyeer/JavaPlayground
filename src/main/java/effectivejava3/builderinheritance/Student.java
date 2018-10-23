package effectivejava3.builderinheritance;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/2 21:29
 */
public class Student extends AbsPerson {
    private int classNo;

    Student(AbsBuilder builder) {
        super(builder);
        this.classNo = builder.classNo;
    }

    public static class AbsBuilder extends AbsPerson.AbsBuilder<AbsBuilder> {
        int classNo;

        public AbsBuilder(int age, String name,int classNo) {
            super(age, name);
            this.classNo = classNo;
        }

        public AbsBuilder classNo(int classNo) {
            this.classNo = classNo;
            return this;
        }

        @Override
        Student build() {
            return new Student(this);
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
