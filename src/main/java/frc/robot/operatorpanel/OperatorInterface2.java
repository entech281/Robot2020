/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.operatorpanel;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.RobotConstants;
import static frc.robot.RobotConstants.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.operatorpanel.OperatorInstruction.ShooterMode;


public class OperatorInterface2 {    

    public static final double DOUBLE_NOT_SELECTED = -999.0;
    //drive controls
    private Joystick driveStick = new Joystick(RobotConstants.GAMEPAD.DRIVER_JOYSTICK);
    private JoystickAxis shooterSpeed = new JoystickAxis(driveStick,driveStick.getThrottleChannel()); //drive throttle but ideally operator panel
    private JoystickAxis driveX  = new JoystickAxis(driveStick,driveStick.getXChannel()); //dr
    private JoystickAxis driveY = new JoystickAxis(driveStick,driveStick.getYChannel()); //dr
    private MomentaryButton intakeSpin = new MomentaryButton(driveStick,0); //dr while held
    private MomentaryButton outtakeSpin = new MomentaryButton(driveStick,0); //dr while held
    private MomentaryButton intakeArmToggle = new MomentaryButton(driveStick,0); //dr toggle
    private MomentaryButton yawAutoAlign = new MomentaryButton(driveStick,0); //dr press
    private MomentaryButton yawNudgeLeft = new MomentaryButton(driveStick,0); //dr press
    private MomentaryButton yawNudgeRight = new MomentaryButton(driveStick,0);     //dr press
    
    //operator controls
    private Joystick operatorPanel = new Joystick(RobotConstants.GAMEPAD.OPERATOR_PANEL);     
    private ThreePositionToggleSwitch hoodMode = new ThreePositionToggleSwitch(operatorPanel, 0,0); //op
    private ToggleSwitch hoodPreset = new ToggleSwitch(operatorPanel,0); //op
    private ThreePositionMomentarySwitch hoodNudge = new ThreePositionMomentarySwitch(operatorPanel,0,0); //op
    private MomentaryButton homeButton = new MomentaryButton(operatorPanel,0); //op
    private MomentaryButton fireButton = new MomentaryButton(operatorPanel,0); //op
    private ToggleSwitch shooterOn = new ToggleSwitch(operatorPanel,0); //op
    


    public OperatorInstruction createInstruction(){
        OperatorInstruction oi = new OperatorInstruction();
        
        //handle shooting and shooter speed
        oi.shooterMode = shooterOn.get() ? ShooterMode.MANUAL : ShooterMode.AUTO;        
        if ( oi.shooterMode == ShooterMode.MANUAL){
            oi.shooterSpeedRPM = MOTORCONTROLLER_VALUES.SHOOTER_MOTOR.SHOOTER_MAX_RPM * shooterSpeed.get();
        }
        else{
            oi.shooterSpeedRPM = DOUBLE_NOT_SELECTED;
        }
        
        //handle drive commands
        if ( yawNudgeLeft.get() ){
            
        }
        else if ( yawNudgeRight.get() ){
            
        }
        else if ( yawAutoAlign.get() ){
            
        }
        else{
            oi.forwardCommand = driveStick.getX();
            oi.turnCommand = driveStick.getY();            
        }

        oi.intakeArmPosition = intakeArmToggle.get() ? oi.intakeArmPosition.UP : oi.intakeArmPosition.DOWN;
        
        return oi;
    }

    

    
}
