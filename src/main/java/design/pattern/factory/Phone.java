package design.pattern.factory;

import java.util.StringJoiner;

/**
 * @Author: kkyeer
 * @Description: 手机实体类
 * @Date:Created in 10:00 2019/6/8
 * @Modified By:
 */
class Phone {
    private String serial;
    private String brand;
    private int price;

    public Phone(String brand, int price) {
        this.brand = brand;
        this.price = price;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Phone.class.getSimpleName() + "[", "]")
                .add("serial='" + serial + "'")
                .add("brand='" + brand + "'")
                .add("price=" + price)
                .toString();
    }
}
