package frc.robot.controllers;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class SparkMaxSettings {

    /**
     *
     */
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
    private ControlType ctrlType = ControlType.kDutyCycle;
    public double demand = DEFAULT_DEMAND;
    public boolean follow = false;

    public CANPIDController pidController;

    public SparkMaxSettings copy() {
        SparkMaxSettings settingsNew = new SparkMaxSettings();
        settingsNew.setControlType(ctrlType);
        settingsNew.gains = this.gains;
        settingsNew.currentLimits = this.currentLimits;
        settingsNew.rampUp = this.rampUp;
        settingsNew.brakeMode = this.brakeMode;
        settingsNew.motorDirections = this.motorDirections;
        settingsNew.outputLimits = this.outputLimits;
        settingsNew.profile = this.profile;
        return settingsNew;
    }

    public void configureSparkMax(CANSparkMax spark) {
        spark.restoreFactoryDefaults();
        //Current Limits
        pidController = new CANPIDController(spark);

        spark.setCANTimeout(TIMEOUT_MS);
        spark.setSmartCurrentLimit(currentLimits.smartLimit);
        spark.setClosedLoopRampRate(rampUp.rampUpSecondsClosedLoop);
        spark.setOpenLoopRampRate(rampUp.rampUpSecondsOpenLoop);
        spark.setInverted(motorDirections.inverted);
        spark.setIdleMode(brakeMode);
        spark.getEncoder().setPositionConversionFactor(spark.getEncoder().getCountsPerRevolution());

        pidController.setOutputRange(outputLimits.minMotorOutput, outputLimits.maxMotorOutput);
        pidController.setFF(gains.f);
        pidController.setI(gains.i);
        pidController.setP(gains.p);
        pidController.setD(gains.d);
        pidController.setSmartMotionAccelStrategy(profile.trapezoStrategy, PID_SLOT);
        pidController.setSmartMotionAllowedClosedLoopError(profile.allowableClosedLoopError, PID_SLOT);

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
        public AccelStrategy trapezoStrategy = AccelStrategy.kTrapezoidal;
        public int allowableClosedLoopError = 20;
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
