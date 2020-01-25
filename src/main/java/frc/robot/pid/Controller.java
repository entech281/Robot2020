package frc.robot.pid;

public interface Controller {
    public double getOutput(double actual, double setpoint);
}