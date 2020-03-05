package frc.robot.utils;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.PerpetualCommand;
import edu.wpi.first.wpilibj2.command.ProxyScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.function.BooleanSupplier;

/**
 *
 * @author dcowden
 */
public class RunWhenDisabledCommandDecorator extends CommandBase{

    private Command command;
    public RunWhenDisabledCommandDecorator(Command command){
        this.command = command;
    }

    @Override
    public void initialize() {
        command.initialize();
    }

    @Override
    public void execute() {
        command.execute();
    }

    @Override
    public void end(boolean interrupted) {
        command.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return command.isFinished();
    }

    @Override
    public ParallelRaceGroup withTimeout(double seconds) {
        return command.withTimeout(seconds);
    }

    @Override
    public ParallelRaceGroup withInterrupt(BooleanSupplier condition) {
        return command.withInterrupt(condition);
    }

    @Override
    public SequentialCommandGroup beforeStarting(Runnable toRun, Subsystem... requirements) {
        return command.beforeStarting(toRun, requirements);
    }

    @Override
    public SequentialCommandGroup andThen(Runnable toRun, Subsystem... requirements) {
        return command.andThen(toRun, requirements);
    }

    @Override
    public SequentialCommandGroup andThen(Command... next) {
        return command.andThen(next);
    }

    @Override
    public ParallelDeadlineGroup deadlineWith(Command... parallel) {
        return command.deadlineWith(parallel);
    }

    @Override
    public ParallelCommandGroup alongWith(Command... parallel) {
        return command.alongWith(parallel);
    }

    @Override
    public ParallelRaceGroup raceWith(Command... parallel) {
        return command.raceWith(parallel);
    }

    @Override
    public PerpetualCommand perpetually() {
        return command.perpetually();
    }

    @Override
    public ProxyScheduleCommand asProxy() {
        return command.asProxy();
    }

    @Override
    public void schedule(boolean interruptible) {
        command.schedule(interruptible);
    }

    @Override
    public void schedule() {
        command.schedule();
    }

    @Override
    public void cancel() {
        command.cancel();
    }

    @Override
    public boolean isScheduled() {
        return command.isScheduled();
    }

    @Override
    public boolean hasRequirement(Subsystem requirement) {
        return command.hasRequirement(requirement);
    }

    @Override
    public String getName() {
        return command.getName();
    }
    
    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
    
    
}