package frc.robot;

/**
 * An instruction to the drive system. When represented as a seprate class, it
 * can be tested more easily.
 * 
 * @author dcowden
 *
 */
public class DriveInstruction {

    public static final double LOWER_BOUND = -1.0;
    public static final double UPPER_BOUND = 1.0;

    public double getLateral() {
        return lateral;
    }

    public double getRotation() {
        return rotation;
    }

    private double lateral = 0.0;
    private double rotation = 0.0;

    public DriveInstruction( double lateral, double rotation) {
        this.lateral = lateral;
        this.rotation = rotation;
    }
}