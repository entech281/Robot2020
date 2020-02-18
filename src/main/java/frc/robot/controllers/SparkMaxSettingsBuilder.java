package frc.robot.controllers;

import com.revrobotics.ControlType;
import com.revrobotics.SparkMax;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANSparkMax.IdleMode;

public class SparkMaxSettingsBuilder {

    public static SafetySettings defaults() {
        return new Builder(new SparkMaxSettings());
    }

    public static SafetySettings copyOf(SparkMaxSettings settings) {
        return new Builder(settings);
    }

    public static SparkMaxSettings copy(SparkMaxSettings settings) {
        return settings.copy();
    }

    public static SparkMaxSettings withDirectionSettings(SparkMaxSettings settings, boolean invertedDirections) {
        SparkMaxSettings s = settings.copy();
        s.motorDirections.inverted = invertedDirections;
        return s;
    }

    public static SparkMaxSettings inverted(SparkMaxSettings settings) {
        SparkMaxSettings s = settings.copy();
        s.motorDirections.inverted = !settings.motorDirections.inverted;
        return s;
    }

    public static SparkMaxSettings follow(SparkMaxSettings other, int otherSparkId) {
        SparkMaxSettings s = other.copy();
        s.follow = true;
        s.demand = otherSparkId;
        return s;
    }

    public static SparkMaxSettings disabledCopy(SparkMaxSettings other) {
        SparkMaxSettings s = other.copy();
        s.setControlType(ControlType.kDutyCycle);
        return s;
    }

    public interface SparkControlMode {

        public PositionControlSettings.GainSettings usePositionControl();

        public SpeedControlSettings useSpeedControl();

        public SpeedControlSettings.GainSettingsSpeed useSpeedControlWithPID();

    }

    public interface PositionControlSettings {

        public interface GainSettings {

            ProfileSettings withGains(double f, double p, double i, double d);
        }

        public interface ProfileSettings {

            ProfileSettings withMotionProfile(int cruiseVelocityRPM, AccelStrategy accelStrategy, int allowableError);
        }

        public interface Finish {

            public SparkMaxSettings build();
        }
    }

    public interface SpeedControlSettings {

        public interface GainSettingsSpeed {

            SpeedControlSettings withGainsSpeed(double f, double p, double i, double d);
        }

        public SparkMaxSettings build();
    }

    public interface SafetySettings {

        public interface BrakeMode {

            public DirectionSettings brakeInNeutral();

            public DirectionSettings coastInNeutral();
        }

        public BrakeMode withPrettySafeCurrentLimits();

        public BrakeMode withCurrentLimits(int smartLimit);
    }

    public interface DirectionSettings {

        public MotorOutputLimits defaultDirectionSettings();

        public MotorOutputLimits withDirections(boolean sensorPhase, boolean inverted);
    }

    public interface MotorOutputLimits {

        public MotorRamping noMotorOutputLimits();

        public MotorRamping limitMotorOutputs(double peak, double min);
    }

    public interface MotorRamping {

        public SparkControlMode noMotorStartupRamping();

        public SparkControlMode withMotorRampUpOnStart(double secondsToFullPower);
    }

    public static class Builder
            implements SparkControlMode, PositionControlSettings, PositionControlSettings.GainSettings,
            PositionControlSettings.ProfileSettings, PositionControlSettings.Finish, SpeedControlSettings,
            SafetySettings, SafetySettings.BrakeMode, DirectionSettings, MotorOutputLimits, MotorRamping, SpeedControlSettings.GainSettingsSpeed {

        private SparkMaxSettings settings = new SparkMaxSettings();

        private Builder(SparkMaxSettings settings) {
            this.settings = settings;
        }

        @Override
        public SparkControlMode noMotorStartupRamping() {
            settings.rampUp.rampUpSecondsClosedLoop = 0;
            settings.rampUp.rampUpSecondsOpenLoop = 0;
            return this;
        }

        @Override
        public SparkControlMode withMotorRampUpOnStart(double secondsToFullPower) {
            settings.rampUp.rampUpSecondsOpenLoop = secondsToFullPower;
            return this;
        }

        @Override
        public MotorRamping noMotorOutputLimits() {
            settings.outputLimits.maxMotorOutput = 1;
            settings.outputLimits.minMotorOutput = -1;
            return this;
        }

        @Override
        public MotorRamping limitMotorOutputs(double peak, double min) {
            settings.outputLimits.maxMotorOutput = peak;
            settings.outputLimits.minMotorOutput = min;
            return this;
        }

        @Override
        public MotorOutputLimits defaultDirectionSettings() {
            settings.motorDirections.inverted = false;
            settings.motorDirections.sensorPhase = false;
            return this;
        }

        @Override
        public MotorOutputLimits withDirections(boolean sensorPhase, boolean inverted) {
            settings.motorDirections.sensorPhase = sensorPhase;
            settings.motorDirections.inverted = inverted;
            return this;
        }

        @Override
        public DirectionSettings brakeInNeutral() {
            settings.brakeMode = IdleMode.kBrake;
            return this;
        }

        @Override
        public DirectionSettings coastInNeutral() {
            settings.brakeMode = IdleMode.kCoast;
            return this;
        }

        @Override
        public BrakeMode withPrettySafeCurrentLimits() {
            settings.currentLimits.smartLimit = 5;
            return this;
        }

        @Override
        public BrakeMode withCurrentLimits(int smartLimit) {
            settings.currentLimits.smartLimit = smartLimit;
            return this;
        }

        @Override
        public SparkMaxSettings build() {
            return settings;
        }

        @Override
        public ProfileSettings withMotionProfile(int cruiseVelocityRPM, AccelStrategy accelStrategy,
                int allowableError) {
            settings.profile.allowableClosedLoopError = allowableError;
            settings.profile.cruiseVelocityRPM = cruiseVelocityRPM;
            settings.profile.trapezoStrategy = accelStrategy;
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
        public SpeedControlSettings withGainsSpeed(double f, double p, double i, double d) {
            settings.gains.f = f;
            settings.gains.p = p;
            settings.gains.i = i;
            settings.gains.d = d;
            return this;
        }

        @Override
        public GainSettings usePositionControl() {
            settings.setControlType(ControlType.kSmartMotion);
            return this;
        }

        @Override
        public SpeedControlSettings useSpeedControl() {
            settings.setControlType(ControlType.kVelocity);
            return this;
        }

        @Override
        public GainSettingsSpeed useSpeedControlWithPID() {
            settings.setControlType(ControlType.kSmartVelocity);
            return this;
        }

    }
}
