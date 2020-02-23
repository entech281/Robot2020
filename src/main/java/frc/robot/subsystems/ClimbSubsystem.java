package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotConstants;
import frc.robot.commands.EntechCommandBase;
import frc.robot.commands.SingleShotCommand;
import frc.robot.controllers.SparkMaxSettings;
import frc.robot.controllers.SparkMaxSettingsBuilder;
import frc.robot.controllers.SparkSpeedController;
import edu.wpi.first.wpilibj2.command.Command;

public class ClimbSubsystem extends BaseSubsystem {

    CANSparkMax winch;
    SparkSpeedController winchController;

    private Solenoid attachHookSolenoid;
    private Solenoid engageWinchSolenoid;

    Timer coord = new Timer();
    
    private boolean hardwareAvailability = RobotConstants.AVAILABILITY.climber;

    public Command pullRobotUp() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                winchController.setDesiredSpeed(0.7);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command stop() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                winchController.setDesiredSpeed(0);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command engageClutchCommand() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                engageClutchWithWinch();
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command AttachHook() {
        return new SingleShotCommand(this) {

            @Override
            public void doCommand() {
                raiseHook();
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command dropHookRaisingMech() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                attachHookSolenoid.set(false);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    @Override
    public void initialize() {
        if(hardwareAvailability){
            SparkMaxSettings motorSettings = SparkMaxSettingsBuilder.defaults().withPrettySafeCurrentLimits()
                    .brakeInNeutral().withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping()
                    .useSpeedControl().build();
            winch = new CANSparkMax(RobotConstants.CAN.INTAKE_MOTOR, MotorType.kBrushless);
            attachHookSolenoid = new Solenoid(RobotConstants.CAN.PCM_ID, RobotConstants.PNEUMATICS.ATTACH_SOLENOID);
            engageWinchSolenoid = new Solenoid(RobotConstants.CAN.PCM_ID, RobotConstants.PNEUMATICS.ENGAGE_WINCH);
            winchController = new SparkSpeedController(winch, motorSettings);
            winchController.configure();


            // The solenoid that controls the hook needs to be disengaged where as the
            // clutch needs to be engaged until we raise hook
            attachHookSolenoid.set(false);
            engageWinchSolenoid.set(true);
        }
    }

    public void raiseHook() {
        if(hardwareAvailability){
        engageWinchSolenoid.set(false);
        attachHookSolenoid.set(true);
        }
    }

    public void delay(double time) {
        Timer.delay(time);
    }

    public void engageClutchWithWinch() {
        if(hardwareAvailability){
        winchController.setDesiredSpeed(0.25);
        engageWinchSolenoid.set(true);
        }
    }

    public void stopClimbing() {
        if(hardwareAvailability){
            winchController.setDesiredSpeed(0);
        }
    }

}
