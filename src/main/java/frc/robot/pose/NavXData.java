package frc.robot.pose;

public class NavXData {

    private double angle;
    private boolean status;

    public static final NavXData EMPTY_NAVX_DATA = new NavXData(0, false);

    public NavXData(double angle, boolean status) {
        this.angle = angle;
        this.status = status;
    }
    

    public double getAngle() {
        return angle;
    }

    public boolean getValidity() {
        return status;
    }
}
