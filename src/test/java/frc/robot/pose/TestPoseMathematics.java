package frc.robot.pose;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestPoseMathematics {

    RobotPosition pose1;
    RobotPosition pose2;
    RobotPosition pose3;
    RobotPosition pose4;

    @Test
    public void testPoseAddition5() {
        pose1 = new RobotPosition(0, 0, 0);
        pose2 = new RobotPosition(0, 0, -90);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPosition(0, 0, 270);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getForward(), pose3.getForward(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);
    }

    @Test
    public void testPoseAddition4() {
        pose1 = new RobotPosition(10, 0, 0);
        pose2 = new RobotPosition(10, 0, 0);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPosition(20, 0, 0);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getForward(), pose3.getForward(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);
    }

    @Test
    public void testPoseAddition3() {
        pose1 = new RobotPosition(0, 0, 180);
        pose2 = new RobotPosition(0, 0, 180);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPosition(0, 0, 0);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getForward(), pose3.getForward(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);
    }

    @Test
    public void testPoseAddition2() {
        pose1 = new RobotPosition(0, 0, 90);
        pose2 = new RobotPosition(0, 0, 90);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPosition(0, 0, 180);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getForward(), pose3.getForward(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);
    }

    @Test
    public void testPoseAddition6() {
        pose1 = new RobotPosition(10, 0, 90);
        pose2 = new RobotPosition(10, 0, 90);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPosition(10, -10, 180);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getForward(), pose3.getForward(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);
    }

    @Test
    public void testPoseAddition7() {
        pose1 = new RobotPosition(0, 10, 270);
        pose2 = new RobotPosition(0, 10, 90);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPosition(-10, 10, 0);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getForward(), pose3.getForward(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);
    }

    @Test
    public void testPoseAddition8() {
        pose1 = new RobotPosition(0, 10, 90);
        pose2 = new RobotPosition(0, 10, 90);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPosition(10, 10, 180);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getForward(), pose3.getForward(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);
    }

    @Test
    public void testPoseAddition9() {
        pose1 = new RobotPosition(0, 0, 0);
        pose2 = new RobotPosition(0, 0, -500);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPosition(0, 0, 220);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getForward(), pose3.getForward(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);
    }

    @Test
    public void testPoseAddition10() {
        pose1 = new RobotPosition(10, 0, 270);
        pose2 = new RobotPosition(10, 0, 90);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPosition(10, 10, 0);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getForward(), pose3.getForward(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);
    }

    @Test
    public void testPoseAddition() {
        pose1 = new RobotPosition(0, 10, 0);
        pose2 = new RobotPosition(0, 10, 0);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPosition(0, 20, 0);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getForward(), pose3.getForward(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);

    }

    @Test
    public void TestRobotPositionChange1() {
        PoseMathematics.setRobotWidthForTesting(1);

        pose1 = PoseMathematics.calculateRobotPositionChange(10, 10);
        pose2 = new RobotPosition(0, 10, 0);
        assertEquals(pose1.getTheta(), pose2.getTheta(), 0.1);
        assertEquals(pose1.getForward(), pose2.getForward(), 0.1);
        assertEquals(pose1.getHorizontal(), pose2.getHorizontal(), 0.1);

    }

    @Test
    public void TestRobotPositionChange2() {
        PoseMathematics.setRobotWidthForTesting(1);

        pose1 = PoseMathematics.calculateRobotPositionChange(0, Math.PI);
        pose2 = new RobotPosition(1, 0, 180);
        assertEquals(pose2.getTheta(), pose1.getTheta(), 0.1);
        assertEquals(pose2.getForward(), pose1.getForward(), 0.1);
        assertEquals(pose2.getHorizontal(), pose1.getHorizontal(), 0.1);
    }

    @Test
    public void TestRobotPositionChange3() {
        PoseMathematics.setRobotWidthForTesting(1);

        pose1 = PoseMathematics.calculateRobotPositionChange(Math.PI, 0);
        pose2 = new RobotPosition(-1, 0, 180);
        assertEquals(pose2.getTheta(), pose1.getTheta(), 0.1);
        assertEquals(pose2.getForward(), pose1.getForward(), 0.1);
        assertEquals(pose2.getHorizontal(), pose1.getHorizontal(), 0.1);
    }
}
