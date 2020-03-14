package frc.robot.vision;

import frc.robot.utils.JStruct;

public class VisionCommManager{
    private SerialProvider connection;

    private DataFrame latestTargetData;
    private DataFrame latestImage;
    private JStruct struct = new JStruct();

    class DataFrame{
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
        
    }

    private DataFrame getFrame() throws Exception {
        long[] data = struct.unpack(">BI", connection.readBytes(5));
        
    }
}