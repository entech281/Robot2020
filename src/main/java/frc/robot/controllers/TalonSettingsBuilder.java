package frc.robot.controllers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * Sets up a CAN Talon. Aims to make the process easier to understand.
 *
 * We store a copy of all the settings so that it is possible to re-set the
 * values at any point we'd like Its annoying, but it makes the sytem much more
 * flexible later on if you're using mixed modes
 *
 * Based on documetnation Here:
 * https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/MotionMagic/src/org/usfirst/frc/team217/robot/Robot.java
 * https://github.com/CrossTheRoadElec/Phoenix-Documentation/raw/master/Talon%20SRX%20Victor%20SPX%20-%20Software%20Reference%20Manual.pdf
 *
 * @author dcowden
 *
 */
public class TalonSettingsBuilder {

    public static SafetySettings defaults() {
        return new Builder(new TalonSettings());
    }

    public static SafetySettings copyOf(TalonSettings settings) {
        return new Builder(settings);
    }

    public static TalonSettings copy(TalonSettings settings) {
        return settings.copy();
    }

    public static TalonSettings withDirectionSettings(TalonSettings settings, boolean invertedSensor, boolean invertedDirection) {
        TalonSettings s = settings.copy();
        s.motorDirections.inverted = invertedDirection;
        s.motorDirections.sensorPhase = invertedSensor;
        return s;
    }

    public static TalonSettings inverted(TalonSettings settings) {
        TalonSettings s = settings.copy();
        s.motorDirections.inverted = !settings.motorDirections.inverted;
        return s;
    }

    public static TalonSettings follow(TalonSettings other, int otherTalonId) {
        TalonSettings s = other.copy();
        s.controlMode = ControlMode.Follower;
        s.demand = otherTalonId;
        return s;
    }

    public static TalonSettings disabledCopy(TalonSettings other) {
        TalonSettings s = other.copy();
        s.controlMode = ControlMode.Disabled;
        return s;
    }

    /**
     * Walk through talon configuration step-by-step Allows reuiring things that
     * are reuired, and provides choices when they are relevant.
     *
     * @author dcowden
     *
     */
    public interface TalonControlMode {

        public PositionControlSettings.GainSettings usePositionControl();

        public SpeedControlSettings useSpeedControl();
    }

    // things you set when you are doing position control
    public interface PositionControlSettings {

        public interface GainSettings {

            ProfileSettings withGains(double f, double p, double i, double d);
        }

        public interface ProfileSettings {

            LimitSwitch withMotionProfile(int cruiseEncoderClicksPerSecond, int accelerationEncoderClicksPerSecond2, int allowableError);
        }
        
        public interface LimitSwitch{
            Finish enableLimitSwitch(boolean enable);
        }

        public interface Finish {

            public TalonSettings build();
        }
    }
    


    // things you do when you are setting up for speed control
    public interface SpeedControlSettings {

        public TalonSettings build();
    }

    // things you need to do no matter what, for safety reasons
    public interface SafetySettings {

        public interface BrakeMode {

            public DirectionSettings brakeInNeutral();

            public DirectionSettings coastInNeutral();
        }

        public BrakeMode withPrettySafeCurrentLimits();

        public BrakeMode withCurrentLimits(int maxInstantaneousAmps, int maxSustainedAmps, int sustainedMilliseconds);
    }

    // things you have to do so the motor goes the right way
    public interface DirectionSettings {

        public MotorOutputLimits defaultDirectionSettings();

        public MotorOutputLimits withDirections(boolean sensorPhase, boolean inverted);

    }

    // things you can do to limit how fast the motor goes
    public interface MotorOutputLimits {

        public MotorRamping noMotorOutputLimits();

        public MotorRamping limitMotorOutputs(double peakPercent, double minimumPercent);
    }

    // things you can do to control how the motor starts up
    public interface MotorRamping {

        public TalonControlMode noMotorStartupRamping();

        public TalonControlMode withMotorRampUpOnStart(double secondsToFullPower);
    }

