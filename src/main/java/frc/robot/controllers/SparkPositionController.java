package frc.robot.controllers;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

public class SparkPositionController extends BaseSparkController implements PositionController{

    public SparkPositionController(CANSparkMax spark, SparkMaxSettings settings, boolean reversed) {
        super(spark, settings,reversed);
    }    
    
    private double desiredPosition = 0.0;

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
        spark.getPIDController().setReference(correctDirection(desiredPosition), ControlType.kCurrent);
    }

    @Override
    public double getActualPosition() {
        return correctDirection(spark.getEncoder().getPosition());
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
}
