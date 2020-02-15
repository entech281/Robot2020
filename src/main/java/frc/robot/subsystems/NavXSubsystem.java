/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import frc.robot.newPoses.FieldPose;
import frc.robot.newPoses.NavXData;
import frc.robot.newPoses.RobotPose;

/**
 *
 * @author dcowden
 */
public class NavXSubsystem extends BaseSubsystem {

    private final AHRS navX = new AHRS(SPI.Port.kMXP);

    public NavXSubsystem() {
    }

    @Override
    public void initialize() {
        logger.log("NavX Initialize Start", false);
        while (navX.isCalibrating()) {
            ;
        }
        navX.zeroYaw();
        logger.log("NavX Initialize Finish", false);
    }

    public NavXData updateNavXAngle() {
        return new NavXData(navX.getAngle());
    }

    @Override
    public void customPeriodic(RobotPose rPose, FieldPose fPose) {
        // TODO Auto-generated method stub

    }
}