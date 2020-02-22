package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class DelayCommand extends CommandBase {

    ClimbSubsystem climb;
    double time;

    public DelayCommand(ClimbSubsystem climb, double seconds) {
        this.climb = climb;
        time = seconds;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        climb.delay(time);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
