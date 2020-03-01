package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotConstants;
import frc.robot.commands.EntechCommandBase;
import frc.robot.commands.SingleShotCommand;
import frc.robot.controllers.TalonSettings;
import frc.robot.controllers.TalonSettingsBuilder;
import frc.robot.controllers.TalonSpeedController;

import static frc.robot.RobotConstants.AVAILABILITY.*;
import frc.robot.pose.FieldPose;
import frc.robot.pose.RobotPose;

public class IntakeSubsystem extends BaseSubsystem {

    private double CURRENT_INTAKE_SPEED = 1;

    private double FULL_SPEED_FWD = 1;
    private double FULL_SPEED_BWD = -1;
    private double STOP_SPEED = 0;


    private WPI_TalonSRX intakeMotor;
    private TalonSpeedController intakeMotorController;
    
    private Solenoid deployIntake1;
    private Solenoid deployIntake2;

    public Command start() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                deployIntakeArms();
                setIntakeMotorSpeed(FULL_SPEED_FWD);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command stop() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                raiseIntakeArms();
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
        if (intake) {
            
            intakeMotor = new WPI_TalonSRX(RobotConstants.CAN.INTAKE_MOTOR);
            TalonSettings motorSettings = TalonSettingsBuilder.defaults().withCurrentLimits(20, 15, 200).brakeInNeutral()
                    .withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping().useSpeedControl().build();

            intakeMotorController = new TalonSpeedController(intakeMotor, motorSettings,false);
            intakeMotorController.configure();
            intakeMotor.set(ControlMode.PercentOutput, 0);
            
            deployIntake1 = new Solenoid(RobotConstants.CAN.PCM_ID, RobotConstants.CAN.INTAKE_SOL_1);
            deployIntake2 = new Solenoid(RobotConstants.CAN.PCM_ID, RobotConstants.CAN.INTAKE_SOL_2);
            
            deployIntake1.set(false);
            deployIntake2.set(false);
        }
    }

    public void deployIntakeArms(){
        deployIntake1.set(true);
        deployIntake2.set(true);
    }

    public void raiseIntakeArms(){
        deployIntake1.set(false);
        deployIntake2.set(false);
    }
    
    @Override
    public void periodic() {
                logger.log("Current command", getCurrentCommand());
    }
    

    public void setIntakeMotorSpeed(double desiredSpeed) {
        logger.log("Intake Motor speed", desiredSpeed);
        if (intake) {
            this.CURRENT_INTAKE_SPEED = desiredSpeed;
            intakeMotorController.setDesiredSpeed(this.CURRENT_INTAKE_SPEED);
        }
    }

}
