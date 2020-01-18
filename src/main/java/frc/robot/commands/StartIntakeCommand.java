package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class StartIntakeCommand extends CommandBase {
    private IntakeSubsystem intake;

    public StartIntakeCommand(IntakeSubsystem intake) {
        this.intake=intake;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        intake.startIntakeMotor();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        intake.startIntakeMotor();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }


}