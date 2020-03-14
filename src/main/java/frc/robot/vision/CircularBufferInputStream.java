/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.vision;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.collections4.queue.CircularFifoQueue;

/**
 *
 * @author dcowden
 */
public class CircularBufferInputStream extends InputStream{

    private int currentPosition = 0;
    private CircularFifoQueue<byte[]> data;
    public CircularBufferInputStream(CircularFifoQueue data){
        this.data = data;
    }
    
    @Override
    public int read() throws IOException {
        if ( data.isEmpty()){
            return -1;
        }

        // Get first element of the List
        byte[] bytes = data.get(0);
        // Get the byte corresponding to the index and post increment the current index
        byte result = bytes[currentPosition++];
        if (currentPosition >= bytes.length) {
            // It was the last index of the byte array so we remove it from the list
            // and reset the current index
            data.remove();
            currentPosition = 0;
        }
        return result;        
        
    }
    
}
