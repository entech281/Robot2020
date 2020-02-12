package frc.robot;

import frc.robot.pose.RobotPose;

public class RobotMap{
    public static final int NAVX_PORT = 0;
    public interface CAN{
        public static final int FRONT_LEFT_MOTOR = 3;
        public static final int FRONT_RIGHT_MOTOR = 1;
        public static final int REAR_LEFT_MOTOR = 4;
        public static final int REAR_RIGHT_MOTOR = 2;
        public static final int SHOOTER_MOTOR = 7;
        public static final int INTAKE_MOTOR = 6;
        public static final int PCM_ID = 10;
    }

    public interface PNEUMATICS{
        public static final int attachSolenoid = 0;
        public static final int engageWinchSolenoid = 1;
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