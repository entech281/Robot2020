package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotConstants;
import frc.robot.commands.EntechCommandBase;


/**
 *
 * @author dcowden
 */
public class CommandFactory {
    
    public static final int DEFAULT_TIMEOUT_SECONDS = 1;
    public static final double TINY_TIMEOUT_SECONDS=0.4;
    public static final double ELEVEATOR_SLOW_SPEED = 0.7;
    
    public static final double FULL_SPEED_FWD = 1;
    public static final double HALF_SPEED_FWD = 0.5;
    public static final double FULL_SPEED_BWD = -0.5;
    public static final double STOP_SPEED = 0;
    public static final double ELEVATOR_INTAKE_SPEED = 0.3;
    public static final double INTAKE_REVERSE = -0.2;
    
    private SubsystemManager sm;
    public CommandFactory(SubsystemManager subsystemManager){
        this.sm = subsystemManager;
    }
    
    public Command hoodAutoAdjustCommand(Vision){
        if(rPose.getVisionDataValidity()){
            config = processor.calculateShooterConfiguration(rPose.getTargetLocation());
            setDesiredShooterConfiguration(config);                    
        }        
    }
    
    public Command hoodAdjustToAngleCommand(double angle){
        return new InstantCommand ( () -> sm.getHoodSubsystem().setHoodPosition(angle) , sm.getHoodSubsystem());
    }
                if(preset1){
                    config = processor.calculateShooterConfiguration(RobotConstants.SHOOT_PRESETS.PRESET_1);
                    setShooterPreset1();
                    preset1 = false;
                }
                else if(preset2){
                    config = processor.calculateShooterConfiguration(RobotConstants.SHOOT_PRESETS.PRESET_2);
                    setShooterPreset2();
                    preset2 = false;        
    }
    
    public Command hoodParkCommand(){
        return hoodAdjustToAngleCommand(1.0);
        
    }
    public Command hoodHomeCommand(){
        
    }
    
    public Command intakeOnCommand(){
        double DELAY1 = 0.5;
        double DELAY2 = 0.25;    
        return  new SequentialCommandGroup(
            new WaitUntilCommand ( sm.getIntakeSubsystem()::isBallAtIntake),
            setIntakeSpeed(0.4),
            setElevatorSpeed(0.3),
            new WaitCommand( DELAY1),
            setIntakeSpeed(0.0),
            setElevatorSpeed(0.5),  
            new WaitCommand( DELAY2),     
            setIntakeSpeed(0.0),
            setElevatorSpeed(1.0)
        );
    }

    public Command fireCommand(){
        return new SequentialCommandGroup(
            new WaitUntilCommand ( sm.getShooterSubsystem())
        
        );
    }

    public Command nudgeHoodForward(){
        return new SingleShotCommandSingleShotCommand(this) {
            @Override
            public void doCommand(){
                double desired = hoodMotorController.getActualPosition() - 50;
                if(!autoAdjust){
                    hoodMotorController.setDesiredPosition(desired);
                }
            }
        };
    }
    public void setStartingLinePreset(){
        adjustHoodPosition(-940);
    }
    
    public void setShooterPreset2(){
        adjustHoodPosition(-930);
    }

    public void setShooterPreset1(){
        adjustHoodPosition(-375);
    }
    public Command nudgeHoodBackward(){
        return new SingleShotCommandSingleShotCommand(this) {
            @Override
            public void doCommand(){
                double desired = hoodMotorController.getActualPosition() + 50;
                if(!autoAdjust){
                    hoodMotorController.setDesiredPosition(desired);
                }
            }
        };
    }   

   public Command goToUpperLimit() {
        return new SingleShotCommandSingleShotCommand(this) {

            @Override
            public void doCommand() {
                hoodMotorController.resetPosition();
                hoodMotorController.setDesiredPosition(hoodMotorController.getActualPosition());
                while (!isUpperLimitHit()) {
                    hoodMotor.set(ControlMode.PercentOutput, 0.3);
                    logger.log("POSE", hoodMotorController.getActualPosition());
                }
                hoodMotor.set(ControlMode.Position, hoodMotorController.getActualPosition());
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    } 
    
    public Command startShooter() {
        return new InstantCommand(
            //todo: use controllers that reverse so we can use a positive here    
            () ->  sm.getShooterSubsystem().setShooterSpeedRPM(5300), sm.getShooterSubsystem()
        ).withTimeout(TINY_TIMEOUT_SECONDS);
    }
    public Command stopShooter() {
        return new InstantCommand(
            () ->  sm.getShooterSubsystem().setShooterSpeedRPM(0), sm.getShooterSubsystem()
        ).withTimeout(TINY_TIMEOUT_SECONDS);
    }  

    public Command setElevatorSpeed(double desiredSpeed) {
        return new InstantCommand(
            () ->    sm.getIntakeSubsystem().setElevatorSpeed(desiredSpeed), sm.getIntakeSubsystem()
        ).withTimeout(TINY_TIMEOUT_SECONDS);
    } 
    
    public Command setIntakeSpeed( double desiredSpeed){
        return new InstantCommand(
            () ->    sm.getIntakeSubsystem().setIntakeMotorSpeed(desiredSpeed), sm.getIntakeSubsystem()
        ).withTimeout(TINY_TIMEOUT_SECONDS);        
    }
    
    public Command startIntake() {
        return new InstantCommand(
            sm.getIntakeSubsystem()::deployIntakeArms, sm.getIntakeSubsystem()
        ).withTimeout(TINY_TIMEOUT_SECONDS);
    }

    public Command stopIntake() {
        return setElevatorSpeed(STOP_SPEED);
    }    
    public Command stopElevator(){
        return setIntakeSpeed(STOP_SPEED);
    }
    public Command stopIntakeAndElevator(){
        return new SequentialCommandGroup ( stopIntake(), stopElevator() );
    }

    public Command reverse() {
        return new SequentialCommandGroup(
            new InstantCommand ( () ->    sm.getIntakeSubsystem().setElevatorSpeed(FULL_SPEED_BWD)  ),
            new InstantCommand ( () ->    sm.getIntakeSubsystem().setIntakeMotorSpeed(FULL_SPEED_BWD)  )
        ).withTimeout(TINY_TIMEOUT_SECONDS);  
    }    

    public Command stopEverything() {
        return new SequentialCommandGroup(
            new InstantCommand ( () ->    sm.getIntakeSubsystem().setElevatorSpeed(STOP_SPEED)  ),
            new InstantCommand ( () ->    sm.getIntakeSubsystem().setIntakeMotorSpeed(STOP_SPEED)  )
        ).withTimeout(TINY_TIMEOUT_SECONDS);
    }         
     
    public Command shiftElevatorBack() {
        return new SequentialCommandGroup(
            new InstantCommand ( () ->    sm.getIntakeSubsystem().setElevatorSpeed(-0.2)  ),
            new WaitCommand( 0.5 ),
            new InstantCommand ( () ->    sm.getIntakeSubsystem().setElevatorSpeed(0)  )
        ).withTimeout(1.0);  
    }  
}
