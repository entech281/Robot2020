package frc.robot.controllers;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
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

        public PositionControlSettings usePositionControl();

        public SmartMotionControl.GainSettings useSmartPositionControl();
        
        public SmartMotionControl.GainSettings useSmartVelocityControl();

        public SpeedControlSettings useSpeedControl();
    }

    public interface PositionControlSettings {

        Finish withGains(double f, double p, double i, double d);
                
    }

    public interface SpeedControlSettings {

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

    public interface SmartMotionControl {

        public interface GainSettings {

            public StrategySettings withPositionGains(double f, double p, double i, double d);
        
            
        }

        public interface StrategySettings {

            public VelocityLimits useAccelerationStrategy(CANPIDController.AccelStrategy strategy);
        }

        public interface VelocityLimits {

            public AccelerationLimits withMaxVelocity(int maxVel);
        }

        public interface AccelerationLimits {

            public AllowedError withMaxAcceleration(int maxAccel);
        }

        public interface AllowedError {

            public Finish withClosedLoopError(int error);
        }
    }

    public interface Finish {

        SparkMaxSettings build();
    }

    public static class Builder
            implements SparkControlMode, PositionControlSettings, SpeedControlSettings,
            SafetySettings, SafetySettings.BrakeMode, DirectionSettings, MotorOutputLimits, MotorRamping,
            SmartMotionControl, SmartMotionControl.AccelerationLimits, SmartMotionControl.AllowedError, SmartMotionControl.GainSettings,
            SmartMotionControl.StrategySettings, SmartMotionControl.VelocityLimits, Finish {

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
        public Finish withGains(double f, double p, double i, double d) {
            settings.gains.f = f;
            settings.gains.p = p;
            settings.gains.i = i;
            settings.gains.d = d;
            return this;
        }

        @Override
        public PositionControlSettings usePositionControl() {
            settings.setControlType(ControlType.kPosition);
            return this;
        }

        @Override
        public SpeedControlSettings useSpeedControl() {
            settings.setControlType(ControlType.kDutyCycle);
            return this;
        }

        @Override
        public GainSettings useSmartPositionControl() {
            settings.setControlType(ControlType.kSmartMotion);
            return this;
        }
        
        

        @Override
        public VelocityLimits useAccelerationStrategy(AccelStrategy strategy) {
            settings.profile.accelStrategy = strategy;
            return this;
        }

        @Override
        public Finish withClosedLoopError(int error) {
            settings.profile.allowableClosedLoopError = error;
            return this;
        }

        @Override
        public StrategySettings withPositionGains(double f, double p, double i, double d) {
            settings.gains.f = f;
            settings.gains.p = p;
            settings.gains.i = i;
            settings.gains.d = d;
            return this;
        }

        @Override
        public AccelerationLimits withMaxVelocity(int maxVel) {
            settings.profile.cruiseVelocityRPM = maxVel;
            return this;
        }

        @Override
        public AllowedError withMaxAcceleration(int maxAccel) {
            settings.profile.maxAccel = maxAccel;
            return this;
        }

        @Override
        public GainSettings useSmartVelocityControl() {
            settings.setControlType(ControlType.kSmartVelocity);
            return this;
        }

    }
}
