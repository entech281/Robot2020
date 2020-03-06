package frc.robot.path;

import java.util.ArrayList;
import java.util.List;

import frc.robot.RobotConstants;

public class PositionCalculator {

    public static final double DISTANCE_BETWEEN_WHEELS = RobotConstants.DIMENSIONS.ROBOT_WIDTH;

    private static double computeTurn(double degrees) {
        return (((DISTANCE_BETWEEN_WHEELS * Math.PI) / 360) * degrees);

    }

    public static Position goForward(double inches) {
        double desiredPosition = inches;
        return new Position(desiredPosition, desiredPosition);
    }

    public static Position turnRight(double degrees) {
        double d = computeTurn(degrees);
        return new Position(d, -d);
    }

    public static Position turnLeft(double degrees) {
        double d = computeTurn(degrees);
        return new Position(-d, d);
    }

    public static BasicMoves builder() {
        return new Builder();
    }

    public interface BasicMoves {

        BasicMoves right(double degrees);

        BasicMoves left(double degrees);

        BasicMoves forward(double inches);

        BasicMoves backward(double inches);

        List<Position> build();
    }

    public static List<Position> mirror(List<Position> a) {
        List<Position> b = new ArrayList<Position>();
        for (int i = 0; i < a.size(); i++) {
            b.add(new Position(a.get(i).getRightInches(), a.get(i).getLeftInches()));
        }
        return b;
    }


    public static class Builder implements BasicMoves {

        private List<Position> commands = new ArrayList<>();

        @Override
        public List<Position> build() {
            return commands;
        }

        @Override
        public BasicMoves right(double degrees) {
            commands.add(turnRight(degrees));
            return this;
        }

        @Override
        public BasicMoves left(double degrees) {
            commands.add(turnLeft(degrees));
            return this;
        }

        @Override
        public BasicMoves forward(double inches) {
            commands.add(goForward(inches));
            return this;
        }

        @Override
        public BasicMoves backward(double inches) {
            commands.add(goForward(-inches));
            return this;
        }

    }

}
