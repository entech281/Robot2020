package frc.robot.vision;

public interface SerialProvider{
    public byte[] readAllBytes();
    public byte[] readBytes( int numBytes);
    public void write(byte[] data);
}