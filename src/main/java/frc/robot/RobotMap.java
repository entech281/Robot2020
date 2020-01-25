package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import frc.robot.pose.RobotPose;

public class RobotMap {
    public static final int NAVX_PORT = 0;
    public interface CAN{
        public static final int FRONT_LEFT_MOTOR = 3;
        public static final int FRONT_RIGHT_MOTOR = 1;
        public static final int REAR_LEFT_MOTOR = 4;
        public static final int REAR_RIGHT_MOTOR = 2;
        public static final int SHOOTER_MOTOR = 7;
        public static final int INTAKE_MOTOR = 8;
        public static final int PCM_ID = 10;
    }

    public interface PNEUMATICS{
    }

    public interface AUTO_DRIVE_CONSTANTS {
        //TODO DO NOT RUN WITH THESE CONSTANTS!!! find real constants using charecterization.
        public static final double ksVolts = 0.22;
        public static final double kvVoltSecondsPerMeter = 1.98;
        public static final double kaVoltSecondsSquaredPerMeter = 0.2;
    
        // Example value only - as above, this must be tuned for your drive!
        public static final double kPDriveVel = 8.5;
        public static final double kTrackwidthMeters = 0.69; //TODO get actual width between wheels
        public static final DifferentialDriveKinematics kDriveKinematics =
            new DifferentialDriveKinematics(kTrackwidthMeters);
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
        public static final RobotPose START_POSE = new RobotPose(0,0,90);
        public static final double DRIVE_GEAR_RATIO = 10.7;
        public static final double ENCODER_TICKS_PER_MOTOR_REVOLUTION = 4096;
        public static final double WHEEL_DIAMETER_INCHES = 6;
        public static final double ENCODER_TICKS_PER_INCH = ENCODER_TICKS_PER_MOTOR_REVOLUTION
            * DRIVE_GEAR_RATIO
            / ( Math.PI * WHEEL_DIAMETER_INCHES);
    }
}