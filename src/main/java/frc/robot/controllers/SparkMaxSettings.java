package frc.robot.controllers;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class SparkMaxSettings {

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

    public SparkMaxSettings copy() {
        SparkMaxSettings settings = new SparkMaxSettings();
        settings.gains.d = gains.d;
        settings.gains.f = gains.f;
        settings.gains.i = gains.i;
        settings.gains.p = gains.p;

        settings.currentLimits.smartLimit = currentLimits.smartLimit;

        settings.outputLimits.maxMotorOutput = outputLimits.maxMotorOutput;
        settings.outputLimits.minMotorOutput = outputLimits.minMotorOutput;

        settings.rampUp.neutralDeadband = rampUp.neutralDeadband;
        settings.rampUp.rampUpSecondsClosedLoop = rampUp.rampUpSecondsClosedLoop;
        settings.rampUp.rampUpSecondsOpenLoop = rampUp.rampUpSecondsOpenLoop;

        settings.motorDirections.inverted = motorDirections.inverted;
        settings.motorDirections.sensorPhase = motorDirections.sensorPhase;

        settings.brakeMode = brakeMode;
        settings.profile.accelStrategy = profile.accelStrategy;
        settings.profile.allowableClosedLoopError = profile.allowableClosedLoopError;
        settings.profile.cruiseVelocityRPM = profile.cruiseVelocityRPM;
        settings.profile.maxAccel = profile.maxAccel;

        settings.ctrlType = ctrlType;
        settings.demand = demand;
        settings.follow = follow;
        return settings;
    }

    public void configureSparkMax(CANSparkMax spark) {
        //Current Limits
        spark.restoreFactoryDefaults();
        pidController = new CANPIDController(spark);

        spark.setCANTimeout(TIMEOUT_MS);
        spark.setSmartCurrentLimit(currentLimits.smartLimit);
        spark.setClosedLoopRampRate(rampUp.rampUpSecondsClosedLoop);
        spark.setOpenLoopRampRate(rampUp.rampUpSecondsOpenLoop);
        spark.setInverted(motorDirections.inverted);
        spark.setClosedLoopRampRate(rampUp.rampUpSecondsClosedLoop);
        spark.setIdleMode(brakeMode);

        pidController.setOutputRange(outputLimits.minMotorOutput, outputLimits.maxMotorOutput);
        pidController.setFF(gains.f, PID_SLOT);
        pidController.setI(gains.i, PID_SLOT);
        pidController.setP(gains.p, PID_SLOT);
        pidController.setD(gains.d, PID_SLOT);
        pidController.setIZone(0, PID_SLOT);
        pidController.setSmartMotionAccelStrategy(profile.accelStrategy, PID_SLOT);
        pidController.setSmartMotionAllowedClosedLoopError(profile.allowableClosedLoopError, PID_SLOT);
        pidController.setSmartMotionMaxAccel(profile.maxAccel, PID_SLOT);
        pidController.setSmartMotionMaxVelocity(profile.cruiseVelocityRPM, PID_SLOT);
        pidController.setSmartMotionMinOutputVelocity(-profile.cruiseVelocityRPM, PID_SLOT);

    }

    /**
     * Sets just the mode. Typically used when you're swtiching back and forth
     * between modes
     *
     * @param spark
     */
    public void setMode(CANSparkMax spark, double value) {
        CANSparkMax leader;
        if (this.follow) {
            leader = new CANSparkMax((int) value, MotorType.kBrushless);
            spark.follow(leader);
        } else {
            pidController = spark.getPIDController();
            pidController.setReference(value, this.ctrlType);
        }
    }

    
    public ControlType getControlType(){
        return this.ctrlType;
    }
    public void setControlType(ControlType ctrlType) {
        this.follow = false;
        this.ctrlType = ctrlType;
    }

    public void setMode(CANSparkMax spark) {
        CANSparkMax leader;
        if (this.follow) {
            leader = new CANSparkMax((int) this.demand, MotorType.kBrushless);
            spark.follow(leader);
        } else {
            pidController = spark.getPIDController();
            pidController.setReference(this.demand, this.ctrlType);
        }
    }

    public static class Gains {

        public double f = 0.0;
        public double p = 0.0;
        public double i = 0.0;
        public double d = 0.0;
    }

    public static class MotionProfile {

        public int cruiseVelocityRPM = 3200;
        public AccelStrategy accelStrategy = AccelStrategy.kTrapezoidal;
        public double allowableClosedLoopError = 20;
        public int maxAccel = 20;
    }

    public static class CurrentLimits {

        public int smartLimit = 80;
    }

    public static class MotorOutputLimits {

        public double maxMotorOutput = 1.0;
        public double minMotorOutput = -1.0;
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
