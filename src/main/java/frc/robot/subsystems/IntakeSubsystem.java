package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.RobotConstants;
import static frc.robot.RobotConstants.*;
import frc.robot.controllers.*;

public class IntakeSubsystem extends BaseSubsystem {

    private WPI_TalonSRX intakeMotor;
    private TalonSpeedController intakeMotorController;
    private WPI_TalonSRX elevatorMotor;
    private TalonSpeedController elevatorMotorController;
    private DoubleSolenoid deployIntakeSolenoids;
    private DigitalInput intakeBallSensor;

    
    @Override
    public void initialize() {

            
        intakeMotor = new WPI_TalonSRX(RobotConstants.CAN.INTAKE_MOTOR);
        intakeMotorController = new TalonSpeedController(intakeMotor, MOTOR_SETTINGS.INTAKE);
        intakeMotorController.configure();
        intakeMotorController.setDesiredSpeed(0);


        deployIntakeSolenoids = new DoubleSolenoid(RobotConstants.CAN.FORWARD, RobotConstants.CAN.REVERSE);
        deployIntakeSolenoids.set(DoubleSolenoid.Value.kReverse);

        elevatorMotor = new WPI_TalonSRX(RobotConstants.CAN.ELEVATOR_MOTOR);
        elevatorMotorController = new TalonSpeedController(elevatorMotor, MOTOR_SETTINGS.ELEVATOR);
        elevatorMotorController.configure();
        elevatorMotorController.setDesiredSpeed(0);


        intakeBallSensor = new DigitalInput(RobotConstants.DIGITIAL_INPUT.BALL_SENSOR);
   
    }

    public void toggleIntakeArms(){
        if ( deployIntakeSolenoids.get() == DoubleSolenoid.Value.kForward){
            deployIntakeSolenoids.set(DoubleSolenoid.Value.kReverse);
        }
        else if ( deployIntakeSolenoids.get() == DoubleSolenoid.Value.kForward ){
            deployIntakeSolenoids.set(DoubleSolenoid.Value.kReverse);
        }
    }
    
    public void deployIntakeArms(){
        deployIntakeSolenoids.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void retractIntakeArms(){
        deployIntakeSolenoids.set(DoubleSolenoid.Value.kForward);
    }
    
    public boolean isIntakeOn(){
        return intakeMotorController.getDesiredSpeed() != 0;
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
        //TODO: set this controller up to be reversed, and
        //use a positivel value
       elevatorMotorController.setDesiredSpeed(-desiredSpeed);
    }


}
