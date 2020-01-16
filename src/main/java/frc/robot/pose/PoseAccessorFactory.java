package frc.robot.pose;

public class PoseAccessorFactory{
    private RobotPose pose;

    public SensorWriter getSensorWriter(){
        return pose;
    }

    public SensorReader getSensorReader(){
        return pose;
    }

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