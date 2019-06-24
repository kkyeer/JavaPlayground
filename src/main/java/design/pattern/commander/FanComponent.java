package design.pattern.commander;

/**
 * @Author: kkyeer
 * @Description: 风扇零件包裹
 * @Date:Created in 17:20 2019/6/24
 * @Modified By:
 */
class FanComponent implements Component {
    private Fan fan;

    public FanComponent(Fan fan) {
        this.fan = fan;
    }

    /**
     * 打开
     */
    @Override
    public void turnOn() {
        fan.turnOn();
    }

    /**
     * 关闭
     */
    @Override
    public void turnOff() {
        fan.turnOff();
    }
}
