package frc.robot;

import frc.robot.pose.*;

public class RobotConstants {

    public interface CAN {

        public static final int FRONT_LEFT_MOTOR = 3;
        public static final int FRONT_RIGHT_MOTOR = 1;
        public static final int REAR_LEFT_MOTOR = 4;
        public static final int REAR_RIGHT_MOTOR = 2;
        public static final int SHOOTER_MOTOR = 7;
        public static final int HOOD_MOTOR = 5;
        public static final int INTAKE_MOTOR = 6;
        public static final int ELEVATOR_MOTOR = 8;
        public static final int PCM_ID = 10;
        public static final int INTAKE_SOL_1 = 11;
        public static final int INTAKE_SOL_2 = 12;
    }

    public interface PNEUMATICS {

        public static final int ATTACH_SOLENOID = 0;
        public static final int ENGAGE_WINCH = 1;
    }

    public interface GAMEPAD {

        public static final int DRIVER_JOYSTICK = 0;
        public static final int OPERATOR_PANEL = 1;
    }

    public interface BUTTONS {
        public static final int TURN_SHOOTER_ON = 8;
        public static final int ENABLE_AUTO_HOOD = 9;
        public static final int FIRE = 11;
        public static final int FIRE_JOYSTICK = 1;
        public static final int DEPLOY_INTAKE = 13;
        public static final int HOOD_FORWARD_ADJUST = 3;
        public static final int HOOD_BACKWARD_ADJUST = 5;
        public static final int SELECT_PRESET_1 = 10;
        public static final int SELECT_PRESET_2 = 7;
        public static final int SNAP_TO_TARGET = 2;
        
    }

    public interface ROBOT_DEFAULTS {

        public static final RobotPosition START_POSITION = new RobotPosition(0, 0, 0);
        public static final RobotPose START_POSE = new RobotPose(START_POSITION, VISION.DEFAULT_VISION_DATA);

        public interface VISION {

            public static final VisionData DEFAULT_VISION_DATA = new VisionData(false, -1, -1, -1);
            public static final int FRAME_WIDTH = 160;
            public static final int FRAME_HEIGHT = 120;
        }
    }

    public interface DIMENSIONS {
        // Must be in inches

        public static final double ROBOT_WIDTH = 23.5;
        public static final double ROBOT_LENGTH = 25;
        public static final double DRIVE_GEAR_RATIO = 10.7;
        public static final double WHEEL_DIAMETER_INCHES = 6;
        public static final double MOTOR_REVOLUTIONS_PER_INCH = (Math.PI * WHEEL_DIAMETER_INCHES)
                / DRIVE_GEAR_RATIO;
    }

    public interface PID{
        public interface AUTO_STRAIGHT{
            public static final double P = 1e-4;
            public static final double I = 2e-7;
            public static final double D = 0;
            public static final double F = 0;
        }
        
        public interface AUTO_TURN{
            public static final double P = 3e-2;
            public static final double I = 4e-7;//2e-5;
            public static final double D = 0;
            public static final double F = 0;
        }
        
        public interface TARGET_LOCK{
            public static final double P = 6e-2;
            public static final double I = 0;//2e-5;
            public static final double D = 0;
            public static final double F = 0;
            
        }
    }

    public interface AUTONOMOUS {

        public static final int MAX_VELOCITY = 5000;//7500
        public static final int MAX_ACCELLERATION = 5000;//30000
        public static final int ACCEPTABLE_ERROR = 0;
        public static final int POSITION_TOLERANCE_INCHES = 2;
    }

    public interface AVAILABILITY {

        public static final boolean CLIMBER = false;
        public static final boolean COLOR_SENSOR = false;
        public static final boolean DRIVE = true;
        public static final boolean ELEVATOR = true;
        public static final boolean INTAKE = true;
        public static final boolean SHOOTER_MOTOR_MOUNTED = true;
        public static final boolean HOOD_MOTOR_MOUNTED = true;
        public static final boolean PNEUMATICS_MOUNTED = false;
        public static final boolean SENSOR_MOUNTED = false;
    }

    public interface MOTORCONTROLLER_VALUES {

        public interface SHOOTER_MOTOR {

            public static final double SHOOTER_PID_P = 10;
            public static final double SHOOTER_PID_I = 4e-4;
            public static final double SHOOTER_PID_D = 0;
            public static final double SHOOTER_PID_F = 0.000015;
            public static final double SHOOTER_MAXOUTPUT = 1;
            public static final double SHOOTER_MINOUTPUT = -1;
            public static final int CURRENT_LIMIT = 35;
            public static final double SHOOTER_MOTOR_RAMPUP = 0.5;
            public static final int SHOOTER_MAX_ACCEL = 100;
            public static final int SHOOTER_TOLERANCE = 5;
            public static final int SHOOTER_MAX_RPM = 6000;
        }

        public interface HOOD_MOTOR {

            public int HOOD_CRUISE_VELOCITY = 1000;
            public int HOOD_ACCELERATION = 20;
            public int ALLOWABLE_ERROR = 5;
            public double ENCODER_CLICKS_PER_HOOD_MOTOR_REVOLUTION = 2100;
            public final double HOOD_PID_F = 4;
            public final double HOOD_PID_P = 2.56 * 2;
            public final double HOOD_PID_I = 0;
            public final double HOOD_PID_D = 0;
            public final double HOOD_GEAR_RATIO = 4;

        }
    }
    
    public interface SHOOT_PRESETS{
        public TargetLocation PRESET_1 = new TargetLocation(5.5, 0);
        public TargetLocation PRESET_2 = new TargetLocation(290, 0);
    }
}
