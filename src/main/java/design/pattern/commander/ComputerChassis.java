package design.pattern.commander;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: kkyeer
 * @Description: 主机箱
 * @Date:Created in 17:14 2019/6/24
 * @Modified By:
 */
class ComputerChassis {
    List<Component> components = new ArrayList<>();

    void addComponent(Component component) {
        this.components.add(component);
    }
    void start(){
        for (Component component : components) {
            component.turnOn();
        }
    }

    void stop(){
        for (Component component : components) {
            component.turnOff();
        }
    }

    public static void main(String[] args) {
        CPU cpu = new CPU();
        CPUComponent cpuComponent = new CPUComponent(cpu);
        Fan fan = new Fan();
        FanComponent fanComponent = new FanComponent(fan);

        ComputerChassis computerChassis = new ComputerChassis();
        computerChassis.addComponent(cpuComponent);
        computerChassis.addComponent(fanComponent);
        computerChassis.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        computerChassis.stop();
    }
}
