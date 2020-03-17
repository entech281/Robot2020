package frc.robot;

import com.revrobotics.CANPIDController;
import frc.robot.controllers.SparkMaxSettings;
import frc.robot.controllers.SparkMaxSettingsBuilder;
import frc.robot.controllers.TalonSettings;
import frc.robot.controllers.TalonSettingsBuilder;
import frc.robot.pose.RobotPose;
import frc.robot.pose.RobotPosition;
import frc.robot.pose.ShooterConfiguration;
import frc.robot.pose.TargetLocation;
import frc.robot.vision.VisionData;

public class RobotConstants {

    public interface DIGITIAL_INPUT{
        public static final int BALL_SENSOR = 0;
    }

    public interface MOTOR_SETTINGS {
        public static TalonSettings INTAKE = TalonSettingsBuilder.defaults()
                .withCurrentLimits(20, 15, 200)
                .brakeInNeutral()
                .withDirections(false, false)
                .noMotorOutputLimits()
                .noMotorStartupRamping()
                .useSpeedControl()
                .build();
        
        public static TalonSettings ELEVATOR = TalonSettingsBuilder.defaults()
                .withCurrentLimits(20, 15, 200)
                .brakeInNeutral()
                .withDirections(false, false)
                .noMotorOutputLimits()
                .noMotorStartupRamping()
                .useSpeedControl()
                .build();
        
        public static TalonSettings HOOD = TalonSettingsBuilder.defaults()
                .withCurrentLimits(1, 1, 1)
                .brakeInNeutral()
                .withDirections(false, false)
                .noMotorOutputLimits()
                .noMotorStartupRamping()
                .usePositionControl()
                .withGains(4, 2.56 * 3, 0, 0)
                .withMotionProfile(1000, 1000, 5)
                .enableLimitSwitch(false).build();
        
        public static TalonSettings HOOD_HOMING_SPEED = TalonSettingsBuilder.defaults()
                .withCurrentLimits(1, 1, 1)
                .brakeInNeutral()
                .withDirections(false, false)
                .limitMotorOutputs(0.3, 0)
                .noMotorStartupRamping()
                .useSpeedControl()
                .build();
        
        
        public static SparkMaxSettings SHOOTER_CLOSED_LOOP = SparkMaxSettingsBuilder.defaults()
                .withCurrentLimits(35)
                .coastInNeutral()
                .withDirections(false, false)
                .limitMotorOutputs(1, -1)
                .noMotorStartupRamping()
                .useSmartVelocityControl()
                .withPositionGains(0.000185, 4e-4, 0, 0.0)
                .useAccelerationStrategy(CANPIDController.AccelStrategy.kTrapezoidal)
                .withMaxVelocity(5700)
                .withMaxAcceleration(200000)
                .withClosedLoopError(50)
                .build();
        
        public static SparkMaxSettings SHOOTER_OPEN_LOOP = SparkMaxSettingsBuilder.defaults()
                .withCurrentLimits(35)
                .coastInNeutral()
                .withDirections(false, false)
                .limitMotorOutputs(0, 1)
                .noMotorStartupRamping()
                .useSpeedControl()
                .build();
     
    }
    
    public interface CAN {

        public static final int FRONT_LEFT_MOTOR = 3;
        public static final int FRONT_RIGHT_MOTOR = 1;
        public static final int REAR_LEFT_MOTOR = 4;
        public static final int REAR_RIGHT_MOTOR = 2;
        public static final int SHOOTER_MOTOR = 7;
        public static final int HOOD_MOTOR = 5;
        public static final int INTAKE_MOTOR = 6;
        public static final int ELEVATOR_MOTOR = 8;
        public static final int PCM_ID = 0;
        public static final int FORWARD = 6;
        public static final int REVERSE = 7;
    }

    public interface PNEUMATICS {

        public static final int ATTACH_SOLENOID = 0;
        public static final int ENGAGE_WINCH = 1;
    }

    public interface GAMEPAD {

