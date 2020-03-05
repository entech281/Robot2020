/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.utils;

/**
 *
 * @author dcowden
 */
public class ClampedDouble {

    private double value = 0.0;
    private double min = 0.0;
    private double max = 0.0;
    private double increment = 0.0;

    private ClampedDouble() {
    }

    private static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public double increment() {
        return setValue(value + increment);
    }

    public double decrement() {
        return setValue(value - increment);
    }

    public double setValue(double newValue) {
        value = clamp(newValue, min, max);
        return value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ClampedDouble{" + "value=" + value + ", min=" + min + ", max=" + max + ", increment=" + increment + '}';
    }

    public interface Bounds {

        public Increment bounds(double min, double max);
    }

    public interface Increment {

        public Value withIncrement(double increment);
    }

    public interface Finish {

        ClampedDouble build();
    }

    public interface Value {

        public Finish withValue(double value);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements Bounds, Increment, Finish, Value {

        private ClampedDouble tmp = new ClampedDouble();

        private Builder() {
        }

        @Override
        public ClampedDouble build() {
            return tmp;
        }

        @Override
        public Value withIncrement(double increment) {
            tmp.increment = increment;
            return this;
        }

        @Override
        public Finish withValue(double value) {
            tmp.value = value;
            return this;
        }

        @Override
        public Increment bounds(double min, double max) {
            tmp.min = min;
            tmp.max = max;
            return this;
        }
    }

}
