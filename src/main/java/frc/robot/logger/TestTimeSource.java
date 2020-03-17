package frc.robot.logger;

/**
 * doesn't keep time but makes tests run
 *
 * @author plaba
 *
 */
public class TestTimeSource implements TimeSource {


    public void resetClock() {
        
    }

    public double getElapsedSeconds() {
        return 0;
    }

    @Override
    public double getSystemTime() {
        return 0;
    }

}
