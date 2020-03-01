package frc.robot.controllers;

import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

public class SparkSpeedController extends BaseSparkController implements SpeedController{

    private double desiredSpeed = 0.0;
    private boolean enabled = true;
    public static int CAN_TIMEOUT_MILLIS = 1000;
    public static double SPEED_NOT_ENABLED=-1;
    public double getDesiredSpeed() {
        return desiredSpeed;
    }

    public double getActualSpeed() {
        if ( enabled ){
            return correctDirection( spark.getEncoder().getVelocity());
        }
        else{
            return SPEED_NOT_ENABLED;
        }
    }

    public void setDesiredSpeed(double desiredSpeed) {
        if (enabled){
            spark.getPIDController().setReference(correctDirection(this.desiredSpeed), ControlType.kVelocity);
        }
    }

    public SparkSpeedController(CANSparkMax spark, SparkMaxSettings settings, boolean reversed) {
        super(spark, settings,reversed);
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void init() {
        settings.configureSparkMax(spark);
        CANError err = spark.setCANTimeout(CAN_TIMEOUT_MILLIS);
        if ( err == err.kOk ){
            settings.configureSparkMax(spark);
            this.enabled = true;
        }
        else{
            this.enabled = false;
        }
    }


}
