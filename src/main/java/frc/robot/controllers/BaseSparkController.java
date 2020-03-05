
package frc.robot.controllers;

import com.revrobotics.CANSparkMax;

public abstract class BaseSparkController extends BaseController{

    protected CANSparkMax spark = null;
    protected SparkMaxSettings settings = null;

    public BaseSparkController(CANSparkMax spark, SparkMaxSettings settings, boolean reversed) {
        super(reversed);
        this.spark = spark;
        this.settings = settings;
    }

    public void configure() {
        settings.configureSparkMax(spark);
    }

}