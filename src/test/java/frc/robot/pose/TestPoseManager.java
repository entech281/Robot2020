package frc.robot.pose;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TestPoseManager{
    @Test
    public void TestStuff(){
        PoseGenerator pg = mock(PoseGenerator.class);
        PoseManager manager = new PoseManager(pg);
        
        
        manager.configureRobotPose(1, 2, 3);
        when(pg.getPose()).thenReturn(new RobotPose(3,4,5));
        assertEquals(manager.getPose(), new RobotPose(3,4,5)); 
    }
}