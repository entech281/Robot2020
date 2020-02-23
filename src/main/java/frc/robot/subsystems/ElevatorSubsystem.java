package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotConstants;
import frc.robot.commands.EntechCommandBase;
import frc.robot.commands.SingleShotCommand;
import frc.robot.controllers.TalonSettings;
import frc.robot.controllers.TalonSettingsBuilder;
import frc.robot.controllers.TalonSpeedController;

public class ElevatorSubsystem extends BaseSubsystem {

    private double elevatorSpeed = 1;

    private WPI_TalonSRX elevatorMotor;
    private TalonSpeedController elevatorMotorController;

    private final int maxCurrent = 20;
    private final int maxSustainedCurrent = 15;
    private final int maxCurrentTime = 200;

    
    private boolean hardwareAvailable = RobotConstants.AVAILABILITY.elevator;
    
    public Command start() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                setElevatorSpeed(0.7);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command stop() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                setElevatorSpeed(0);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    @Override
    public void initialize() {
        if(hardwareAvailable){
            TalonSettings motorSettings = TalonSettingsBuilder.defaults()
                    .withCurrentLimits(maxCurrent, maxSustainedCurrent, maxCurrentTime).brakeInNeutral()
                    .withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping().useSpeedControl().build();

            elevatorMotor = new WPI_TalonSRX(RobotConstants.CAN.INTAKE_MOTOR);
            elevatorMotorController = new TalonSpeedController(elevatorMotor, motorSettings);
            elevatorMotorController.configure();
            elevatorMotor.set(ControlMode.PercentOutput, 0);
        }
    }

    public void setElevatorSpeed(double desiredSpeed) {
        if(hardwareAvailable){
            logger.log("Intake Motor speed", desiredSpeed);
            this.elevatorSpeed = desiredSpeed;
            elevatorMotorController.setDesiredSpeed(this.elevatorSpeed);
        }
    }

}
