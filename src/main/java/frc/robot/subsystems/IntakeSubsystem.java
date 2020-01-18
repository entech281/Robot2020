package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.RobotMap;
import frc.robot.controllers.TalonSpeedController;



 public class IntakeSubsystem extends BaseSubsystem{
    private final double INTAKE_SPEED = 0.2;

    private final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(RobotMap.CAN.INTAKE_MOTOR);
    private TalonSpeedController intakeMotorController;

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
        intakeMotorController = new TalonSpeedController(intakeMotor, motorSettings);
        intakeMotorController.configure();
        intakeMotor.set(ControlMode.PercentOutput,0);
    }

    public void startIntakeMotor() {
        intakeMotorController.setDesiredSpeed(INTAKE_SPEED);
    }

    public void stopIntakeMotor(){
        intakeMotorController.setDesiredSpeed(0);
    }

 } 