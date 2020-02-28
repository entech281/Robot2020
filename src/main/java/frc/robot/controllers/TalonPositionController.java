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
        this.resetMode(desiredPosition);

    }

    public TalonPositionController(TalonSRX talon, TalonSettings settings) {
        super(talon, settings);
    }
}
