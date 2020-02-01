/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.commands.AutoCommandFactory;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.pose.PoseManager;
import frc.robot.subsystems.BaseSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.NavXSubsystem;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {


     private DataLogger logger;
     DriveSubsystem robotDrive;
     IntakeSubsystem intake;
     OperatorInterface oi;
     PoseManager officialPose;
     NavXSubsystem navX;

     public void robotInit(){
          
          intake = new IntakeSubsystem();
          robotDrive = new DriveSubsystem();
          navX = new NavXSubsystem();
          BaseSubsystem.initializeList();
          robotDrive.reset();          
          officialPose = new PoseManager();
          //officialPose.addGenerator(robotDrive.getEncoderPoseGenerator());
          officialPose.addGenerator(navX.getNavXPoseGenerator());
          officialPose.configureRobotPose(RobotMap.DIMENSIONS.START_POSE.getHorizontal(), RobotMap.DIMENSIONS.START_POSE.getForward(), RobotMap.DIMENSIONS.START_POSE.getTheta());
          oi = new OperatorInterface(this);
          
          this.logger = DataLoggerFactory.getLoggerFactory().createDataLogger("Robot Main Loop");
     }

     @Override
     public void teleopPeriodic(){
          //allPeriodic();
          robotDrive.drive(oi.getNextInstruction());
          CommandScheduler.getInstance().run();
     }

     public void allPeriodic(){
          robotDrive.updatePose(officialPose.getPose());
          logger.log("Current Pose",officialPose.getPose());
     }

     @Override
     public void disabledPeriodic(){
          robotDrive.drive(new DriveInstruction(0.0, 0.0));
          CommandScheduler.getInstance().run();
     }

     @Override
     public void autonomousInit(){
          new AutoCommandFactory().getExampleCommand(robotDrive, officialPose).schedule();
     }

     @Override
     public void autonomousPeriodic() {
          allPeriodic();
     }

     public IntakeSubsystem getIntakeSubsystem(){
          return intake;
     }

     public NavXSubsystem getNavXSubsystem(){
          return navX;
     }

     public DriveSubsystem getDriveSubsystem(){
          return robotDrive;
     }

     public PoseManager getOfficialPose(){
          return officialPose;
     }
  
}
