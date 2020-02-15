/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.posev2.FieldPose;
import frc.robot.posev2.RobotPose;

/**
 * Add your docs here.
 */
public abstract class BaseSubsystem extends SubsystemBase {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  protected DataLogger logger;
  // Creates a list of all the subsystems extending BaseSubsystem
  private static List <BaseSubsystem> initialize_these_list = new ArrayList <BaseSubsystem>();


  public BaseSubsystem() {
    DataLoggerFactory.configureForMatch();
    this.logger = DataLoggerFactory.getLoggerFactory().createDataLogger(this.getName());
    initialize_these_list.add(this);
  }


  public abstract void initialize();
  public void customPeriodic(RobotPose rPose, FieldPose fPose){

  }
}
