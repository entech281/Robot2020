package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElevatorSubsystem;

public class StopElevatorFeedCommand extends CommandBase {
    private ElevatorSubsystem elevator;
    private double desiredSpeed = 0;

    public StopElevatorFeedCommand(ElevatorSubsystem elevator) {
        this.elevator=elevator;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        elevator.setElevatorSpeed(desiredSpeed);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        elevator.update();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }

}