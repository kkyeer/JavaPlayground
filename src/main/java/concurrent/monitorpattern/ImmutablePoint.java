package concurrent.monitorpattern;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * @Author: kkyeer
 * @Description: 不可变的Pointer
 * @Date:Created in 10:59 2019/6/1
 * @Modified By:
 */
public class ImmutablePoint extends Point {
    private ImmutablePoint(){

    }
    ImmutablePoint(Point point) {
        super(point);
    }
    @Override
    public void setLocation(Point p) {
        throw new RuntimeException("could not change after constructed");
    }

    @Override
    public void setLocation(int x, int y) {
        throw new RuntimeException("could not change after constructed");
    }

    @Override
    public void setLocation(Point2D p) {
        throw new RuntimeException("could not change after constructed");
    }

    @Override
    public void setLocation(double x, double y) {
        throw new RuntimeException("could not change after constructed");
    }
}
