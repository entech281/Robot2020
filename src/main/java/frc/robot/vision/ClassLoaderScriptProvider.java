/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.vision;

import java.util.Scanner;

/**
 *
 * @author dcowden
 */
public class ClassLoaderScriptProvider implements ScriptProvider{

    @Override
    public String loadScript(String scriptName) {
        return new Scanner(ClassLoaderScriptProvider.class.getResourceAsStream("/" + scriptName ), "UTF-8").useDelimiter("\\A").next();
    }
    
}
