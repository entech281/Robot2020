package frc.robot.logger;

/**
 * This data logger sends everything to a list of other loggers
 *
 * @author dcowden
 *
 */
public class CompositeLogger extends DataLogger {

    private DataLogger[] loggers;

    public CompositeLogger(DataLogger... loggers) {
        super("");
        this.loggers = loggers;
    }

    @Override
    public void driverinfo(String key, Object value) {
        for (DataLogger l : loggers) {
            l.driverinfo(key, value);
        }
    }

    @Override
    public void driverinfo(String key, double value) {
        for (DataLogger l : loggers) {
            l.driverinfo(key, value);
        }
    }

    @Override
    public void driverinfo(String key, int value) {
        for (DataLogger l : loggers) {
            l.driverinfo(key, value);
        }
    }

    @Override
    public void driverinfo(String key, String value) {
        for (DataLogger l : loggers) {
            l.driverinfo(key, value);
        }
    }

    @Override
    public void driverinfo(String key, long value) {
        for (DataLogger l : loggers) {
            l.driverinfo(key, value);
        }
    }

    @Override
    public void driverinfo(String key, boolean value) {
        for (DataLogger l : loggers) {
            l.driverinfo(key, value);
        }
    }

    @Override
    public void warn(String message) {
        for (DataLogger l : loggers) {
            l.warn(message);
        }
    }

    @Override
    public void log(String key, Object value) {
        for (DataLogger l : loggers) {
            l.log(key, value);
        }
    }

    @Override
    public void log(String key, double value) {
        for (DataLogger l : loggers) {
            l.log(key, value);
        }
    }

    @Override
    public void log(String key, int value) {
        for (DataLogger l : loggers) {
            l.log(key, value);
        }
    }

    @Override
    public void log(String key, String value) {
        for (DataLogger l : loggers) {
            l.log(key, value);
        }
    }

    @Override
    public void log(String key, long value) {
        for (DataLogger l : loggers) {
            l.log(key, value);
        }
    }

    @Override
    public void log(String key, boolean value) {
        for (DataLogger l : loggers) {
            l.log(key, value);
        }
    }

}
