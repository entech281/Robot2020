/*----------------------------------------------------------------------------*/
 /* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
 /* Open Source Software - may be modified and shared by FRC teams. The code   */
 /* must be accompanied by the FIRST BSD license file in the root directory of */
 /* the project.                                                               */
 /*----------------------------------------------------------------------------*/
package frc.robot.subsystems;

import com.ctre.phoenix.CANifier.PinValues;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.SingleShotCommand;
import frc.robot.pose.FieldPose;
import frc.robot.pose.NavXData;
import frc.robot.pose.RobotPose;

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
            if (timer.get() > TIMEOUT_CALIBRATION_SECONDS) {
                logger.log("NAVX Initialization FAILED", 0.0);
                navXWorking = false;
                break;
            }
        }
        navX.zeroYaw();
        logger.log("Nav angle", navX.getYaw());
        logger.log("NavX Initialize Finish", false);
    }

    public NavXData updateNavXAngle() {
        return new NavXData(navX.getYaw(), this.navXWorking);
    }


    public void zeroYaw(){
        navX.zeroYaw();
    }
    @Override
    public void customPeriodic(RobotPose rPose, FieldPose fPose) {
        logger.log("Angle reported by NavX", navX.getYaw());
    }
    
    public Command zeroYawOfNavX(boolean inverted){
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                navX.zeroYaw();
                if(inverted)
                    navX.setAngleAdjustment(180);
            }
        };
    }
    

}
