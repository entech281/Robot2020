package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
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
    private double HALF_SPEED_FWD = 0.5;
    private double FULL_SPEED_BWD = -0.5;
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

    private DoubleSolenoid deployIntakeSolenoids;
    
    private boolean intakeOn = false;
    
    private Timer timer;
    private boolean timerRunning = false;

    private DigitalInput beam;
    private boolean lastBallEntered = false;
    
    private boolean checkSensor = true;
    
    private boolean elevatorSpinning = false;

    public Command startIntake() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                deployIntakeArms();
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
                raiseIntakeArms();
                intakeOn = false;
                setIntakeMotorSpeed(0);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    
    public Command reverse() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                setIntakeMotorSpeed(FULL_SPEED_BWD);
                setElevatorSpeed(FULL_SPEED_BWD);
                
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command stopEverything() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                setIntakeMotorSpeed(STOP_SPEED);
                setElevatorSpeed(STOP_SPEED);
            }
        };
    }
    
    
    public Command stopElevator() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                setElevatorSpeed(0);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }
    
    public Command shiftElevatorBack(){
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                shiftElevatorBackToAllowForRoom();
            }
        };
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
            
        }
        
        if(PNEUMATICS_MOUNTED){
            
            deployIntakeSolenoids = new DoubleSolenoid(RobotConstants.CAN.FORWARD, RobotConstants.CAN.REVERSE);
            
            deployIntakeSolenoids.set(DoubleSolenoid.Value.kReverse);
        }
        
        if (elevator) {
            TalonSettings motorSettings = TalonSettingsBuilder.defaults()
                    .withCurrentLimits(maxCurrent, maxSustainedCurrent, maxCurrentTime).brakeInNeutral()
                    .withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping().useSpeedControl().build();

            elevatorMotor = new WPI_TalonSRX(RobotConstants.CAN.ELEVATOR_MOTOR);
            elevatorMotorController = new TalonSpeedController(elevatorMotor, motorSettings,false);
            elevatorMotorController.configure();
            elevatorMotor.set(ControlMode.PercentOutput, 0);
        }
        timer = new Timer();
        if(BALL_SENSOR){
            beam = new DigitalInput(RobotConstants.DIGITIAL_INPUT.BALL_SENSOR);
        }
    }

    public void deployIntakeArms(){
        if(PNEUMATICS_MOUNTED){
            deployIntakeSolenoids.set(DoubleSolenoid.Value.kForward);
        }
    }

    public void raiseIntakeArms(){
        if(PNEUMATICS_MOUNTED){
            deployIntakeSolenoids.set(DoubleSolenoid.Value.kReverse);
        }
    }
    
    public boolean isIntakeOn(){
        return intakeOn;
    }
    
    @Override
    public void periodic() {
                logger.log("Current command", getCurrentCommand());

    }
    
    
    public void shiftElevatorBackToAllowForRoom(){
        setElevatorSpeed(-0.2);
        timer.start();
        while(!timer.hasElapsed(0.5)){
            
        }
        setElevatorSpeed(0);
    }
    
    
    public boolean sensorReadingValue(){
        if(BALL_SENSOR){
            return !beam.get();
        } else{
            return true;
        }
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
