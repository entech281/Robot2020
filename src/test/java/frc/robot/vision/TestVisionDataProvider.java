/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.vision;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.junit.Test;

/**
 *
 * @author dcowden
 */
public class TestVisionDataProvider {
 
    @Test
    public void testVisionDataProviderWithJserialCom() throws Exception{
        
         SerialDataSource sd = JSerialCommSerialDataSource.openMV(921000, 3);
         String script = new ClassLoaderScriptProvider().loadScript("vision.py");
         System.out.println("Loaded Script:" + script);
         JOpenMV jomv = new JOpenMV(sd,script );
         System.out.println("Connected");
         VisionDataProvider vdp = new VisionDataProvider(jomv);
         jomv.enableFb(5);
         jomv.stopScript();
         
        
         jomv.execScript(script.getBytes());
         System.out.println("Started Script");
         Thread.sleep(500);
         jomv.enableFb(5);
         BufferedReader br = new BufferedReader( 
                 new InputStreamReader( vdp.getTrackingDataStream())
         );
         for ( int i=0;i<1000;i++){
             vdp.update();
             System.out.println("From Cam:  " + br.readLine() );
             Thread.sleep(20);
             //System.out.println("From Cam:  " + new String(jomv.readTxBuf()) );
//         for ( int i=0;i<10;i++){
//             vdp.update();
//             System.out.println(br.readLine());
//             byte [] f = vdp.getNextFrame();
//             if ( f != null ){
//                 System.out.println("Frame Size=" + f.length);
//             }
             
         }
    }
}
