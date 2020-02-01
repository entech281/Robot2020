package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import frc.robot.pose.RobotPose;

public class RobotMap {
    public interface CAN{
        public static final int FRONT_LEFT_MOTOR = 3;
        public static final int FRONT_RIGHT_MOTOR = 1;
        public static final int REAR_LEFT_MOTOR = 4;
        public static final int REAR_RIGHT_MOTOR = 2;
        public static final int SHOOTER_MOTOR = 7;
        public static final int INTAKE_MOTOR = 6;
        public static final int PCM_ID = 10;
    }

    public interface INVERSIONS {
        public static final boolean FRONT_LEFT_MOTOR = false;
        public static final boolean FRONT_RIGHT_MOTOR = false;
        public static final boolean BACK_LEFT_MOTOR = false;
        public static final boolean BACK_RIGHT_MOTOR = false;

        public static final boolean FRONT_LEFT_ENCODER = false;
        public static final boolean FRONT_RIGHT_ENCODER = false;
        public static final boolean BACK_LEFT_ENCODER = false;
        public static final boolean BACK_RIGHT_ENCODER = false;
    }

    public interface PNEUMATICS{
    }

    public interface AUTO_DRIVE_CONSTANTS {
        public static final double ksVolts = 0.147;
        public static final double kvVoltSecondsPerMeter = 0.0707;
        public static final double kaVoltSecondsSquaredPerMeter = 0.00971;
    
        public static final double kPDriveVel = 0.451;
        public static final double kTrackwidthMeters = DIMENSIONS.ROBOT_WIDTH * CONVERSIONS.INCHES_TO_METERS;
        public static final DifferentialDriveKinematics kDriveKinematics =
            new DifferentialDriveKinematics(kTrackwidthMeters);
		public static final double kMaxAccelerationMetersPerSecondSquared = 1;
		public static final double kRamseteB = 0;
		public static final double kMaxSpeedMetersPerSecond =   1;
		public static final double kRamseteZeta = 0;
    }

    public interface GAMEPAD{
        public static final int driverStick = 0;
    }

    public interface BUTTONS{
        public static final int INTAKE_BUTTON = 1;
    }

    public interface DIMENSIONS {
        // Must be in inches

        public static final double ROBOT_WIDTH = 27.5;
        public static final double ROBOT_LENGTH = 32.5;
        public static final RobotPose START_POSE = new RobotPose(0,0,0);
        public static final double DRIVE_GEAR_RATIO = 10.7;

        public static final double WHEEL_DIAMETER_INCHES = 6;
    }

    public interface CONVERSIONS {
        public static final double ENCODER_TICKS_PER_MOTOR_REVOLUTION = 4096;
        public static final double ENCODER_TICKS_PER_INCH = ENCODER_TICKS_PER_MOTOR_REVOLUTION
            * DIMENSIONS.DRIVE_GEAR_RATIO
            / ( Math.PI * DIMENSIONS.WHEEL_DIAMETER_INCHES);
        public static final double METERS_TO_INCHES = 39.3700787;
        public static final double INCHES_TO_METERS = 1/METERS_TO_INCHES;
        
    }
}