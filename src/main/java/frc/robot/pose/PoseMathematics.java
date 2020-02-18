package frc.robot.pose;

import frc.robot.RobotMap;

public class PoseMathematics {

    private static double robotWidth = RobotMap.DIMENSIONS.ROBOT_WIDTH;
    public final static double DEGREES_TO_RADIANS = Math.PI / 180;
    public final static double RADIANS_TO_DEGREES = 1 / DEGREES_TO_RADIANS;

    public static void setRobotWidthForTesting(double width) {
        robotWidth = width;
    }

    // X is positive to the right of the robot & y is positive forward
    public static RobotPose calculateRobotPositionChange(double inchesLeft, double inchesRight) {
        double deltaX;
        double deltaY;
        double deltaTheta;
        if (inchesLeft == inchesRight) {
            deltaX = 0;
            deltaY = inchesLeft;
            deltaTheta = 0;
        } else {
            double theta;
            double w;
            if (inchesRight > inchesLeft) {
                theta = (inchesRight - inchesLeft) / robotWidth;
                w = inchesLeft / theta;
                deltaX = (w + robotWidth / 2) * (1 - Math.cos(theta));
                deltaY = (w + robotWidth / 2) * Math.sin(theta);
                deltaTheta = -theta;
            } else {
                theta = (inchesLeft - inchesRight) / robotWidth;
                w = inchesRight / theta;
                deltaX = (w + robotWidth / 2) * (Math.cos(theta) - 1);
                deltaY = (w + robotWidth / 2) * Math.sin(theta);
                deltaTheta = theta;
            }
        }
        return new RobotPose(deltaY, deltaX, deltaTheta * RADIANS_TO_DEGREES);
    }

    // This is not commutative (I.E addposes(pose1, pose2) != addposes(pose2,
    // pose1))
    public static RobotPose addPoses(PositionReader pose1, PositionReader pose2) {
        RobotPose pose = new RobotPose();
        pose.setTheta(pose1.getTheta() + pose2.getTheta());
        pose.setHorizontal(
                pose1.getHorizontal()
                + Math.cos(pose1.getTheta() * DEGREES_TO_RADIANS) * pose2.getHorizontal()
                + Math.cos(pose1.getTheta() * DEGREES_TO_RADIANS + Math.PI / 2) * pose2.getForward());
        pose.setForward(pose1.getForward()
                + Math.sin(pose1.getTheta() * DEGREES_TO_RADIANS) * pose2.getHorizontal()
                + Math.sin(pose1.getTheta() * DEGREES_TO_RADIANS + Math.PI / 2) * pose2.getForward());

        return pose;
    }

}
