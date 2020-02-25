package frc.robot.preferences;

import edu.wpi.first.wpilibj2.command.Command;

public class AutoCommandFactory{
    public static Command getSelectedCommand(AutoOption selected){
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