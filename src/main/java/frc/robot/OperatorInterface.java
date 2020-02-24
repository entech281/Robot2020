package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.AutonomousPathCommand;
import frc.robot.commands.DriveForwardSetDistance;
import frc.robot.commands.FollowPositionPathCommand;
import frc.robot.commands.HoodHomingCommand;
import frc.robot.commands.SnapToVisionTargetCommand;
import frc.robot.commands.SnapToYawCommand;
import frc.robot.commands.StartIntakeCommand;
import frc.robot.commands.StartShooterCommand;
import frc.robot.commands.StopIntakeCommand;
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
                .whenPressed(new StartIntakeCommand(subsystemManager.getIntakeSubsystem(), subsystemManager.getElevatorSubsystem(), subsystemManager.getShooterSubsystem()))
                .whenReleased(new StopIntakeCommand(subsystemManager.getIntakeSubsystem(), subsystemManager.getElevatorSubsystem()))
                .add();

        manager.addButton(RobotConstants.BUTTONS.START_SHOOTER_BUTTON)
                .whenPressed(new StartShooterCommand(subsystemManager.getShooterSubsystem(), subsystemManager.getElevatorSubsystem()))
                .whenReleased(new StopShooterCommand(subsystemManager.getShooterSubsystem()))
                .add();
        
        manager.addButton(RobotConstants.BUTTONS.FIRE_BUTTON)
                .whenPressed(subsystemManager.getElevatorSubsystem().start())
                .whenReleased(subsystemManager.getElevatorSubsystem().stop())
                .add();


        
        drive = subsystemManager.getDriveSubsystem();
        
        manager.addButton(4)
                .whenPressed(new DriveForwardSetDistance(drive,48))
                .add();
        
        manager.addButton(6)
                .whenPressed(new SnapToYawCommand(subsystemManager.getNavXSubsystem(), subsystemManager.getDriveSubsystem(), -90, true))
                .add();


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
        
        manager.addButton(12)
                .whenPressed(new AutonomousPathCommand(new AutoPathFactory(subsystemManager).autoPath1()))
                .add();

        drive.setDefaultCommand(new TankDriveCommand(drive, driveStick));

    }

}
