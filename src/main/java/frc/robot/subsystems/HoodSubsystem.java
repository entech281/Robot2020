/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.RobotConstants;
import frc.robot.controllers.TalonPositionController;
import frc.robot.utils.ClampedDouble;

/**
 *
 * @author dcowden
 */
public class HoodSubsystem extends BaseSubsystem {

    private WPI_TalonSRX hoodMotor;
    private TalonPositionController hoodMotorController;
    private final PositionAngleConverter positionConverter = new PositionAngleConverter();
    public static final double HOOD_TOLERANCE_COUNTS = 50;

    private final ClampedDouble desiredHoodPositionDegrees = ClampedDouble.builder()
            .bounds(0, 90)
            .withIncrement(5.0)
            .withValue(0.0).build();

    @Override
    public void initialize() {
        hoodMotor = new WPI_TalonSRX(RobotConstants.CAN.HOOD_MOTOR);

        hoodMotorController = new TalonPositionController(hoodMotor, frc.robot.RobotConstants.MOTOR_SETTINGS.HOOD, false);
        hoodMotorController.configure();
        hoodMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
        hoodMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
        hoodMotor.overrideLimitSwitchesEnable(true);

    }

    public boolean isUpperLimitHit() {
        return hoodMotor.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public boolean isLowerLimitHit() {
        return hoodMotor.getSensorCollection().isRevLimitSwitchClosed();
    }

    private void update() {
        hoodMotorController.setDesiredPosition(positionConverter.positionFromAngle(desiredHoodPositionDegrees.getValue()));
    }

    public void setHoodAngle(double desiredAngle) {
        desiredHoodPositionDegrees.setValue(desiredAngle);
        update();
    }

    public double getHoodAngle() {
        return positionConverter.angleFromPosition(hoodMotorController.getActualPosition());
    }

    public boolean atHoodPosition() {
        return Math.abs(hoodMotorController.getDesiredPosition() - hoodMotorController.getActualPosition()) < HOOD_TOLERANCE_COUNTS;
    }

    public void adjustHoodForward() {
        desiredHoodPositionDegrees.increment();
        update();
    }

    public void adjustHoodBackward() {
        desiredHoodPositionDegrees.decrement();
        update();
    }

    @Override
    public void periodic() {
        logger.log("Hood current position1", hoodMotorController.getActualPosition());
        logger.log("Hood Desired Position1", hoodMotorController.getDesiredPosition());
    }

    private static class LimitSwitchState {
        public static int closed = 1;
        public static int open = 0;
    }

}
