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
        // TODO: remove ConsoleDataLogger at the competition.
        return new CompositeLogger(new SmartDashboardLogger(name));
        // return new CompositeLogger(new SmartDashboardLogger(name), new ConsoleDataLogger(name, new WpilibTimeSource()));
    }

}
