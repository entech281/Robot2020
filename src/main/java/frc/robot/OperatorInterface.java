package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.path.AutoPathFactory;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.SubsystemManager;
import frc.robot.commands.FollowPositionPathCommand;
import frc.robot.commands.TankDriveCommand;

public class OperatorInterface {

    private Joystick driveStick;
    private JoystickButtonManager manager;
    private SubsystemManager subsystemManager;

    public OperatorInterface(final SubsystemManager subMan) {
        this.subsystemManager = subMan;
        this.driveStick = new Joystick(RobotConstants.GAMEPAD.DRIVER_JOYSTICK);
        this.manager = new JoystickButtonManager(driveStick);

        manager.addButton(RobotConstants.BUTTONS.INTAKE_BUTTON)
                .whenPressed(subsystemManager.getIntakeSubsystem().start())
                .whenReleased(subsystemManager.getIntakeSubsystem().stop())
                .add();

        manager.addButton(RobotConstants.BUTTONS.SHOOT_BUTTON)
                .whenPressed(subsystemManager.getShooterSubsystem().shootMaxSpeed())
                .whenReleased(subsystemManager.getShooterSubsystem().stop())
                .add();


        manager.addButton(5)
                .whenPressed(new FollowPositionPathCommand(subsystemManager.getDriveSubsystem(), AutoPathFactory.getExamplePath()))
                .add();
        DriveSubsystem drive = subsystemManager.getDriveSubsystem();

        manager.addButton(RobotConstants.BUTTONS.RESET_BUTTON)
                .whenPressed(drive.reset())
                .add();

        drive.setDefaultCommand(new TankDriveCommand(drive, driveStick));

    }

}
