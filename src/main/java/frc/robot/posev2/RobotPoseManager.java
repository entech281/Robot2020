package frc.robot.posev2;

import frc.robot.RobotMap;

public class RobotPoseManager {

    private EncoderValues encoders;
    private EncoderValues lastEncoderValues = new EncoderValues(0, 0, 0, 0);
    private NavXData navXData;
    private VisionData vData = RobotMap.DIMENSIONS.DEFAULT_VISION_DATA;
    private WheelColorValue wColor;

    private RobotPose pose = RobotMap.DIMENSIONS.START_POSE;
    private boolean navXWorking = true;

    public RobotPose getCurrentPose() {
        return pose;
    }

    public void update() {
        //do all the maths to get a new pose
        pose = new RobotPose(PoseMathematics.addPoses(pose, PoseMathematics.calculateRobotPositionChange(encoderDeltaLeft(), getEncodersRight())).getRobotPosition(), vData);
        if (navXWorking) {
            RobotPosition withNavXPosition = new RobotPosition(pose.getRobotPosition().getForward(), pose.getRobotPosition().getHorizontal(), navXData.getAngle());
            pose = new RobotPose(withNavXPosition, vData);
        }
    }

    public void updateNavxAngle(NavXData newNavxData) {
        this.navXData = newNavxData;
        navXWorking = this.navXData.getValidity();
    }

    public void updateEncoders(EncoderValues newEncoderValues) {
        this.lastEncoderValues = this.encoders;
        this.encoders = newEncoderValues;
    }

    public void updateVisionData(VisionData newVisionData) {
        this.vData = newVisionData;
    }

    public void updateWheelColor(WheelColorValue newWheelColor) {
        this.wColor = newWheelColor;
    }

    public double encoderDeltaLeft() {
        return (this.encoders.getLeftFront() + this.encoders.getLeftRear()) / 2
                - (this.lastEncoderValues.getLeftFront() + this.lastEncoderValues.getLeftRear()) / 2;
    }

    public double getEncodersRight() {
        return (this.encoders.getRightFront() + this.encoders.getRightRear()) / 2
                - (this.lastEncoderValues.getRightFront() + this.lastEncoderValues.getRightRear()) / 2;
    }

}
