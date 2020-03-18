/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.controllers.solenoid;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 *
 * @author plaba
 */
public class RealDoubleSolonoidController extends BaseDoubleSolenoidController {
    private DoubleSolenoid solenoids;
    public RealDoubleSolonoidController(int forwardSolenoidID, int reverseSolenoidID){
        solenoids = new DoubleSolenoid(forwardSolenoidID, reverseSolenoidID);
    }
    @Override
    public void set(Value v){
        super.set(v);
        solenoids.set(v);
    }
}
