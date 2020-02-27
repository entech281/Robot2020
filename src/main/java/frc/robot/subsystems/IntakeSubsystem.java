package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
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
    private double FULL_SPEED_BWD = -1;
    private double STOP_SPEED = 0;
    
    private double ELEVATOR_INTAKE_SPEED = 0.3;
    private double INTAKE_REVERSE = -0.2;
    
    
    private final int maxCurrent = 20;
    private final int maxSustainedCurrent = 15;
    private final int maxCurrentTime = 200;


    private WPI_TalonSRX intakeMotor;
    private TalonSpeedController intakeMotorController;
    
    private WPI_TalonSRX elevatorMotor;
    private TalonSpeedController elevatorMotorController;
    
    private Solenoid deployIntake1;
    private Solenoid deployIntake2;
    
    private boolean intakeOn = false;
    
    private Timer timer;
    private boolean timerRunning = false;

    public Command startIntake() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                intakeOn = true;
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }
    
    public Command startElevator() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                setElevatorSpeed(0.7);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command stopIntake() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                intakeOn = false;
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

    
    public Command stopElevator() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                setElevatorSpeed(0);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }
    @Override
    public void initialize() {
        if (intake) {
            
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
        
        if (elevator) {
            TalonSettings motorSettings = TalonSettingsBuilder.defaults()
                    .withCurrentLimits(maxCurrent, maxSustainedCurrent, maxCurrentTime).brakeInNeutral()
                    .withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping().useSpeedControl().build();

            elevatorMotor = new WPI_TalonSRX(RobotConstants.CAN.ELEVATOR_MOTOR);
            elevatorMotorController = new TalonSpeedController(elevatorMotor, motorSettings);
            elevatorMotorController.configure();
            elevatorMotor.set(ControlMode.PercentOutput, 0);
        }
        timer = new Timer();
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
    
    public boolean isIntakeOn(){
        return intakeOn;
    }
    
    @Override
    public void customPeriodic(RobotPose rPose, FieldPose fPose) {
        logger.log("Current command", getCurrentCommand());
    }
    
    public boolean hasBallEntedElevator(){
        return false;
    }
    
    public boolean isTimerRunning(){
        if(timerRunning){
            timerRunning = !timer.hasElapsed(0.5);
            if(!timerRunning){
                timer.stop();
            }
        }
        return timerRunning;
    }
    
    public void startTimer(){
        if(timerRunning){
            timer.stop();
            timer.start();
        } else {
            timer.start();
        }
    }
    
    public void startElevatorIntakeAndStopIntake(){
        setElevatorSpeed(ELEVATOR_INTAKE_SPEED);
        setIntakeMotorSpeed(INTAKE_REVERSE);
    }

    public void stopElevatorAndStartIntake(){
        setElevatorSpeed(STOP_SPEED);
        setIntakeMotorSpeed(FULL_SPEED_FWD);
    }    
    
    public void setIntakeMotorSpeed(double desiredSpeed) {
        logger.log("Intake Motor speed", desiredSpeed);
        if (intake) {
            this.CURRENT_INTAKE_SPEED = desiredSpeed;
            intakeMotorController.setDesiredSpeed(this.CURRENT_INTAKE_SPEED);
        }
    }
    
    public void setElevatorSpeed(double desiredSpeed) {
        if (elevator) {
            logger.log("Intake Motor speed", -desiredSpeed);
            elevatorMotorController.setDesiredSpeed(-desiredSpeed);
        }
    }

}
