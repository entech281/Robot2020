package frc.robot.controllers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;

import frc.robot.subsystems.SparkMaxSettings;
import frc.robot.subsystems.TalonSettings;

public abstract class BaseSparkController {

	private CANSparkMax spark = null;
	private SparkMaxSettings settings = null;
	private boolean inverted = false;

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

	public void setInverted( boolean inverted){
		this.inverted = true;
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
			return (int)this.getSpark().getEncoder().getPosition() * (inverted ? -1 : 1 );
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