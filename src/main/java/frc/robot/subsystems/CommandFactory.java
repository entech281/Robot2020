package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.commands.AdjustHoodBackwardCommand;
import frc.robot.commands.AdjustRaiseHoodCommand;
import frc.robot.commands.SnapToVisionTargetCommand;
import frc.robot.commands.SnapToYawCommand;
import frc.robot.pose.PoseSource;


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
    
    private final SubsystemManager sm;
    public CommandFactory(SubsystemManager subsystemManager){
        this.sm = subsystemManager;
    }
    
    public Command toggleIntakeArms(){
        return new InstantCommand( sm.getIntakeSubsystem()::toggleIntakeArms, sm.getIntakeSubsystem())
                .andThen(new PrintCommand("Toggling Arms"));
    }
    public Command turnIntakeOn(){
        return new InstantCommand ( sm.getIntakeSubsystem()::intakeOn, sm.getIntakeSubsystem());
    }
    public Command turnIntakeOff(){
        return new InstantCommand ( sm.getIntakeSubsystem()::intakeOff, sm.getIntakeSubsystem());
    }    
    
    public Command zeroYawOfNavX(boolean inverted){
        return new InstantCommand ( () -> sm.getNavXSubsystem().zeroYawMethod(inverted));
    }
    public Command middleSixBallAuto(){
        throw new UnsupportedOperationException("Not yet Implemented");
    }
    
    public Command leftEightBallAuto(){
        throw new UnsupportedOperationException("Not yet Implemented");
    }
    
    public Command doNothing(){
        return new PrintCommand("Doing Nothing Skipper!");
    }
    public Command simpleBackupAuto(){
        throw new UnsupportedOperationException("Not yet Implemented");
    }
    
    public Command startIntakeCommand(){
        //addCommands(intake.startIntake(), intake.stopElevator(), shoot.turnOffShooter());
        throw new UnsupportedOperationException("Not yet Implemented");
    }
    
    public Command startShooterCommand(){
        // addCommands(intake.shiftElevatorBack() ,shoot.turnOnShooter());
        throw new UnsupportedOperationException("Not yet Implemented");
    }
    public Command stopIntakeCommand(){
        //addCommands(intake.stopIntake(), intake.stopElevator());
        throw new UnsupportedOperationException("Not yet Implemented");
    }
    
    public Command snapAndShootCommand(){
        //addCommands(shoot.enableAutoShooting() , new StartShooterCommand(shoot, intakeSubsystem), new SnapToVisionTargetCommand(drive));  
        throw new UnsupportedOperationException("Not yet Implemented");        
    }
    public Command snapToVisionTargetCommand(){
        return new SnapToVisionTargetCommand(sm.getDriveSubsystem(),sm);
    }
    public Command snapToYawCommand(double desiredAngle, boolean relative){
        return new SnapToYawCommand(sm.getDriveSubsystem(),  desiredAngle,  relative, sm );
    }
    public Command hoodAutoAdjustCommand(){
//        VisionData vd = vision.getVisionData();
//        if(vd.getVisionDataValidity()){
//            ShooterConfiguration config = processor.calculateShooterConfiguration(rPose.getTargetLocation());
//            setDesiredShooterConfiguration(config);                    
//        }           
        //return new HoodAutoAdjustCommand(sm.getVisionSubsystem(),sm.getHoodSubsystem());
        throw new UnsupportedOperationException("Not yet Implemented");
    }
    
    public Command hoodAdjustToAngleCommand(double angle){
        return new InstantCommand ( () -> sm.getHoodSubsystem().setHoodPosition(angle) , sm.getHoodSubsystem());
    }
