/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.vision;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.InputStream;
/**
 *
 * @author dcowden
 */
public class JSerialCommSerialDataSource implements SerialDataSource{

    public static JSerialCommSerialDataSource openMV(int baudRate, int timeout){
        SerialPort connection = null;
        for(SerialPort port: SerialPort.getCommPorts()){
            if(port.getPortDescription().contains("OpenMV")){
                connection = port;
                break;
            }
        }
        if ( connection == null){
            throw new RuntimeException("Can't find OPENMV Camera Connected");
        }
        else{
           connection.setBaudRate(baudRate);
           connection.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, timeout, timeout);
           boolean opened = connection.openPort();   
           if ( opened ){
               return new JSerialCommSerialDataSource(connection);
           }
           else{
               throw new RuntimeException("Found OpenMV But couldn't connect");
           }
        }
            
    }
    private SerialPort port;
    
    public JSerialCommSerialDataSource(SerialPort port){
        this.port = port;        
    }
    
    @Override
    public byte[] readBytes(int numBytes){
        var buff = new byte[(int)numBytes];
        port.readBytes(buff, numBytes);
        return buff;        
    }
    @Override
    public byte[] readBytes() {
        
        //int MAX_READ = 32000;
        //int available = port.bytesAvailable();
        //int bytesToRead = Math.min(MAX_READ, available);
        int bytesToRead = port.bytesAvailable();
        var buff = new byte[(int)bytesToRead];
        port.readBytes(buff, bytesToRead);
        return buff;
    }


    @Override
    public void writeBytes(byte[] bytes){
        port.writeBytes(bytes, bytes.length);
    }

}
