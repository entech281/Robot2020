package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.controllers.TalonSpeedController;
import frc.robot.logger.DataLoggerFactory;



 public class ShooterSubsystem extends BaseSubsystem{
    private double SHOOTER_SPEED = 1;

    private final WPI_TalonSRX shooterMotor = new WPI_TalonSRX(RobotMap.CAN.SHOOTER_MOTOR);
    private TalonSpeedController shooterMotorController;

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
        shooterMotorController = new TalonSpeedController(shooterMotor, motorSettings);
        shooterMotorController.configure();
        shooterMotor.set(ControlMode.PercentOutput,0);
    }

    public void setShooterMotorSpeed(double desiredSpeed) {
        logger.log("Shooter Motor speed", desiredSpeed);
        this.SHOOTER_SPEED = desiredSpeed;
        updateShooterMotor();
    }
    private void updateShooterMotor() {
        shooterMotorController.setDesiredSpeed(this.SHOOTER_SPEED);
    }
 } 



