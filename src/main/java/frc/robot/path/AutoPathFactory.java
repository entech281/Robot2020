package frc.robot.path;

import java.util.List;

public class AutoPathFactory {
    public static List<Position> getExamplePath(){
        return PositionCalculator.builder().forward(24).build();
    }

}