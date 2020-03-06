package frc.robot.logger;

/**
 * Makes dataloggers. A factory is an object that makes other objects. In this
 * case, we want a factory so that we can let other objects create loggers
 * without knowing how to do it themselves.
 *
 * @author dcowden
 *
 */
public abstract class DataLoggerFactory {

    public abstract DataLogger createDataLogger(String name);

    private static DataLoggerFactory factory = new TestDataLoggerFactory();

    public static void setLoggerFactory(DataLoggerFactory factory) {
        DataLoggerFactory.factory = factory;
    }

    public static DataLoggerFactory getLoggerFactory() {
        return DataLoggerFactory.factory;
    }

    public static void configureForTesting() {
        setLoggerFactory(new TestDataLoggerFactory());
    }

    public static void configureForMatch() {
        setLoggerFactory(new MatchDataLoggerFactory());
    }
}
