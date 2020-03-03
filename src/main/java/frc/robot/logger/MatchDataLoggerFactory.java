package frc.robot.logger;

/**
 * Creates DataLoggers that will send to SmartDashboard and the console. Useful
 * for in a match.
 *
 * @author dcowden
 *
 */
public class MatchDataLoggerFactory extends DataLoggerFactory {

    @Override
    public DataLogger createDataLogger(String name) {
        return new CompositeLogger(new ShuffleBoardLogger(name));
    }

}
