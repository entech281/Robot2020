package frc.robot.subsystems;

import frc.robot.controllers.SparkMaxSettings;
import frc.robot.controllers.SparkMaxSettingsBuilder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.DriveInstruction;
import frc.robot.DriveInstructionSource;
import frc.robot.RobotMap;
import frc.robot.controllers.SparkPositionController;
import frc.robot.controllers.SparkPositionControllerGroup;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.EntechCommandBase;
import frc.robot.commands.SingleShotCommand;
import frc.robot.posev2.*;



public class DriveSubsystem extends BaseSubsystem{

    private CANSparkMax frontLeftSpark;
    private CANSparkMax frontRightSpark;
    private CANSparkMax rearLeftSpark;
    private CANSparkMax rearRightSpark;

    private SparkPositionController frontLeftPositionController;
    private SparkPositionController rearLeftPositionController;
    private SparkPositionController frontRightPositionController;
    private SparkPositionController rearRightPositionController;
    
    private CANEncoder frontLeftEncoder;
    private CANEncoder frontRightEncoder;
    private CANEncoder rearLeftEncoder;
    private CANEncoder rearRightEncoder;

    
    private SpeedControllerGroup leftSpeedController;
    private SpeedControllerGroup rightSpeedController;    
    private DifferentialDrive robotDrive;    

    private SparkMaxSettings speedSettings = SparkMaxSettingsBuilder.defaults()
                                                    .withCurrentLimits(35)
                                                    .coastInNeutral()
                                                    .withDirections(false, false)
                                                    .noMotorOutputLimits()
                                                    .noMotorStartupRamping()
                                                    .useSpeedControl()
                                                    .build();
    
    private SparkMaxSettings positionSettings = SparkMaxSettingsBuilder.defaults()
                                                    .withCurrentLimits(35)
                                                    .coastInNeutral()
                                                    .withDirections(false, false)
                                                    .noMotorOutputLimits()
                                                    .noMotorStartupRamping()
                                                    .useSpeedControl()
                                                    .build(); 
    
    public Command reset(){
        return new SingleShotCommand(this){
        
            @Override
            public void doCommand() {
                frontLeftPositionController.resetPosition();
                frontRightPositionController.resetPosition();
                rearLeftPositionController.resetPosition();
                rearRightPositionController.resetPosition();
                logger.log("Clicks per rotation", rearRightEncoder.getCountsPerRevolution());
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);

    }                                                
    
    @Override
    public void initialize() {
        frontLeftSpark = new CANSparkMax(RobotMap.CAN.FRONT_LEFT_MOTOR, MotorType.kBrushless);
        rearLeftSpark = new CANSparkMax(RobotMap.CAN.REAR_LEFT_MOTOR, MotorType.kBrushless);
        leftSpeedController = new SpeedControllerGroup(frontLeftSpark, rearLeftSpark);
    
        frontRightSpark = new CANSparkMax(RobotMap.CAN.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
        rearRightSpark = new CANSparkMax(RobotMap.CAN.REAR_RIGHT_MOTOR, MotorType.kBrushless);
        rightSpeedController = new SpeedControllerGroup(frontRightSpark, rearRightSpark);
        
        frontLeftEncoder = frontLeftSpark.getEncoder();
        frontRightEncoder = frontRightSpark.getEncoder();
        rearLeftEncoder = rearLeftSpark.getEncoder();
        rearRightEncoder = rearRightSpark.getEncoder();

        robotDrive = new DifferentialDrive(leftSpeedController, rightSpeedController);                                            

        frontLeftPositionController = new SparkPositionController(frontLeftSpark, positionSettings);
        frontRightPositionController = new SparkPositionController(frontRightSpark, positionSettings);
        rearLeftPositionController = new SparkPositionController(rearLeftSpark, positionSettings);
        rearRightPositionController = new SparkPositionController(rearRightSpark, positionSettings);

        reset();
    }
    public void setSpeedMode(){
        speedSettings.configureSparkMax(frontLeftSpark);
        speedSettings.configureSparkMax(frontRightSpark);
        speedSettings.configureSparkMax(rearLeftSpark);
        speedSettings.configureSparkMax(rearRightSpark);        
    }
    public void setPositionMode(){
        positionSettings.configureSparkMax(frontLeftSpark);
        positionSettings.configureSparkMax(frontRightSpark);
        positionSettings.configureSparkMax(rearLeftSpark);
        positionSettings.configureSparkMax(rearRightSpark);
    }
    
    public EncoderValues getEncoderValues(){
       return new EncoderValues(frontLeftEncoder.getPosition(), 
                rearLeftEncoder.getPosition(), 
                frontRightEncoder.getPosition(), 
                rearRightEncoder.getPosition());
    }
   
    @Override 
    public void customPeriodic(RobotPose rp, FieldPose fp) {
        
        logger.log("Front Left Encoder Ticks", frontLeftEncoder.getPosition());
        logger.log("Front Right Encoder Ticks", frontRightEncoder.getPosition());
        logger.log("Rear Left Encoder Ticks", rearLeftEncoder.getPosition());
        logger.log("Rear Right Encoder Ticks", rearRightEncoder.getPosition());
        
    }

    public void drive(DriveInstruction di) {
        robotDrive.arcadeDrive(di.getFoward(), di.getRotation());
    }
}