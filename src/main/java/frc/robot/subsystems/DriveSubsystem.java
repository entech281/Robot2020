package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


import edu.wpi.first.wpilibj.SpeedControllerGroup;


public class DriveSubsystem extends BaseSubsystem{

    CANSparkMax m_frontLeft;
    CANSparkMax m_rearLeft;
	SpeedControllerGroup m_left;

    CANSparkMax m_frontRight;
    CANSparkMax m_rearRight;
    SpeedControllerGroup m_right;
    
    DifferentialDrive m_robotDrive;
    
    CANEncoder e_frontLeft;
    CANEncoder e_frontRight;
    CANEncoder e_rearLeft;
    CANEncoder e_rearRight;

    @Override
    public void initialize() {
        m_frontLeft = new CANSparkMax(RobotMap.CAN.FRONT_LEFT_MOTOR, MotorType.kBrushless);
        m_rearLeft = new CANSparkMax(RobotMap.CAN.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
        m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);
    
        m_frontRight = new CANSparkMax(RobotMap.CAN.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
        m_rearRight = new CANSparkMax(RobotMap.CAN.REAR_RIGHT_MOTOR, MotorType.kBrushless);
        m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);
        
        m_robotDrive = new DifferentialDrive(m_left, m_right);
        e_frontLeft = m_frontLeft.getEncoder();
        e_frontRight = m_frontRight.getEncoder();
        e_rearLeft = m_rearLeft.getEncoder();
        e_rearRight = m_rearRight.getEncoder();
    }

    public void drive(double x, double y){
        m_robotDrive.arcadeDrive(y, x);
    }

    public Pose2d Update
        
}
