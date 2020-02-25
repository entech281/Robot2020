package frc.robot.preferences;

import frc.robot.commands.EntechCommandGroup;

public class AutoCommandFactory{
    public static EntechCommandGroup getSelectedCommand(AutoOption selected){
        switch(selected){
            case MiddleSixBall:
                break;
            case RightSevenBall:
                break;
            case ShootAndBackUp:
                break;
        }

        return null;//TODO fix this.
    }
}