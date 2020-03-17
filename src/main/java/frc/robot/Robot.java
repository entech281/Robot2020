/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.StopDrivingCommand;

import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.pose.FieldPoseManager;
import frc.robot.pose.RobotPoseManager;
import frc.robot.preferences.AutoCommandFactory;
import frc.robot.preferences.SmartDashboardPathChooser;
import frc.robot.subsystems.CommandFactory;
import frc.robot.subsystems.SubsystemManager;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    private DataLogger logger;
    private SubsystemManager  subsystemManager;
    private CommandFactory commandFactory;


    private SmartDashboardPathChooser optionChooser;
    OperatorInterface oi;
    Command autoCommand;
    private Compressor compressor;

    @Override
    public void robotInit() {
        if(RobotConstants.AVAILABILITY.PNEUMATICS_MOUNTED){
            compressor = new Compressor(RobotConstants.CAN.PCM_ID);
            compressor.start();
        }
        
        DataLoggerFactory.configureForMatch();
        this.logger = DataLoggerFactory.getLoggerFactory().createDataLogger("Robot Main Loop");
        subsystemManager = new SubsystemManager();
        subsystemManager.initAll();

        optionChooser = new SmartDashboardPathChooser();
        commandFactory = new CommandFactory(subsystemManager);
    }

    @Override
    public void robotPeriodic() {
        //runs after everything else
        subsystemManager.updatePoses();

        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        subsystemManager.getNavXSubsystem().zeroYawMethod(false);
        if (autoCommand != null) {
            autoCommand.cancel();
        }
        if(!subsystemManager.getHoodSubsystem().knowsHome()){
            commandFactory.hoodHomeCommand().schedule();
        }else{
            commandFactory.parkHood();
        }

        oi = new OperatorInterface(subsystemManager);
//        subsystemManager.getVisionSubsystem().ensureConnected();
        subsystemManager.getShooterSubsystem().initialize();

    }

    @Override
    public void teleopPeriodic() {
        SmartDashboard.putNumber("Target Distance", subsystemManager.getRobotPose().getTargetLocation().getDistanceToTarget());
    }

    @Override
    public void autonomousInit() {
//        subsystemManager.getVisionSubsystem().ensureConnected();



        if(!subsystemManager.getHoodSubsystem().knowsHome()){
            commandFactory.hoodHomeCommand().schedule();
        }else{
            commandFactory.parkHood();
        }

        autoCommand = new AutoCommandFactory(commandFactory).getSelectedCommand(optionChooser.getSelected());
        CommandScheduler.getInstance().schedule(autoCommand);
        subsystemManager.getShooterSubsystem().initialize();
        subsystemManager.getDriveSubsystem().setDefaultCommand(new StopDrivingCommand(subsystemManager.getDriveSubsystem()));
    }

    @Override
    public void autonomousPeriodic() {
        subsystemManager.getDriveSubsystem().feedWatchDog();

    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void testInit(){

    }

    @Override
    public void testPeriodic() {

    }
    
}
