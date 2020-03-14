package frc.robot.vision;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dcowden
 */
public class TestJSerialCommSerialDataSource {
    
    @Test
    public void testBasic() throws Exception{
         SerialDataSource sd = JSerialCommSerialDataSource.openMV(900000, 3);
         System.out.println("Connected");
         CircularFifoQueue q = new CircularFifoQueue(100);
         CircularBufferInputStream cis = new CircularBufferInputStream(q);
         BufferedReader br = new BufferedReader( new InputStreamReader ( cis));
         while ( true ){
             byte[] b = sd.readBytes();
             //System.out.println("Read" + new String(b));
             if ( b != null && b.length > 0 ){
                 q.add(b);
             }
             System.out.println(br.readLine());
             Thread.sleep(100);
         }

    }
}
