package frc.robot.utils;

import frc.robot.posev2.TargetLocation;
import frc.robot.posev2.VisionData;

public class VisionDataProcessor{
    public VisionDataProcessor(){
    }

    public TargetLocation compute(VisionData vData){
        double distance = 0;
        double angle = 0;
        //Code here from empirical testing
        return new TargetLocation(distance, angle);
    }
}