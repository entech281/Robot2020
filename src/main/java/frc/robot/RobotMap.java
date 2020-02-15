package frc.robot;

import frc.robot.pose.RobotPose;

public class RobotMap{
    public static final int NAVX_PORT = 0;
	public static final double POSITION_TOLERANCE_INCHES = 0;
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
        public static final double MOTOR_REVOLUTIONS_PER_INCH = DRIVE_GEAR_RATIO
            / ( Math.PI * WHEEL_DIAMETER_INCHES);
    }

    public interface PID{
        public interface DRIVE{
            public static final double P = 1e-4;
            public static final double I = 0;
            public static final double D = 0;
            public static final double F = 0;
        }
    }

    public interface AUTONOMOUS{
        public static final int MAX_VELOCITY = 2000;
        public static final int MAX_ACCELLERATION = 1500;
        public static final int ACCEPTABLE_ERROR = 10;
    }
}