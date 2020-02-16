package frc.robot.controllers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;



public abstract class BaseSparkController {

	private CANSparkMax spark = null;
	private SparkMaxSettings settings = null;

	public BaseSparkController(CANSparkMax spark, SparkMaxSettings settings) {
		this.spark = spark;
		this.settings = settings;
	}

	public void configure() {
		settings.configureSparkMax(spark);
	}

	public void resetPosition() {
		this.getSpark().getEncoder().setPosition(0);
	}

	/**
	 * A little tricky-- this is an Integer so that we can return Null if this talon
	 * is a follower. That's because a follower is configured because its encoder is
	 * broken!
	 * 
	 * @return
	 */
	public Integer getActualPosition() {
		if (this.settings.follow) {
			return null;
		} else {
			return (int)this.getSpark().getEncoder().getPosition();
		}

	}

	public void resetMode() {
		settings.setMode(spark);
	}

	public void resetMode(double settingValue) {
		settings.setMode(spark, settingValue);
	}

	public CANSparkMax getSpark() {
		return spark;
	}

	public SparkMaxSettings getSettings() {
		return settings;
	}
}