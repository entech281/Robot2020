package frc.robot.controllers;

import com.revrobotics.CANSparkMax;

public abstract class BaseSparkController {

    private CANSparkMax spark = null;
    private SparkMaxSettings settings = null;

    public BaseSparkController(CANSparkMax spark, SparkMaxSettings settings) {
        this.spark = spark;
        this.settings = settings;
    }

    public void configure() {
        settings.configureSparkMax(spark);
    }

    public void resetPosition() {
        this.getSpark().getEncoder().setPosition(0);
    }

    /**
     * A little tricky-- this is an Double so that we can return Null if this
     * spark is a follower. That's because a follower is configured because its
     * encoder is broken!
     *
     * @return
     */
    public Double getActualPosition() {
        if (this.settings.follow) {
            return null;
        } else {
            return (double) this.getSpark().getEncoder().getPosition();
        }

    }

    public void resetMode() {
        settings.setMode(spark);
    }

    public void resetMode(double settingValue) {
        settings.setMode(spark, settingValue);
    }

    public CANSparkMax getSpark() {
        return spark;
    }

    public SparkMaxSettings getSettings() {
        return settings;
    }
}
