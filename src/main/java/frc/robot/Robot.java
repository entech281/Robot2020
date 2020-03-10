/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import java.sql.Time;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.pose.FieldPoseManager;
import frc.robot.pose.RobotPoseManager;
import frc.robot.preferences.AutoCommandFactory;
import frc.robot.preferences.SmartDashboardPathChooser;
import frc.robot.subsystems.CommandFactory;
import frc.robot.subsystems.DriveSubsystem;
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
    Command selfTestCommand;
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

        oi = new OperatorInterface(subsystemManager);
        commandFactory = new CommandFactory(subsystemManager);
        selfTestCommand = commandFactory.selfTestCommand();
    }

    @Override
    public void robotPeriodic() {
        //runs after everything else
        subsystemManager.updatePoses();

        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        selfTestCommand.cancel();        
        subsystemManager.getDriveSubsystem().setSpeedMode();
        subsystemManager.getNavXSubsystem().zeroYawMethod(false);
        if (autoCommand != null) {
            autoCommand.cancel();
        }
        if(!subsystemManager.getHoodSubsystem().knowsHome()){
            commandFactory.hoodHomeCommand().schedule();
        }
        subsystemManager.getVisionSubsystem().ensureConnected();

    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void autonomousInit() {
        selfTestCommand.cancel();
        subsystemManager.getVisionSubsystem().ensureConnected();

        subsystemManager.getDriveSubsystem().setPositionMode();

        if(!subsystemManager.getHoodSubsystem().knowsHome()){
            commandFactory.hoodHomeCommand().schedule();
        }

        autoCommand = new AutoCommandFactory(commandFactory).getSelectedCommand(optionChooser.getSelected());
        CommandScheduler.getInstance().schedule(autoCommand);
    }

    @Override
    public void autonomousPeriodic() {
        subsystemManager.getDriveSubsystem().feedWatchDog();

    }

    @Override
    public void disabledInit() {
        subsystemManager.getDriveSubsystem().setSpeedMode();
    }


    
    @Override
    public void testInit() {
        subsystemManager.getNavXSubsystem().zeroYawMethod(false);
        if (autoCommand != null) {
            autoCommand.cancel();
        }
        subsystemManager.getVisionSubsystem().ensureConnected();
        subsystemManager.getDriveSubsystem().setSpeedMode();
        CommandScheduler.getInstance().schedule(commandFactory.getStopShooterCommandGroup());
    }

    @Override
    public void testPeriodic() {
    }
    

    
    }
    

