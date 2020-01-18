package frc.robot.pose;

import frc.robot.RobotMap;

public class PoseMathematics{
 
    //X is positive to the right of the robot & y is positive forward
    public static double[] calculateDelta(double dR, double dL){
        double[] deltas = new double[3];
        double deltaX;
        double deltaY;
        double deltaTheta;
        if(dR==dL){
            deltaX = 0;
            deltaY = dR;
            deltaTheta = 0;
        } else {
            double theta;
            double w;
            if(dL > dR){
                theta = (dL - dR)/RobotMap.DIMENSIONS.robotWidth;
                w = dR/theta;
                deltaX = w*(1-Math.cos(theta));
                deltaY = w*Math.sin(theta);
                deltaTheta = theta;
            } else {
                theta = (dR - dL)/RobotMap.DIMENSIONS.robotWidth;
                w = dL/theta;
                deltaX = w*(1-Math.cos(theta));
                deltaY = w*Math.sin(theta);
                deltaTheta = theta;
                
            }
        }
        deltas[0] = deltaX;
        deltas[1] = deltaY;
        deltas[2] = deltaTheta;
        return deltas;
    }
    
}