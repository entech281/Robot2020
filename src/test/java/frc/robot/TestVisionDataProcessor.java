/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot;

import frc.robot.pose.VisionData;
import frc.robot.utils.ByteConverter;
import frc.robot.vision.VisionDataProcessor;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author aryan
 */
public class TestVisionDataProcessor {

    String buffer;
    VisionDataProcessor processor = new VisionDataProcessor();
    VisionData output;
    double TOLERANCE = 0.001;

    @Test
    public void testReaderFallingBehindMultipleInputs() {
        String[] inputs = {"true 6", "3 45 56 45.3 - \n", "true 45 3", "6 31 43.5 - \n"};
        for (String e : inputs) {
            processor.addInput(e);
        }
        double lateralOffset = Math.abs(RobotConstants.ROBOT_DEFAULTS.VISION.FRAME_WIDTH / 2 - 45);
        output = processor.getCurrentVisionData();
        assertTrue(output.targetFound());
        assertEquals(output.getLateralOffset(), lateralOffset, TOLERANCE);
        assertEquals(output.getVerticalOffset(), 36, TOLERANCE);
        assertEquals(output.getTargetWidth(), 31, TOLERANCE);
    }

    @Test
    public void TestFallingBehindAndGettingAhead() {
        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        String[] inputs = {"true 6", "3 45 56 45.3 - \n", "true 45 3", "6 31 43.5 - \n"};
        for (String e : inputs) {
            processor.addInput(e);
        }
        double lateralOffset = Math.abs(RobotConstants.ROBOT_DEFAULTS.VISION.FRAME_WIDTH / 2 - 45);
        output = processor.getCurrentVisionData();

        processor.addInput("true 42");
        output = processor.getCurrentVisionData();
        assertTrue(output.targetFound());
        assertEquals(output.getLateralOffset(), lateralOffset, TOLERANCE);
        assertEquals(output.getVerticalOffset(), 36, TOLERANCE);
        assertEquals(output.getTargetWidth(), 31, TOLERANCE);

        processor.addInput(" 43 23 45.6 - \n");
        lateralOffset = Math.abs(RobotConstants.ROBOT_DEFAULTS.VISION.FRAME_WIDTH / 2 - 42);

        output = processor.getCurrentVisionData();
        assertTrue(output.targetFound());
        assertEquals(output.getLateralOffset(), lateralOffset, TOLERANCE);
        assertEquals(output.getVerticalOffset(), 43, TOLERANCE);
        assertEquals(output.getTargetWidth(), 23, TOLERANCE);
        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println(ByteConverter.convertBytesToMB(endMemory - startMemory));
    }
}
