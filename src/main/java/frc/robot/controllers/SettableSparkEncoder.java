package frc.robot.controllers;

import com.revrobotics.CANEncoder;


/**
 * This is an encoder that allows setting its value to a given position.
 * When that happens, we track the difference between 
 * the actual reading and the reset value, to give the appearance that we reset it.
 * @author dave
 *
 */
public class SettableSparkEncoder {

	private CANEncoder encoder;
	private int offset = 0;
	
	public SettableSparkEncoder ( CANEncoder original ) {
		this.encoder = original;
	}
	
	public CANEncoder getRawEncoder() {
		return encoder;
	}
	public void reset() {
		offset=0;
	}
	public void set(int counts) {
		int currentPos = (int)(encoder.getPosition()*encoder.getCountsPerRevolution());
		offset = (counts - currentPos);
	}
	public int get() {
		return (int)(encoder.getPosition()*encoder.getCountsPerRevolution()) + offset;
	}
}