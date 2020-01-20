package frc.robot.subsystems;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * This class holds talon settings. Sometimes you need to re-program a Talon on
 * the fly when you're changing modes, so this allows you to keep a set of
 * configurations in memory and continually re-apply them. ( vs the lazy way of
 * just setting them in the talon and forgetting about it )
 * 
 * This also provides a great way to document the defaults
 * 
 * https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/MotionMagic/src/org/usfirst/frc/team217/robot/Robot.java
 * https://github.com/CrossTheRoadElec/Phoenix-Documentation/raw/master/Talon%20SRX%20Victor%20SPX%20-%20Software%20Reference%20Manual.pdf
 * 
 * @author dcowden
 *
 */
public class TalonSettings implements Serializable {

	/**
	 * This class doesnt use javabean conventions becasue its just a huge structure,
	 * and because we use TalonBuilder to enforce all of the requirements ( IE,
	 * get/set is not sufficient to enforce the semantics of this very complex setup
	 * process for a Talon
	 */
	public static final int TIMEOUT_MS = 10;
	public static final int PID_SLOT = 0;
	public static final int PROFILE_SLOT = 0;
	public static final int DEFAULT_FAST_FRAMERATE_MILLIS = 10;
	public static final double DEFAULT_DEMAND = 0;

	public MotionProfile profile = new MotionProfile();
	public Gains gains = new Gains();
	public CurrentLimits currentLimits = new CurrentLimits();
	public MotorOutputLimits outputLimits = new MotorOutputLimits();
	public MotorRampUp rampUp = new MotorRampUp();
	public FramePeriods framePeriods = new FramePeriods();
	public MotorDirections motorDirections = new MotorDirections();

	public NeutralMode brakeMode = NeutralMode.Brake;
	public FeedbackDevice feedbackDevice = FeedbackDevice.QuadEncoder;
	public ControlMode controlMode = ControlMode.Disabled;
	public double demand = DEFAULT_DEMAND;

	public TalonSettings copy() {
		return SerializationUtils.clone(this);
	}

	/**
	 * Copies the settings to the talon.
	 */
	public void configureTalon(TalonSRX talon) {

		// hard coded stuff that's not configurable
		talon.selectProfileSlot(PROFILE_SLOT, PID_SLOT);
		talon.setSelectedSensorPosition(0, PID_SLOT, TIMEOUT_MS);
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_SLOT, 0);

		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, this.framePeriods.pidMilliseconds,
				TIMEOUT_MS);
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, this.framePeriods.motionMagicMilliseconds,
				TIMEOUT_MS);

		talon.setSensorPhase(this.motorDirections.sensorPhase);
		talon.setInverted(this.motorDirections.inverted);

		talon.configNominalOutputForward(this.outputLimits.minMotorOutputForward, TIMEOUT_MS);
		talon.configNominalOutputReverse(this.outputLimits.minMotorOutputBackward, TIMEOUT_MS);
		talon.configPeakOutputForward(this.outputLimits.maxMotorOutputForward, TIMEOUT_MS);
		talon.configPeakOutputReverse(this.outputLimits.maxMotorOutputBackward, TIMEOUT_MS);

		talon.configPeakCurrentLimit(this.currentLimits.instantaneousPeak, TIMEOUT_MS);
		talon.configPeakCurrentDuration(this.currentLimits.continuousPeakMilliseconds, TIMEOUT_MS);
		talon.configContinuousCurrentLimit(this.currentLimits.continuousPeak, TIMEOUT_MS);
		talon.enableCurrentLimit(true);

		talon.setNeutralMode(this.brakeMode);
		talon.configSelectedFeedbackSensor(this.feedbackDevice, PID_SLOT, TIMEOUT_MS);

		talon.configClosedloopRamp(this.rampUp.rampUpSecondsClosedLoop, TIMEOUT_MS);
		talon.configOpenloopRamp(this.rampUp.rampUpSecondsOpenLoop, TIMEOUT_MS);
		talon.configNeutralDeadband(this.rampUp.neutralDeadband, TIMEOUT_MS);

		talon.config_kF(PID_SLOT, this.gains.f, TIMEOUT_MS);
		talon.config_kP(PID_SLOT, this.gains.p, TIMEOUT_MS);
		talon.config_kI(PID_SLOT, this.gains.i, TIMEOUT_MS);
		talon.config_kD(PID_SLOT, this.gains.d, TIMEOUT_MS);

		talon.configMotionCruiseVelocity(this.profile.cruiseVelocityEncoderClicksPerSecond, TIMEOUT_MS);
		talon.configMotionAcceleration(this.profile.accelerationEncoderClicksPerSecond2, TIMEOUT_MS);
		talon.configAllowableClosedloopError(PID_SLOT, this.profile.allowableClosedLoopError, TIMEOUT_MS);
		talon.set(this.controlMode, 0);
	}

	/**
	 * Sets just the mode. Typically used when you're swtiching back and forth
	 * between modes
	 * 
	 * @param talon
	 */
	public void setMode(TalonSRX talon, double settingValue) {
		talon.set(this.controlMode, settingValue);
	}

	public void setMode(TalonSRX talon) {
		talon.set(this.controlMode, this.demand);
	}

	public static class Gains implements Serializable {
		/**
		 * 
		 */
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
		private static final long serialVersionUID = 9098684713948573571L;
		public int instantaneousPeak = 35;
		public int continuousPeak = 30;
		public int continuousPeakMilliseconds = 200;

	}

	public static class MotorOutputLimits implements Serializable {
		private static final long serialVersionUID = -288486995864831923L;
		public double maxMotorOutputForward = 1.0;
		public double maxMotorOutputBackward = 1.0;
		public double minMotorOutputForward = -1.0;
		public double minMotorOutputBackward = -1.0;
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