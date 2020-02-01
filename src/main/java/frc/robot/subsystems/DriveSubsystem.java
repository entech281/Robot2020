package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import frc.robot.DriveInstruction;
import frc.robot.DriveInstructionSource;
import frc.robot.RobotMap;
import frc.robot.controllers.SparkPositionController;
import frc.robot.controllers.SparkPositionControllerGroup;
import frc.robot.pose.EncoderPoseGenerator;
import frc.robot.subsystems.drive.FourSparkMaxWithSettings;
import frc.robot.pose.PositionReader;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


import edu.wpi.first.wpilibj.SpeedControllerGroup;


public class DriveSubsystem extends BaseSubsystem{

    DriveInstructionSource source;

    CANSparkMax m_frontLeft;
    //SparkPositionController pc_frontLeft;
    CANSparkMax m_rearLeft;
    //SparkPositionController pc_rearLeft;
	SpeedControllerGroup m_left;

    CANSparkMax m_frontRight;
    //SparkPositionController pc_frontRight;
    CANSparkMax m_rearRight;
    //SparkPositionController pc_rearRight;
    SpeedControllerGroup m_right;
    
    DifferentialDrive m_robotDrive;
    
    CANEncoder e_frontLeft;
    CANEncoder e_frontRight;
    CANEncoder e_rearLeft;
    CANEncoder e_rearRight;

    private FourSparkMaxWithSettings positiionModeSparks;

    private SparkPositionControllerGroup posController;

    private EncoderPoseGenerator poseGen;

    private double positionConfidence = 1.0;
    private double yawConfidence = 1.0;

    @Override
    public void initialize() {
        m_frontLeft = new CANSparkMax(RobotMap.CAN.FRONT_LEFT_MOTOR, MotorType.kBrushless);
        m_rearLeft = new CANSparkMax(RobotMap.CAN.REAR_LEFT_MOTOR, MotorType.kBrushless);
        m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);
    
        m_frontRight = new CANSparkMax(RobotMap.CAN.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
        
        m_rearRight = new CANSparkMax(RobotMap.CAN.REAR_RIGHT_MOTOR, MotorType.kBrushless);

        
        m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);
        

        m_robotDrive = new DifferentialDrive(m_left, m_right);
        /*
        SparkMaxSettings frontLeftPositionSettings = SparkMaxSettingsBuilder.defaults()
                                                    .withCurrentLimits(35)
                                                    .coastInNeutral()
                                                    .withDirections( RobotMap.INVERSIONS.FRONT_LEFT_ENCODER,RobotMap.INVERSIONS.FRONT_LEFT_MOTOR)
                                                    .limitMotorOutputs(1.0, -1.0)
                                                    .noMotorStartupRamping()
                                                    .useSpeedControl()
                                                    .build();
                                                
        SparkMaxSettings frontRightPositionSettings = SparkMaxSettingsBuilder.defaults()
                                                    .withCurrentLimits(35)
                                                    .coastInNeutral()
                                                    .withDirections(RobotMap.INVERSIONS.FRONT_RIGHT_ENCODER, RobotMap.INVERSIONS.FRONT_RIGHT_MOTOR)
                                                    .limitMotorOutputs(1.0, -1.0)
                                                    .noMotorStartupRamping()
                                                    .useSpeedControl()
                                                    .build();



        SparkMaxSettings rearLeftPositionSettings = SparkMaxSettingsBuilder.defaults()
                                                    .withCurrentLimits(35)
                                                    .coastInNeutral()
                                                    .withDirections(RobotMap.INVERSIONS.BACK_LEFT_ENCODER, RobotMap.INVERSIONS.BACK_LEFT_MOTOR)
                                                    .limitMotorOutputs(1.0, -1.0)
                                                    .noMotorStartupRamping()
                                                    .useSpeedControl()
                                                    .build();
                                                
        SparkMaxSettings rearRightPositionSettings = SparkMaxSettingsBuilder.defaults()
                                                    .withCurrentLimits(35)
                                                    .coastInNeutral()
                                                    .withDirections(RobotMap.INVERSIONS.BACK_RIGHT_ENCODER, RobotMap.INVERSIONS.BACK_RIGHT_MOTOR)
                                                    .limitMotorOutputs(1.0, -1.0)
                                                    .noMotorStartupRamping()
                                                    .useSpeedControl()
                                                    .build();

        pc_frontLeft = new SparkPositionController(m_frontLeft, frontLeftPositionSettings);
        pc_frontLeft.configure();
        pc_frontRight = new SparkPositionController(m_frontRight, frontRightPositionSettings);
        pc_frontRight.configure();
        pc_rearLeft = new SparkPositionController(m_rearLeft, rearLeftPositionSettings);
        pc_rearLeft.configure();
        pc_rearRight = new SparkPositionController(m_rearRight, rearRightPositionSettings);
        pc_rearRight.configure();

        */
        //posController = new SparkPositionControllerGroup(pc_frontLeft, pc_frontRight, pc_rearLeft, pc_rearRight);
        //positiionModeSparks = new FourSparkMaxWithSettings(m_frontLeft, m_rearLeft, m_frontRight, m_rearRight, frontLeftPositionSettings, rearLeftPositionSettings, frontRightPositionSettings, rearRightPositionSettings);


        //poseGen = new EncoderPoseGenerator(posController, positionConfidence, yawConfidence);
        e_frontLeft = m_frontLeft.getEncoder();
        e_frontRight = m_frontRight.getEncoder();
        e_rearLeft = m_rearLeft.getEncoder();
        e_rearRight = m_rearRight.getEncoder();

    }

    @Override 
    public void periodic(){
        logger.log("Front Left Encoder Ticks", e_frontLeft.getPosition());
        logger.log("Front Right Encoder Ticks", e_frontRight.getPosition());
        logger.log("Rear Left Encoder Ticks", e_rearLeft.getPosition());
        logger.log("Rear Right Encoder Ticks", e_rearRight.getPosition());
    }

    public void reset(){
        /*pc_frontLeft.resetPosition();
        pc_frontRight.resetPosition();
        pc_rearLeft.resetPosition();
        pc_rearRight.resetPosition();*/
        logger.log("Clicks per rotation", e_rearRight.getCountsPerRevolution());
    }
    

    public void updatePose(PositionReader pose){
        poseGen.updateFromOfficialPose(pose);
        poseGen.updatePose();
    }

    public void drive(DriveInstruction di) {
        m_robotDrive.arcadeDrive(di.getFoward(), di.getRotation());
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        m_left.setVoltage(leftVolts);
        m_right.setVoltage(-rightVolts);
        logger.log("leftVolts", leftVolts);
        logger.log("rightVolts", rightVolts);

        m_robotDrive.feed();
    }

    public EncoderPoseGenerator getEncoderPoseGenerator(){
        return this.poseGen;
    }
    public DifferentialDriveWheelSpeeds getWheelSpeeds(){
        return poseGen.getWheelSpeeds();
    }
}
