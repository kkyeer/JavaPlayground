package design.pattern.template;

/**
 * @Author: kkyeer
 * @Description: 抽象的作家类
 * @Date:Created in 21:06 2019/6/25
 * @Modified By:
 */
abstract class AbstractWriter {
    protected String name;
    private int price;


    AbstractWriter(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * 钩子函数，写作前
     */
    protected void beforeWrite(){

    }

    /**
     * 写开头
     */
    private void writeHeader(){
        System.out.println("Written by " + name);
    }

    /**
     * 写正文
     */
    abstract protected void writeContent();

    /**
     * 写结尾
     */
    private void writeFooter(){
        System.out.println("Price:" + price);
    }

    /**
     * 写作方法
     */
    void write(){
        beforeWrite();
        writeHeader();
        writeContent();
        writeFooter();
    }
}
