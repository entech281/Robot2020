package frc.robot.subsystems;

import frc.robot.controllers.talon.TalonSpeedController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.RobotConstants;
import static frc.robot.RobotConstants.*;
import frc.robot.controllers.*;
import frc.robot.controllers.digitialinput.BaseDigitalInput;
import frc.robot.controllers.digitialinput.RealDigitalInput;
import frc.robot.controllers.solenoid.BaseDoubleSolenoidController;
import frc.robot.controllers.solenoid.RealDoubleSolonoidController;

public class IntakeSubsystem extends BaseSubsystem {

    private SpeedController intakeMotorController;
    private SpeedController elevatorMotorController;
    private BaseDoubleSolenoidController deployIntakeSolenoids;
    private BaseDigitalInput intakeBallSensor;
    
    public static final double INTAKE_ON = 1.0;
    public static final double INTAKE_OFF = 0.0;
    
      
    @Override
    public void initialize() {
        WPI_TalonSRX intakeMotor;
        WPI_TalonSRX elevatorMotor;
        intakeMotor = new WPI_TalonSRX(RobotConstants.CAN.INTAKE_MOTOR);
        intakeMotorController = new TalonSpeedController(intakeMotor, MOTOR_SETTINGS.INTAKE,false);
        intakeMotorController.configure();
        intakeMotorController.setDesiredSpeed(0);


        deployIntakeSolenoids = new RealDoubleSolonoidController(RobotConstants.CAN.FORWARD, RobotConstants.CAN.REVERSE);
        deployIntakeSolenoids.set(Value.kReverse);

        elevatorMotor = new WPI_TalonSRX(RobotConstants.CAN.ELEVATOR_MOTOR);
        elevatorMotorController = new TalonSpeedController(elevatorMotor, MOTOR_SETTINGS.ELEVATOR,true);
        elevatorMotorController.configure();
        elevatorMotorController.setDesiredSpeed(0);

        intakeBallSensor = new RealDigitalInput(RobotConstants.DIGITIAL_INPUT.BALL_SENSOR);
   
    }

    public void deployAndStart(){
        deployIntakeSolenoids.set(Value.kForward);
        intakeOn();
    }
    
    public void raiseAndStop(){
        deployIntakeSolenoids.set(Value.kReverse);
        intakeOff();
    }
    
    public void deployIntakeArms(){
        if ( deployIntakeSolenoids.get() == Value.kReverse ){
            deployIntakeSolenoids.set(Value.kForward);
        }
    }
    
    public void raiseIntakeArms(){
        if ( deployIntakeSolenoids.get() == Value.kForward){
            deployIntakeSolenoids.set(Value.kReverse);
        }        
    }

    public void ensureIntakeArmState(){
        deployIntakeSolenoids.set(deployIntakeSolenoids.get());
    };
    
    public void intakeOn(){
        intakeMotorController.setDesiredSpeed(INTAKE_ON);
    }
    
    public void intakeOff(){
        intakeMotorController.setDesiredSpeed(INTAKE_OFF);
    }
    
  
    public void toggleIntakeArms(){
        if (deployIntakeSolenoids.get() == Value.kForward) {
            deployIntakeSolenoids.set(Value.kReverse);
        } else {
            deployIntakeSolenoids.set(Value.kForward);
        }
    }

    
    @Override
    public void periodic() {
        logger.log("Current command", getCurrentCommand());
        logger.log("Ball sensor", intakeBallSensor.get());
    }
  
    public boolean isBallAtIntake(){
        return !intakeBallSensor.get();
    }
    
    public void setIntakeMotorSpeed(double desiredSpeed) {
        intakeMotorController.setDesiredSpeed(desiredSpeed);
    }
    
    public void setElevatorSpeed(double desiredSpeed) {
       elevatorMotorController.setDesiredSpeed(desiredSpeed);
    }

    @Override
    public void initializeForTest() {
        intakeMotorController = new TestSpeedController();
        elevatorMotorController = new TestSpeedController();
        deployIntakeSolenoids = new BaseDoubleSolenoidController();
        deployIntakeSolenoids.set(Value.kReverse);
        intakeBallSensor = new BaseDigitalInput();
        intakeBallSensor.set(true);
    }

}
