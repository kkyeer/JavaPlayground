package design.pattern.template;

/**
 * @Author: kkyeer
 * @Description: 小说作家
 * @Date:Created in 19:26 2019/6/26
 * @Modified By:
 */
class NovelWriter extends AbstractWriter {

    NovelWriter(String name, int price) {
        super(name, price);
    }

    @Override
    protected void writeContent() {
        System.out.println("小说正文");
    }
}
