package frc.robot.commands;

import frc.robot.subsystems.BaseSubsystem;

public abstract class SingleShotCommand extends EntechCommandBase {

    public SingleShotCommand(BaseSubsystem bs) {
        super(bs);
    }

    public SingleShotCommand(BaseSubsystem bs, double timeOut) {
        super(bs, timeOut);
    }

    public abstract void doCommand();

    @Override
    public void execute() {
    }

    @Override
    public void initialize() {
        doCommand();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
