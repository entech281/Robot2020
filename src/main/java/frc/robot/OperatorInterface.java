package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class OperatorInterface {
    private Robot robot;
    private Joystick driveStick;
    private JoystickButtonManager manager;

    public OperatorInterface(Robot robot){
        this.robot = robot;
        this.driveStick = new Joystick(RobotMap.GAMEPAD.driverStick);
        this.manager = new JoystickButtonManager(driveStick);
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