package frc.robot.controllers;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

public class SparkSpeedController extends BaseSparkController {

    private double desiredSpeed = 0.0;

    public double getDesiredSpeed() {
        return desiredSpeed;
    }

    public double getActualSpeed() {
        return this.getSpark().getEncoder().getVelocity();
    }

    public boolean isSpeedWithinTolerance(double tolerance){
        return Math.abs(getActualSpeed() - getDesiredSpeed()) < tolerance;
    }
    
    public void setDesiredSpeed(double desiredSpeed) {
        this.getSpark().getPIDController().setReference(desiredSpeed, ControlType.kVelocity);
    }

    public SparkSpeedController(CANSparkMax spark, SparkMaxSettings settings) {
        super(spark, settings);
    }
}
 