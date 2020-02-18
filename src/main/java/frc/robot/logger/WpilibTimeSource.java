package frc.robot.logger;

import edu.wpi.first.wpilibj.Timer;

/**
 * Keeps time based on the built-in wpilib Timer.
 *
 * @author dcowden
 *
 */
public class WpilibTimeSource implements TimeSource {

    protected double startTime = Timer.getFPGATimestamp();

    public void resetClock() {
        startTime = Timer.getFPGATimestamp();
    }

    public double getElapsedSeconds() {
        return getSystemTime() - startTime;
    }

    @Override
    public double getSystemTime() {
        return Timer.getFPGATimestamp();
    }

}
