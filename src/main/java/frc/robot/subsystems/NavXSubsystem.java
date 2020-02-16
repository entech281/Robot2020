/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.posev2.FieldPose;
import frc.robot.posev2.NavXData;
import frc.robot.posev2.RobotPose;

/**
 *
 * @author dcowden
 */
public class NavXSubsystem extends BaseSubsystem {

    private final AHRS navX = new AHRS(SPI.Port.kMXP);
    private Timer timer;
    private double TIMEOUT_CALIBRATION_SECONDS = 31;
    private boolean navXWorking = true;

    public NavXSubsystem() {
        timer = new Timer();
    }

    @Override
    public void initialize() {
        logger.log("NavX Initialize Start", false);
        timer.start();
        while (navX.isCalibrating()) {
            if(timer.get() > TIMEOUT_CALIBRATION_SECONDS){
                logger.log("NAVX Initialization FAILED", 0.0);
                navXWorking = false;
                break;
            }
        }
        navX.zeroYaw();
        logger.log("NavX Initialize Finish", false);
    }

    public NavXData updateNavXAngle() {
        return new NavXData(navX.getAngle(), this.navXWorking);
    }

}