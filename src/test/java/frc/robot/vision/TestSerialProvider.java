package frc.robot.vision;

import java.nio.ByteBuffer;

class TestSerialProvider implements SerialProvider {
    private ByteBuffer buffer;
    public void setBuffer(byte[] buffer){
        this.buffer = ByteBuffer.wrap(buffer);
    }

    

    @Override
    public byte[] readAllBytes() {
        var ans = new byte[buffer.remaining()];
        buffer.get(ans);
        return ans;
    }

    @Override
    public byte[] readBytes(int numBytes) {
        var ans = new byte[numBytes];
        buffer.get(ans,0,numBytes);
        return ans;
    }

    @Override
    public void write(byte[] data) {
        System.out.println(new String(data));
    }

}