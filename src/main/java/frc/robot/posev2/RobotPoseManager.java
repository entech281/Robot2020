package frc.robot.posev2;

import frc.robot.RobotMap;

public class RobotPoseManager {

    EncoderValues encoders;
    NavXData navXData;
    VisionData vData;
    WheelColorValue wColor;

    RobotPose pose = RobotMap.DIMENSIONS.START_POSE;
    private boolean navXWorking = true;

    public RobotPose getCurrentPose() {
        return pose;
    }

    public void update() {
        //do all the maths to get a new pose
        pose = PoseMathematics.addPoses(pose, PoseMathematics.calculateRobotPositionChange(encoders.getDeltaLeft(), encoders.getDeltaRight()));
        if (navXWorking) {
            pose = new RobotPose(pose.getRobotPosition().getForward(), pose.getRobotPosition().getHorizontal(), navXData.getAngle(), vData);
        }
    }

    public void updateNavxAngle(NavXData newNavxData) {
        this.navXData = newNavxData;
        navXWorking = this.navXData.getValidity();
    }

    public void updateEncoders(EncoderValues newEncoderValues) {
        this.encoders = newEncoderValues;
    }

    public void updateVisionData(VisionData newVisionData) {
        this.vData = newVisionData;
    }

    public void updateWheelColor(WheelColorValue newWheelColor) {
        this.wColor = newWheelColor;
    }
}