//                if(preset1){
//                    config = processor.calculateShooterConfiguration(RobotConstants.SHOOT_PRESETS.PRESET_1);
//                    setShooterPreset1();
//                    preset1 = false;
//                }
//                else if(preset2){
//                    config = processor.calculateShooterConfiguration(RobotConstants.SHOOT_PRESETS.PRESET_2);
//                    setShooterPreset2();
//                    preset2 = false;        
//    }
    
    public Command hoodParkCommand(){
        return hoodAdjustToAngleCommand(1.0);
        
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
            new WaitUntilCommand ( () ->  
                    sm.getShooterSubsystem().atShootSpeed() &&
                    sm.getHoodSubsystem().atHoodPosition() ),
            new InstantCommand(() -> sm.getIntakeSubsystem().setElevatorSpeed(ELEVEATOR_SLOW_SPEED) )        
        );
    }

    public Command nudgeHoodForward(){
        return new AdjustRaiseHoodCommand(sm.getHoodSubsystem());
    }
    
    public Command hoodHomeCommand(){
        return new SequentialCommandGroup(
        new InstantCommand(() -> sm.getHoodSubsystem().goToHomePosition()),
        new WaitUntilCommand(() -> sm.getHoodSubsystem().isUpperLimitHit()),
        new InstantCommand(() -> sm.getHoodSubsystem().reset()),
        new InstantCommand(() -> sm.getHoodSubsystem().adjustHoodForward()),
        new WaitUntilCommand(() -> sm.getHoodSubsystem().atHoodPosition())
        );                      
    }
    
    public Command nudgeHoodBackward(){
        return new AdjustHoodBackwardCommand(sm.getHoodSubsystem());
    }    
    public Command setStartingLinePreset(){
        //adjustHoodPosition(-940);
        return new InstantCommand( () -> sm.getHoodSubsystem().setHoodPosition( 10.0), sm.getHoodSubsystem());
        
    }
    
    public Command setShooterPreset2(){
        //adjustHoodPosition(-930);
        return new InstantCommand( () -> sm.getHoodSubsystem().upAgainstTargetPreset(), sm.getHoodSubsystem());
    }

    public Command setShooterPreset1(){
        //adjustHoodPosition(-375);
        return new InstantCommand(() -> sm.getHoodSubsystem().upAgainstTargetPreset(), sm.getHoodSubsystem());
    }
    
    public Command startShooter() {
        return new InstantCommand(
            //todo: use controllers that reverse so we can use a positive here    
            () ->  sm.getShooterSubsystem().startShooter(), sm.getShooterSubsystem()
        ).withTimeout(TINY_TIMEOUT_SECONDS);
    }
    public Command stopShooter() {
        return new InstantCommand(
            () ->  sm.getShooterSubsystem().stopShooter(), sm.getShooterSubsystem()
        ).alongWith(new InstantCommand(()-> sm.getHoodSubsystem().park(), sm.getHoodSubsystem()));
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
    
//    public Command startIntake() {
//        return new InstantCommand(
//            sm.getIntakeSubsystem()::deployIntakeArms, sm.getIntakeSubsystem()
//        ).withTimeout(TINY_TIMEOUT_SECONDS);
//    }

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
    
    public Command testDriveForward(){
        return new SequentialCommandGroup(
            new InstantCommand ( () ->    sm.getDriveSubsystem().drive(1.0, 0.0)  )
        ).withTimeout(1.0); 
    }

    public Command testDriveBackwards(){
        return new SequentialCommandGroup(
            new InstantCommand ( () ->    sm.getDriveSubsystem().drive(-1.0, 0.0)  )
        ).withTimeout(1.0); 
    }

    public Command testTurnRight(){
        return new SequentialCommandGroup(
            new InstantCommand ( () ->    sm.getDriveSubsystem().drive(0.0, 1.0)  )
        ).withTimeout(1.0); 
    }

    public Command testTurnLeft(){
        return new SequentialCommandGroup(
            new InstantCommand ( () ->    sm.getDriveSubsystem().drive(0.0, -1.0)  )
        ).withTimeout(1.0); 
    }
    
    public Command testShooterSpeed(){
        return new SequentialCommandGroup(
            new InstantCommand( () ->  sm.getShooterSubsystem().startShooter()  ),
            new WaitCommand(0.5),
            new InstantCommand(  () -> sm.getShooterSubsystem().stopShooter()   )
        ).withTimeout(5.0);
    }

    public Command testHoodPosition(){
        return new SequentialCommandGroup(
            new InstantCommand(  () -> sm.getHoodSubsystem().setHoodPosition(90.0)),
            new InstantCommand(  () -> sm.getHoodSubsystem().adjustHoodForward()),
            new InstantCommand(  () -> sm.getHoodSubsystem().setHoodPosition(0.0)),
            new InstantCommand(  () -> sm.getHoodSubsystem().adjustHoodBackward())
        ).withTimeout(5.0);
    }

    public Command testIntake(){
        return new SequentialCommandGroup(
            
        ).withTimeout(5.0);
    }

    public Command getStopShooterCommandGroup(){
//        return new EntechCommandGroup()
//                .addCommand(subsystemManager.getIntakeSubsystem().stopElevator())
//                .addCommand(subsystemManager.getShooterSubsystem().turnOffShooter())
//                .addCommand(new HoodHomingCommand(subsystemManager.getShooterSubsystem()))
//                .getSequentialCommandGroup();
        throw new UnsupportedOperationException("Not yet Implemented");
    }
    
//    public SequentialCommandGroup getStartIntakeCommandGroup(){
//        return new EntechCommandGroup()
//                .addCommand(subsystemManager.getShooterSubsystem().turnOffShooter())
//                .addCommand(subsystemManager.getIntakeSubsystem().startIntake())
//                .addCommand(new IntakeOnCommand(subsystemManager.getIntakeSubsystem()))
//                .getSequentialCommandGroup();
//    }
    
    public Command getSnapToGoalAndStartShooter(){
//        return new EntechCommandGroup()
//                .addCommand(subsystemManager.getShooterSubsystem().enableAutoShooting())
//                .addCommand(startShooterCommandGroup)
//                .addCommand(new SnapToVisionTargetCommand(subsystemManager.getDriveSubsystem()))
//                .getSequentialCommandGroup();
        throw new UnsupportedOperationException("Not yet Implemented");
    }
    
    public Command getStartShooterCommandGroup(){
//        return new EntechCommandGroup()
//                .addCommand(getStopIntakeCommandGroup())
//                .addCommand(subsystemManager.getIntakeSubsystem().shiftElevatorBack())
//                .addCommand(subsystemManager.getShooterSubsystem().turnOnShooter())
//                .getSequentialCommandGroup();
        throw new UnsupportedOperationException("Not yet Implemented");
    }
    
    public Command turnOffAllSubsystems(){
//        return new EntechCommandGroup()
//                .addCommand(getStopIntakeCommandGroup())
//                .addCommand(subsystemManager.getIntakeSubsystem().stopElevator())
//                .addCommand(getStopShooterCommandGroup())
//                .getSequentialCommandGroup();
         throw new UnsupportedOperationException("Not yet Implemented");       
    }
    
//    public SequentialCommandGroup getHoodHomingCommandGroup(){
//        return new EntechCommandGroup()
//                .addCommand(subsystemManager.getShooterSubsystem().goToUpperLimit())
//                .addCommand(subsystemManager.getShooterSubsystem().returnToStartPos())
//                .getSequentialCommandGroup();
//    }
    
    public SequentialCommandGroup hoodHomeAndStartShooter(){
//        SequentialCommandGroup hoodHomingCommand = new EntechCommandGroup()
//                .addCommand(subsystemManager.getShooterSubsystem().goToUpperLimit())
//                .addCommand(subsystemManager.getShooterSubsystem().returnToStartPos())
//                .getSequentialCommandGroup();
//        
//        return new EntechCommandGroup()
//                .addCommand(subsystemManager.getShooterSubsystem().turnOnShooter())
//                .addCommand(hoodHomingCommand)
//                .getSequentialCommandGroup();
        throw new UnsupportedOperationException("Not yet Implemented");
    }    
    
}
