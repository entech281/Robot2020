package frc.robot;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import frc.robot.subsystems.EncoderInchesConverter;

public class TestEncoderInchesConverter {
    @Test
    public void testEncoderInchesConverter(){
        var e = new EncoderInchesConverter(5);

        assertEquals(e.toCounts(1), 5);
        assertEquals(e.toInches(25), 5, 0.001);
    }
}