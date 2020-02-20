package frc.robot.controllers;

import com.revrobotics.CANSparkMax;
import frc.robot.RobotConstants;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.path.Position;
import frc.robot.path.PositionSource;
import frc.robot.utils.EncoderInchesConverter;

/**
 * Drives to positions given by the position buffer.
 * 
 * @author dcowden
 *
 */
public class PositionDriveController{

    // TODO: this should be computed from the spark settings
    public static final double TOLERANCE_INCHES = 3;

	private SparkPositionControllerGroup positionControllerGroup;
	private EncoderInchesConverter encoderConverter;
	private Position desiredPosition;
	private PositionSource positionSource;
	private int updateCount = 0;
    private DataLogger dataLogger = DataLoggerFactory.getLoggerFactory().createDataLogger("Position Drive Controller");
	private CANSparkMax frontRight, backRight, frontLeft, backLeft;
	private SparkMaxSettings autoSettings;

	public PositionDriveController(CANSparkMax frontRight, CANSparkMax backRight,CANSparkMax frontLeft, CANSparkMax backLeft, SparkMaxSettings settings, PositionSource positionSource,
			EncoderInchesConverter encoderConverter) {
		this.encoderConverter = encoderConverter;
		this.positionSource = positionSource;
		
		this.backLeft = backLeft;
		this.backRight = backRight;
		this.frontLeft = frontLeft;
		this.frontRight = frontRight;

		this.autoSettings = settings;
	}


	public void activate() {

		
		positionControllerGroup = new SparkPositionControllerGroup(
				new SparkPositionController(frontLeft, autoSettings),
				new SparkPositionController(frontRight, autoSettings),
				new SparkPositionController(backLeft, autoSettings),
				new SparkPositionController(backRight, autoSettings));

		positionControllerGroup.resetPosition();

	}

	public boolean isFinished() {
		if (this.desiredPosition == null) {
			return true;
		}
		return getCurrentPosition().isCloseTo(desiredPosition, RobotConstants.AUTONOMOUS.POSITION_TOLERANCE_INCHES);
	}

	public Position getCurrentPosition() {
		return positionControllerGroup.getCurrentPosition(encoderConverter);
	}


	public boolean hasCurrentCommand() {
		return this.desiredPosition != null;
	}
	public void setCurrentCommand(Position position) {
		this.desiredPosition = position;
	}
	public Position getCurrentCommand() {
		return this.desiredPosition;
	}
 
	public void periodic() {		
		processPositionCommand();
		displayControllerStatuses();
	}

	protected void processPositionCommand() {
		if ( hasCurrentCommand() ) {
			Position current = getCurrentPosition();
			Position command = getCurrentCommand();
			if ( command.isCloseTo(current, TOLERANCE_INCHES)) {
			    positionSource.next();
				setCurrentCommand(null);
			}			
		}
		else {
			if ( positionSource.hasNextPosition()) {
				Position p = positionSource.getCurrentPosition();
				setCurrentCommand(p);
				dataLogger.log("wanted inches", p.getLeftInches());
				double encoderLeft = encoderConverter.toCounts(p.getLeftInches());
				double encoderRight = encoderConverter.toCounts(p.getRightInches());
				
				dataLogger.log("encoderLeft", encoderLeft);
				positionControllerGroup.setDesiredPosition(encoderLeft, encoderRight, p.isRelative());
			}
			else {
				
			}
		}		
		if ( hasCurrentCommand() ) {
			dataLogger.log("commandPosition", getCurrentCommand());
		}
		else {
			dataLogger.log("commandPosition", "<IDLE>");
		}		
		dataLogger.log("currentPosition", getCurrentPosition());
		dataLogger.log("updateCount", updateCount++);
		
	}
	
	protected void displayControllerStatuses() {
		displayControllerStatus(frontLeft,"FrontLeft");
		displayControllerStatus(frontRight,"FrontRight");
		displayControllerStatus(backLeft,"RearLeft");
		displayControllerStatus(backRight,"RearRight");
	}
	
	protected void displayControllerStatus(CANSparkMax spark, String name) {
        dataLogger.log(name + ": errorMsg", spark.getLastError());
        dataLogger.log(name + ": get", spark.get() );
        dataLogger.log(name + ": tvolts", spark.getBusVoltage() );
        dataLogger.log(name + ": current", spark.getOutputCurrent() );
        dataLogger.log(name + ": pos", spark.getEncoder().getPosition() );
		dataLogger.log(name + ": vel", spark.getEncoder().getVelocity() );
		dataLogger.log(name + ": appliedoutput", spark.getAppliedOutput());	
		dataLogger.log(name + ": max accel", spark.getPIDController().getSmartMotionMaxAccel(0));
		dataLogger.log(name + ": max vel", spark.getPIDController().getSmartMotionMaxVelocity(0));
	}
	
}