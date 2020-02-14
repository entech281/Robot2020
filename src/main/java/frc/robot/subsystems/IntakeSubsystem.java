package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.controllers.TalonSettings;
import frc.robot.controllers.TalonSettingsBuilder;
import frc.robot.controllers.TalonSpeedController;
import frc.robot.logger.DataLoggerFactory;



 public class IntakeSubsystem extends BaseSubsystem{
    private double INTAKE_SPEED = 1;

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

    public void setIntakeMotorSpeed(double desiredSpeed) {
        logger.log("Intake Motor speed", desiredSpeed);
        this.INTAKE_SPEED = desiredSpeed;
        updateIntakeMotor();
    }
    public void updateIntakeMotor() {
        intakeMotorController.setDesiredSpeed(this.INTAKE_SPEED);
    }
 } 