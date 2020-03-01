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
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.SingleShotCommand;
import frc.robot.pose.FieldPose;
import frc.robot.pose.NavXData;
import frc.robot.pose.RobotPose;
import frc.robot.utils.NavXDataProcessor;

/**
 *
 * @author dcowden
 */
public class NavXSubsystem extends BaseSubsystem {

    private final AHRS navX = new AHRS(SPI.Port.kMXP);
    private Timer timer;
    private double TIMEOUT_CALIBRATION_SECONDS = 31;
    private boolean navXWorking = true;
    private boolean inverted = false;

    public NavXSubsystem() {
        timer = new Timer();
        inverted = false;
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
        return new NavXData(calculateNavX(navX.getYaw()), this.navXWorking);
    }

    public void enableOffset(){
        inverted = true;
    }
    
    @Override
    public void periodic() {
        logger.log("Angle reported by NavX", calculateNavX(navX.getYaw()));
    }
    
    public Command zeroYawOfNavX(boolean inverted){
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                zeroYawMethod(inverted);
            }
        };
    }
    
    public double calculateNavX(double angle){
        if(inverted){
            angle = NavXDataProcessor.bringInRange(angle + 180);
        }
        return angle;
    }
    
    public void zeroYawMethod(boolean inverted){
        navX.zeroYaw();
        if(inverted)
            enableOffset();
    }
    

}
