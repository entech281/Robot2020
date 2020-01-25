package frc.robot.subsystems.drive;

import frc.robot.pid.CappedLinearControl;

public class AlignToYawFilter extends DriveFilter{
    public static final double ANGLE_THRESHOLD_DEGREES = 1;
    private CappedLinearControl cappedLinear = new CappedLinearControl(ANGLE_THRESHOLD_DEGREES, 15.0, 0.15, 0.4);
    double desiredAngle = 0.0;

    public AlignToYawFilter() {
        super(false);
        this.cappedLinear.manage180Degrees(true);
    }

    public void setDesiredYaw(double angle){
        desiredAngle = angle;
    }
}