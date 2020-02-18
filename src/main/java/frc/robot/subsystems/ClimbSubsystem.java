package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotMap;
import frc.robot.commands.EntechCommandBase;
import frc.robot.commands.SingleShotCommand;
import frc.robot.controllers.SparkMaxSettings;
import frc.robot.controllers.SparkMaxSettingsBuilder;
import frc.robot.controllers.SparkSpeedController;
import frc.robot.controllers.TalonSpeedController;
import frc.robot.posev2.FieldPose;
import frc.robot.posev2.RobotPose;
import edu.wpi.first.wpilibj2.command.Command;

public class ClimbSubsystem extends BaseSubsystem {

    CANSparkMax winch = new CANSparkMax(RobotMap.CAN.INTAKE_MOTOR, MotorType.kBrushless);
    SparkSpeedController winchController;

    private final Solenoid attachHookSolenoid = new Solenoid(RobotMap.CAN.PCM_ID, RobotMap.PNEUMATICS.ATTACH_SOLENOID);
    private final Solenoid engageWinchSolenoid = new Solenoid(RobotMap.CAN.PCM_ID,
            RobotMap.PNEUMATICS.ENGAGE_WINCH);

    Timer coord = new Timer();

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

        SparkMaxSettings motorSettings = SparkMaxSettingsBuilder.defaults().withPrettySafeCurrentLimits()
                .brakeInNeutral().withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping()
                .useSpeedControl().build();
        winchController = new SparkSpeedController(winch, motorSettings);
        winchController.configure();

        // The solenoid that controls the hook needs to be disengaged where as the
        // clutch needs to be engaged until we raise hook
        attachHookSolenoid.set(false);
        engageWinchSolenoid.set(true);

    }

    public void raiseHook() {
        engageWinchSolenoid.set(false);
        attachHookSolenoid.set(true);
    }

    public void delay(double time) {
        coord.delay(time);
    }

    public void engageClutchWithWinch() {
        winchController.setDesiredSpeed(0.25);
        engageWinchSolenoid.set(true);
    }

    public void stopClimbing() {
        winchController.setDesiredSpeed(0);
    }

}
