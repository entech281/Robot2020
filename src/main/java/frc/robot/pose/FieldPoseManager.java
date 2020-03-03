package frc.robot.pose;

public class FieldPoseManager {

    private ColorWheel colorWheel = new ColorWheel();
    private FieldPose pose = new FieldPose(colorWheel);

    public FieldPose getCurrentPose() {
        return pose;
    }

    public void setCurrentColorWheel(ColorWheel wheelColor) {
        this.colorWheel = wheelColor;
    }

}
