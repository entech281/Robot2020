package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap;
import frc.robot.commands.SingleShotCommand;
import frc.robot.controllers.TalonSettings;
import frc.robot.controllers.TalonSettingsBuilder;
import frc.robot.controllers.TalonSpeedController;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.newPoses.FieldPose;
import frc.robot.newPoses.RobotPose;

public class IntakeSubsystem extends BaseSubsystem {
    private double INTAKE_SPEED = 1;

    private final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(RobotMap.CAN.INTAKE_MOTOR);
    private TalonSpeedController intakeMotorController;

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
        this.INTAKE_SPEED = desiredSpeed;
        updateIntakeMotor();
    }

    public void updateIntakeMotor() {
        intakeMotorController.setDesiredSpeed(this.INTAKE_SPEED);
    }

    public Command start(){
        return new SingleShotCommand(this){        
            @Override
            public void doCommand() {
                setIntakeMotorSpeed(1);;
            }
        };
    }

    public Command stop(){
        return new SingleShotCommand(this){        
            @Override
            public void doCommand() {
                setIntakeMotorSpeed(0);;
            }
        };
    }

    public Command reverse(){
        return new SingleShotCommand(this){        
            @Override
            public void doCommand() {
                setIntakeMotorSpeed(-1);;
            }
        };
    }

    @Override
    public void customPeriodic(RobotPose rPose, FieldPose fPose) {
        // TODO Auto-generated method stub

    }
 } 