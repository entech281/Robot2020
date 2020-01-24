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

    public double getForward() {
        return forward;
    }

    private double lateral = 0.0;
    private double forward = 0.0;

    public DriveInstruction(double forward, double lateral) {
        this.lateral = lateral;
        this.forward = forward;
        checkLimits();
    }

    protected void checkLimits() {
        checkLimit("lateral", this.lateral, UPPER_BOUND, LOWER_BOUND);
        checkLimit("forward", this.forward, UPPER_BOUND, LOWER_BOUND);
    }

    protected void checkLimit(String name, double value, double upper, double lower) {
        if (value > upper || value < lower) {
            throw new RuntimeException(name + ": must be between " + lower + " and " + upper + ": got" + value);
        }
    }
}