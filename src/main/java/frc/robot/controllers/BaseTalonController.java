package frc.robot.controllers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;


public abstract class BaseTalonController {

	private TalonSRX talon = null;
	private TalonSettings settings = null;

	public BaseTalonController(TalonSRX talon, TalonSettings settings) {
		this.talon = talon;
		this.settings = settings;
	}

	public void configure() {
		settings.configureTalon(talon);
	}

	public void resetPosition() {
		talon.setSelectedSensorPosition(0, TalonSettings.PID_SLOT, TalonSettings.TIMEOUT_MS);
	}

	/**
	 * A little tricky-- this is an Integer so that we can return Null if this talon
	 * is a follower. That's because a follower is configured because its encoder is
	 * broken!
	 * 
	 * @return
	 */
	public Integer getActualPosition() {
		if (talon.getControlMode().equals(ControlMode.Follower)) {
			return null;
		} else {
			return (int)this.getTalon().getSelectedSensorPosition(TalonSettings.PID_SLOT);
		}

	}

	public void resetMode() {
		settings.setMode(talon);
	}

	public void resetMode(double settingValue) {
		settings.setMode(talon, settingValue);
	}

	public TalonSRX getTalon() {
		return talon;
	}

	public TalonSettings getSettings() {
		return settings;
	}

}
