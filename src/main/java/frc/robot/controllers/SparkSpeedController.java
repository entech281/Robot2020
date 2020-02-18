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

    public void setDesiredSpeed(double desiredSpeed) {
        this.getSettings().setMode(this.getSpark(), desiredSpeed);
    }

    public SparkSpeedController(CANSparkMax spark, SparkMaxSettings settings) {
        super(spark, settings);
    }
}
