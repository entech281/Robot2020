package frc.robot.preferences;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.AutonomousPathCommand;
import frc.robot.path.AutoPathFactory;

public class AutoCommandFactory{
    public static Command getSelectedCommand(AutoOption selected, AutoPathFactory pathFactory){
        switch(selected){
            case MiddleSixBall:
                return new AutonomousPathCommand(pathFactory.middleSixBallAuto());
            case LeftSevenBall:
                return new AutonomousPathCommand(pathFactory.leftEightBallAuto());
            case ShootAndBackUp:
                return new AutonomousPathCommand(pathFactory.backUp());
        }

        return null;
    }
}
