package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;

public class OperatorInterface implements DriveInstructionSource{
    private Robot robot;
    private Joystick driveStick;
    private JoystickButtonManager manager;
    private DataLogger logger;


    public OperatorInterface(Robot robot){
        this.robot = robot;
        
        logger = DataLoggerFactory.getLoggerFactory().createDataLogger("OperatorInterface");
        driveStick = new Joystick(RobotMap.GAMEPAD.driverStick);
        manager = new JoystickButtonManager(driveStick);
    }
    
    public double getDriveInputX(){
        logger.log("drive X", driveStick.getX());
        return driveStick.getX();
    }

    public double getDriveInputY(){
        logger.log("drive Y", driveStick.getY());
        return -driveStick.getY();
    }

    @Override
    public DriveInstruction getNextInstruction() {
        double x;

        if (driveStick.getTrigger()) {
            x = -driveStick.getTwist();
        } else {
            x = -driveStick.getX();
        }
        double y = driveStick.getY();

        return new DriveInstruction( x , y );
    }
}