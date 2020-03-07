package frc.robot.controllers;

import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;

public class SparkSpeedController extends BaseSparkController implements SpeedController{

    private double desiredSpeed = 0.0;
    private boolean enabled = true;
    public static final int CAN_TIMEOUT_MILLIS = 1000;
    public static final double SPEED_NOT_ENABLED=-1;
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

    public boolean isSpeedWithinTolerance(double tolerance){
        return Math.abs(getActualSpeed() - getDesiredSpeed()) < tolerance;
    }

    public boolean isSpeedWithinTolerance(double tolerance, double desired){
        return Math.abs(getActualSpeed() - desired) < tolerance || getActualSpeed() > desired;
    }

    
    public void setDesiredSpeed(double desiredSpeed) {
        if (enabled){
            this.desiredSpeed = desiredSpeed;
            spark.getPIDController().setReference(correctDirection(this.desiredSpeed), settings.ctrlType, 0);
        }
    }

    public void stop(){
        spark.set(0);
    }
    
    public SparkSpeedController(CANSparkMax spark, SparkMaxSettings settings, boolean reversed) {
        super(spark, settings,reversed);
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
    
    @Override
    public void configure() {
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

