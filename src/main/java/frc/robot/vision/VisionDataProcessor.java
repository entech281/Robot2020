package frc.robot.vision;

import frc.robot.pose.ShooterConfiguration;
import frc.robot.pose.TargetLocation;
import frc.robot.utils.FixedStack;

        
public class VisionDataProcessor {
    private int visionDataMemory = 5;

    private FixedStack visionDataStack = new FixedStack(visionDataMemory);

    public VisionDataProcessor() {
        visionDataStack.add(VisionData.DEFAULT_VISION_DATA);
    }

    public TargetLocation compute(VisionData vData, double angle) {
        double distance = 108 - 0.505 * vData.getVerticalOffset() + 0.0799 * Math.pow(vData.getVerticalOffset(), 2);
        return new TargetLocation(distance, angle);
    }

    //distance in inches
    public ShooterConfiguration calculateShooterConfiguration(TargetLocation location) {
        double distance = location.getDistanceToTarget();
        double speedRPM = 5350;
        double angle = 19 + 0.287 * distance - 7.07e-4 * Math.pow(distance, 2);

        return new ShooterConfiguration(angle, speedRPM);
    }
}
