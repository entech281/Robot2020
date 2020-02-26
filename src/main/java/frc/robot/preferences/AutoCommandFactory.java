package frc.robot.preferences;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.AutonomousPathCommand;
import frc.robot.path.AutoPathFactory;

public class AutoCommandFactory{
    public static Command getSelectedCommand(AutoOption selected, AutoPathFactory factory){
        switch(selected){
            case MiddleSixBall:
                return new AutonomousPathCommand(factory.middleSixBallAuto());
            case RightSevenBall:
                return new AutonomousPathCommand(factory.leftEightBallAuto());
            case ShootAndBackUp:
                return new AutonomousPathCommand(factory.simplePath());
        }

        return null;
    } 
}