/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.vision;

/**
 *
 * @author dcowden
 */
public interface SerialDataSource {
    public byte[] readBytes();
    public byte[] readBytes(int numBytes);
    public void writeBytes(byte[] bytes);
}
