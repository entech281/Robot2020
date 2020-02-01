package frc.robot.commands;

import java.util.List;


//import edu.wpi.first.wpilibj.PIDController;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotMap;
import frc.robot.pose.PoseManager;
import frc.robot.subsystems.DriveSubsystem;

public class AutoCommandFactory {
    public SequentialCommandGroup getExampleCommand(DriveSubsystem robotDrive, PoseManager poseManager) {
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
                new Translation2d(12, 12),
                new Translation2d(24, -12)
            ),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(36, 0, new Rotation2d(0)),
            // Pass config
            config
        );

		RamseteCommand ramseteCommand = new RamseteCommand(
            exampleTrajectory,
            poseManager::getWPIPose,
            new RamseteController(RobotMap.AUTO_DRIVE_CONSTANTS.kRamseteB, RobotMap.AUTO_DRIVE_CONSTANTS.kRamseteZeta),
            new SimpleMotorFeedforward(RobotMap.AUTO_DRIVE_CONSTANTS.ksVolts,
                                RobotMap.AUTO_DRIVE_CONSTANTS.kvVoltSecondsPerMeter,
                                RobotMap.AUTO_DRIVE_CONSTANTS.kaVoltSecondsSquaredPerMeter),
            RobotMap.AUTO_DRIVE_CONSTANTS.kDriveKinematics,
            robotDrive::getWheelSpeeds,
            new PIDController(RobotMap.AUTO_DRIVE_CONSTANTS.kPDriveVel, 0, 0),
            new PIDController(RobotMap.AUTO_DRIVE_CONSTANTS.kPDriveVel, 0, 0),
            // RamseteCommand passes volts to the callback
            robotDrive::tankDriveVolts,
            robotDrive
        );

        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(()->{robotDrive.tankDriveVolts(0, 0);});
    }
}
