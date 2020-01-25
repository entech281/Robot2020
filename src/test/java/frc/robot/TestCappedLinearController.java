package frc.robot;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import frc.robot.pid.*;

public class TestCappedLinearController{
    public double TOLERANCE = 0.001;
    @Test
    public void testActualCalculationsForMaxOutput(){
        CappedLinearControl cappedControl = new CappedLinearControl(0.1, 10, 0.2, 0.75);
        double actual = 0.0;
        double desired = 15.1;
        double output = cappedControl.getOutput(actual, desired);
        assertEquals(output, 0.75, TOLERANCE);        
    }

    @Test
    public void testControllerForAngle(){
        CappedLinearControl cappedControl = new CappedLinearControl(1, 15.0, 0.15, 0.4);
        cappedControl.manage180Degrees(true);
        double output = cappedControl.getOutput(60.0, 0.0);
        assertEquals(output, -0.4, TOLERANCE);
    }
}