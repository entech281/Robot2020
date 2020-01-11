package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;



public class ClimbingSubsystem extends BaseSubsystem{
    private double m_speed = 0;

    
    private final CANSparkMax m_ClimberMotor = new CANSparkMax(RobotMap.CAN.SHOOTER_MOTOR, MotorType.kBrushless);
    
    @Override
    public void initialize() {
        m_ClimberMotor.set(0);
    }

    public void setClimberSpeed(final double speed) {
        m_speed = speed;
        m_ClimberMotor.set(m_speed);
    }

    public void climb(){
        m_ClimberMotor.set(m_speed);
    }


    public void stopClimbing(){
        setClimberSpeed(0);
    }

}