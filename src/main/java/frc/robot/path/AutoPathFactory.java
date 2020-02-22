package frc.robot.path;

import java.util.List;

public class AutoPathFactory {
    public static List<Position> getExamplePath(){
        return PositionCalculator.builder().left(90).forward(48).left(90).forward(166).right(180).build();
    }

}