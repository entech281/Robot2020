package frc.robot.vision;

import org.junit.Test;
import org.junit.After;

import edu.wpi.first.wpilibj.SerialPort.Port;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author plaba
 */
public class TestOpenMV{


    //Uncomment if you want to test communication with the OpenMV
    //I have to keep this commented so that the build doesn't fail
    /*private OpenMV openMV;
    
    public static void main(String[] args) throws Exception {
        var openMV = new OpenMV(921600, Port.kUSB1, 300);
        openMV.stopScript();
        openMV.enableFb(1);
        Thread.sleep(2000);
        openMV.execScript("while True:\n\tprint('hello world')".getBytes());

        int counter= 0;
        while (++counter < 10 && !openMV.scriptRunning() ) 
            Thread.sleep(1000); 
        System.out.println("Given output: " + openMV.txBufLen());
        while(true){
            Thread.sleep(20);
            System.out.println(new String(openMV.txBuf(openMV.txBufLen())));
        }
    }

    @Test
    public void TestsOpenMV() throws Exception {
        openMV = new OpenMV(921600, Port.kUSB1, 300);
        openMV.stopScript();
        openMV.enableFb(1);
        Thread.sleep(2000);
        openMV.execScript("while True:\n\tprint('hello world')".getBytes());

        int counter= 0;
        while (++counter < 10 && !openMV.scriptRunning() ) 
            Thread.sleep(1000); 
        System.out.println("Given output: " + openMV.txBufLen());
        assertTrue("Script is not running",openMV.scriptRunning());
        assertTrue("No serial output", openMV.getSerialOutput().length > 0);
    }

    @After
    public void end() throws Exception {
        openMV.reset();
        openMV.close();
    }*/
}