package frc.robot.pose;


public class FieldPoseManager {

    private WheelColorValue colorWheel;
    private FieldPose pose = new FieldPose(colorWheel);

    public FieldPose getCurrentPose() {
        return pose;
    }

    public void updateFieldColor(WheelColorValue wheelColor) {
        this.colorWheel = wheelColor;
    }

}
