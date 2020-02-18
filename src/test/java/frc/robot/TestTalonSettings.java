package frc.robot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.robot.controllers.*;

public class TestTalonSettings {

    @Test
    public void testTalonSettingsInverted() {

        TalonSettings ts = new TalonSettings();
        ts.motorDirections.inverted = false;
        ts.motorDirections.sensorPhase = false;

        ts.demand = 1.0;
        TalonSettings inverted = TalonSettingsBuilder.withDirectionSettings(ts, true, true);

        assertTrue(inverted.motorDirections.inverted);
        assertTrue(inverted.motorDirections.sensorPhase);
    }

    @Test
    public void testCopy() {

        // set non-default values in each class, make sure they are all copied
        double TOLERANCE = 0.001;
        TalonSettings ts = new TalonSettings();
        ts.brakeMode = NeutralMode.Coast;
        ts.gains.d = 1.0;
        ts.gains.i = 2.0;
        ts.currentLimits.continuousPeak = 99;
        ts.feedbackDevice = FeedbackDevice.Analog;
        ts.framePeriods.pidMilliseconds = 88;
        ts.outputLimits.maxMotorOutputBackward = 77;
        ts.profile.accelerationEncoderClicksPerSecond2 = 66;
        ts.rampUp.neutralDeadband = 55;

        TalonSettings copy = TalonSettingsBuilder.copy(ts);

        assertEquals(ts.brakeMode, copy.brakeMode);
        assertEquals(ts.gains.d, copy.gains.d, TOLERANCE);
        assertEquals(ts.gains.i, copy.gains.i, TOLERANCE);
        assertEquals(ts.currentLimits.continuousPeak, copy.currentLimits.continuousPeak);
        assertEquals(ts.feedbackDevice, copy.feedbackDevice);
        assertEquals(ts.framePeriods.pidMilliseconds, copy.framePeriods.pidMilliseconds);
        assertEquals(ts.outputLimits.maxMotorOutputBackward, copy.outputLimits.maxMotorOutputBackward, TOLERANCE);
        assertEquals(ts.profile.accelerationEncoderClicksPerSecond2, copy.profile.accelerationEncoderClicksPerSecond2);
        assertEquals(ts.rampUp.neutralDeadband, copy.rampUp.neutralDeadband, TOLERANCE);

    }
}
