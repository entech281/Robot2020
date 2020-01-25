
package frc.robot.controllers;

import frc.robot.RobotMap;
import frc.robot.subsystems.SparkMaxSettingsBuilder;
import frc.robot.subsystems.drive.FourSparkMaxWithSettings;

/**
 * Checks to see how we should configure motors, based on encoder readings
 * 
 *
 */
public class EncoderCheck {

	private int leftRearCounts = 0;
	private int rightRearCounts = 0;
	private int leftFrontCounts = 0;
	private int rightFrontCounts = 0;

	public EncoderCheck(int leftRearCounts, int leftFrontCounts, int rightFrontCounts, int rightRearCounts) {
		this.leftFrontCounts = leftFrontCounts;
		this.rightFrontCounts = rightFrontCounts;
		this.rightRearCounts = rightRearCounts;
		this.leftRearCounts = leftRearCounts;
	}
	
	public EncoderCheck( FourSparkMaxWithSettings sparks ) {
		this(   (int)sparks.getRearLeft().getEncoder().getPosition(),
				(int)sparks.getFrontLeft().getEncoder().getPosition(),
				(int)sparks.getFrontRight().getEncoder().getPosition(),
				(int)sparks.getRearRight().getEncoder().getPosition());
	}
	
	public void adjustTalonSettingsToWorkAroundBrokenEncoders(FourSparkMaxWithSettings originalSparks) {
		if (shouldDisableAll()) {
			originalSparks.disableAllSettings();
		} else {
			if (shouldLeftFrontFollowLeftRear()) {
				originalSparks.setFrontLeftSettings(
					SparkMaxSettingsBuilder.follow(originalSparks.getRearLeftSettings(), RobotMap.CAN.FRONT_RIGHT_MOTOR));
			}
			if (shouldLeftRearFollowLeftFront()) {
				originalSparks.setRearLeftSettings(
					SparkMaxSettingsBuilder.follow(originalSparks.getFrontLeftSettings(), RobotMap.CAN.FRONT_LEFT_MOTOR));
			}
			if (shouldRightFrontFollowRightRear()) {
				originalSparks.setFrontRightSettings(
					SparkMaxSettingsBuilder.follow(originalSparks.getRearRightSettings(), RobotMap.CAN.FRONT_RIGHT_MOTOR));
			}
			if (shouldRightRearFollowRightFront()) {
				originalSparks.setRearRightSettings(
					SparkMaxSettingsBuilder.follow(originalSparks.getFrontRightSettings(), RobotMap.CAN.FRONT_RIGHT_MOTOR));

			}
		}		
	}

	public boolean shouldLeftFrontFollowLeftRear() {
		return hasProblems() && canDrive() && isLeftRearOk() && (!isLeftFrontOk());
	}

	public boolean shouldLeftRearFollowLeftFront() {
		return hasProblems() && canDrive() && isLeftFrontOk() && (!isLeftRearOk());
	}

	public boolean shouldRightRearFollowRightFront() {
		return hasProblems() && canDrive() && isRightFrontOk() && (!isRightRearOk());
	}

	public boolean shouldRightFrontFollowRightRear() {
		return hasProblems() && canDrive() && isRightRearOk() && (!isRightFrontOk());
	}

	public boolean shouldDisableAll() {
		return this.hasProblems() && !this.canDrive();
	}

	public boolean isLeftRearOk() {
		return this.leftRearCounts > 0;
	}

	public boolean isLeftFrontOk() {
		return this.leftFrontCounts > 0;
	}

	public boolean isRightRearOk() {
		return this.rightRearCounts > 0;
	}

	public boolean isRightFrontOk() {
	    // return false;
		return this.rightFrontCounts > 0;
	}

	public boolean isLeftOk() {
		return isLeftFrontOk() || isLeftRearOk();
	}

	public boolean isRightOk() {
		return isRightFrontOk() || isRightRearOk();
	}

	public boolean canDrive() {
		return isLeftOk() && isRightOk();
	}

	public boolean hasLeftProblems() {
		return !isLeftOk();
	}

	public boolean hasRightProblems() {
		return !isRightOk();
	}

	public boolean hasProblems() {
		return !allOk();
	}

	public boolean allOk() {
		return isLeftRearOk() && isLeftFrontOk() && isRightRearOk() && isRightFrontOk();

	}
}