package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.SubsystemManager;
import frc.robot.commands.TankDriveCommand;

public class OperatorInterface {

    private Joystick driveStick;
    private JoystickButtonManager manager;
    private SubsystemManager subsystemManager;

    public OperatorInterface(final SubsystemManager subMan) {
        this.subsystemManager = subMan;
        this.driveStick = new Joystick(RobotMap.GAMEPAD.DRIVER_STICK);
        this.manager = new JoystickButtonManager(driveStick);

        manager.addButton(RobotMap.BUTTONS.INTAKE_BUTTON)
                .whenPressed(subsystemManager.getIntakeSubsystem().start())
                .whenReleased(subsystemManager.getIntakeSubsystem().stop())
                .add();

        manager.addButton(RobotMap.BUTTONS.SHOOT_BUTTON)
                .whenPressed(subsystemManager.getShooterSubsystem().shootMaxSpeed())
                .whenReleased(subsystemManager.getShooterSubsystem().stop())
                .add();

        DriveSubsystem drive = subsystemManager.getDriveSubsystem();

        manager.addButton(RobotMap.BUTTONS.RESET_BUTTON)
                .whenPressed(drive.reset())
                .add();

        drive.setDefaultCommand(new TankDriveCommand(drive, driveStick));

    }

}
