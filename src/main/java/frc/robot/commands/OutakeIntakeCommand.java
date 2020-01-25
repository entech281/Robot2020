package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class OutakeIntakeCommand extends CommandBase {
    private IntakeSubsystem intake;
    private double intake_speed = -1;

    public OutakeIntakeCommand(IntakeSubsystem intake) {
        this.intake=intake;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        intake.setIntakeMotorSpeed(intake_speed);
        intake.updateIntakeMotor();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        intake.setIntakeMotorSpeed(intake_speed);
        intake.updateIntakeMotor();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }


}