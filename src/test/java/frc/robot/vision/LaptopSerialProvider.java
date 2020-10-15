package frc.robot.vision;

import com.fazecast.jSerialComm.*;

public class LaptopSerialProvider implements SerialProvider {
    private SerialPort port;

    public LaptopSerialProvider(String portDescriptor){
        port = SerialPort.getCommPort​(portDescriptor);
        port.openPort();
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