/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot;

import frc.robot.utils.VisionDataProcessor;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 *
 * @author aryan
 */
public class TestVisionDataProcessor {
    String buffer;
    VisionDataProcessor processor;
    
    @Test
    public void testProcessor(){
        String value = "False 63 45 34 56\n";
        buffer = "False 63 45 34 56\n";
        for(int i=0; i< 10000; i++){
            buffer += buffer;
            buffer = processor.removeFirstInput(buffer);
            assertEquals(value, buffer);
        }
    }
    
    @Test
    public void weirdEntryTestProcessor(){
        String value = "False 63 45 34 56\n";
        buffer = "False 6";
        for(int i=0; i< 10000; i++){
            buffer = processor.removeFirstInput(buffer);
            if(i == 0){
                buffer += "3 45 34 56\n";
            } else{
                buffer += value;
                assertEquals(value, buffer);
            }
        }
    }
}
