package frc.robot.logger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A datalogger that sends data to the smartDashboard. Dont' create them
 * directly, use MatchDataLoggerFactory.
 *
 * @author dcowden
 *
 */
public class SmartDashboardLogger extends DataLogger {

    public SmartDashboardLogger(String name) {
        super(name);
    }

    @Override
    public void driverinfo(String key, Object value) {
        SmartDashboard.putString(computePath(key), "" + value);
    }

    @Override
    public void driverinfo(String key, double value) {
        SmartDashboard.putNumber(computePath(key), value);
    }

    @Override
    public void driverinfo(String key, int value) {
        SmartDashboard.putNumber(computePath(key), value);
    }

    @Override
    public void driverinfo(String key, String value) {
        SmartDashboard.putString(computePath(key), value);
    }

    @Override
    public void driverinfo(String key, long value) {
        SmartDashboard.putNumber(computePath(key), value);
    }

    @Override
    public void driverinfo(String key, boolean value) {
        SmartDashboard.putBoolean(computePath(key), value);
    }

    @Override
    public void warn(String message) {
        DriverStation.reportWarning(message, true);
    }

    @Override
    public void log(String key, Object value) {
        SmartDashboard.putString(computePath(key), "" + value);
    }

    @Override
    public void log(String key, double value) {
        SmartDashboard.putNumber(computePath(key), value);
    }

    @Override
    public void log(String key, int value) {
        SmartDashboard.putNumber(computePath(key), value);
    }

    @Override
    public void log(String key, String value) {
        SmartDashboard.putString(computePath(key), value);
    }

    @Override
    public void log(String key, long value) {
        SmartDashboard.putNumber(computePath(key), value);
    }

    @Override
    public void log(String key, boolean value) {
        SmartDashboard.putBoolean(computePath(key), value);
    }

}
