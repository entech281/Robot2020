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
     * theta_calculated depends on the height of the target, the height difference 
     * between the camera and the bottom of the target, and the distance the camera 
     * is away from the target.
     * 
     * Here is the formula to calculate the angle
     * theta_calculated = atan( ( h_diff + t_height) / dist_away ) - atan( h_diff / dist_away )
     * 
     * Great! We have a formula that depends on the distance away and some other constants and 
     * is tied to the height of the target in pixels. All we need to do is invert the function 
     * used to calculate theta_calculated, and grab dist_away from it. 
     * 
     * There's a problem though,
     * That function is not invertable. It has two branches which are. So there are two answers
     * to what the distance away actually is, that result from the same image. 
     * 
     * I solved the problem and got the functions from Mathematica.
    */

    private static double closerBranch(double h,double d,double k){
        return Complex.I.multiply(4 * 1 / Math.tan(k))
            .subtract(4)
            .reciprocal()
            .multiply(
                Complex.I.multiply(
                    Complex.I.multiply(2 * k).exp().add(1)
                )
                .multiply(2 * d + h)
                .add(
                    Complex.I.multiply(
                        Complex.I.multiply(- 2 * k).exp()
                            .multiply(8 * d * d + 8 * d * h + h * h * (1 + Math.cos(2 * k)) )
                            .getArgument()
                    )
                    .multiply(1/2)
                    .exp()
                    .multiply(Math.sqrt(2))
                    .multiply(Math.pow(8 * d * d + 8 * d * h + h * h * (1 + Math.cos(2 * k) ), 1 / 4))
                )
            ).getReal()
            * 1 / Math.pow(Math.cos(k), 2);   
    }

    private static double furtherBranch(double h,double d,double k){
        return Complex.I.add( 1 / Math.tan(k))
            .multiply(4)
            .reciprocal()
            .multiply(
                Complex.I.multiply(2 * k).exp().multiply(h)
                .add(h)
                .add(
                    Complex.I.multiply(
                        Complex.I.multiply(- 2 * k).exp()
                            .multiply(4 * d * d + 4 * d * h + h * h * (1 + Math.cos(2 * k)) )
                            .getArgument()
                    )
                    .multiply(1/2)
                    .exp()
                    .multiply(Math.sqrt(2))
                    .multiply(Complex.I)
                    .multiply(Math.pow(8 * d * d + 8 * d * h + h * h * (1 + Math.cos(2 * k) ), 1 / 4))
                )
            ).getReal()
            * 1 / Math.pow(Math.cos(k), 2);   
    }

    private static boolean isImageTooBig(){
        return true;
    }
}