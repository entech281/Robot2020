package frc.robot.controllers;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TalonPositionController extends BaseTalonController implements PositionController {

    private double desiredPosition = 0.0;

    @Override
    public double getDesiredPosition() {
        return desiredPosition;
    }

    /**
     * When you call this, the talon will be put in the right mode for control
     *
     * @param desiredPosition
     */
    @Override
    public void setDesiredPosition(double desiredPosition) {
        this.desiredPosition = desiredPosition;
        talon.set(settings.controlMode, correctDirection(desiredPosition));

    }

    public TalonPositionController(TalonSRX talon, TalonSettings settings, boolean reversed) {
        super(talon, settings,reversed);
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
