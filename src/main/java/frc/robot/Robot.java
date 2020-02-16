/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.commands.HoodHomingCommand;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.subsystems.SubsystemManager;
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
     private SubsystemManager subsystemManager = new SubsystemManager();
     OperatorInterface oi;


     @Override
     public void robotInit(){
          this.logger = DataLoggerFactory.getLoggerFactory().createDataLogger("Robot Main Loop");
          subsystemManager.initAll();
          oi = new OperatorInterface(subsystemManager);
     }

     @Override
     public void robotPeriodic() {
     }

     @Override
     public void teleopInit(){
          subsystemManager.getDriveSubsystem().setSpeedMode();
     }


     @Override
     public void teleopPeriodic(){
          subsystemManager.periodicAll();
          CommandScheduler.getInstance().run();
     }



     @Override
     public void autonomousInit() {
          subsystemManager.getDriveSubsystem().setPositionMode();
          new HoodHomingCommand(subsystemManager.getShooterSubsystem()).schedule();
     }
     
     @Override
     public void autonomousPeriodic() {
          subsystemManager.periodicAll();
          CommandScheduler.getInstance().run();
     }

  
}
