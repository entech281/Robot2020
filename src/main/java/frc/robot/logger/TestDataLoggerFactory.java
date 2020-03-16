package frc.robot.logger;

/**
 * Creates Dataloggers for Testing.
 *
 * @author dcowden
 *
 */
public class TestDataLoggerFactory extends DataLoggerFactory {

    @Override
    public DataLogger createDataLogger(String name) {
        return new ConsoleDataLogger(name, new TestTimeSource());
    }

}
