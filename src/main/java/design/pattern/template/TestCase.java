package design.pattern.template;

/**
 * @Author: kkyeer
 * @Description: 试用一下
 * @Date:Created in 19:28 2019/6/26
 * @Modified By:
 */
class TestCase {
    public static void main(String[] args) {
        AbstractWriter writer = new BookWriter("Book Writer",99);
        writer.write();

        AbstractWriter writer2 = new NovelWriter("Novel Writer",23);
        writer2.write();
    }
}
