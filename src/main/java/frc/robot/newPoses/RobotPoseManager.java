  
package frc.robot.newPoses;

import frc.robot.RobotMap;


public class RobotPoseManager {
 
    EncoderValues encoders;
    NavXData navXData;
    VisionData vData;
    WheelColor wColor;

    RobotPose pose = RobotMap.DIMENSIONS.START_POSE;
    
    public RobotPose getCurrentPose(){
        return pose;
    }
    
    public void update(){
        //do all the maths to get a new pose
        pose = PoseMathematics.addPoses(pose, PoseMathematics.calculateRobotPositionChange(encoders.getDeltaLeft(), encoders.getDeltaRight()));
        pose = new RobotPose(pose.getForward(), pose.getHorizontal(), navXData.getAngle(), vData);
    }
    public void updateNavxAngle(NavXData newNavxData ){
        this.navXData = newNavxData;
    }
    
    public void updateEncoders ( EncoderValues newEncoderValues){
        this.encoders = newEncoderValues;
    }
    
    public void updateVisionData ( VisionData newVisionData){
        this.vData = newVisionData;
    }
    
    public void updateWheelColor ( WheelColor newWheelColor){
        this.wColor = newWheelColor;
    }
}