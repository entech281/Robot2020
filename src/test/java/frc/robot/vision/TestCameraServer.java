/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.vision;

import frc.robot.subsystems.OpenMVCameraSubsystem;
import org.junit.Test;

/**
 *
 * @author dcowden
 */
public class TestCameraServer {
    
    
    @Test
    public void testCameraServer(){
        OpenMVCameraSubsystem oms = new OpenMVCameraSubsystem(true);
    }
}
