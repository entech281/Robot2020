/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.pose;

/**
 *
 * @author dcowden
 */
public interface PoseSource {
    public RobotPose getRobotPose();
    public FieldPose getFieldPose();
}
