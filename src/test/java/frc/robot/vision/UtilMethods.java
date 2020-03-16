package frc.robot.vision;

import static org.junit.Assert.assertEquals;

class UtilMethods {
    public static void assertVisionDataEquals(VisionData expected, VisionData actual){
        assertEquals(expected.targetFound(), actual.targetFound());
        assertEquals(expected.getLateralOffset(), actual.getLateralOffset(), 0.01);
        assertEquals(expected.getVerticalOffset(), actual.getVerticalOffset(), 0.01);
        assertEquals(expected.getTargetWidth(), actual.getTargetWidth(),0.01);
    }
}