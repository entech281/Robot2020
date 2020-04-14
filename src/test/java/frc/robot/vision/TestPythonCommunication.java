package frc.robot.vision;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;

import org.junit.Test;
import org.python.util.PythonInterpreter;

import frc.robot.RobotConstants;

public class TestPythonCommunication{
    @Test
    public void TestPythonSendSingleVisionData() throws Exception{
        try(PythonInterpreter pyInterp = new PythonInterpreter()) {
            
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            pyInterp.setOut(output);
            try{
                pyInterp.exec("__name__ = '__test__'");
                pyInterp.execfile("src/main/python/vision.py");
                pyInterp.exec("communication.send_string(TargetData().format())");
            }catch(Exception e){
                System.out.println(new String(output.toByteArray()));
                e.printStackTrace();
                fail();
            }
            var testSerial = new TestSerialProvider();
            testSerial.setBuffer(output.toByteArray());
            
            var conn = new VisionCommManager(testSerial);
            conn.update();
            
            VisionData expected = new VisionData(false, -1, -1, -1);
            VisionData actual = conn.getLatestTargetData();

            UtilMethods.assertVisionDataEquals(expected, actual);

        }
    }

    @Test
    public void TestPythonSendSingleImage() throws Exception{
        try(PythonInterpreter pyInterp = new PythonInterpreter()) {
            
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            pyInterp.setOut(output);
            try{
                pyInterp.exec("__name__ = '__test__'");
                pyInterp.execfile("src/main/python/vision.py");
                pyInterp.exec("communication.send_image_bytes(b'1234')");
            }catch(Exception e){
                System.out.println(new String(output.toByteArray()));
                e.printStackTrace();
                fail();
            }
            var testSerial = new TestSerialProvider();
            testSerial.setBuffer(output.toByteArray());
            
            var conn = new VisionCommManager(testSerial);
            conn.update();
            
            byte[] expected = "1234".getBytes();
            byte[] actual = conn.getLatestImage();

            assertArrayEquals(expected, actual);

        }
    }

    @Test
    public void TestPythonSendMultipleVisionData() throws Exception{
        try(PythonInterpreter pyInterp = new PythonInterpreter()) {
            
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            pyInterp.setOut(output);
            try{
                pyInterp.exec("__name__ = '__test__'");
                pyInterp.execfile("src/main/python/vision.py");
                pyInterp.exec("communication.send_string(TargetData(found = True, width = 5).format())");
                pyInterp.exec("communication.send_string(TargetData().format())");
            }catch(Exception e){
                System.out.println(new String(output.toByteArray()));
                e.printStackTrace();
                fail();
            }
            var testSerial = new TestSerialProvider();
            testSerial.setBuffer(output.toByteArray());
            
            var conn = new VisionCommManager(testSerial);

            conn.update();

            VisionData expected = new VisionData(false, -1, -1, -1);
            VisionData actual = conn.getLatestTargetData();

            UtilMethods.assertVisionDataEquals(expected, actual);

        }
    }

    @Test
    public void TestPythonSendMultipleImages() throws Exception{
        try(PythonInterpreter pyInterp = new PythonInterpreter()) {
            
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            pyInterp.setOut(output);
            try{
                pyInterp.exec("__name__ = '__test__'");
                pyInterp.execfile("src/main/python/vision.py");
                pyInterp.exec("communication.send_image_bytes(b'5678')");
                pyInterp.exec("communication.send_image_bytes(b'1234')");
            }catch(Exception e){
                System.out.println(new String(output.toByteArray()));
                e.printStackTrace();
                fail();
            }
            var testSerial = new TestSerialProvider();
            testSerial.setBuffer(output.toByteArray());
            
            var conn = new VisionCommManager(testSerial);
            conn.update();

            conn.update();
            
            byte[] expected = "1234".getBytes();
            byte[] actual = conn.getLatestImage();

            assertArrayEquals(expected, actual);

        }
    }
}