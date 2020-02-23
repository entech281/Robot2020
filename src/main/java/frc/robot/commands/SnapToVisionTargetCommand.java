package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
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
            RobotConstants.PID.TARGET_LOCK.D,
            RobotConstants.PID.TARGET_LOCK.F);
    }
    @Override
    public void initialize(){
        controller.setSetpoint(0);
    }

    @Override
    public void execute(){
        offset = vision.getVisionData().getLateralOffset();
        double output = controller.calculate(offset);
        logger.log("offset", offset);
        logger.log("output", output);
        output = Math.min(output, 1);
        output = Math.max(output, -1);
        drive.drive(new DriveInstruction(0, output));
    }

    @Override
    public boolean isFinished(){
        return Math.abs(offset) < 1;
    }

}