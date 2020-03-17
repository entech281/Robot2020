package frc.robot.subsystems;

import java.util.Arrays;

import frc.robot.pose.FieldPose;
import frc.robot.pose.FieldPoseManager;
import frc.robot.pose.PoseSource;
import frc.robot.pose.RobotPose;
import frc.robot.pose.RobotPoseManager;



public class SubsystemManager implements PoseSource{

    boolean hasClimb = false;

    public SubsystemManager() {
    }

    public DriveSubsystem getDriveSubsystem() {
        return driveSubsystem;
    }

    public IntakeSubsystem getIntakeSubsystem() {
        return intakeSubsystem;
    }

    public NavXSubsystem getNavXSubsystem() {
        return navXSubsystem;
    }

    public ClimbSubsystem getClimbSubsystem() {
        return climbSubsystem;
    }

    public ShooterSubsystem getShooterSubsystem() {
        return shooterSubsystem;
    }
    
//    public VisionSubsystem getVisionSubsystem(){
//        return visionSubsystem;
//    }

    public HoodSubsystem getHoodSubsystem() {
        return hoodSubsystem;
    }

    private DriveSubsystem driveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private NavXSubsystem navXSubsystem;
    private ShooterSubsystem shooterSubsystem;
    private ClimbSubsystem climbSubsystem;
//    private VisionSubsystem visionSubsystem;
    private HoodSubsystem hoodSubsystem;

    private final RobotPoseManager robotPoseManager = new RobotPoseManager();
    private final FieldPoseManager fieldPoseManager = new FieldPoseManager();
    
    public void setHoodSubsystem(HoodSubsystem hoodSubsystem) {
        this.hoodSubsystem = hoodSubsystem;
    }

    public void initAll() {
        driveSubsystem = new DriveSubsystem();
        intakeSubsystem = new IntakeSubsystem();
        navXSubsystem = new NavXSubsystem();
        shooterSubsystem = new ShooterSubsystem();
        climbSubsystem = new ClimbSubsystem();
//        visionSubsystem = new VisionSubsystem();
        hoodSubsystem  = new HoodSubsystem();
        
        Arrays.asList(
            driveSubsystem, 
            intakeSubsystem, 
            navXSubsystem, 
//            visionSubsystem,
            shooterSubsystem,
            hoodSubsystem).forEach(subsystem -> subsystem.initialize());
 
    }
    public void updatePoses() {
        robotPoseManager.updateEncoders(driveSubsystem.getEncoderValues());
        robotPoseManager.updateNavxAngle(navXSubsystem.updateNavXAngle());
//        robotPoseManager.updateVisionData(visionSubsystem.getVisionData());
        robotPoseManager.update();
    }

    @Override
    public RobotPose getRobotPose() {
        return robotPoseManager.getCurrentPose();
    }

    @Override
    public FieldPose getFieldPose() {
        return fieldPoseManager.getCurrentPose();
    }
}
