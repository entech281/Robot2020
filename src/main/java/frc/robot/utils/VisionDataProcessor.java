package frc.robot.utils;

import frc.robot.pose.TargetLocation;
import frc.robot.pose.VisionData;

public class VisionDataProcessor {

    public VisionDataProcessor() {
    }

    public TargetLocation compute(VisionData vData) {
        double distance = 0;
        double angle = 0;
        //Code here from empirical testing
        return new TargetLocation(distance, angle);
    }
}
