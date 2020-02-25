/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.EntechCommandGroup;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.preferences.AutoCommandFactory;
import frc.robot.preferences.SmartDashboardPathChooser;
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
    private SubsystemManager subsystemManager = new SubsystemManager();

    private SmartDashboardPathChooser optionChooser;
    OperatorInterface oi;
    Command autoCommand;

    @Override
    public void robotInit() {
        DataLoggerFactory.configureForMatch();
        this.logger = DataLoggerFactory.getLoggerFactory().createDataLogger("Robot Main Loop");
        subsystemManager.initAll();

        optionChooser = new SmartDashboardPathChooser();
        oi = new OperatorInterface(subsystemManager);
    }

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void teleopInit() {
        subsystemManager.getNavXSubsystem().zeroYawOfNavX(false);
        if (autoCommand != null) {
            autoCommand.cancel();
        }
        subsystemManager.getDriveSubsystem().setSpeedMode();
    }

    @Override
    public void teleopPeriodic() {
        subsystemManager.periodicAll();
        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        autoCommand = AutoCommandFactory.getSelectedCommand(optionChooser.getSelected());
        CommandScheduler.getInstance().schedule(autoCommand);
    }

    @Override
    public void autonomousPeriodic() {
        subsystemManager.periodicAll();
        CommandScheduler.getInstance().run();

    }

    @Override
    public void disabledInit() {
        subsystemManager.getDriveSubsystem().setSpeedMode();
    }

}
