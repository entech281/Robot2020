package frc.robot.utils;

import frc.robot.RobotMap;
import frc.robot.posev2.TargetLocation;
import frc.robot.posev2.VisionData;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

// Template for data through serial port is
// boolean(targetfound) int(x) int(y) int(targetwidth) double(framerate) -\n

public class VisionDataProcessor {

    private String buffer = "";
    private String retval;
    private int visionDataMemory = 5;
    
    private FixedStack visionDataStack = new FixedStack(visionDataMemory);
    
    public VisionDataProcessor() {
        visionDataStack.add(RobotMap.ROBOT_DEFAULTS.VISION.DEFAULT_VISION_DATA);
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
    
    private boolean completeData(String data){
        if(data.length() == 0){
            return false;
        }
        return data.substring(data.length() - 1).equals("-");
    }
    
    private void parseBuffer(){
        String[] dataEntriesBuffer = buffer.split("\n");
        for(String data: dataEntriesBuffer){
            if(!completeData(data)){
                continue;
            }
            visionDataStack.add(calculateVisionData(data));
        }
        if(buffer.lastIndexOf("\n") != -1){
            buffer = buffer.substring(buffer.lastIndexOf("\n"));
        }
    }
    
    public VisionData getCurrentVisionData(){
        parseBuffer();
        return visionDataStack.peek();
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
