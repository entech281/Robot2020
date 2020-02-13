package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class DelayCommand extends CommandBase {
    ClimbSubsystem climb;
    double time;
    public DelayCommand(ClimbSubsystem climb, double seconds) {
        this.climb = climb;
        time = seconds;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        climb.delay(time);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}