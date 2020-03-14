/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.vision;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author dcowden
 */
public class TestCircularBufferInputStream {
    
    //@Test
    public void testBufferReading() throws Exception{
         CircularFifoQueue q = new CircularFifoQueue(100);
         CircularBufferInputStream cis = new CircularBufferInputStream(q);
         BufferedReader br = new BufferedReader ( new InputStreamReader( cis));
         q.add( "A".getBytes());
         q.add("B".getBytes());
         //assertEquals( br.readLine(), null);
         q.add("C\n".getBytes());
         assertEquals("ABC",br.readLine());
         
    }
    
    @Test
    public void testRegularReading() throws Exception{
        BufferedReader br = new BufferedReader ( new StringReader ( "ABC\nDEF"));
        assertEquals("ABC", br.readLine());
        assertEquals(null, br.readLine());
    }
    
}
