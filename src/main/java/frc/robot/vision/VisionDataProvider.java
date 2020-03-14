/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.vision;

import com.fazecast.jSerialComm.SerialPort;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Iterator;
import org.apache.commons.collections4.queue.CircularFifoQueue;

/**
 *
 * @author dcowden
 */
public class VisionDataProvider {
    
    private JOpenMV jopenMV;
    private boolean connected = false;
    private CircularFifoQueue<byte[]> serialQueue = new CircularFifoQueue<>(100);
    private CircularFifoQueue<byte[]> frameQueue = new CircularFifoQueue<>(10);
    private CircularBufferInputStream queuedData = new CircularBufferInputStream(serialQueue);
    
    public VisionDataProvider(JOpenMV jopenMV){
        this.jopenMV = jopenMV;
    }
    
    public InputStream getTrackingDataStream(){
       return queuedData;
    }
    
    public byte[] getNextFrame(){
        return frameQueue.poll();
    }
    
    public void update() throws Exception{
        //System.out.println(new String(jopenMV.readTxBuf()));
        addIfNotNull(serialQueue,jopenMV.readTxBuf());
        //addIfNotNull(frameQueue,jopenMV.getFrame());
    }
    
    private void addIfNotNull(CircularFifoQueue fq, byte[] o){
        if ( o != null && o.length > 0 ){
            fq.add(o);
        }
    }
    
}
