package frc.robot.subsystems;



 import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.RobotMap;



 public class IntakeSubsystem extends BaseSubsystem{
    private final double INTAKE_SPEED = 1;

    private final TalonSRX intakeMotor = new TalonSRX(RobotMap.CAN.INTAKE_MOTOR);

    @Override
    public void initialize() {
        intakeMotor.set(ControlMode.PercentOutput,0);
    }

    public void startIntakeMotor() {
        intakeMotor.set(ControlMode.PercentOutput,INTAKE_SPEED);
    }

    public void stopIntakeMotor(){
        intakeMotor.set(ControlMode.PercentOutput,0);
    }

 } 