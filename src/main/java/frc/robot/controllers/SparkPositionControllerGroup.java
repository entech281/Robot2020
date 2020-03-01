package frc.robot.controllers;

import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.path.Position;
import frc.robot.utils.EncoderInchesConverter;

public class SparkPositionControllerGroup {

    private DataLogger logger = DataLoggerFactory.getLoggerFactory().createDataLogger("SparkPositionControllerGroup");

    private SparkPositionController frontLeft;
    private SparkPositionController frontRight;
    private SparkPositionController rearLeft;
    private SparkPositionController rearRight;
    public static final double RIGHT_ADJUST = -1.0;
    public static final double LEFT_ADJUST = 1.0;

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


    public void resetPosition() {
        frontLeft.resetPosition();
        frontRight.resetPosition();
        rearLeft.resetPosition();
        rearRight.resetPosition();
    }

    public void setDesiredPosition(double leftPose, double rightPose, boolean isRelative) {
        if (isRelative) {
            resetPosition();
        }

        frontLeft.setDesiredPosition(leftPose * LEFT_ADJUST);
        rearLeft.setDesiredPosition(leftPose * LEFT_ADJUST);
        frontRight.setDesiredPosition(rightPose * RIGHT_ADJUST);
        rearRight.setDesiredPosition(rightPose * RIGHT_ADJUST);
        logger.log("set position left", leftPose * LEFT_ADJUST);
        logger.log("set position right", rightPose * RIGHT_ADJUST);
    }

    public double getLeftCurrentPosition(EncoderInchesConverter converter) {
        double left = converter.toInches(computeLeftEncoderCounts());
        return left;
    }

    public double getRightCurrentPosition(EncoderInchesConverter converter) {
        double right = converter.toInches(computeRightEncoderCounts());
        return right;
    }

    public double computeLeftEncoderCounts() {
        double total = 0;
        int count = 0;
        Double pos = frontLeft.getActualPosition();
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

    public double computeRightEncoderCounts() {
        double total = 0;
        int count = 0;
        Double pos = frontRight.getActualPosition();
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

    public Position getCurrentPosition(EncoderInchesConverter encoderConverter) {
        return new Position(getLeftCurrentPosition(encoderConverter), getRightCurrentPosition(encoderConverter));
    }
}
