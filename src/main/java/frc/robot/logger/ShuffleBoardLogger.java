package frc.robot.logger;

public class ShuffleBoardLogger extends SmartDashboardLogger{
    private static final String DRIVER_PREFIX = "driver_";
    public ShuffleBoardLogger(String name) {
        super(name);
    }

    @Override
    public void driverinfo(String key, boolean value) {
        super.driverinfo(key, value);
        super.driverinfo(key+DRIVER_PREFIX, value);
    }
    @Override
    public void driverinfo(String key, Object value) {
        super.driverinfo(key, value);       
        super.driverinfo(key+DRIVER_PREFIX, value);
    }
    @Override
    public void driverinfo(String key, String value) {
        super.driverinfo(key, value);       
        super.driverinfo(key+DRIVER_PREFIX, value);
    }
    @Override
    public void driverinfo(String key, double  value) {
        super.driverinfo(key, value);       
        super.driverinfo(key+DRIVER_PREFIX, value);
    }
    @Override
    public void driverinfo(String key, int value) {
        super.driverinfo(key, value);       
        super.driverinfo(key+DRIVER_PREFIX, value);
    }
    @Override
    public void driverinfo(String key, long value) {
        super.driverinfo(key, value);       
        super.driverinfo(key+DRIVER_PREFIX, value);
    }

    
}