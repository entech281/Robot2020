package frc.robot.utils;

import frc.robot.posev2.ShooterConfiguration;
import frc.robot.posev2.TargetLocation;
import frc.robot.posev2.VisionData;

public class VisionDataProcessor {

    public VisionDataProcessor() {
    }

    public TargetLocation compute(VisionData vData) {
        double distance = 0;
        double angle = 0;
        //Code here from empirical testing
        return new TargetLocation(distance, angle);
    }
    //distance in inches
    public ShooterConfiguration calculateShooterConfiguration(double distance){
        double speedRPM = 5350;
        double angle = 17.7 + 0.287*distance - 7.07e-4*Math.pow(distance, 2);
        
        return new ShooterConfiguration(angle, speedRPM);
    }
}
