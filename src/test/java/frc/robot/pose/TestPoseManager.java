package frc.robot.pose;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TestPoseManager{
    @Test
    public void TestStuff(){
        PoseGenerator pg = mock(PoseGenerator.class);
        PoseManager manager = new PoseManager();
        manager.addGenerator(pg);
        
        
        manager.configureRobotPose(1, 2, 3);
        when(pg.getPose()).thenReturn(new RobotPose(3,4,5));
        when(pg.getPositionConfidence()).thenReturn(1.0);
        when(pg.getThetaConfidence()).thenReturn(1.0);
        assertEquals(manager.getPose(), new RobotPose(3,4,5)); 
    }

    @Test
    public void TestWeightedAverage(){
        PoseGenerator encoders = mock(PoseGenerator.class);
        PoseGenerator navX = mock(PoseGenerator.class);
        PoseManager manager = new PoseManager();
        manager.addGenerator(encoders);
        manager.addGenerator(navX);
        
        manager.configureRobotPose(1, 2, 3);
        when(encoders.getPose()).thenReturn(new RobotPose(3,4,5));
        when(encoders.getPositionConfidence()).thenReturn(1.0);
        when(encoders.getThetaConfidence()).thenReturn(0.0);

        when(navX.getPose()).thenReturn(new RobotPose(3,4,10));
        when(navX.getPositionConfidence()).thenReturn(0.0);
        when(navX.getThetaConfidence()).thenReturn(1.0);

        assertEquals(manager.getPose(), new RobotPose(3,4,10)); 
    }
}