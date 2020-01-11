package frc.robot;


public class RobotMap{
    public interface CAN{
        public static final int FRONT_LEFT_MOTOR = 3;
        public static final int FRONT_RIGHT_MOTOR = 1;
        public static final int REAR_LEFT_MOTOR = 4;
        public static final int REAR_RIGHT_MOTOR = 2;
        public static final int SHOOTER_MOTOR = 5;
        public static final int HOPPER_MOTOR = 6;
        public static final int INTAKE_MOTOR = 7;
        public static final int PCM_ID = 10;
    }

    public interface PNEUMATICS{
        public static final int solenoid1 = 0;
        public static final int solenoid2 = 1;
    }

    public interface GAMEPAD{
        public static final int driverStick = 0;
    }

    public interface BUTTONS{
        public static final int shootButton = 7;
        public static final int emergencyStopIntakeButton = 10;
    }
}