package frc.robot.pid;

public class CappedLinearControl implements Controller{
    
    private double tolerance, threshold;
    private double minOutput, maxOutput;
    private boolean manage180 = false;
    
    public CappedLinearControl( double tolerance, double threshold, double minOutput, double maxOutput){
        this.tolerance = tolerance;
        this.threshold = threshold;
        this.minOutput = minOutput;
        this.maxOutput = maxOutput;
    }
     
    public void manage180Degrees(boolean enable) {
        manage180 = enable;
    }

    private double adjustDesiredFor180(double actual, double desired) {
        // This works by seeing if the actual angle is negative or positive
        // this is what comes from getYaw().  If the angle is negative, we change
        // the desired to -180.0.  If positive, desired goes to +git 180. 
        if (Math.abs(Math.abs(desired) - 180.0) < tolerance) {
            if (actual < 0.0) {
                desired = -180.0;
            } else {
                desired = 180.0;
            }
        }
        return desired;
    }
   
    public double getOutput(double actual, double desired){
        if (manage180) {
            desired = adjustDesiredFor180(actual, desired);
        }
        double delta = (actual - desired);
        double scaledOut = ((maxOutput - minOutput)*((Math.abs(delta) - tolerance)/(threshold-tolerance))) + minOutput;
        if ( delta > threshold){
            return -maxOutput;
        } else if ( delta < -threshold){
            return maxOutput;
        } else if (delta > tolerance) {
            return -scaledOut;
        } else if (delta < -tolerance) {
            return scaledOut;
        } else {
            return 0.0;
        }
    }
}