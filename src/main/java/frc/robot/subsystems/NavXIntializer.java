package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;

public class NavXIntializer {

	private AHRS navX;
	private SerialPort.Port serialPort;
	private DataLogger dataLogger;
	private int calibration_timeout = 0;
	public NavXIntializer(SerialPort.Port port , int calibration_timeout) {
		this.serialPort = port;
		this.calibration_timeout = calibration_timeout;
		
		this.dataLogger = DataLoggerFactory.getLoggerFactory().createDataLogger("NavXInit");
	}
	
	public void calibrate() {
		// Do NavX first to try and give it time to calibrate
		try {
			this.navX = new AHRS(serialPort);
			this.navX.reset();
		} catch (Exception e) {
			this.navX = null;
			dataLogger.warn("Trouble with NavX MXP");
		}

		if (this.navX != null) {
			while (this.navX.isCalibrating()) {
				try {
					Thread.sleep(calibration_timeout);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			this.navX.zeroYaw();
		}		
	}
	public boolean isOk() {
		return this.navX != null && this.navX.isConnected();
	}
	
	public AHRS getCalibratedNavX() {
		calibrate();
		return this.navX;
	}
}