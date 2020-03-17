package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.SnapToVisionTargetCommand;
import frc.robot.commands.SnapToYawCommand;
import frc.robot.commands.TankDriveCommand;
import frc.robot.commands.TankDriveCommandTwoJoysticks;
import frc.robot.commands.TankDriveCurvatureCommand;
import frc.robot.subsystems.CommandFactory;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.SubsystemManager;

public class OperatorInterface {

    private Joystick driveStick;
    private Joystick driveStick2;
    private Joystick operatorPanel;
    private JoystickButtonManager joystickManager;
    private JoystickButtonManager operatorPanelManager;
    private SubsystemManager subsystemManager;
    private DriveSubsystem drive;
    private CommandFactory commandFactory;

    public OperatorInterface(final SubsystemManager subMan) {
        this.subsystemManager = subMan;
        commandFactory = new CommandFactory(subsystemManager);
        this.driveStick = new Joystick(RobotConstants.GAMEPAD.DRIVER_JOYSTICK);
        this.driveStick2 = new Joystick(RobotConstants.GAMEPAD.DRIVER_JOYSTICK2);
        this.operatorPanel = new Joystick(RobotConstants.GAMEPAD.OPERATOR_PANEL);
        this.joystickManager = new JoystickButtonManager(driveStick);
        this.operatorPanelManager = new JoystickButtonManager(operatorPanel);
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.TURN_SHOOTER_ON)
                .whenPressed(commandFactory.startShooter())
                .whenReleased(commandFactory.stopShooter())
                .add();
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.ENABLE_AUTO_HOOD)
                .whenPressed(commandFactory.enableAutoShooterAndHood())
                .whenReleased(commandFactory.disableAutoShooterAndHood())
                .add();
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.DEPLOY_INTAKE)
                .whileHeldContinous(commandFactory.deployAndStartIntake())
                .whenReleased(commandFactory.raiseAndStopIntake())
                .add();
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.HOOD_FORWARD_ADJUST)
                .whileHeld(commandFactory.nudgeHoodForward())
                .add();
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.HOOD_BACKWARD_ADJUST)
                .whileHeld(commandFactory.nudgeHoodBackward())
                .add();
                

        operatorPanelManager.addButton(RobotConstants.BUTTONS.SELECT_PRESET_1)
                .whenPressed(commandFactory.hoodUpAgainstTargetPreset())
                .add();

        operatorPanelManager.addButton(RobotConstants.BUTTONS.SELECT_PRESET_2)
                .whenPressed(commandFactory.hoodTrenchPreset())
                .add();
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.FIRE)
                .whenPressed(commandFactory.fireCommand())
                .whenReleased(commandFactory.stopElevator())
                .add();
        

        drive = subsystemManager.getDriveSubsystem();
                               
        
        joystickManager.addButton(RobotConstants.BUTTONS.OUTAKE)
                .whenPressed(commandFactory.reverse())
                .whenReleased(commandFactory.stopEverything())
                .add();

        
        joystickManager.addButton(6)
                .whenPressed(commandFactory.snapToYawCommand( 90, true))
                .add();
        
        joystickManager.addButton(5)
                .whenPressed(commandFactory.snapToYawCommand( -90, true))
                .add();

        joystickManager.addButton(12)
                .whenPressed(commandFactory.driveForwardSpeedMode(126, 1))
                .add();
        
        joystickManager.addButton(9)
                .whenPressed(commandFactory.hoodHomeCommand())
                .add();
        
        drive.setDefaultCommand(new TankDriveCurvatureCommand(drive, driveStick, 
                new JoystickButton(driveStick,RobotConstants.JOYSTICK_BUTTONS.CURVATURE_DRIVE_PIVOT)));
        //drive.setDefaultCommand ( new TankDriveCommand(drive,driveStick));
        //drive.setDefaultCommand ( new TankDriveCommandTwoJoysticks(drive,driveStick,driveStick2));
    }

}
