
package frc.robot.commands;
import frc.robot.subsystems.IntakeSubsystem;

/**
 *
 * @author aryan
 */

public class IntakeOnCommand extends EntechCommandBase{

    private IntakeSubsystem intake;

    public IntakeOnCommand(IntakeSubsystem intake){
        super(intake);
        this.intake = intake;
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        if(intake.isIntakeOn()){
            if(intake.hasBallEntedElevator()|| intake.isTimerRunning()){
                if(intake.hasBallEntedElevator()){
                    intake.startTimer();
                }
                intake.startElevatorIntakeAndStopIntake();
            } else {
                intake.stopElevatorAndStartIntake();
            }
        } else {
            intake.setIntakeMotorSpeed(0);
        }
    }
    @Override
    public boolean isFinished(){
        return true;
    }
}