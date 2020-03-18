
package frc.robot.controllers.spark;

import frc.robot.controllers.spark.SparkMaxSettings;
import com.revrobotics.CANSparkMax;
import frc.robot.controllers.BaseController;

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