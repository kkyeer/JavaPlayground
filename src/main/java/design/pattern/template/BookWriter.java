package design.pattern.template;

/**
 * @Author: kkyeer
 * @Description: 课本作家
 * @Date:Created in 19:28 2019/6/26
 * @Modified By:
 */
class BookWriter extends AbstractWriter {
    BookWriter(String name, int price) {
        super(name, price);
    }

    @Override
    protected void writeContent() {
        System.out.println("知识点");
    }
}
