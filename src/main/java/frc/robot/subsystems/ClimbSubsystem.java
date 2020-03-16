package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotConstants;
import frc.robot.controllers.SparkMaxSettings;
import frc.robot.controllers.SparkMaxSettingsBuilder;
import frc.robot.controllers.SparkSpeedController;
public class ClimbSubsystem extends BaseSubsystem {

    CANSparkMax winch;
    SparkSpeedController winchController;

    private Solenoid attachHookSolenoid;
    private Solenoid engageWinchSolenoid;


    @Override
    public void initialize() {
        SparkMaxSettings motorSettings = SparkMaxSettingsBuilder.defaults()
                .withPrettySafeCurrentLimits()
                .brakeInNeutral()
                .withDirections(false, false)
                .noMotorOutputLimits()
                .noMotorStartupRamping()
                .useSpeedControl()
                .build();
        winch = new CANSparkMax(RobotConstants.CAN.INTAKE_MOTOR, MotorType.kBrushless);
        attachHookSolenoid = new Solenoid(RobotConstants.CAN.PCM_ID, RobotConstants.PNEUMATICS.ATTACH_SOLENOID);
        engageWinchSolenoid = new Solenoid(RobotConstants.CAN.PCM_ID, RobotConstants.PNEUMATICS.ENGAGE_WINCH);
        winchController = new SparkSpeedController(winch, motorSettings, false);
        winchController.configure();

        // The solenoid that controls the hook needs to be disengaged where as the
        // clutch needs to be engaged until we raise hook
        attachHookSolenoid.set(false);
        engageWinchSolenoid.set(true);
        
    }


}
