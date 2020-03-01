/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;

/**
 *
 * @author dcowden
 */
public class WpilibTestUtils {
 
    public static void runCommand(Command c, int iterations){
        c.initialize();
        for ( int i=0;i<iterations;i++){
            c.execute();
        }
    }
    
    public static void runCommandTillFinished(Command c){
        System.out.println("DEBUG: Initialize Command: " + c);
        c.initialize();
        while ( ! c.isFinished()){
            c.execute();
            System.out.println("DEBUG: Execute Command: " + c);
        }
        c.end(false);
    }
}
