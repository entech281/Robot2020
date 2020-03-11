package frc.robot.preferences;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.CommandFactory;

public class AutoCommandFactory{
    private CommandFactory commandFactory;
    public AutoCommandFactory( CommandFactory commandFactory ){
        this.commandFactory = commandFactory; 
    }
    public  Command getSelectedCommand(AutoOption selected){
        switch(selected){
            case MiddleSixBall:
                return commandFactory.middleSixBallAuto();
            case LeftSevenBall:
                return commandFactory.leftEightBallAuto();
            case ShootAndBackUp:
                return commandFactory.simpleForwardShoot3Auto();
        }
        return commandFactory.doNothing();
    }
}
