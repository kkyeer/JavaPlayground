package design.pattern.commander;

/**
 * @Author: kkyeer
 * @Description: 包裹CPU的零件
 * @Date:Created in 17:19 2019/6/24
 * @Modified By:
 */
class CPUComponent implements Component {
    private CPU cpu;

    public CPUComponent(CPU cpu) {
        this.cpu = cpu;
    }

    /**
     * 打开
     */
    @Override
    public void turnOn() {
        cpu.powerOn();
    }

    /**
     * 关闭
     */
    @Override
    public void turnOff() {
        cpu.powerOff();
    }
}
