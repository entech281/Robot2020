package frc.robot.controllers;

import frc.robot.subsystems.Position;
import frc.robot.utils.EncoderInchesConverter;

public class SparkPositionControllerGroup {

    private SparkPositionController frontLeft;
    private SparkPositionController frontRight;
    private SparkPositionController rearLeft;
    private SparkPositionController rearRight;
    public static final double FRONT_RIGHT_ADJUST = 1.0;
    public static final double REAR_RIGHT_ADJUST = 1.0;

    public SparkPositionControllerGroup(SparkPositionController fL, SparkPositionController fR, SparkPositionController rL, SparkPositionController rR) {
        this.frontLeft = fL;
        this.frontRight = fR;
        this.rearLeft = rL;
        this.rearRight = rR;
    }

    public void configureAll() {
        frontRight.configure();
        frontLeft.configure();
        rearLeft.configure();
        rearRight.configure();
    }

    public void resetMode() {
        frontRight.resetMode();
        frontLeft.resetMode();
        rearLeft.resetMode();
        rearRight.resetMode();
    }

    public void resetPosition() {
        frontLeft.resetPosition();
        frontRight.resetPosition();
        rearLeft.resetPosition();
        rearRight.resetPosition();
    }

    public void setDesiredPosition(int leftPose, int rightPose, boolean isRelative) {
        if (isRelative) {
            resetPosition();
        }

        frontLeft.setDesiredPosition(leftPose);
        rearLeft.setDesiredPosition(leftPose);
        frontRight.setDesiredPosition(rightPose * FRONT_RIGHT_ADJUST);
        rearRight.setDesiredPosition(rightPose * REAR_RIGHT_ADJUST);
    }

    public double getLeftCurrentPosition(EncoderInchesConverter converter) {
        double left = converter.toInches(computeLeftEncoderCounts());
        return left;
    }

    public double getRightCurrentPosition(EncoderInchesConverter converter) {
        double right = converter.toInches(computeRightEncoderCounts());
        return right;
    }

    public int computeLeftEncoderCounts() {
        int total = 0;
        int count = 0;
        Integer pos = frontLeft.getActualPosition();
        if (pos != null && Math.abs(pos) > 0) {
            total += pos;
            count += 1;
        }
        pos = rearLeft.getActualPosition();
        if (pos != null && Math.abs(pos) > 0) {
            total += pos;
            count += 1;
        }
        if (count == 0) {
            return 0;
        } else {
            return total / count;
        }

    }

    public int computeRightEncoderCounts() {
        int total = 0;
        int count = 0;
        Integer pos = frontRight.getActualPosition();
        if (pos != null && Math.abs(pos) > 0) {
            total += pos;
            count += 1;
        }
        pos = rearRight.getActualPosition();
        if (pos != null && Math.abs(pos) > 0) {
            total += pos;
            count += 1;
        }
        if (count == 0) {
            return 0;
        } else {
            return -total / count;
        }
    }
}
