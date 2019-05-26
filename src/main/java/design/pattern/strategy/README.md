# 策略模式

## 说明

将可变的策略，抽象成接口，类内部使用setter方法或者其他方法（工厂模式、IOC）引入，保存一份引用，实际使用时，通过调用策略
的接口方法来使用

## 代码

完整代码参考 design.pattern.strategy，此包实现了一个简单的计算器，计算器的品牌和价格是通用属性，定义在父类中，
加法的实现作为可变部分，使用策略模式引入，子类可以增加自己的属性和方法，但不需要去管加法的实现部分，实际使用时，
按需set进相关实现
```java
abstract class BaseCalculator {
    /**
     * 加法算法
     */
    private AddArithmetic addArithmetic;

    /**
     * 使用setter引入加法算法
     *
     * @param addArithmetic 加法算法
     */
    public void setAddArithmetic(AddArithmetic addArithmetic) {
        this.addArithmetic = addArithmetic;
    }

    /**
     * 使用内部的加法算法来计算
     *
     * @param num1 数字1
     * @param num2 数字2
     * @return 和
     * @throws Exception 算法为空或者加和过程出错
     */
    long add(long num1, long num2) throws Exception {
        if (this.addArithmetic == null) {
            throw new NullPointerException("加法算法为空，请定义");
        }
        return this.addArithmetic.add(num1, num2);
    }
}

```
说明：加法的算法，抽象成一种策略，通过setter引入

## 测试用例
```java
    public static void main(String[] args){
        CheapCalculator casio = new CheapCalculator("Casio", 100, new BigDecimal("0.99"));
        System.out.println(casio);
        try {
            System.out.println(casio.add(2, 3));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AddArithmetic simpleAdd = new SimpleAddArithmetic();
        casio.setAddArithmetic(simpleAdd);
        try {
            System.out.println(casio.add(2, 3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
说明：组装一个廉价计算器，注意第一次调用add方法时，因为没有指定加法算法，是失败的
