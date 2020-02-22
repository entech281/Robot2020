package frc.robot.pose;

import frc.robot.pose.WheelColorValue;
import frc.robot.subsystems.ColorPanelSubsystem;

public class ColorWheel {

    private ColorPanelSubsystem colorPanel;

//for all calculations, the assumption is made that we are spinning clockwise

    public ColorWheel() {

    }

    public WheelColorValue getCurrentColor() {
        return colorPanel.getSensorColor();
    }

    public WheelColorValue getTargetColor() {
        return colorPanel.getFieldColor();
    }

    public WheelColorValue getNextColor() {
        
        WheelColorValue readColor;
        
        if (colorPanel.getSensorColor() == WheelColorValue.BLUE){
            readColor = WheelColorValue.YELLOW;
        } else if (colorPanel.getSensorColor() == WheelColorValue.YELLOW) {
            readColor = WheelColorValue.RED;
        } else if (colorPanel.getSensorColor() == WheelColorValue.RED) {
            readColor = WheelColorValue.GREEN;
        } else if (colorPanel.getSensorColor() == WheelColorValue.GREEN) {
            readColor = WheelColorValue.BLUE;
        } else {
            readColor = WheelColorValue.NULL;
        }


        return readColor;

    
    }

    public int getNumSectionsTillTargetColor() {
        
        WheelColorValue currentColor = getCurrentColor();
        WheelColorValue targetColor = getTargetColor();
        int numSections = 0 ;
        
         if (currentColor != targetColor) {

            if (currentColor == WheelColorValue.BLUE){
                if (targetColor == WheelColorValue.YELLOW){
                    numSections = 1;
                } else if (targetColor == WheelColorValue.RED) {
                    numSections = 2;
                } else if (targetColor == WheelColorValue.GREEN) {
                    numSections = 3;
                }
            }else if (currentColor == WheelColorValue.YELLOW){
                    if (targetColor == WheelColorValue.RED){
                        numSections = 1;
                    } else if (targetColor == WheelColorValue.GREEN) {
                        numSections = 2;
                    } else if (targetColor == WheelColorValue.BLUE) {
                        numSections = 3;
                    }
            }else if (currentColor == WheelColorValue.RED){
                    if (targetColor == WheelColorValue.GREEN){
                            numSections = 1;
                    } else if (targetColor == WheelColorValue.BLUE) {
                            numSections = 2;
                    } else if (targetColor == WheelColorValue.YELLOW) {
                            numSections = 3;
                    } 
                }else if (currentColor == WheelColorValue.GREEN){
                        if (targetColor == WheelColorValue.BLUE){
                                numSections = 1;
                        } else if (targetColor == WheelColorValue.YELLOW) {
                                numSections = 2;
                        } else if (targetColor == WheelColorValue.RED) {
                                numSections = 3;
                        }
            }
        } 
            
        
        return numSections;
    }
}
    