    public static class Builder
            implements TalonControlMode, PositionControlSettings, PositionControlSettings.GainSettings,
            PositionControlSettings.ProfileSettings, PositionControlSettings.Finish, SpeedControlSettings,
            SafetySettings, SafetySettings.BrakeMode, DirectionSettings, MotorOutputLimits, MotorRamping, PositionControlSettings.LimitSwitch {

        private TalonSettings settings = new TalonSettings();

        private Builder(TalonSettings settings) {
            this.settings = settings;
        }

        @Override
        public TalonControlMode noMotorStartupRamping() {
            settings.rampUp.rampUpSecondsClosedLoop = 0;
            settings.rampUp.rampUpSecondsOpenLoop = 0;
            return this;
        }

        @Override
        public TalonControlMode withMotorRampUpOnStart(double secondsToFullPower) {
            settings.rampUp.rampUpSecondsOpenLoop = secondsToFullPower;
            return this;
        }

        @Override
        public MotorRamping noMotorOutputLimits() {
            settings.outputLimits.maxMotorOutputBackward = -1;
            settings.outputLimits.maxMotorOutputForward = 1;
            settings.outputLimits.minMotorOutputForward = 0;
            settings.outputLimits.minMotorOutputBackward = 0;
            return this;
        }

        @Override
        public MotorRamping limitMotorOutputs(double peakPercent, double minimumPercent) {
            settings.outputLimits.maxMotorOutputBackward = -peakPercent;
            settings.outputLimits.maxMotorOutputForward = peakPercent;
            settings.outputLimits.minMotorOutputForward = minimumPercent;
            settings.outputLimits.minMotorOutputBackward = -minimumPercent;
            return this;
        }

        @Override
        public ProfileSettings withGains(double f, double p, double i, double d) {
            settings.gains.f = f;
            settings.gains.p = p;
            settings.gains.i = i;
            settings.gains.d = d;
            return this;
        }

        @Override
        public PositionControlSettings.GainSettings usePositionControl() {
            settings.controlMode = ControlMode.MotionMagic;
            return this;
        }

        @Override
        public SpeedControlSettings useSpeedControl() {
            settings.controlMode = ControlMode.PercentOutput;
            return this;
        }

        @Override
        public BrakeMode withCurrentLimits(int maxInstantaneousAmps, int maxSustainedAmps, int sustainedMilliseconds) {
            settings.currentLimits.instantaneousPeak = maxInstantaneousAmps;
            settings.currentLimits.continuousPeak = maxSustainedAmps;
            settings.currentLimits.continuousPeakMilliseconds = sustainedMilliseconds;
            return this;
        }

        @Override
        public DirectionSettings brakeInNeutral() {
            settings.brakeMode = NeutralMode.Brake;
            return this;
        }

        @Override
        public DirectionSettings coastInNeutral() {
            settings.brakeMode = NeutralMode.Coast;
            return this;
        }

        @Override
        public LimitSwitch withMotionProfile(int cruiseEncoderClicksPerSecond, int accelerationEncoderClicksPerSecond2, int allowableError) {
            settings.profile.accelerationEncoderClicksPerSecond2 = accelerationEncoderClicksPerSecond2;
            settings.profile.cruiseVelocityEncoderClicksPerSecond = cruiseEncoderClicksPerSecond;
            settings.profile.allowableClosedLoopError = allowableError;
            return this;
        }

        @Override
        public MotorOutputLimits defaultDirectionSettings() {
            settings.motorDirections.inverted = false;
            settings.motorDirections.sensorPhase = false;
            return this;
        }

        @Override
        public TalonSettings build() {
            return settings;
        }

        @Override
        public MotorOutputLimits withDirections(boolean sensorPhase, boolean inverted) {
            settings.motorDirections.sensorPhase = sensorPhase;
            settings.motorDirections.inverted = inverted;
            return this;
        }

        @Override
        public BrakeMode withPrettySafeCurrentLimits() {
            settings.currentLimits.instantaneousPeak = 5;
            settings.currentLimits.continuousPeak = 3;
            settings.currentLimits.continuousPeakMilliseconds = 200;
            return this;
        }

        @Override
        public Finish enableLimitSwitch(boolean enable) {
            if(enable){
                settings.limitSwitches = true;
            }
            return this;
        }

    }
}
