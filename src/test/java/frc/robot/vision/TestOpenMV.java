package frc.robot.vision;

import org.junit.Test;

import edu.wpi.first.wpilibj.SerialPort.Port;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author plaba
 */
public class TestOpenMV{
    @Test
    public void TestsOpenMV() throws Exception {
        OpenMV openMV = new OpenMV(912000, Port.kUSB1, 3);
        openMV.stopScript();
        openMV.enableFb(1);
        Thread.sleep(4);
        openMV.execScript("print('hello world')".getBytes());
        Thread.sleep(4);
        assertTrue(openMV.getSerialOutput().length > 0);
    }
}