package frc.robot.subsystems.drive;


import com.revrobotics.CANSparkMax;

import frc.robot.subsystems.SparkMaxSettings;
import frc.robot.subsystems.SparkMaxSettingsBuilder;

/**
 * The motors and associated settings for 4 talons
 * 
 *
 */
public class FourSparkMaxWithSettings {

	protected CANSparkMax frontLeft;
	protected CANSparkMax frontRight;
	protected CANSparkMax rearLeft;
	protected CANSparkMax rearRight;
	protected SparkMaxSettings frontLeftSettings;
	protected SparkMaxSettings frontRightSettings;
	protected SparkMaxSettings rearLeftSettings;
	protected SparkMaxSettings rearRightSettings;

	
	public FourSparkMaxWithSettings(CANSparkMax frontLeft, CANSparkMax rearLeft, CANSparkMax frontRight,CANSparkMax rearRight,
			SparkMaxSettings frontLeftSettings,SparkMaxSettings rearLeftSettings, SparkMaxSettings frontRightSettings,SparkMaxSettings rearRightSettings) {
	    
		this.frontLeft = frontLeft;
		this.frontRight = frontRight;
		this.rearLeft = rearLeft;
		this.rearRight = rearRight;
		this.frontLeftSettings = frontLeftSettings;
        this.rearLeftSettings = rearLeftSettings;
        this.frontRightSettings = frontRightSettings;
        this.rearRightSettings = rearRightSettings;
	}	
	private FourSparkMaxWithSettings() {
		
	}
	public FourSparkMaxWithSettings copy() {
		FourSparkMaxWithSettings fg = new FourSparkMaxWithSettings();
		fg.frontLeft = this.frontLeft;
		fg.frontRight = this.frontRight;
		fg.rearLeft = this.rearLeft;
		fg.rearRight = this.rearRight;
		fg.frontLeftSettings = this.frontLeftSettings.copy();
		fg.frontRightSettings = this.frontRightSettings.copy();
		fg.rearLeftSettings = this.rearLeftSettings.copy();
		fg.rearRightSettings = this.rearRightSettings.copy();
		return fg;
	}
	
	public void applySettings(SparkMaxSettings leftSettings, SparkMaxSettings rightSettings) {
		this.frontLeftSettings = leftSettings;
		this.rearLeftSettings = leftSettings;
		this.frontRightSettings = rightSettings;
		this.rearRightSettings = rightSettings;		
	}
	
	public void configureAll() {
		frontLeftSettings.configureSparkMax(frontLeft);
		frontRightSettings.configureSparkMax(frontRight);
		rearLeftSettings.configureSparkMax(rearLeft);
		rearRightSettings.configureSparkMax(rearRight);
	}

	public void disableAllSettings() {
		frontLeftSettings = SparkMaxSettingsBuilder.disabledCopy(frontLeftSettings);
		frontRightSettings = SparkMaxSettingsBuilder.disabledCopy(frontRightSettings);
		rearLeftSettings = SparkMaxSettingsBuilder.disabledCopy(rearLeftSettings);
		rearRightSettings = SparkMaxSettingsBuilder.disabledCopy(rearRightSettings);
	}

	public CANSparkMax getFrontLeft() {
		return frontLeft;
	}

	public CANSparkMax getFrontRight() {
		return frontRight;
	}

	public CANSparkMax getRearLeft() {
		return rearLeft;
	}

	public CANSparkMax getRearRight() {
		return rearRight;
	}

	public SparkMaxSettings getFrontLeftSettings() {
		return frontLeftSettings;
	}

	public SparkMaxSettings getFrontRightSettings() {
		return frontRightSettings;
	}

	public SparkMaxSettings getRearLeftSettings() {
		return rearLeftSettings;
	}

	public SparkMaxSettings getRearRightSettings() {
		return rearRightSettings;
	}

	public void setFrontLeftSettings(SparkMaxSettings frontLeftSettings) {
		this.frontLeftSettings = frontLeftSettings;
	}

	public void setFrontRightSettings(SparkMaxSettings frontRightSettings) {
		this.frontRightSettings = frontRightSettings;
	}

	public void setRearLeftSettings(SparkMaxSettings rearLeftSettings) {
		this.rearLeftSettings = rearLeftSettings;
	}

	public void setRearRightSettings(SparkMaxSettings rearRightSettings) {
		this.rearRightSettings = rearRightSettings;
	}

}