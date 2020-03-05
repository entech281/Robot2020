package frc.robot.controllers;

import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

public class SparkPositionController extends BaseSparkController implements PositionController{

    public SparkPositionController(CANSparkMax spark, SparkMaxSettings settings, boolean reversed) {
        super(spark, settings,reversed);
    }    
    
    private double desiredPosition = 0.0;
    public static final int CAN_TIMEOUT_MILLIS = 1000;
    public static final double POSITION_NOT_ENABLED=-1;
    private boolean enabled = true;

    public double getDesiredPosition() {
        return desiredPosition;
    }

    /**
     * When you call this, the talon will be put in the right mode for control
     *
     * @param desiredPosition
     */
    public void setDesiredPosition(double desiredPosition) {
        this.desiredPosition = desiredPosition;
        if(enabled)
            spark.getPIDController().setReference(correctDirection(desiredPosition), settings.getControlType());
    }

    @Override
    public double getActualPosition() {
        if(enabled)
            return correctDirection(spark.getEncoder().getPosition());
        return POSITION_NOT_ENABLED;
    }

    @Override
    public boolean isAtLowerLimit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAtUpperLimit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
 