package frc.robot.utils;


public class VisionDataFormatter{
    public static String removeFirstInput(String inp){
        return inp.substring(inp.indexOf("\n", 1));
    }
}