        public static final int DRIVER_JOYSTICK = 0;
         public static final int OPERATOR_PANEL = 1;
        public static final int DRIVER_JOYSTICK2 = 2;
    }

    public interface JOYSTICK_BUTTONS{
        public static final int CURVATURE_DRIVE_PIVOT=2;
    }
    
    public interface BUTTONS {
        public static final int TURN_SHOOTER_ON = 8;
        public static final int ENABLE_AUTO_HOOD = 9;
        public static final int FIRE = 11;
        public static final int DRIVER_SHOOT = 1;
        public static final int DEPLOY_INTAKE = 13;
        public static final int HOOD_FORWARD_ADJUST = 3;
        public static final int HOOD_BACKWARD_ADJUST = 5;
        public static final int SELECT_PRESET_1 = 10;
        public static final int SELECT_PRESET_2 = 7;
        public static final int OUTAKE = 7;
        public static final int TOGGLE_INTAKE=12;
        public static final int NUDGE_YAW_RIGHT = 14;
        public static final int NUDGE_YAW_LEFT = 15;
        public static final int NUDGE_HOOD_FORWARD = 16;
        public static final int NUDGE_HOOD_BACKWARD = 17;
        
    }

    public interface DEFAULTS {

        public static final RobotPosition START_POSITION = new RobotPosition(0, 0, 0);
        public static final RobotPose START_POSE = new RobotPose(START_POSITION, VISION.DEFAULT_VISION_DATA);

        public interface VISION {
            public static final VisionData DEFAULT_VISION_DATA = new VisionData(false, -1, -1, -1);
            public static final int FRAME_WIDTH = 160;
            public static final int FRAME_HEIGHT = 120;
            public static final int STREAM_FPS = 50;
            public static final int STREAM_PORT = 8080;

        }
    }

    public interface DIMENSIONS {
        // Must be in inches

        public static final double ROBOT_WIDTH = 23.5;
        public static final double ROBOT_LENGTH = 25;
        public static final double DRIVE_GEAR_RATIO = 10.7 * (48/50.5); //(23.5/25.5)
        public static final double WHEEL_DIAMETER_INCHES = 6;
        public static final double MOTOR_REVOLUTIONS_PER_INCH = (Math.PI * WHEEL_DIAMETER_INCHES)
                / DRIVE_GEAR_RATIO;
    }

    public interface PID{
        public interface AUTO_STRAIGHT{
            public static final double P = 4e-4;
            public static final double I = 2e-7;
            public static final double D = 0;
            public static final double F = 0;
        }
        
        public interface AUTO_TURN{
            public static final double P = 2e-2; //1e-2
            public static final double I = 1e-7;
            public static final double D = 0;
            public static final double F = 0;
        }

        public interface AUTO_STRAIGHT_SPEED{
            public static final double P = 0.25;
            public static final double I = 0;
            public static final double D = 0;
            public static final double F = 0;
        }
        
        public interface TARGET_LOCK{
            public static final double P = 5e-3;
            public static final double I = 1e-2;
            public static final double D = 0;
            public static final double F = 0;
            
        }
    }

    public interface AUTONOMOUS {

        public static final int MAX_VELOCITY = 7500;
        public static final int MAX_ACCELLERATION = 30000;
        public static final int ACCEPTABLE_ERROR = 0;
        public static final int POSITION_TOLERANCE_INCHES = 1;
    }

    public interface AVAILABILITY {
        public static final boolean PNEUMATICS_MOUNTED = true;
        public static final boolean BALL_SENSOR = true;

    }
    
    public interface SHOOT_PRESETS{
        public TargetLocation PRESET_1 = new TargetLocation(5.5, 0);
        public TargetLocation PRESET_2 = new TargetLocation(190, 0);
        
        public ShooterConfiguration CLOSER_PRESET = new ShooterConfiguration(19, 5350);
        public ShooterConfiguration FARTHER_PRESET = new ShooterConfiguration(45, 5350);
    }
}
