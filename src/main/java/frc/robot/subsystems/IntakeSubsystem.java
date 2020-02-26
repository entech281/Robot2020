package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotConstants;
import frc.robot.commands.EntechCommandBase;
import frc.robot.commands.SingleShotCommand;
import frc.robot.controllers.TalonSettings;
import frc.robot.controllers.TalonSettingsBuilder;
import frc.robot.controllers.TalonSpeedController;

import static frc.robot.RobotConstants.AVAILABILITY.*;
import frc.robot.pose.FieldPose;
import frc.robot.pose.RobotPose;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class IntakeSubsystem extends BaseSubsystem {
    
    private final ColorSensorV3  colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    private final ColorMatch  colorMatcher = new ColorMatch();
    private final Color YellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    private double COLOR_MATCH_THRESHOLD = 0.5;
    private int PROXIMITY_THRESHOLD = 1024;

    private boolean doneWaitingForNow = false;
    private boolean shouldWait = false;
    private double savedIntakeSpeed = 0;

    private double CURRENT_INTAKE_SPEED = 1;

    private double FULL_SPEED_FWD = 1;
    private double FULL_SPEED_BWD = -1;
    private double STOP_SPEED = 0;


    private WPI_TalonSRX intakeMotor;
    private TalonSpeedController intakeMotorController;
    
    private Solenoid deployIntake1;
    private Solenoid deployIntake2;

    public Command start() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                deployIntakeArms();
                setIntakeMotorSpeed(FULL_SPEED_FWD);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command stop() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                raiseIntakeArms();
                setIntakeMotorSpeed(STOP_SPEED);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    public Command reverse() {
        return new SingleShotCommand(this) {
            @Override
            public void doCommand() {
                setIntakeMotorSpeed(FULL_SPEED_BWD);
            }
        }.withTimeout(EntechCommandBase.DEFAULT_TIMEOUT_SECONDS);
    }

    @Override
    public void initialize() {
        if (intake) {
            
            intakeMotor = new WPI_TalonSRX(RobotConstants.CAN.INTAKE_MOTOR);
            TalonSettings motorSettings = TalonSettingsBuilder.defaults().withCurrentLimits(20, 15, 200).brakeInNeutral()
                    .withDirections(false, false).noMotorOutputLimits().noMotorStartupRamping().useSpeedControl().build();

            intakeMotorController = new TalonSpeedController(intakeMotor, motorSettings);
            intakeMotorController.configure();
            intakeMotor.set(ControlMode.PercentOutput, 0);
            
            deployIntake1 = new Solenoid(RobotConstants.CAN.PCM_ID, RobotConstants.CAN.INTAKE_SOL_1);
            deployIntake2 = new Solenoid(RobotConstants.CAN.PCM_ID, RobotConstants.CAN.INTAKE_SOL_2);
            
            deployIntake1.set(false);
            deployIntake2.set(false);

            colorMatcher.addColorMatch(YellowTarget);
        }
    }

    public void deployIntakeArms(){
        deployIntake1.set(true);
        deployIntake2.set(true);
    }

    public void raiseIntakeArms(){
        deployIntake1.set(false);
        deployIntake2.set(false);
    }
    
    @Override
    public void customPeriodic(RobotPose rPose, FieldPose fPose) {
        logger.log("Current command", getCurrentCommand());
        logger.log("Has Ball in Intake", isBallAtBeginningOfInput());
        if(intake){
            //if(s)
        }
    }
    

    public void setIntakeMotorSpeed(double desiredSpeed) {
        logger.log("Intake Motor speed", desiredSpeed);
        if (intake) {
            this.CURRENT_INTAKE_SPEED = desiredSpeed;
            intakeMotorController.setDesiredSpeed(this.CURRENT_INTAKE_SPEED);
        }
    }

    public boolean isBallAtBeginningOfInput(){
        Color detected = colorSensor.getColor();
        double yellowness = colorMatcher.matchClosestColor(detected).confidence;
        int closeness = colorSensor.getProximity();
        return yellowness > COLOR_MATCH_THRESHOLD && closeness > PROXIMITY_THRESHOLD;
    }

    
}
