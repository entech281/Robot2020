package frc.robot.vision;

import frc.robot.RobotConstants;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.utils.JStruct;

public class VisionCommManager{

    private int numConsecutiveBadData = 0;
    private final int TOLERANCE_CONSECUTIVE_BAD_DATA = 20;

    private DataLogger logger = DataLoggerFactory.getLoggerFactory().createDataLogger("Vision Communication");
    private SerialProvider connection;
    private DataFrame latestTargetData;
    private DataFrame latestImage;
    private JStruct struct = new JStruct();

    private class DataFrame{
        public char type;
        public int size;
        public byte[] payload;

        public DataFrame(char type, int size, byte[] payload){
            this.type = type;
            this.size = size;
            this.payload = payload;
        }
    }

    public VisionCommManager(SerialProvider sp){
        connection = sp;
    }

    public void update(){
        try{
            var frame = getFrame();
            switch(frame.type){
                case 'D': latestTargetData = frame;
                    logger.warn("Recieved new frame");
                    logger.warn(new String(frame.payload));
                break;
                case 'I': latestImage = frame; break;
            }
        } catch(Exception e){
            logger.warn(e.getMessage());
        }
    }

    private DataFrame getFrame() throws Exception {
        long[] data = struct.unpack(">BI", connection.readBytes(5));
        char type = (char) data[0];
        int size = (int) data[1];
        byte[] payload = connection.readBytes(size);

        return new DataFrame(type,size,payload);
    }

    private VisionData parseVisionData(String data){
        String[] outputData = data.split(" ");
        boolean visionTargetFound = Boolean.parseBoolean(outputData[0]);

        double lateralOffset = -1;
        double verticalOffset = -1;
        double blobWidth = -1;

        if (visionTargetFound) {
            lateralOffset = RobotConstants.DEFAULTS.VISION.FRAME_WIDTH / 2 - Integer.parseInt(outputData[1]);
            verticalOffset = Double.parseDouble(outputData[2]);
            blobWidth = Double.parseDouble(outputData[3]);
        }

        return new VisionData(visionTargetFound, lateralOffset, verticalOffset, blobWidth);
    }

    public VisionData getLatestTargetData() {
        try{
            var ans = parseVisionData(new String(latestTargetData.payload));
            numConsecutiveBadData = 0;
            return ans;
        }
        catch(Exception e){
            numConsecutiveBadData ++;
            return RobotConstants.DEFAULTS.VISION.DEFAULT_VISION_DATA;
        }
    }

    public byte[] getLatestImage() {
        return latestImage.payload;
    }

    public boolean isGettingData(){
        return numConsecutiveBadData < TOLERANCE_CONSECUTIVE_BAD_DATA;
    }
}