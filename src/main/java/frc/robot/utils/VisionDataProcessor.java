package frc.robot.utils;

import frc.robot.RobotConstants;
import frc.robot.pose.ShooterConfiguration;
import frc.robot.pose.TargetLocation;
import frc.robot.pose.VisionData;

public class VisionDataProcessor {

    private String buffer = "";
    private String retval;
    private int visionDataMemory = 5;

    private FixedStack visionDataStack = new FixedStack(visionDataMemory);

    public VisionDataProcessor() {
        visionDataStack.add(RobotConstants.ROBOT_DEFAULTS.VISION.DEFAULT_VISION_DATA);
    }

    public TargetLocation compute(VisionData vData, double angle) {
        double distance = 98.2 - 0.609 * vData.getVerticalOffset() + 0.0508 * Math.pow(vData.getVerticalOffset(), 2);
        return new TargetLocation(distance, angle);
    }

    //distance in inches
    public ShooterConfiguration calculateShooterConfiguration(TargetLocation location) {
        double distance = location.getDistanceToTarget();
        double speedRPM = 5350;
        double angle = 17.7 + 0.287 * distance - 7.07e-4 * Math.pow(distance, 2);

        return new ShooterConfiguration(angle, speedRPM);
    }

    public void addInput(String readings) {
        buffer += readings;
    }

    private boolean completeData(String data) {
        if (data.length() == 0) {
            return false;
        }
        return data.substring(data.length() - 2, data.length() - 1).equals("-");
    }

    private void parseBuffer() {
        String[] dataEntriesBuffer = buffer.split("\n");
        for (String data : dataEntriesBuffer) {
            if (!completeData(data)) {
                continue;
            }
            visionDataStack.add(calculateVisionData(data));
        }
        if (buffer.lastIndexOf("\n") != -1) {
            buffer = buffer.substring(buffer.lastIndexOf("\n"));
        }
    }

    public VisionData getCurrentVisionData() {
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
            lateralOffset = RobotConstants.ROBOT_DEFAULTS.VISION.FRAME_WIDTH / 2 - Integer.parseInt(outputData[1]);
            verticalOffset = Double.parseDouble(outputData[2]);
            blobWidth = Double.parseDouble(outputData[3]);
            frameRate = Double.parseDouble(outputData[4]);
        }
        return new VisionData(visionTargetFound, lateralOffset, verticalOffset, blobWidth);
    }
}
