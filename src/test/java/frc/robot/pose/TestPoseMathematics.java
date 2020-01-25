package frc.robot.pose;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestPoseMathematics {
    @Test
    public void testPoseAddition(){
        RobotPose pose1 = new RobotPose(10,0,0);
        RobotPose pose2 = new RobotPose(10,0,0);
        RobotPose pose3 = PoseMathematics.addPoses(pose1, pose2);
        RobotPose pose4 = new RobotPose(20,0,0);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getLateral(), pose3.getLateral(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);

        pose1 = new RobotPose(0,10,0);
        pose2 = new RobotPose(0,10,0);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPose(0,20,0);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getLateral(), pose3.getLateral(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);

        pose1 = new RobotPose(0,0,90);
        pose2 = new RobotPose(0,0,90);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPose(0,0,180);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getLateral(), pose3.getLateral(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);

        pose1 = new RobotPose(0,0,180);
        pose2 = new RobotPose(0,0,180);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPose(0,0,0);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getLateral(), pose3.getLateral(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);

        pose1 = new RobotPose(10,0,90);
        pose2 = new RobotPose(10,0,90);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPose(10, 10, 180);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getLateral(), pose3.getLateral(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);

        pose1 = new RobotPose(10,0,270);
        pose2 = new RobotPose(10,0,90);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPose(10,-10,0);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getLateral(), pose3.getLateral(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);

        pose1 = new RobotPose(0,10,90);
        pose2 = new RobotPose(0,10,90);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPose(-10,10,180);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getLateral(), pose3.getLateral(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);

        pose1 = new RobotPose(0,10,270);
        pose2 = new RobotPose(0,10,90);
        pose3 = PoseMathematics.addPoses(pose1, pose2);
        pose4 = new RobotPose(10,10,0);
        assertEquals(pose4.getTheta(), pose3.getTheta(), 0.1);
        assertEquals(pose4.getLateral(), pose3.getLateral(), 0.1);
        assertEquals(pose4.getHorizontal(), pose3.getHorizontal(), 0.1);
    }

    @Test
    public void TestRobotPositionChange(){
        PoseMathematics.setRobotWidthForTesting(1);

        RobotPose pose1 = PoseMathematics.calculateRobotPositionChange(10, 10);
        RobotPose pose2 = new RobotPose(0, 10, 0);
        assertEquals(pose1.getTheta(), pose2.getTheta(), 0.1);
        assertEquals(pose1.getLateral(), pose2.getLateral(), 0.1);
        assertEquals(pose1.getHorizontal(), pose2.getHorizontal(), 0.1);

        pose1 = PoseMathematics.calculateRobotPositionChange(0, Math.PI);
        pose2 = new RobotPose(1, 0, 180);
        assertEquals(pose2.getTheta(), pose1.getTheta(), 0.1);
        assertEquals(pose2.getLateral(), pose1.getLateral(), 0.1);
        assertEquals(pose2.getHorizontal(), pose1.getHorizontal(), 0.1);

        pose1 = PoseMathematics.calculateRobotPositionChange(Math.PI, 0);
        pose2 = new RobotPose(-1, 0, 180);
        assertEquals(pose2.getTheta(), pose1.getTheta(), 0.1);
        assertEquals(pose2.getLateral(), pose1.getLateral(), 0.1);
        assertEquals(pose2.getHorizontal(), pose1.getHorizontal(), 0.1);
    }
}