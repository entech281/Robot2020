package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.RobotMap;



public class ShooterSubsystem extends BaseSubsystem{
    private double m_speed = 0;
    private double h_speed = 0;
    
    private final WPI_TalonSRX m_ShooterMotor  = new WPI_TalonSRX(RobotMap.CAN.SHOOTER_MOTOR);
    
    private WPI_TalonSRX m_HopperMotor = new WPI_TalonSRX(RobotMap.CAN.HOPPER_MOTOR);
    
    private final Solenoid m_solenoid1 = new Solenoid(RobotMap.CAN.PCM_ID, RobotMap.PNEUMATICS.solenoid1);
    private final Solenoid m_solenoid2 = new Solenoid(RobotMap.CAN.PCM_ID, RobotMap.PNEUMATICS.solenoid2);
    
    @Override
    public void initialize() {
        m_ShooterMotor.set(0);
        m_HopperMotor.set(0);
    }

    public void setShooterSpeed(final double speed){
        m_speed = speed;
        m_ShooterMotor.set(m_speed);
    }

    public void triggerOpen(){
        m_solenoid1.set(false);
        m_solenoid2.set(true);
    }

    public void triggerClose(){
        m_solenoid1.set(true);
        m_solenoid2.set(false);
    }

    public void shoot(){
        m_ShooterMotor.set(m_speed);
        triggerOpen();
        m_HopperMotor.set(h_speed);
    }

    public void setHopperSpeed(double speed){
        m_HopperMotor.set(speed);
    }

    public void stopShooting(){
        m_HopperMotor.set(0);
        triggerClose();
        Timer.delay(2);
        setShooterSpeed(0);
    }

}