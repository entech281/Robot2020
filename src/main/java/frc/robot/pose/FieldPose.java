package frc.robot.pose;


public class FieldPose {

    private WheelColorValue colorWheel;

    public FieldPose(WheelColorValue colorWheel2) {

	}

	public void updateFieldPose(WheelColorValue colorWheel2) {
        this.colorWheel = colorWheel2;
    }

	public WheelColorValue getColorWheel() {
        return colorWheel;
    }


}
