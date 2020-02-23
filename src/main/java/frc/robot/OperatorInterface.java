package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.FollowPositionPathCommand;
import frc.robot.commands.HoodHomingCommand;
import frc.robot.commands.SnapToVisionTargetCommand;
import frc.robot.commands.StopShooterCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.SubsystemManager;
import frc.robot.commands.TankDriveCommand;
import frc.robot.path.AutoPathFactory;

public class OperatorInterface {

    private Joystick driveStick;
    private JoystickButtonManager manager;
    private SubsystemManager subsystemManager;
    private DriveSubsystem drive;

    public OperatorInterface(final SubsystemManager subMan) {
        this.subsystemManager = subMan;
        this.driveStick = new Joystick(RobotConstants.GAMEPAD.DRIVER_JOYSTICK);
        this.manager = new JoystickButtonManager(driveStick);

        manager.addButton(RobotConstants.BUTTONS.INTAKE_BUTTON)
                .whenPressed(subsystemManager.getIntakeSubsystem().start())
                .whenReleased(subsystemManager.getIntakeSubsystem().stop())
                .add();

        manager.addButton(RobotConstants.BUTTONS.SHOOT_BUTTON)
                .whenPressed(subsystemManager.getShooterSubsystem().turnOnShooter())
                .whenReleased(new StopShooterCommand(subsystemManager.getShooterSubsystem()))
                .add();

        drive = subsystemManager.getDriveSubsystem();

        manager.addButton(RobotConstants.BUTTONS.RESET_BUTTON)
                .whenPressed(drive.reset())
                .add();

        manager.addButton(10)
                .whenPressed(subsystemManager.getShooterSubsystem().enableAutoShooting())
                .whenReleased(subsystemManager.getShooterSubsystem().disableAutoShooting())
                .add();

        manager.addButton(7)
                .whenPressed(new HoodHomingCommand(subsystemManager.getShooterSubsystem()))
                .add();

        manager.addButton(RobotConstants.BUTTONS.RESET_BUTTON)
                .whenPressed(drive.reset())
                .add();
        
        manager.addButton(3)
                .whenPressed(new SnapToVisionTargetCommand(subsystemManager.getDriveSubsystem(), subsystemManager.getVisionSubsystem()))
                .add();

        drive.setDefaultCommand(new TankDriveCommand(drive, driveStick));

    }

}
