package frc.robot.vision;

import frc.robot.RobotConstants;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.utils.JStruct;

public class VisionCommManager{

    private static final int FRAME_SIZE = 5;
    private static final int TOLERANCE_CONSECUTIVE_BAD_DATA = 20;

    private int numConsecutiveBadData = 0;

    private final DataLogger logger = DataLoggerFactory.getLoggerFactory().createDataLogger("Vision Communication");
    private final SerialProvider connection;
    private DataFrame latestTargetData;
    private DataFrame latestImage;
    private DataFrame unfinishedFrame;
    private boolean hasUnfinishedFrame;
    
    private final JStruct struct = new JStruct();

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
    
    public void update() throws Exception{
        while(canContinueReading()){
            if(hasUnfinishedFrame){
                fillUnfinishedFrame();
                hasUnfinishedFrame = false;
            } else{
                readHeaders();
                hasUnfinishedFrame = true; 
            }
        }
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
    
    private boolean canContinueReading() {
        if(hasUnfinishedFrame){
            return connection.bytesAvailable() >= unfinishedFrame.size;
        } else{
            return connection.bytesAvailable() >= FRAME_SIZE;
        }
    }
    
    private void fillUnfinishedFrame() {
        byte[] payload = connection.readBytes(unfinishedFrame.size);
        unfinishedFrame.payload = payload;
        
        switch(unfinishedFrame.type){
            case 'D': latestTargetData = unfinishedFrame;
            break;
            case 'I': latestImage = unfinishedFrame; break;
        }
    }
    
    private void readHeaders() throws Exception {
        long[] data = struct.unpack(">BI", connection.readBytes(FRAME_SIZE));
        char type = (char) data[0];
        int size = (int) data[1];
        
        unfinishedFrame = new DataFrame(type, size, null);
    }
    
    @Deprecated
    private DataFrame getFrame() throws Exception {
        long[] data = struct.unpack(">BI", connection.readBytes(FRAME_SIZE));
        char type = (char) data[0];
        int size = (int) data[1];
        byte[] payload = connection.readBytes(size);

        return new DataFrame(type,size,payload);
    }
    
    @Deprecated
    private void processOneFrame(){
        try{
            
            var frame = getFrame();
            switch(frame.type){
                case 'D': latestTargetData = frame;
                break;
                case 'I': latestImage = frame; break;
            }
        } catch(Exception e){
            logger.warn(e.getMessage());
        }
    }
}