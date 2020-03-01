/*----------------------------------------------------------------------------*/
 /* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
 /* Open Source Software - may be modified and shared by FRC teams. The code   */
 /* must be accompanied by the FIRST BSD license file in the root directory of */
 /* the project.                                                               */
 /*----------------------------------------------------------------------------*/
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.pose.FieldPose;
import frc.robot.pose.RobotPose;
import static frc.robot.RobotConstants.AVAILABILITY.*;
import frc.robot.pose.PoseSource;

/**
 * Add your docs here.
 */
public abstract class BaseSubsystem extends SubsystemBase {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    protected DataLogger logger;
    protected PoseSource poseSource;

    public PoseSource getPoseSource() {
        return poseSource;
    }

    public void setPoseSource(PoseSource poseSource) {
        this.poseSource = poseSource;
    }
    
    public BaseSubsystem() {
        this.logger = DataLoggerFactory.getLoggerFactory().createDataLogger(this.getName());
    }
    
    public abstract void initialize();

}
