package frc.robot.utils;

public class ByteConverter{
    private static final long MEGA_BYTE = 1024L * 1024L;

    public static double convertBytesToMB(long bytes){
        return bytes/MEGA_BYTE;
    }
}