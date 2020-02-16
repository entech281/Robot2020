package frc.robot.controllers;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class TalonSpeedController extends BaseTalonController {

	private double desiredSpeed = 0.0;

	public double getDesiredSpeed() {
		return desiredSpeed;
	}

	public double getActualSpeed() {
		return this.getTalon().getSelectedSensorVelocity(TalonSettings.PID_SLOT);
	}

	public void setDesiredSpeed(double desiredSpeed) {
		this.desiredSpeed = desiredSpeed;
		this.resetMode(desiredSpeed);
	}

	public TalonSpeedController(TalonSRX talon, TalonSettings settings) {
		super(talon, settings);
	}

}