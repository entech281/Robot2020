package frc.robot.vision;

import com.fazecast.jSerialComm.*;

public class LaptopSerialProvider implements SerialProvider {
    private SerialPort port;

    public LaptopSerialProvider(String portDescriptor){
        this.port = SerialPort.getCommPort​(portDescriptor);
    }

    @Override
    public byte[] readAllBytes() {
        var ans = new byte[bytesAvailable()];
        port.readBytes(ans, bytesAvailable());
        return ans;
    }

    @Override
    public byte[] readBytes(int numBytes) {
        var ans = new byte[numBytes];
        port.readBytes(ans, numBytes);
        return ans;
    }

    @Override
    public void write(byte[] data) {
        port.writeBytes​(data, data.length);
    }

    @Override
    public int bytesAvailable() {
        return port.bytesAvailable​();
    }

}