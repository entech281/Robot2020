package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import frc.robot.RobotMap;

class AutoCommandFactory {
    public Command getExampleCommand(){
        // Create a voltage constraint to ensure we don't accelerate too fast
    var autoVoltageConstraint =
    new DifferentialDriveVoltageConstraint(
        new SimpleMotorFeedforward(RobotMap.AUTO_DRIVE_CONSTANTS.ksVolts,
                                   RobotMap.AUTO_DRIVE_CONSTANTS.kvVoltSecondsPerMeter,
                                   RobotMap.AUTO_DRIVE_CONSTANTS.kaVoltSecondsSquaredPerMeter),
                                   RobotMap.AUTO_DRIVE_CONSTANTS.kDriveKinematics,
        10);

// Create config for trajectory
TrajectoryConfig config =
    new TrajectoryConfig(RobotMap.AUTO_DRIVE_CONSTANTS.kMaxSpeedMetersPerSecond,
            RobotMap.AUTO_DRIVE_CONSTANTS.kMaxAccelerationMetersPerSecondSquared)
        // Add kinematics to ensure max speed is actually obeyed
        .setKinematics(RobotMap.AUTO_DRIVE_CONSTANTS.kDriveKinematics)
        // Apply the voltage constraint
        .addConstraint(autoVoltageConstraint);

// An example trajectory to follow.  All units in meters.
Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
    // Start at the origin facing the +X direction
    new Pose2d(0, 0, new Rotation2d(0)),
    // Pass through these two interior waypoints, making an 's' curve path
    List.of(
        new Translation2d(1, 1),
        new Translation2d(2, -1)
    ),
    // End 3 meters straight ahead of where we started, facing forward
    new Pose2d(3, 0, new Rotation2d(0)),
    // Pass config
    config
);

RamseteCommand ramseteCommand = new RamseteCommand(
    exampleTrajectory,
    m_robotDrive::getPose,
    new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
    new SimpleMotorFeedforward(DriveConstants.ksVolts,
                               DriveConstants.kvVoltSecondsPerMeter,
                               DriveConstants.kaVoltSecondsSquaredPerMeter),
    DriveConstants.kDriveKinematics,
    m_robotDrive::getWheelSpeeds,
    new PIDController(DriveConstants.kPDriveVel, 0, 0),
    new PIDController(DriveConstants.kPDriveVel, 0, 0),
    // RamseteCommand passes volts to the callback
    m_robotDrive::tankDriveVolts,
    m_robotDrive
);

// Run path following command, then stop at the end.
return ramseteCommand.andThen(() -> m_robotDrive.tankDriveVolts(0, 0));
}
    }
}