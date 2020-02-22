package frc.robot;

import frc.robot.pose.*;

public class RobotConstants {
    
    public interface CAN {
        
        public static final int FRONT_LEFT_MOTOR = 3;
        public static final int FRONT_RIGHT_MOTOR = 1;
        public static final int REAR_LEFT_MOTOR = 4;
        public static final int REAR_RIGHT_MOTOR = 2;
        public static final int SHOOTER_MOTOR = 7;
        public static final int INTAKE_MOTOR = 6;
        public static final int PCM_ID = 10;
    }
    
    public interface PNEUMATICS {
        
        public static final int ATTACH_SOLENOID = 0;
        public static final int ENGAGE_WINCH = 1;
    }
    
    public interface GAMEPAD {
        
        public static final int DRIVER_JOYSTICK = 0;
    }
    
    public interface BUTTONS {
        
        public static final int INTAKE_BUTTON = 1;
        public static final int SHOOT_BUTTON = 2;
        public static final int RESET_BUTTON = 10;
    }

    public interface ROBOT_DEFAULTS{
        public static final RobotPosition START_POSITION = new RobotPosition(0, 0, 0);
        public static final RobotPose START_POSE = new RobotPose(START_POSITION, VISION.DEFAULT_VISION_DATA);
        public interface VISION{
            public static final VisionData DEFAULT_VISION_DATA = new VisionData(false, -1, -1, -1);
            public static final int FRAME_WIDTH = 160;
            public static final int FRAME_HEIGHT = 120;
        } 
    }
    
    public interface DIMENSIONS {
        // Must be in inches
        
        public static final double ROBOT_WIDTH = 23;
        public static final double ROBOT_LENGTH = 32.5;
        public static final double DRIVE_GEAR_RATIO = 10.7;
        public static final double WHEEL_DIAMETER_INCHES = 6;
        public static final double MOTOR_REVOLUTIONS_PER_INCH = ( Math.PI * WHEEL_DIAMETER_INCHES)
         / DRIVE_GEAR_RATIO;
    }
    
    public interface PID{
        public interface DRIVE{
            public static final double P = 5e-4;
            public static final double I = 0;//2e-5;
            public static final double D = 0;
            public static final double F = 0;
        }
    }
    
    public interface AUTONOMOUS{
        public static final int MAX_VELOCITY = 3000;
        public static final int MAX_ACCELLERATION = 3000;
        public static final int ACCEPTABLE_ERROR = 0;
        public static final int POSITION_TOLERANCE_INCHES = 1;
    }
}
