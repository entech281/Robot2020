package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANDigitalInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
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
    private double FULL_SPEED_BWD = -0.2;
    private double STOP_SPEED = 0;

    private double ELEVATOR_INTAKE_SPEED = 0.4;
    private double ELEVATOR_SHOOT_SPEED = 0.7;

    private WPI_TalonSRX intakeMotor;
    private TalonSpeedController intakeMotorController;
    
    private double elevatorSpeed = 1;

    private WPI_TalonSRX elevatorMotor;
    private TalonSpeedController elevatorMotorController;

    private final int maxCurrent = 20;
    private final int maxSustainedCurrent = 15;
    private final int maxCurrentTime = 200;

    private boolean intakeOn = false;
    
    private Solenoid deployIntake1;
    private Solenoid deployIntake2;
    private Timer timer;
    
    private boolean timerRunning = false;

    private DigitalInput ballSensingSensor;
    
    @Override
    public void initialize() {
        timer = new Timer();
        if (INTAKE) {
            
            intakeMotor = new WPI_TalonSRX(RobotConstants.CAN.INTAKE_MOTOR);
            TalonSettings motorSettings = TalonSettingsBuilder.defaults().withCurrentLimits(20, 15, 200).brakeInNeutral()
                    .withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping().useSpeedControl().build();

            intakeMotorController = new TalonSpeedController(intakeMotor, motorSettings);
            intakeMotorController.configure();
            intakeMotor.set(ControlMode.PercentOutput, 0);
        }
        if(PNEUMATICS_MOUNTED){
            deployIntake1 = new Solenoid(RobotConstants.CAN.PCM_ID, RobotConstants.CAN.INTAKE_SOL_1);
            deployIntake2 = new Solenoid(RobotConstants.CAN.PCM_ID, RobotConstants.CAN.INTAKE_SOL_2);

            deployIntake1.set(false);
            deployIntake2.set(false);
        }
        if(SENSOR_MOUNTED){
            ballSensingSensor = new DigitalInput(1);
        }
        
        if (ELEVATOR) {
            TalonSettings motorSettings = TalonSettingsBuilder.defaults()
                    .withCurrentLimits(maxCurrent, maxSustainedCurrent, maxCurrentTime).brakeInNeutral()
                    .withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping().useSpeedControl().build();

            elevatorMotor = new WPI_TalonSRX(RobotConstants.CAN.ELEVATOR_MOTOR);
            elevatorMotorController = new TalonSpeedController(elevatorMotor, motorSettings);
            elevatorMotorController.configure();
            elevatorMotor.set(ControlMode.PercentOutput, 0);
        }
    }

    public void deployIntakeArms(){
        if(PNEUMATICS_MOUNTED){
            deployIntake1.set(true);
            deployIntake2.set(true);
        }
    }

    public void raiseIntakeArms(){
        if(PNEUMATICS_MOUNTED){
            deployIntake1.set(false);
            deployIntake2.set(false);
        }
    }
    
    public Command stopElevator(){
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                setElevatorSpeed(STOP_SPEED);
            }
        };
    }
    
    public Command stopIntake(){
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                intakeOn = false;
            }
        };
    }

    public Command startIntake(){
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                intakeOn = true;
            }
        };
    }
    
    public boolean isIntakeOn(){
        return intakeOn;
    }
    
    public Command fire(){
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                setElevatorSpeed(ELEVATOR_SHOOT_SPEED);
            }
        };
    }
        
    @Override
    public void customPeriodic(RobotPose rPose, FieldPose fPose) {
        logger.log("Current command", getCurrentCommand());
    }
    
    public void startTimer(){
        if(!timerRunning){
            timer.start();
            timerRunning = true;
        }
    }
    
    public boolean timerRunning(){
        if(timerRunning){
            timerRunning = !timer.hasElapsed(0.5);
            if(!timerRunning){
                timer.stop();
            }
        }
        return timerRunning;
    }
    
    public boolean ballEnteredElevator(){
        if(SENSOR_MOUNTED){
            return ballSensingSensor.get();
        }
        return false;
    }

    public void startElevatorIntakeAndStopIntake(){
        setElevatorSpeed(ELEVATOR_INTAKE_SPEED);
        setIntakeMotorSpeed(FULL_SPEED_BWD);
    }

    public void stopElevatorAndStartIntake(){
        setElevatorSpeed(STOP_SPEED);
        setIntakeMotorSpeed(FULL_SPEED_FWD);
    }
    
    public void setIntakeMotorSpeed(double desiredSpeed) {
        logger.log("Intake Motor speed", desiredSpeed);
        if (INTAKE) {
            this.CURRENT_INTAKE_SPEED = desiredSpeed;
            intakeMotorController.setDesiredSpeed(this.CURRENT_INTAKE_SPEED);
        }
    }

    
    public void setElevatorSpeed(double desiredSpeed) {
        if (ELEVATOR) {
            logger.log("Intake Motor speed", desiredSpeed);
            this.elevatorSpeed = desiredSpeed;
            elevatorMotorController.setDesiredSpeed(this.elevatorSpeed);
        }
    }
}
