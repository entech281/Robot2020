package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.posev2.WheelColorValue;
import edu.wpi.first.wpilibj.DriverStation;

public class ColorPanelSubsystem extends BaseSubsystem{
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3  colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch  colorMatcher = new ColorMatch();
    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    @Override
    public void initialize() {
         colorMatcher.addColorMatch(kBlueTarget);
         colorMatcher.addColorMatch(kGreenTarget);
         colorMatcher.addColorMatch(kRedTarget);
         colorMatcher.addColorMatch(kYellowTarget);  
    }
    public void detectColor(){
        /**
         * The method GetColor() returns a normalized color value from the sensor and can be
         * useful if outputting the color to an RGB LED or similar. To
         * read the raw color, use GetRawColor().
         * 
         * The color sensor works best when within a few inches from an object in
         * well lit conditions (the built in LED is a big help here!). The farther
         * an object is the more light from the surroundings will bleed into the 
         * measurements and make it difficult to accurately determine its color.
         */
        Color detectedColor = colorSensor.getColor();

        /**
         * Run the color match algorithm on our detected color
         */
        String colorString;
        ColorMatchResult match =  colorMatcher.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget) {
            colorString = "BLUE";
        } else if (match.color == kRedTarget) {
            colorString = "RED";
        } else if (match.color == kGreenTarget) {
            colorString = "GREEN";
        } else if (match.color == kYellowTarget) {
            colorString = "YELLOW";
        } else {
            colorString = "UNKNOWN";
        }

        /**
         * Open Smart Dashboard 
         * or Shuffleboard to see the color detected by the 
         * sensor.
         */
        SmartDashboard.putNumber("RED", detectedColor.red);
        SmartDashboard.putNumber("GREEN", detectedColor.green);
        SmartDashboard.putNumber("BLUE", detectedColor.blue);
        SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Detected Color", colorString);
     
        
    }
    public String returnColor() {
      /**
       * The method GetColor() returns a normalized color value from the sensor and can be
       * useful if outputting the color to an RGB LED or similar. To
       * read the raw color, use GetRawColor().
       * 
       * The color sensor works best when within a few inches from an object in
       * well lit conditions (the built in LED is a big help here!). The farther
       * an object is the more light from the surroundings will bleed into the 
       * measurements and make it difficult to accurately determine its color.
       */
      Color detectedColor = colorSensor.getColor();

      /**
       * Run the color match algorithm on our detected color
       */
      String colorString;
      ColorMatchResult match =  colorMatcher.matchClosestColor(detectedColor);

      if (match.color == kBlueTarget) {
          colorString = "BLUE";
      } else if (match.color == kRedTarget) {
          colorString = "RED";
      } else if (match.color == kGreenTarget) {
          colorString = "GREEN";
      } else if (match.color == kYellowTarget) {
          colorString = "YELLOW";
      } else {
          colorString = "UNKNOWN";
      }

      /**
       * Open Smart Dashboard 
       * or Shuffleboard to see the color detected by the 
       * sensor.
       */
   
      return colorString;
      
  }


    public void fieldColor() {
        String gameData;
gameData = DriverStation.getInstance().getGameSpecificMessage();
if(gameData.length() > 0)
{
  switch (gameData.charAt(0))
  {
    case 'B' :
      SmartDashboard.putBoolean("Blue",true);
      break;
    case 'G' :
    SmartDashboard.putBoolean("Green",true);
      break;
    case 'R' :
    SmartDashboard.putBoolean("Red",true);
      break;
    case 'Y' :
    SmartDashboard.putBoolean("Yellow",true);
      break;
    default :
    SmartDashboard.putBoolean("Color",false);
      break;
  }
} else {
  SmartDashboard.putBoolean("Color",false);
}
    }
    
    
    public void periodic (){
        detectColor();
        fieldColor();
    }
    
}
