package frc.robot.subsystems;

import java.io.Serializable;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.apache.commons.lang3.SerializationUtils;



public class SparkMaxSettings implements Serializable{
	
	public static final int TIMEOUT_MS = 10;
	public static final int PID_SLOT = 0;
	public static final int PROFILE_SLOT = 0;
	public static final int DEFAULT_FAST_FRAMERATE_MILLIS = 10;
	public static final double DEFAULT_DEMAND = 0;
	public Gains gains = new Gains();


	public CurrentLimits currentLimits = new CurrentLimits();
	public MotorRampUp rampUp = new MotorRampUp();
	public MotorDirections motorDirections = new MotorDirections();
	public MotorOutputLimits outputLimits = new MotorOutputLimits();
	public IdleMode brakeMode = IdleMode.kBrake;
	public MotionProfile profile = new MotionProfile();
	public ControlType ctrlType = ControlType.kDutyCycle;
	public double demand = DEFAULT_DEMAND;
	public boolean follow = false;

	public CANPIDController pidController;
	

    public SparkMaxSettings copy(){
		return SerializationUtils.clone(this);
	}
	
    public void configureSparkMax(CANSparkMax spark){
		//Current Limits
		pidController = new CANPIDController(spark);

		spark.setSmartCurrentLimit(currentLimits.smartLimit);
		spark.setClosedLoopRampRate(rampUp.rampUpSecondsClosedLoop);
		spark.setOpenLoopRampRate(rampUp.rampUpSecondsOpenLoop);
		spark.setInverted(motorDirections.inverted);
		spark.setIdleMode(brakeMode);

		
		pidController.setOutputRange(outputLimits.minMotorOutput, outputLimits.maxMotorOutput);
		pidController.setFF(gains.f);
		pidController.setI(gains.i);
		pidController.setP(gains.p);
		pidController.setD(gains.d);
		pidController.setSmartMotionAccelStrategy(profile.trapezoStrategy, PID_SLOT);
		pidController.setSmartMotionAllowedClosedLoopError(profile.allowableClosedLoopError, PID_SLOT);


		spark.burnFlash();
	}

	/**
	 * Sets just the mode. Typically used when you're swtiching back and forth
	 * between modes
	 * 
	 * @param spark
	 */
	public void setMode(CANSparkMax spark, double value) {
		CANSparkMax leader;
		if(this.follow){
			leader  = new CANSparkMax((int)value, MotorType.kBrushless);
			spark.follow(leader);
		} else {
			pidController = spark.getPIDController();
			pidController.setReference(value, this.ctrlType);
		}
	}

	public void setMode(CANSparkMax spark) {
		CANSparkMax leader;
		if(this.follow){
			leader  = new CANSparkMax((int)this.demand, MotorType.kBrushless);
			spark.follow(leader);
		}
		else {
			pidController = spark.getPIDController();
			pidController.setReference(this.demand, this.ctrlType);
		}
	}

	public static class Gains implements Serializable {
		private static final long serialVersionUID = -1615614551300215877L;
		public double f = 0.0;
		public double p = 0.0;
		public double i = 0.0;
		public double d = 0.0;
	}

	public static class MotionProfile implements Serializable {
		private static final long serialVersionUID = -9034830588710443069L;
		public int cruiseVelocityRPM = 3200;
		public AccelStrategy trapezoStrategy = AccelStrategy.kTrapezoidal;
		public int allowableClosedLoopError = 20;
	}

	public static class CurrentLimits implements Serializable {
		public int smartLimit = 80;
	}

	public static class MotorOutputLimits implements Serializable {
		private static final long serialVersionUID = -288486995864831923L;
		public double maxMotorOutput = 1.0;
		public double minMotorOutput = -1.0;	
	}

	public static class MotorRampUp implements Serializable {
		private static final long serialVersionUID = 7622395199499681872L;
		public double rampUpSecondsOpenLoop = 0.0;
		public double rampUpSecondsClosedLoop = 0.0;
		public double neutralDeadband = 0.001;
	}

	public static class FramePeriods implements Serializable {
		private static final long serialVersionUID = 2845750610037826988L;
		public int motionMagicMilliseconds = DEFAULT_FAST_FRAMERATE_MILLIS;
		public int pidMilliseconds = DEFAULT_FAST_FRAMERATE_MILLIS;
	}

	public static class MotorDirections implements Serializable {
		private static final long serialVersionUID = -331460067125013734L;
		public boolean sensorPhase = false;
		public boolean inverted = false;
	}
}