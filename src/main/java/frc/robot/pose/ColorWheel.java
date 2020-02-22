package frc.robot.pose;

import frc.robot.subsystems.ColorPanelSubsystem;

public class ColorWheel {

    private ColorPanelSubsystem colorPanel;

//for all calculations, a positive rotation value is clockwise and negative is counterclockwise
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
    

    public int smartNumSections(){
        
        int currentColor = getValue(getCurrentColor());
        int targetColor = getValue(getTargetColor());
        int numSections = 0 ;

    if (getCurrentColor() == WheelColorValue.NULL){
        
        numSections=0;

    }else if (getTargetColor() == WheelColorValue.NULL){
        numSections=0;
    }else if (getTargetColor() != WheelColorValue.NULL && getCurrentColor() != WheelColorValue.NULL){
    numSections = targetColor-currentColor;
    }
    if (numSections == -3)  {
        numSections =1;
    }else if (numSections == 3) {
        numSections = -1;
    }else if (numSections == 2) {
        numSections = -2;
    }else {
        numSections = 0;
    }
    return numSections;
    }

    private int getValue(WheelColorValue targetColor2) {
        int colorValue;
        
     if(targetColor2 == WheelColorValue.BLUE) {
         colorValue=1;
     }else if (targetColor2 == WheelColorValue.YELLOW){
         colorValue=2;
     }else if (targetColor2 == WheelColorValue.RED) {
         colorValue=3;
     }else if (targetColor2 == WheelColorValue.GREEN) {
         colorValue=4;
     }else {
         colorValue=0;
     }
        

        return colorValue;
    }
}
    

