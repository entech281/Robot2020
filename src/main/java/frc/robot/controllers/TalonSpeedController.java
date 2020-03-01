package frc.robot.controllers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TalonSpeedController extends BaseTalonController implements SpeedController {

    public TalonSpeedController(TalonSRX talon, TalonSettings settings, boolean reversed) {
        super(talon, settings,reversed);
    }
    private double desiredSpeed = 0.0;

    @Override
    public double getDesiredSpeed() {
        return desiredSpeed;
    }

    @Override
    public double getActualSpeed() {
        return correctDirection((double)talon.getSelectedSensorVelocity(TalonSettings.PID_SLOT));
    }

    @Override
    public void setDesiredSpeed(double desiredSpeed) {
        this.desiredSpeed = desiredSpeed;
        talon.set(ControlMode.Current, correctDirection(desiredSpeed));
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
