package frc.robot.subsystems;

/**
 * Converts encoder counts to inches and back
 * 
 * @author dcowden
 *
 */
public class EncoderInchesConverter {

	private double encoderCountsPerInch = 1.0;

	public EncoderInchesConverter(double encoderCountsPerInch) {
		this.encoderCountsPerInch = encoderCountsPerInch;
	}

	public EncoderInchesConverter(int encoderCounts, double wheelDiameter, double gearRatio) {
		this.encoderCountsPerInch = encoderCounts / (wheelDiameter * Math.PI) * gearRatio;
	}

	public double toInches(int encoderCounts) {
		return (double) (encoderCounts) / encoderCountsPerInch;
	}

	public double toCounts(double inches) {
		return (encoderCountsPerInch * inches);
	}

}