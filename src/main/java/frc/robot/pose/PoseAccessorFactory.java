package frc.robot.pose;

public class PoseAccessorFactory{
    private RobotPose pose;

    public PositionAccessor getPositionAccessor(){
        return pose;
    }

    public PositionReader getPositionReader(){
        return pose;
    }

    public PositionWriter getPositionWriter(){
        return pose;
    }

}