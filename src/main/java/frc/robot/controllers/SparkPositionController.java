package frc.robot.controllers;


import com.revrobotics.CANSparkMax;

import frc.robot.subsystems.SparkMaxSettings;

public class SparkPositionController extends BaseSparkController {

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
		this.resetMode(desiredPosition);

	}

	public SparkPositionController(CANSparkMax spark, SparkMaxSettings settings) {
		super(spark, settings);
	}
}
