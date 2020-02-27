package frc.robot.logger;

/**
 * Base class for DataLoggers. Each logger has a name, and sends data somewhere
 * for logging and display later.
 *
 * @author dcowden
 *
 */
public abstract class DataLogger {

    public final static String SEPARATOR = ": ";
    private String name = "";

    public DataLogger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected String computePath(String key) {
        return getName() + SEPARATOR + key;
    }

    public abstract void warn(String message);

    public abstract void log(String key, Object value);

    public abstract void log(String key, double value);

    public abstract void log(String key, int value);

    public abstract void log(String key, String value);

    public abstract void log(String key, long value);

    public abstract void log(String key, boolean value);

    public abstract void driverinfo(String key, Object value);

    public abstract void driverinfo(String key, double value);

    public abstract void driverinfo(String key, int value);

    public abstract void driverinfo(String key, String value);

    public abstract void driverinfo(String key, long value);

    public abstract void driverinfo(String key, boolean value);

}
