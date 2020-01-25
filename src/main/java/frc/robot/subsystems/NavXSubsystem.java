/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;


/**
 *
 * @author dcowden
 */
public class NavXSubsystem extends BaseSubsystem{
    private final AHRS navX;
    public NavXSubsystem() {
        navX = new AHRS(SPI.Port.kMXP);
    }

    @Override
    public void initialize() {
        logger.log("NavX Initialize Start", false);
        while (navX.isCalibrating()) {
            ;
        }
        navX.zeroYaw();
        logger.log("NavX Initialize Finish", false);    }
}