package frc.robot.utils;

public class ByteConverter {

    private static final double MEGA_BYTE = Math.pow(10, 6);

    public static double convertBytesToMB(long bytes) {
        return bytes / MEGA_BYTE;
    }
}
