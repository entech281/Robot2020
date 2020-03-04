package frc.robot;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import frc.robot.logger.*;

public class TestLogger {

    @Test
    public void testCompositeLogger() {

        FakeDataLogger logger1 = new FakeDataLogger("test1");
        FakeDataLogger logger2 = new FakeDataLogger("test2");
        CompositeLogger cdl = new CompositeLogger(logger1, logger2);

        cdl.log("foo", "bar");
        cdl.log("foo2", true);
        cdl.log("foo3", 0.0);
        cdl.log("foo4", (int) 0);
        cdl.log("foo5", new Object());
        cdl.warn("foo5");
        assertEquals(12, logger1.getCounter() + logger2.getCounter());
    }
}

class FakeDataLogger extends DataLogger {

    private int counter = 0;

    public FakeDataLogger(String name) {
        super(name);
    }

    @Override
    public void log(String key, Object value) {
        counter++;
    }

    @Override
    public void log(String key, double value) {
        counter++;
    }

    @Override
    public void log(String key, int value) {
        counter++;
    }

    @Override
    public void log(String key, String value) {
        counter++;
    }

    @Override
    public void log(String key, long value) {
        counter++;
    }

    @Override
    public void log(String key, boolean value) {
        counter++;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public void warn(String message) {
        counter++;

    }

    @Override
    public void driverinfo(String key, Object value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void driverinfo(String key, double value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void driverinfo(String key, int value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void driverinfo(String key, String value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void driverinfo(String key, long value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void driverinfo(String key, boolean value) {
        // TODO Auto-generated method stub

    }
}
