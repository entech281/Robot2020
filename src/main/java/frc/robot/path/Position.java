package frc.robot.path;

public class Position {

    private double leftInches;
    private double rightInches;
    private boolean relative = true;

    public Position(double leftInches, double rightInches) {
        this(leftInches, rightInches, true);
    }

    public Position(double leftInches, double rightInches, boolean relative) {
        this.leftInches = leftInches;
        this.rightInches = rightInches;
        this.relative = relative;
    }

    public double getLeftInches() {
        return leftInches;
    }

    public double getRightInches() {
        return rightInches;
    }

    public boolean isRelative() {
        return relative;
    }

    public boolean isCloseTo(Position other, double tolerance) {
        return Math.sqrt(Math.pow(this.getLeftInches() - other.getLeftInches(), 2)
                + Math.pow(this.getRightInches() - other.getRightInches(), 2)) <= tolerance;
    }

    @Override
    public String toString() {
        return String.format("L=%.2f, R=%.2f", leftInches, rightInches);

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(leftInches);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (relative ? 1231 : 1237);
        temp = Double.doubleToLongBits(rightInches);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Position other = (Position) obj;
        if (Double.doubleToLongBits(leftInches) != Double.doubleToLongBits(other.leftInches)) {
            return false;
        }
        if (relative != other.relative) {
            return false;
        }
        if (Double.doubleToLongBits(rightInches) != Double.doubleToLongBits(other.rightInches)) {
            return false;
        }
        return true;
    }

}
