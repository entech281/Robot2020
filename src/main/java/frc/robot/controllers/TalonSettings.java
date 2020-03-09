package frc.robot.controllers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
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
public class TalonSettings {

    /**
     * This class doesnt use javabean conventions becasue its just a huge
     * structure, and because we use TalonBuilder to enforce all of the
     * requirements ( IE, get/set is not sufficient to enforce the semantics of
     * this very complex setup process for a Talon
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
    public boolean limitSwitches = false;

    public TalonSettings copy() {
        TalonSettings copySettings = new TalonSettings();

        copySettings.gains = this.gains;
        copySettings.currentLimits = this.currentLimits;
        copySettings.outputLimits = this.outputLimits;
        copySettings.rampUp = this.rampUp;
        copySettings.framePeriods = this.framePeriods;
        copySettings.motorDirections = this.motorDirections;
        copySettings.brakeMode = this.brakeMode;
        copySettings.feedbackDevice = this.feedbackDevice;
        copySettings.controlMode = this.controlMode;
        copySettings.demand = this.demand;
        copySettings.profile = this.profile;
        copySettings.limitSwitches = this.limitSwitches;
        return copySettings;
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
        if(limitSwitches){
            talon.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,
                    0);
            talon.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,
                    0);

            talon.overrideLimitSwitchesEnable(true);
        }
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
    
    public boolean isLimitSwitchesEnabled(){
        return this.limitSwitches;
    }
    
    public ControlMode getControlMode(){
        return this.controlMode;
    }

    public static class Gains {

        /**
         *
         */
        public double f = 0.0;
        public double p = 0.0;
        public double i = 0.0;
        public double d = 0.0;
    }

    public static class MotionProfile {

        public int cruiseVelocityEncoderClicksPerSecond = 3200;
        public int accelerationEncoderClicksPerSecond2 = 1;
        public int allowableClosedLoopError = 20;
    }

    public static class CurrentLimits {

        public int instantaneousPeak = 35;
        public int continuousPeak = 30;
        public int continuousPeakMilliseconds = 200;

    }

    public static class MotorOutputLimits {

        public double maxMotorOutputForward = 1.0;
        public double maxMotorOutputBackward = 1.0;
        public double minMotorOutputForward = -1.0;
        public double minMotorOutputBackward = -1.0;
    }

    public static class MotorRampUp {

        public double rampUpSecondsOpenLoop = 0.0;
        public double rampUpSecondsClosedLoop = 0.0;
        public double neutralDeadband = 0.001;
    }

    public static class FramePeriods {

        public int motionMagicMilliseconds = DEFAULT_FAST_FRAMERATE_MILLIS;
        public int pidMilliseconds = DEFAULT_FAST_FRAMERATE_MILLIS;
    }

    public static class MotorDirections {

        public boolean sensorPhase = false;
        public boolean inverted = false;
    }
}
