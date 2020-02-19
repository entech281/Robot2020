package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotConstants;
import frc.robot.commands.EntechCommandBase;
import frc.robot.commands.SingleShotCommand;
import frc.robot.controllers.TalonSettings;
import frc.robot.controllers.TalonSettingsBuilder;
import frc.robot.controllers.TalonSpeedController;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.pose.FieldPose;
import frc.robot.pose.RobotPose;

public class IntakeSubsystem extends BaseSubsystem {

    private double CURRENT_INTAKE_SPEED = 1;

    private double FULL_SPEED_FWD = 1;
    private double FULL_SPEED_BWD = -1;
    private double STOP_SPEED = 0;

    private final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(RobotConstants.CAN.INTAKE_MOTOR);
    private TalonSpeedController intakeMotorController;

    public Command start() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                setIntakeMotorSpeed(FULL_SPEED_FWD);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command stop() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                setIntakeMotorSpeed(STOP_SPEED);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command reverse() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                setIntakeMotorSpeed(FULL_SPEED_BWD);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    @Override
    public void initialize() {
        TalonSettings motorSettings = TalonSettingsBuilder.defaults().withCurrentLimits(20, 15, 200).brakeInNeutral()
                .withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping().useSpeedControl().build();

        intakeMotorController = new TalonSpeedController(intakeMotor, motorSettings);
        intakeMotorController.configure();
        intakeMotor.set(ControlMode.PercentOutput, 0);
    }

    public void setIntakeMotorSpeed(double desiredSpeed) {
        logger.log("Intake Motor speed", desiredSpeed);
        this.CURRENT_INTAKE_SPEED = desiredSpeed;
        intakeMotorController.setDesiredSpeed(this.CURRENT_INTAKE_SPEED);
    }

}
