package frc.robot.controllers;

/**
 *
 * @author plaba
 */
public class TestSpeedController implements SpeedController{
    private boolean enabled;
    private boolean reversed;
    private double actualSpeed;
    private double desiredSpeed;

    @Override
    public double getActualSpeed() {
        return actualSpeed;
    }

    public void setActualSpeed(double actualSpeed) {
        this.actualSpeed = actualSpeed;
    }

    @Override
    public double getDesiredSpeed() {
        return desiredSpeed;
    }

    @Override
    public void setDesiredSpeed(double desiredSpeed) {
        this.desiredSpeed = desiredSpeed;
    }

    @Override
    public boolean isReversed() {
        return reversed;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    @Override
    public void configure() {
        
    }
    
}
