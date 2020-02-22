package frc.robot.pose;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotConstants;

public class RobotPoseManager {

    private EncoderValues encoders = new EncoderValues(0, 0, 0, 0);
    private EncoderValues lastEncoderValues = new EncoderValues(0, 0, 0, 0);
    private NavXData navXData = new NavXData(0, true);
    private VisionData vData = RobotConstants.ROBOT_DEFAULTS.VISION.DEFAULT_VISION_DATA;
    private WheelColorValue wColor;

    private RobotPose pose = RobotConstants.ROBOT_DEFAULTS.START_POSE;
    private boolean navXWorking = true;
    private int count = 0;

    public RobotPose getCurrentPose() {
        return pose;
    }

    public void update() {
        //do all the maths to get a new pose
        pose = new RobotPose(PoseMathematics.addPoses(pose.getRobotPosition(), PoseMathematics.calculateRobotPositionChange(encoderDeltaLeft(), getEncodersRight())), vData);
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
        count += 1;
        SmartDashboard.putNumber("Number of visionUpdates", count);
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
