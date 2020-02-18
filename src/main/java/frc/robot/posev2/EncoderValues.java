package frc.robot.posev2;


public class EncoderValues {


    private double rightFront = 0.0;
    private double rightRear = 0.0;
    private double leftFront = 0.0;
    private double leftRear = 0.0;

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


}