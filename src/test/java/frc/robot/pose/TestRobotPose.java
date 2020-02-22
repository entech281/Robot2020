package frc.robot.pose;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

public class TestRobotPose {

    @Test
    public void testPoseGettersAndSetters() {
        RobotPosition rp = new RobotPosition(7, 5, 9);
        assertEquals(5, rp.getHorizontal(), 0.03);
        assertEquals(7, rp.getForward(), 0.03);
        assertEquals(9, rp.getTheta(), 0.03);
        assertEquals(rp.getWPIRobotPose(), new Pose2d(7, 5, new Rotation2d(9)));
    }

}
