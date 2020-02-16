package frc.robot.posev2;

public class RobotPosition{
    private double forward;
    private double horizontal;
    private double theta;

    public RobotPosition(double forward, double horizontal, double theta){
        this.forward = forward;
        this.horizontal = horizontal;
        this.theta = theta;
    }

    public RobotPosition(){
        this.forward = 0;
        this.horizontal = 0;
        this.theta = 0;
    }

    public double getForward(){
        return forward;
    }

    public double getHorizontal(){
        return horizontal;
    }

    public double getTheta(){
        return theta;
    }
}