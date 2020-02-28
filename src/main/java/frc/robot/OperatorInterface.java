package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.AdjustBackwardHoodCommand;
import frc.robot.commands.AdjustRaiseHoodCommand;
import frc.robot.commands.CommandGroupFactory;
import frc.robot.commands.DriveForwardSetDistance;
import frc.robot.commands.HoodHomingCommand;
import frc.robot.commands.SnapToVisionTargetCommand;
import frc.robot.commands.SnapToYawCommand;
import frc.robot.commands.TankDriveCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.SubsystemManager;

public class OperatorInterface {

    private Joystick driveStick;
    private Joystick operatorPanel;
    private JoystickButtonManager joystickManager;
    private JoystickButtonManager operatorPanelManager;
    private SubsystemManager subsystemManager;
    private DriveSubsystem drive;
    private CommandGroupFactory commandFactory;

    public OperatorInterface(final SubsystemManager subMan) {
        this.subsystemManager = subMan;
        commandFactory = new CommandGroupFactory((subsystemManager));
        this.driveStick = new Joystick(RobotConstants.GAMEPAD.DRIVER_JOYSTICK);
        this.operatorPanel = new Joystick(RobotConstants.GAMEPAD.OPERATOR_PANEL);
        this.joystickManager = new JoystickButtonManager(driveStick);
        this.operatorPanelManager = new JoystickButtonManager(operatorPanel);
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.TURN_SHOOTER_ON)
                .whenPressed(commandFactory.getStartShooterCommandGroup())
                .whenReleased(commandFactory.getStopShooterCommandGroup())
                .add();
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.ENABLE_AUTO_HOOD)
                .whenPressed(subsystemManager.getShooterSubsystem().enableAutoShooting())
                .whenReleased(subsystemManager.getShooterSubsystem().disableAutoShooting())
                .add();
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.DEPLOY_INTAKE)
                .whileHeld(commandFactory.getStartIntakeCommandGroup())
                .whenReleased(commandFactory.getStopIntakeCommandGroup())
                .add();
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.HOOD_FORWARD_ADJUST)
                .whileHeld(new AdjustRaiseHoodCommand(subsystemManager.getShooterSubsystem()))
                .add();
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.HOOD_BACKWARD_ADJUST)
                .whileHeld(new AdjustBackwardHoodCommand(subsystemManager.getShooterSubsystem()))
                .add();
                
        operatorPanelManager.addButton(RobotConstants.BUTTONS.SELECT_PRESET_1)
                .whenPressed(subsystemManager.getShooterSubsystem().selectPreset1())
                .add();
        
        operatorPanelManager.addButton(RobotConstants.BUTTONS.SELECT_PRESET_2)
                .whenPressed(subsystemManager.getShooterSubsystem().selectPreset2())
                .add();
        
        drive = subsystemManager.getDriveSubsystem();
        
        joystickManager.addButton(RobotConstants.BUTTONS.SNAP_TO_TARGET)
                .whenPressed(new SnapToVisionTargetCommand(drive))
                .add();
        
        joystickManager.addButton(RobotConstants.BUTTONS.DRIVER_SHOOT)
                .whenPressed(subsystemManager.getIntakeSubsystem().startElevator())
                .whenReleased(subsystemManager.getIntakeSubsystem().stopElevator())
                .add();
        
        joystickManager.addButton(12)
                .whenPressed(new HoodHomingCommand(subsystemManager.getShooterSubsystem()))
                .add();
        
        joystickManager.addButton(9)
                .whenPressed(new DriveForwardSetDistance(drive, 24))
                .add();
        
        joystickManager.addButton(8)
                .whenPressed(new SnapToYawCommand(drive, 90, true))
                .add();
        
        joystickManager.addButton(RobotConstants.BUTTONS.OUTAKE)
                .whenPressed(subsystemManager.getIntakeSubsystem().reverse())
                .whenReleased(subsystemManager.getIntakeSubsystem().stopEverything())
                .add();
        
        drive.setDefaultCommand(new TankDriveCommand(drive, driveStick));

    }

}
