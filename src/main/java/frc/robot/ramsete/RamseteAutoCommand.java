package frc.robot.ramsete;

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
import frc.robot.RobotConstants;
import frc.robot.pose.RobotPoseManager;
import frc.robot.subsystems.DriveSubsystem;

public class RamseteAutoCommand {
    public SequentialCommandGroup getExampleCommand(DriveSubsystem robotDrive, RobotPoseManager poseManager) {
        // Create a voltage constraint to ensure we don't accelerate too fast
        var autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(RobotConstants.RAMSETE.ksVolts,
                                    RobotConstants.RAMSETE.kvVoltSecondsPerMeter,
                                    RobotConstants.RAMSETE.kaVoltSecondsSquaredPerMeter),
                                    RobotConstants.RAMSETE.kDriveKinematics,
            10);

        // Create config for trajectory
        TrajectoryConfig config =
            new TrajectoryConfig(RobotConstants.RAMSETE.kMaxSpeedMetersPerSecond,
                    RobotConstants.RAMSETE.kMaxAccelerationMetersPerSecondSquared)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(RobotConstants.RAMSETE.kDriveKinematics)
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
            poseManager::getWPIPose,
            new RamseteController(RobotConstants.RAMSETE.kRamseteB, RobotConstants.RAMSETE.kRamseteZeta),
            new SimpleMotorFeedforward(RobotConstants.RAMSETE.ksVolts,
                                RobotConstants.RAMSETE.kvVoltSecondsPerMeter,
                                RobotConstants.RAMSETE.kaVoltSecondsSquaredPerMeter),
            RobotConstants.RAMSETE.kDriveKinematics,
            robotDrive::getWheelSpeeds,
            new PIDController(RobotConstants.RAMSETE.kPDriveVel, 0, 0),
            new PIDController(RobotConstants.RAMSETE.kPDriveVel, 0, 0),
            // RamseteCommand passes volts to the callback
            robotDrive::tankDriveVolts,
            robotDrive
        );

        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(()->{robotDrive.tankDriveVolts(0, 0);});
    }
}
