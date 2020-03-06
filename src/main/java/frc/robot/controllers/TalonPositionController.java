package frc.robot.controllers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TalonPositionController extends BaseTalonController implements PositionController {

    private double desiredPosition = 0.0;

    @Override
    public double getDesiredPosition() {
        return this.desiredPosition;
    }

    /**
     * When you call this, the talon will be put in the right mode for control
     *
     * @param desiredPosition
     */
    @Override
    public void setDesiredPosition(double desiredPosition) {
        SmartDashboard.putNumber("Input to controller", desiredPosition);
        this.desiredPosition = desiredPosition;
        talon.set(settings.controlMode, correctDirection(desiredPosition));
    }

    public TalonPositionController(TalonSRX talon, TalonSettings settings, boolean reversed) {
        super(talon, settings,reversed);
    }

    @Override
    public void resetPosition() {
        talon.setSelectedSensorPosition(0, TalonSettings.PID_SLOT, TalonSettings.TIMEOUT_MS);
        PositionController.super.resetPosition(); 
    }

    
    
    @Override
    public double getActualPosition() {
        return correctDirection((double)talon.getSelectedSensorPosition());
    }

    @Override
    public boolean isAtLowerLimit() {
        if(settings.isLimitSwitchesEnabled()){
            return talon.isRevLimitSwitchClosed() == 1;
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAtUpperLimit() {
        if(settings.isLimitSwitchesEnabled()){
            return talon.isFwdLimitSwitchClosed()== 1;
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
