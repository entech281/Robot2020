package frc.robot.subsystems;

import java.util.*;

import frc.robot.logger.DataLoggerFactory;

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

    public ClimbSubsystem getClimbSubsystem() {
        return climbSubsystem;
    }

    public ShooterSubsystem getShooterSubsystem() {
        return shooterSubsystem;
    }
    
    public VisionSubsystem getVisionSubsystem(){
        return visionSubsystem;
    }

    public HoodSubsystem getHoodSubsystem() {
        return hoodSubsystem;
    }

    private DriveSubsystem driveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private NavXSubsystem navXSubsystem;
    private ShooterSubsystem shooterSubsystem;
    private ClimbSubsystem climbSubsystem;
    private ColorSubsystem colorSubsystem;
    private VisionSubsystem visionSubsystem;
    private HoodSubsystem hoodSubsystem;

    public void setHoodSubsystem(HoodSubsystem hoodSubsystem) {
        this.hoodSubsystem = hoodSubsystem;
    }

    public void initAll() {
        driveSubsystem = new DriveSubsystem();
        intakeSubsystem = new IntakeSubsystem();
        navXSubsystem = new NavXSubsystem();
        shooterSubsystem = new ShooterSubsystem();
        climbSubsystem = new ClimbSubsystem();
        colorSubsystem = new ColorSubsystem();
        visionSubsystem = new VisionSubsystem();
        hoodSubsystem  = new HoodSubsystem();
        
        Arrays.asList(
            driveSubsystem, 
            intakeSubsystem, 
            navXSubsystem, 
            visionSubsystem,
            shooterSubsystem,
            hoodSubsystem).forEach(subsystem -> subsystem.initialize());
 
    }

}
