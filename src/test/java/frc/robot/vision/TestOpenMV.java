package frc.robot.vision;

import org.junit.Test;

import edu.wpi.first.wpilibj.SerialPort.Port;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author plaba
 */
public class TestOpenMV{
    @Test
    public void TestsOpenMV() throws Exception {
        OpenMV openMV = new OpenMV(921600, Port.kUSB1, 300);
        openMV.stopScript();
        openMV.enableFb(1);
        Thread.sleep(4000);
        openMV.execScript("while True:\n\tprint('hello world')".getBytes());
        Thread.sleep(4000); 
        assertTrue(openMV.scriptRunning());
        assertTrue(openMV.getSerialOutput().length > 0);
    }
}