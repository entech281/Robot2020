package frc.robot;


import static org.junit.Assert.assertEquals;
import org.junit.Test;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import frc.robot.pose.*;
public class TestRobotPose {
    @Test
    public void testThatThisRuns(){
        RobotPose rp = new RobotPose();
        rp.setHorizontal(5);
        assertEquals(5, rp.getHorizontal(),0.03);
        rp.setLateral(7);
        assertEquals(7, rp.getLateral(),0.03);
        rp.setTheta(9);
        assertEquals(9, rp.getTheta(),0.03);
        assertEquals(rp.getWPIRobotPose(), new Pose2d(7, 5, new Rotation2d(9)));
    }
}