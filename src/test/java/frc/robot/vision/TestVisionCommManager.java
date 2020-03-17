package frc.robot.vision;

import org.junit.Test;
import frc.robot.utils.JStruct;
import static org.junit.Assert.assertEquals;
import java.io.ByteArrayOutputStream;

public class TestVisionCommManager{
    private JStruct struct = new JStruct();
    @Test
    public void TestSingleImageReception() throws Exception {
        TestSerialProvider testConnection = new TestSerialProvider();
        testConnection.setBuffer(struct.pack(">BIBBBB", (byte)'I',(int)4,(byte)1,(byte)2,(byte)3,(byte)4));
        VisionCommManager comm = new VisionCommManager(testConnection);
        comm.update();
        byte[] expected = new byte[]{1,2,3,4};
        byte[] result = comm.getLatestImage();

        assertEquals(expected.length, result.length);

        for(int i = 0; i < expected.length; i ++){
            assertEquals(expected[i], result[i]);
        }
    }

    @Test
    public void TestSingleVisionDataReception() throws Exception {
        String message = "False -1 -1 -1";
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(struct.pack(">BI", (byte) 'D',message.getBytes().length));
        b.write(message.getBytes());
        
        TestSerialProvider testConnection = new TestSerialProvider();

        testConnection.setBuffer(b.toByteArray());
        VisionCommManager comm = new VisionCommManager(testConnection);
        comm.update();
        var expected = new VisionData(false, -1, -1, -1);
        var actual = comm.getLatestTargetData();

        assertEquals(expected.targetFound(), actual.targetFound());
        assertEquals(expected.getLateralOffset(), actual.getLateralOffset(), 0.01);
        assertEquals(expected.getVerticalOffset(), actual.getVerticalOffset(), 0.01);
        assertEquals(expected.getTargetWidth(), actual.getTargetWidth(),0.01);
    }

    @Test
    public void TestMultipleVisionDataReception() throws Exception {
        String message = "False -1 -1 -1";
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(struct.pack(">BI", (byte) 'D',message.getBytes().length));
        b.write(message.getBytes());

        b.write(struct.pack(">BI", (byte) 'D',message.getBytes().length));
        b.write(message.getBytes());

        b.write(struct.pack(">BI", (byte) 'D',message.getBytes().length));
        b.write(message.getBytes());
        
        TestSerialProvider testConnection = new TestSerialProvider();

        testConnection.setBuffer(b.toByteArray());

        VisionCommManager comm = new VisionCommManager(testConnection);
        comm.update();
        var expected = new VisionData(false, -1, -1, -1);
        var actual = comm.getLatestTargetData();

        UtilMethods.assertVisionDataEquals(expected, actual);

        comm.update();
        expected = new VisionData(false, -1, -1, -1);
        actual = comm.getLatestTargetData();

        UtilMethods.assertVisionDataEquals(expected, actual);

        comm.update();
        expected = new VisionData(false, -1, -1, -1);
        actual = comm.getLatestTargetData();

        UtilMethods.assertVisionDataEquals(expected, actual);
    }
}

