package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.RobotMap;
import frc.robot.controllers.TalonSpeedController;



 public class ElevatorSubsystem extends BaseSubsystem {
    private double elevatorSpeed = 1;

    private final WPI_TalonSRX elevatorMotor = new WPI_TalonSRX(RobotMap.CAN.INTAKE_MOTOR);
    private TalonSpeedController elevatorMotorController;

    @Override
    public void initialize() {
        TalonSettings motorSettings = TalonSettingsBuilder.defaults()
            .withCurrentLimits(20, 15, 200)
            .brakeInNeutral()
            .withDirections(false, false)
            .noMotorOutputLimits()
            .noMotorStartupRamping()
            .useSpeedControl()
            .build();
            
        elevatorMotorController = new TalonSpeedController(elevatorMotor, motorSettings);
        elevatorMotorController.configure();
        elevatorMotor.set(ControlMode.PercentOutput,0);
    }

    public void setElevatorSpeed(double desiredSpeed) {
        logger.log("Intake Motor speed", desiredSpeed);
        this.elevatorSpeed = desiredSpeed;
        update();
    }
    public void update() {
        elevatorMotorController.setDesiredSpeed(this.elevatorSpeed);
    }
 } 