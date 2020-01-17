package frc.robot.subsystems;

 import com.revrobotics.CANSparkMax;
 import com.revrobotics.CANSparkMaxLowLevel.MotorType;

 import frc.robot.RobotMap;



 public class IntakeSubsystem extends BaseSubsystem{
    private final double INTAKE_SPEED = 1;

    private final CANSparkMax intakeMotor = new CANSparkMax(RobotMap.CAN.INTAKE_MOTOR, MotorType.kBrushless);

    @Override
    public void initialize() {
        intakeMotor.set(0);
    }

    public void startIntakeMotor() {
        intakeMotor.set(INTAKE_SPEED);
    }

    public void stopIntakeMotor(){
        intakeMotor.set(0);
    }

 } 