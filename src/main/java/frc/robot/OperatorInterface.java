package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.StartIntakeCommand;
import frc.robot.commands.StopIntakeCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class OperatorInterface {
    private final Robot robot;
    private final Joystick driveStick;
    private final JoystickButtonManager manager;

    public OperatorInterface(final Robot robot){
        this.robot = robot;
        this.driveStick = new Joystick(RobotMap.GAMEPAD.driverStick);
        this.manager = new JoystickButtonManager(driveStick);

        manager.addButton(RobotMap.BUTTONS.START_INTAKE_BUTTON)
            .whenPressed(new StartIntakeCommand(robot.getIntakeSubsystem()))
            .add();
        manager.addButton(RobotMap.BUTTONS.STOP_INTAKE_BUTTON)
            .whenPressed(new StopIntakeCommand(robot.getIntakeSubsystem()))
            .add();
    }
  
    
    public double getDriveInputX(){
        SmartDashboard.putNumber("Joystick X", driveStick.getX());
        return driveStick.getX();
    }

    public double getDriveInputY(){
        SmartDashboard.putNumber("Joystick Y", driveStick.getY());
        return -driveStick.getY();
    }
}