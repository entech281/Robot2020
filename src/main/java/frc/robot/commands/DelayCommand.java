package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class DelayCommand extends CommandBase {

    double time;
    Timer timer;

    public DelayCommand(double seconds) {
        timer = new Timer();
        time = seconds;
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(time);
    }
}