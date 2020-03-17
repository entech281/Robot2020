package frc.robot.vision;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class WpilibSerialProvider implements SerialProvider{

    private SerialPort connection;

    public WpilibSerialProvider(int baudRate, int timeout, Port port){
        connection = new SerialPort(baudRate, port);
        connection.setTimeout(timeout);
    }

    @Override
    public byte[] readAllBytes() {
        return connection.read(connection.getBytesReceived());
    }

    @Override
    public byte[] readBytes(int numBytes) {
        return connection.read(numBytes);
    }

    @Override
    public void write(byte[] data) {
        connection.write(data, data.length);
    }

}