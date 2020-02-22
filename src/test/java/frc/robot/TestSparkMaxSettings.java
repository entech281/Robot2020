package frc.robot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANSparkMax.IdleMode;

import frc.robot.controllers.*;

public class TestSparkMaxSettings {

    @Test
    public void testSparkSettingsInverted() {

        SparkMaxSettings sparkSettings = new SparkMaxSettings();
        sparkSettings.motorDirections.inverted = false;
        sparkSettings.motorDirections.sensorPhase = false;

        sparkSettings.demand = 1.0;
        SparkMaxSettings inverted = SparkMaxSettingsBuilder.withDirectionSettings(sparkSettings, true);

        assertTrue(inverted.motorDirections.inverted);
    }

    @Test
    public void testCopy() {

        // set non-default values in each class, make sure they are all copied
        double TOLERANCE = 0.001;
        SparkMaxSettings sparkSettings = new SparkMaxSettings();
        sparkSettings.brakeMode = IdleMode.kCoast;
        sparkSettings.gains.d = 1.0;
        sparkSettings.gains.i = 2.0;
        sparkSettings.gains.p = 1.3;
        sparkSettings.currentLimits.smartLimit = 50;
        sparkSettings.outputLimits.maxMotorOutput = 0.9;
        sparkSettings.profile.accelStrategy = AccelStrategy.kSCurve;
        sparkSettings.rampUp.neutralDeadband = 55;

        SparkMaxSettings copy = SparkMaxSettingsBuilder.copy(sparkSettings);

        assertEquals(sparkSettings.brakeMode, copy.brakeMode);
        assertEquals(sparkSettings.gains.d, copy.gains.d, TOLERANCE);
        assertEquals(sparkSettings.gains.p, copy.gains.p, TOLERANCE);
        assertEquals(sparkSettings.gains.i, copy.gains.i, TOLERANCE);
        assertEquals(sparkSettings.currentLimits.smartLimit, copy.currentLimits.smartLimit);
        assertEquals(sparkSettings.outputLimits.maxMotorOutput, copy.outputLimits.maxMotorOutput, TOLERANCE);
        assertEquals(sparkSettings.profile.accelStrategy, copy.profile.accelStrategy);
        assertEquals(sparkSettings.rampUp.neutralDeadband, copy.rampUp.neutralDeadband, TOLERANCE);

    }
}
