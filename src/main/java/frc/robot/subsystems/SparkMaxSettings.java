package main.java.frc.robot.subsystems;

import java.io.Serializable;
import org.apache.commons.lang3.SerializationUtils;




public class SparkMaxSettings implements Serializable{
	
	public static final int TIMEOUT_MS = 10;
	public static final int PID_SLOT = 0;
	public static final int PROFILE_SLOT = 0;
	public static final int DEFAULT_FAST_FRAMERATE_MILLIS = 10;
	public static final double DEFAULT_DEMAND = 0;

	public CurrentLimits currentLimits = new CurrentLimits();
	public MotorRampUp rampUp = new MotorRampUp();
	public MotorDirections motorDirections = new MotorDirections();
	public MotorOutputLimits outputLimits = new MotorOutputLimits();
	public NeutralMode brakeMode = NeutralMode.Brake;
	public FeedbackDevice feedbackDevice = FeedbackDevice.QuadEncoder;

    public SparkMaxSettings copy(){
        return SerializationUtils.clone(this);
    }
    public void configureSparkMax(CANSparkMax spark){
		//Current Limits
		spark.setSmartCurrentLimit(currentLimits.smartLimit);
		spark.setClosedLoopRampRate(rampUp.rampUpSecondsClosedLoop);
		spark.setOpenLoopRampRate(rampUp.rampUpSecondsOpenLoop);
		spark.setInverted(motorDirections.inverted);
		spark.setOutputRange(outputLimits.minMotorOutput, outputLimits.maxMotorOutput);
		spark.setIdleMode(this.brakeMode);

    }

    	/**
	 * Sets just the mode. Typically used when you're swtiching back and forth
	 * between modes
	 * 
	 * @param spark
	 */
	public void setMode(CANSparkMax spark, double settingValue) {
			spark.setReference(settingValue, PID_SLOT);
	}

	public void setMode(CANSparkMax spark){
		
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
		public int cruiseVelocityEncoderClicksPerSecond = 3200;
		public int accelerationEncoderClicksPerSecond2 = 1;
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