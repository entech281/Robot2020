package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveInstruction;
import frc.robot.RobotConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class SnapToVisionTargetCommand extends EntechCommandBase {

    private DriveSubsystem drive;
    private VisionSubsystem vision;
    private PIDController controller;
    private double offset;
    

    public SnapToVisionTargetCommand(DriveSubsystem drive, VisionSubsystem vision) {
        super(drive);
        this.drive = drive;
        this.vision = vision;
        this.controller = new PIDController(RobotConstants.PID.TARGET_LOCK.P,
            RobotConstants.PID.TARGET_LOCK.I,
            RobotConstants.PID.TARGET_LOCK.D);
    }
    @Override
    public void initialize(){
        controller.setSetpoint(0);
        controller.setTolerance(1);
    }

    @Override
    public void execute(){
        if(vision.getVisionData().targetFound()){
            offset = vision.getVisionData().getLateralOffset();
            double output = controller.calculate(offset);
            controller.calculate(offset, 0);
            logger.log("offset", offset);
            logger.log("output", output);
            if(output > 0)
                output = Math.min(output, 0.3);
            if(output < 0)
                output = Math.max(output, -0.3);
            logger.log("Final output", output);
            drive.drive(new DriveInstruction(0, output));
        }
    }

    @Override
    public boolean isFinished(){
        return controller.atSetpoint() || !vision.getVisionData().targetFound();
    }

}