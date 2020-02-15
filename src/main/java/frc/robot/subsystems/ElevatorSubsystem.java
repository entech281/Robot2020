package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap;
import frc.robot.commands.SingleShotCommand;
import frc.robot.controllers.TalonSettings;
import frc.robot.controllers.TalonSettingsBuilder;
import frc.robot.controllers.TalonSpeedController;
import frc.robot.posev2.FieldPose;
import frc.robot.posev2.RobotPose;

public class ElevatorSubsystem extends BaseSubsystem {
    private double elevatorSpeed = 1;

    private final WPI_TalonSRX elevatorMotor = new WPI_TalonSRX(RobotMap.CAN.INTAKE_MOTOR);
    private TalonSpeedController elevatorMotorController;

    private final int maxCurrent = 20;
    private final int maxSustainedCurrent = 15;
    private final int maxCurrentTime = 200;

    @Override
    public void initialize() {
        TalonSettings motorSettings = TalonSettingsBuilder.defaults()
                .withCurrentLimits(maxCurrent, maxSustainedCurrent, maxCurrentTime).brakeInNeutral()
                .withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping().useSpeedControl().build();

        elevatorMotorController = new TalonSpeedController(elevatorMotor, motorSettings);
        elevatorMotorController.configure();
        elevatorMotor.set(ControlMode.PercentOutput, 0);
    }

    public void setElevatorSpeed(double desiredSpeed) {
        logger.log("Intake Motor speed", desiredSpeed);
        this.elevatorSpeed = desiredSpeed;
        update();
    }

    public void update() {
        elevatorMotorController.setDesiredSpeed(this.elevatorSpeed);
    }

    public Command start(){
        return new SingleShotCommand(this){        
            @Override
            public void doCommand() {
                setElevatorSpeed(0.7);
            }
        };
    }

    public Command stop(){
        return new SingleShotCommand(this){        
            @Override
            public void doCommand() {
                setElevatorSpeed(0);
            }
        };
    }

 } 