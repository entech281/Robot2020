/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.vision;

import edu.wpi.first.wpilibj.SerialPort;

/**
 *
 * @author dcowden
 */
public class RoboRioSerialDataSource implements SerialDataSource{

    private SerialPort port;
    //should be an already connected serial port
    public RoboRioSerialDataSource( SerialPort port){
        this.port = port;
    }
    

    @Override
    public byte[] readBytes() {
        return this.port.read(this.port.getBytesReceived());
    }


    @Override
    public void writeBytes(byte[] bytes) {
        this.port.write(bytes, bytes.length);
    }

    @Override
    public byte[] readBytes(int numBytes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
