package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.RobotMap;



public class ShooterSubsystem extends BaseSubsystem{
    private double m_speed = 0;

    
    private final WPI_TalonSRX m_ShooterMotor  = new WPI_TalonSRX(RobotMap.CAN.SHOOTER_MOTOR);
    

    @Override
    public void initialize() {
        m_ShooterMotor.set(0);
    }

    public void setShooterSpeed(final double speed){
        m_speed = speed;
        m_ShooterMotor.set(m_speed);
    }

    public void shoot(){
        m_ShooterMotor.set(m_speed);
    }


    public void stopShooting(){
        Timer.delay(2);
        setShooterSpeed(0);
    }

}