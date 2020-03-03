package frc.robot.utils;

import org.apache.commons.math3.complex.Complex;
/*
 * @author: Plaba
 * This class takes in a target height and converts it to a distance away from the target.
 */
public class TargetLocationProvider{
    /*
     * Derivation of formula: 
     * The target height is given to us in pixels. From geometry, we know that 
     * the ratio of a detected height to the frame height is equal to the ratio 
     * of the calculated angle to the camera range angle, or
     *    theta_calculated / theta_camera = pix_detected / pix_height
     * pix_detected is the input to the class.
     * theta_calculated depends on the height of the target, the distance that the targe is away from the 
    */
}