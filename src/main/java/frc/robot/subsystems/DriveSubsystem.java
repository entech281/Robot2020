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

    CANSparkMax  frontLeft;
    CANSparkMax  rearLeft;
	SpeedControllerGroup  left;

    CANSparkMax  frontRight;
    CANSparkMax  rearRight;
    SpeedControllerGroup  right;
    
    DifferentialDrive  robotDrive;
    
    CANEncoder  frontLeftEncoder;
    CANEncoder  frontRightEncoder;
    CANEncoder  rearLeftEncoder;
    CANEncoder  rearRightEncoder;

    @Override
    public void initialize() {
         frontLeft = new CANSparkMax(RobotMap.CAN.FRONT_LEFT_MOTOR, MotorType.kBrushless);
         rearLeft = new CANSparkMax(RobotMap.CAN.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
         left = new SpeedControllerGroup( frontLeft,  rearLeft);
    
         frontRight = new CANSparkMax(RobotMap.CAN.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
         rearRight = new CANSparkMax(RobotMap.CAN.REAR_RIGHT_MOTOR, MotorType.kBrushless);
         right = new SpeedControllerGroup( frontRight,  rearRight);
        
         robotDrive = new DifferentialDrive( left,  right);
         frontLeftEncoder =  frontLeft.getEncoder();
         frontRightEncoder =  frontRight.getEncoder();
         rearLeftEncoder =  rearLeft.getEncoder();
         rearRightEncoder =  rearRight.getEncoder();
    }

    public void drive(double x, double y){
         robotDrive.arcadeDrive(y, x);
    }
}
