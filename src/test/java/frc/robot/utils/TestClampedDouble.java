/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.utils;

import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author dcowden
 */
public class TestClampedDouble {
    public static final double TOLERANCE = 0.01;
    protected ClampedDouble cd;
    
    @Before
    public void setup(){
        cd = ClampedDouble.builder().bounds(0.0, 10.0).withIncrement(1.0).withValue(0.0).build();
    }
    @Test
    public void testInitialValue(){
        
        assertEquals(0.0,cd.getValue(), TOLERANCE);
        
    }
    
    @Test
    public void testMin(){
        assertEquals(0.0, cd.setValue(-1.0), TOLERANCE);
    }
    
    @Test
    public void testMax(){
        assertEquals(10, cd.setValue(15.0),  TOLERANCE);
    }   
    
    @Test
    public void testIncrement(){
        assertEquals(1.0, cd.increment(), TOLERANCE);
    }
    @Test
    public void testDecrement(){
        cd.setValue(2.0);
        assertEquals(cd.decrement(), 1.0, TOLERANCE);
    }    
}
