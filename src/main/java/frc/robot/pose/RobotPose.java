package frc.robot.pose;

public class RobotPose{
    private double theta;
    private double x; 
    private double y;
    private boolean matchesRed;
    private boolean matchesBlue;
    private boolean matchesGreen;


    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isMatchesRed() {
        return matchesRed;
    }

    public void setMatchesRed(boolean matchesRed) {
        this.matchesRed = matchesRed;
    }

    public boolean isMatchesBlue() {
        return matchesBlue;
    }

    public void setMatchesBlue(boolean matchesBlue) {
        this.matchesBlue = matchesBlue;
    }

    public boolean isMatchesGreen() {
        return matchesGreen;
    }

    public void setMatchesGreen(boolean matchesGreen) {
        this.matchesGreen = matchesGreen;
    }
}