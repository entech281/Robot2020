package frc.robot.logger;

/**
 * Keeps time for dataloggers, which print the time since the tests began. Also
 * allows re-setting the clock, and getting elapsed time, useful in unit
 * testing.
 *
 * @author dcowden
 *
 */
public interface TimeSource {

    double getSystemTime();

    void resetClock();

    double getElapsedSeconds();
}
