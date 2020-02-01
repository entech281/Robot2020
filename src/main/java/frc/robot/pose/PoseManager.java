package frc.robot.pose;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import java.util.ArrayList;

//The kept pose is at the center of the robot.
public class PoseManager{

    PositionReader pose;
    PositionReader encoderPose;
    ArrayList<PoseGenerator> generators;

    
    public PoseManager(){
        generators = new ArrayList<PoseGenerator>();
    }
    
    public PositionReader getPose(){
        normalizeConfidenceValues();
        double horizontal = 0.0;
        double forward = 0.0;
        double yaw = 0.0;
        for(PoseGenerator generator: generators){
            PositionReader generatorPose = generator.getPose();
            horizontal += generatorPose.getHorizontal() * generator.getPositionConfidence();
            forward += generatorPose.getForward() * generator.getPositionConfidence();
            yaw += generatorPose.getTheta() * generator.getThetaConfidence();
        }
        this.pose = new RobotPose(horizontal, forward, yaw);
        return this.pose;
    }

    public Pose2d getWPIPose(){
        return pose.getWPIRobotPose();
    }

    public void configureRobotPose(double horizontal, double lateral, double theta){
        pose = new RobotPose(horizontal, lateral, theta);
        for(PoseGenerator generator : generators){
            generator.updateFromOfficialPose(pose);
        }
    }

    public void addGenerator(PoseGenerator generator){
        generators.add(generator);
    }

    public void normalizeConfidenceValues(){
        double totalAngleConfidences = 0;
        double totalPositionConfidences = 0;
        for(PoseGenerator gen: generators){
            totalAngleConfidences += gen.getThetaConfidence();
            totalPositionConfidences += gen.getPositionConfidence();
        }
        for(PoseGenerator gen: generators){
            gen.updateConfidences(gen.getPositionConfidence()/totalPositionConfidences, gen.getThetaConfidence()/totalAngleConfidences);
        }
    }

}