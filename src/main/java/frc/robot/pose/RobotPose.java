package frc.robot.pose;

public class RobotPose implements SensorWriter, SensorReader, PositionWriter, PositionReader, PositionAccessor{
    
    private double theta;
    private double lateral; 
    private double horizontal;
    private boolean matchesRed;
    private boolean matchesBlue;
    private boolean matchesGreen;
    
    @Override
    public double getTheta() {
        return this.theta;
    }
    @Override
    public void setTheta(double theta) {
        this.theta = theta;
    }
    @Override

    public double getLateral() {
        return this.lateral;
    }
    @Override
    public void setLateral(double lateral) {
        this.lateral = lateral;
    }
    @Override
    public double getHorizontal() {
        return this.horizontal;
    }

    @Override
    public void setHorizontal(double horizontal) {
        this.horizontal = horizontal;
    }

    @Override
    public boolean isRedMatch() {
        return matchesRed;
    }
    @Override
    public void setMatchesRed(boolean matchesRed) {
        this.matchesRed = matchesRed;
    }
    @Override
    public boolean isBlueMatch() {
        return matchesBlue;
    }
    @Override
    public void setMatchesBlue(boolean matchesBlue) {
        this.matchesBlue = matchesBlue;
    }
    @Override   
    public boolean isGreenMatch() {
        return matchesGreen;
    }
    @Override
    public void setMatchesGreen(boolean matchesGreen) {
        this.matchesGreen = matchesGreen;
    }
}