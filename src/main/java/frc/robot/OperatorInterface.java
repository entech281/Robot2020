package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.commands.OutakeIntakeCommand;
import frc.robot.commands.ResetPositionCommand;
import frc.robot.commands.StartIntakeCommand;
import frc.robot.commands.StopIntakeCommand;

public class OperatorInterface implements DriveInstructionSource{
    private Robot robot;
    private Joystick driveStick;
    private JoystickButtonManager manager;
    private DataLogger logger;

    public OperatorInterface(final Robot robot){
        this.robot = robot;
        logger = DataLoggerFactory.getLoggerFactory().createDataLogger("OperatorInterface");
        this.driveStick = new Joystick(RobotMap.GAMEPAD.driverStick);
        this.manager = new JoystickButtonManager(driveStick);

        manager.addButton(RobotMap.BUTTONS.INTAKE_BUTTON)
            .whenPressed(new StartIntakeCommand(robot.getIntakeSubsystem()))
            .whenReleased(new StopIntakeCommand(robot.getIntakeSubsystem()))
            .add();

        manager.addButton(2)
            .whenPressed(new OutakeIntakeCommand(robot.getIntakeSubsystem()))
            .whenReleased(new StopIntakeCommand(robot.getIntakeSubsystem()))
            .add();

        manager.addButton(10)
            .whenPressed(new ResetPositionCommand(robot.getDriveSubsystem(), robot.getOfficialPose()))
            .add();
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
        return new DriveInstruction( -driveStick.getY() , driveStick.getX());
    }
}