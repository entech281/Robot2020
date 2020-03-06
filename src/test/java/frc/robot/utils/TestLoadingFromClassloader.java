/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.utils;

import java.util.Scanner;
import org.junit.Test;


/**
 *
 * @author dcowden
 */
public class TestLoadingFromClassloader {
    
    @Test
    public void testLoadingFromClassloader(){
        String text = new Scanner(TestLoadingFromClassloader.class.getResourceAsStream("/test.txt"), "UTF-8").useDelimiter("\\A").next();
        System.out.println("TEXT = " + text);

    }
}
