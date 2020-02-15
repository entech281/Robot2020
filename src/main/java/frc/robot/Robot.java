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
     private SubsystemManager subManager = new SubsystemManager();
     OperatorInterface oi;


     @Override
     public void robotInit(){
          this.logger = DataLoggerFactory.getLoggerFactory().createDataLogger("Robot Main Loop");
          subManager.initAll();
          oi = new OperatorInterface(subManager);
     }

     @Override
     public void robotPeriodic() {
          CommandScheduler.getInstance().run();
     }

     @Override
     public void teleopInit(){
          subManager.getDriveSubsystem().setPositionMode();
     }


     @Override
     public void teleopPeriodic(){
          subManager.periodicAll();
     }

     @Override
     public void disabledPeriodic(){
     }

     @Override
     public void disabledInit(){
          super.disabledInit();
     }

     @Override
     public void autonomousInit() {
          HoodHomingCommand hoodHomeCom = new HoodHomingCommand(subManager.getShooterSubsystem());
          hoodHomeCom.initialize();
          hoodHomeCom.execute();
     }
     
     @Override
     public void autonomousPeriodic() {
          subManager.periodicAll();
     }

  
}
