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
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.CommandGroupFactory;
import frc.robot.commands.EntechCommandGroup;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.path.AutoPathFactory;
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
    private CommandGroupFactory commandFactor;

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
        subsystemManager.initAll();

        optionChooser = new SmartDashboardPathChooser();
        oi = new OperatorInterface(subsystemManager);
        commandFactor = new CommandGroupFactory((subsystemManager));
    }
    
    @Override
    public void teleopInit() {
        subsystemManager.getNavXSubsystem().zeroYawMethod(false);
        if (autoCommand != null) {
            autoCommand.cancel();
        }
        if(!subsystemManager.getVisionSubsystem().isConnected()){
            subsystemManager.getVisionSubsystem().tryConnect();
        }
        subsystemManager.getDriveSubsystem().setSpeedMode();
        CommandScheduler.getInstance().schedule(commandFactor.getStopShooterCommandGroup());
    }

    @Override
    public void disabledInit() {
        subsystemManager.getDriveSubsystem().setSpeedMode();
    }    
    
    @Override
    public void robotPeriodic() {
    }
    
    @Override
    public void teleopPeriodic() {
        subsystemManager.updatePoses();
        CommandScheduler.getInstance().run();
    }
    
    @Override
    public void autonomousInit() {
        if(!subsystemManager.getVisionSubsystem().isConnected()){
            subsystemManager.getVisionSubsystem().tryConnect();
        }
        AutoPathFactory factory = new AutoPathFactory(subsystemManager, new CommandGroupFactory(subsystemManager));
        autoCommand = AutoCommandFactory.getSelectedCommand(optionChooser.getSelected(),factory);
        CommandScheduler.getInstance().schedule(autoCommand);

    }

    @Override
    public void autonomousPeriodic() {
        subsystemManager.updatePoses();
        CommandScheduler.getInstance().run();

    }



}
