package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.SnapToVisionTargetCommand;
import frc.robot.commands.SnapToYawCommand;
import frc.robot.commands.TankDriveCommand;
import frc.robot.subsystems.CommandFactory;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.SubsystemManager;

public class OperatorInterface {

    private Joystick driveStick;
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
        this.operatorPanel = new Joystick(RobotConstants.GAMEPAD.OPERATOR_PANEL);
        this.joystickManager = new JoystickButtonManager(driveStick);
        this.operatorPanelManager = new JoystickButtonManager(operatorPanel);
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.TURN_SHOOTER_ON)
                .whenPressed(commandFactory.startShooter())
                .whenReleased(commandFactory.stopShooter())
                .add();
        
        //operatorPanelManager.addButton(RobotConstants.BUTTONS.ENABLE_AUTO_HOOD)
        //        .whenPressed(subsystemManager.getShooterSubsystem().enableAutoShooting())
        //        .whenReleased(subsystemManager.getShooterSubsystem().disableAutoShooting())
        //        .add();
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.DEPLOY_INTAKE)
                .whileHeld(commandFactory.startIntake())
                .whenReleased(commandFactory.stopIntake())
                .add();
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.HOOD_FORWARD_ADJUST)
                .whileHeld(commandFactory.nudgeHoodForward())
                .add();
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.HOOD_BACKWARD_ADJUST)
                .whileHeld(commandFactory.nudgeHoodBackward())
                .add();
                
        //operatorPanelManager.addButton(RobotConstants.BUTTONS.SELECT_PRESET_1)
        //        .whenPressed(subsystemManager.getShooterSubsystem().selectPreset1())
        //        .add();
        
        //operatorPanelManager.addButton(RobotConstants.BUTTONS.SELECT_PRESET_2)
        //        .whenPressed(subsystemManager.getShooterSubsystem().selectPreset2())
        //        .add();
        
        drive = subsystemManager.getDriveSubsystem();
        
        joystickManager.addButton(RobotConstants.BUTTONS.SNAP_TO_TARGET)
                .whenPressed(new SnapToVisionTargetCommand(drive).withTimeout(5))
                .add();
        
        joystickManager.addButton(RobotConstants.BUTTONS.DRIVER_SHOOT)
                .whileHeld(commandFactory.fireCommand())
                .add();
        
        joystickManager.addButton(12)
                .whenPressed(commandFactory.hoodHomeCommand())
                .add();

        
        joystickManager.addButton(RobotConstants.BUTTONS.OUTAKE)
                .whenPressed(commandFactory.reverse())
                .whenReleased(commandFactory.stopEverything())
                .add();
        
        joystickManager.addButton(8)
                .whenPressed(commandFactory.hoodHomeCommand())
                .add();
        
        joystickManager.addButton(9)
                .whenPressed( new InstantCommand(
                        () -> subsystemManager.getHoodSubsystem().setHoodAngle(10.0)))
                .add();
        
        joystickManager.addButton(6)
                .whenPressed(new SnapToYawCommand(drive, 2.5, true).withTimeout(0.25))
                .add();
        
        joystickManager.addButton(5)
                .whenPressed(new SnapToYawCommand(drive, -2.5, true).withTimeout(0.25))
                .add();
        
        drive.setDefaultCommand(new TankDriveCommand(drive, driveStick));

    }

}
