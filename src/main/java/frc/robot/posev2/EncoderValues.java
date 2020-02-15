package frc.robot.posev2;


public class EncoderValues {
    private double lastLeft = 0.0;
    private double lastRight = 0.0;

    public EncoderValues(double leftFront, double leftRear, double rightFront, double rightRear){
        this.leftFront = leftFront;
        this.leftRear = leftRear;
        this.rightFront = rightFront;
        this.rightRear = rightRear;
    }

    public double getRightFront() {
        return rightFront;
    }

    public double getRightRear() {
        return rightRear;
    }

    public double getLeftFront() {
        return leftFront;
    }

    public double getLeftRear() {
        return leftRear;
    }

    public double getCurrentLeft(){
        return (getLeftFront() + getLeftRear())/2;
    }

    public double getCurrentRight(){
        return (getRightFront() + getRightRear())/2;
    }

    public double getDeltaRight(){
        double currentRight = getCurrentRight();
        double deltaRight = currentRight - lastRight;
        lastRight = currentRight;
        return deltaRight;
    }

    public double getDeltaLeft(){
        double currentLeft = getCurrentLeft();
        double deltaLeft = currentLeft - lastLeft;
        lastLeft = currentLeft;
        return deltaLeft;
    }

    private double rightFront = 0.0;
    private double rightRear = 0.0;
    private double leftFront = 0.0;
    private double leftRear = 0.0;
}