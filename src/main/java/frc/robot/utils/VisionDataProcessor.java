package frc.robot.utils;

import frc.robot.RobotMap;
import frc.robot.posev2.TargetLocation;
import frc.robot.posev2.VisionData;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

public class VisionDataProcessor {

    private String buffer = "";
    private String retval = "False -1 -1 -1 -1";

    
    public VisionDataProcessor() {
    }

    public TargetLocation compute(VisionData vData) {
        double distance = 0;
        double angle = 0;
        //Code here from empirical testing
        return new TargetLocation(distance, angle);
    }

    public void addInput(String readings){
        buffer += readings;
    }
    
    public VisionData getCurrentVisionData(){
        int lastInd = buffer.lastIndexOf("\n");
        int secLastInd = buffer.substring(0, lastInd - 1).lastIndexOf("\n");
        if(lastInd != -1 && secLastInd != -1){
            retval = buffer.substring(secLastInd + 1, lastInd);
        }
        buffer = buffer.substring(lastInd - 1);
        return calculateVisionData(retval);
    }
     
    private VisionData calculateVisionData(String readings) {
        String[] outputData = readings.split(" ");
        boolean visionTargetFound = Boolean.parseBoolean(outputData[0]);

        double lateralOffset = -1;
        double verticalOffset = -1;
        double blobWidth = -1;

        double frameRate = 0.0;

        if (visionTargetFound) {
            lateralOffset = Math.abs(RobotMap.ROBOT_DEFAULTS.VISION.FRAME_WIDTH / 2 - Integer.parseInt(outputData[1]));
            verticalOffset = Double.parseDouble(outputData[2]);
            blobWidth = Double.parseDouble(outputData[3]);
            frameRate = Double.parseDouble(outputData[4]);
        }
        return new VisionData(visionTargetFound, lateralOffset, verticalOffset, blobWidth);
    }
}
