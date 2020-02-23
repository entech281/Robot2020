package frc.robot.subsystems;

import java.util.*;

import frc.robot.logger.DataLoggerFactory;
import frc.robot.pose.FieldPoseManager;
import frc.robot.pose.RobotPoseManager;


public class SubsystemManager {

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

    public ElevatorSubsystem getElevatorSubsystem() {
        return elevatorSubsystem;
    }

    public ClimbSubsystem getClimbSubsystem() {
        return climbSubsystem;
    }

    public ShooterSubsystem getShooterSubsystem() {
        return shootSubsystem;
    }
    
    public VisionSubsystem getVisionSubsystem(){
        return visionSubsystem;
    }

    private DriveSubsystem driveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private NavXSubsystem navXSubsystem;
    private ShooterSubsystem shootSubsystem;
    private ClimbSubsystem climbSubsystem;
    private ElevatorSubsystem elevatorSubsystem;
    private ColorSubsystem colorSubsystem;

    private OpenMVCameraFeedSubsystem openmvSubsystem;
    private VisionSubsystem visionSubsystem;


    private final RobotPoseManager robotPoseManager = new RobotPoseManager();
    private final FieldPoseManager fieldPoseManager = new FieldPoseManager();

    private final List<BaseSubsystem> allSubsystems = new ArrayList<>();

    public void initAll() {
        driveSubsystem = new DriveSubsystem();
        intakeSubsystem = new IntakeSubsystem();
        navXSubsystem = new NavXSubsystem();
        shootSubsystem = new ShooterSubsystem();
        climbSubsystem = new ClimbSubsystem();
        elevatorSubsystem = new ElevatorSubsystem();
        colorSubsystem = new ColorSubsystem();
        openmvSubsystem = new OpenMVCameraFeedSubsystem(true);
        visionSubsystem = new VisionSubsystem();

        Collections.addAll(allSubsystems, driveSubsystem, intakeSubsystem, 
                navXSubsystem, visionSubsystem, shootSubsystem,openmvSubsystem);

        allSubsystems.forEach(subsystem -> subsystem.initialize());

    }

    public void periodicAll() {
        updatePoses();
        allSubsystems.forEach(subsystem -> subsystem.customPeriodic(
                robotPoseManager.getCurrentPose(),
                fieldPoseManager.getCurrentPose()
        ));
    }

    private void updatePoses() {
        robotPoseManager.updateEncoders(driveSubsystem.getEncoderValues());
        robotPoseManager.updateNavxAngle(navXSubsystem.updateNavXAngle());
        robotPoseManager.updateVisionData(visionSubsystem.getVisionData());
        robotPoseManager.updateWheelColor(colorSubsystem.getRobotColorSensorReading());
        robotPoseManager.update();
    }

}
