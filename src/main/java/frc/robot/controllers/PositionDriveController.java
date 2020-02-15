package frc.robot.controllers;

import com.revrobotics.CANSparkMax;
import frc.robot.RobotMap;
import frc.robot.controllers.SparkPositionController;
import frc.robot.controllers.SparkPositionControllerGroup;
import frc.robot.logger.DataLogger;
import frc.robot.logger.DataLoggerFactory;
import frc.robot.path.Position;
import frc.robot.path.PositionSource;
import frc.robot.subsystems.EncoderInchesConverter;
import frc.robot.subsystems.drive.FourSparkMaxWithSettings;

/**
 * Drives to positions given by the position buffer.
 * 
 * @author dcowden
 *
 */
public class PositionDriveController{

    // TODO: this should be computed from the spark settings
    public static final double TOLERANCE_INCHES = RobotMap.AUTONOMOUS.ACCEPTABLE_ERROR/RobotMap.DIMENSIONS.MOTOR_REVOLUTIONS_PER_INCH;

    private FourSparkMaxWithSettings sparks;
	private SparkPositionControllerGroup positionControllerGroup;
	private EncoderInchesConverter encoderConverter;
	private Position desiredPosition;
	private PositionSource positionSource;
	private int updateCount = 0;
    private DataLogger dataLogger = DataLoggerFactory.getLoggerFactory().createDataLogger("Position Drive Controller");
	
	public PositionDriveController(FourSparkMaxWithSettings sparks, PositionSource positionSource,
			EncoderInchesConverter encoderConverter) {
		this.sparks = sparks;
		this.encoderConverter = encoderConverter;
        this.positionSource = positionSource;
        dataLogger.log("sparks", sparks);
	}


	public void activate() {

		sparks.configureAll();
		
		positionControllerGroup = new SparkPositionControllerGroup(
				new SparkPositionController(sparks.getFrontLeft(), sparks.getFrontLeftSettings()),
				new SparkPositionController(sparks.getFrontRight(), sparks.getFrontRightSettings()),
				new SparkPositionController(sparks.getRearLeft(), sparks.getRearLeftSettings()),
				new SparkPositionController(sparks.getRearRight(), sparks.getRearRightSettings()));

		positionControllerGroup.resetPosition();

	}

	public boolean isFinished() {
		if (this.desiredPosition == null) {
			return true;
		}
		return getCurrentPosition().isCloseTo(desiredPosition, RobotMap.POSITION_TOLERANCE_INCHES);
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
				int encoderLeft = encoderConverter.toCounts(p.getLeftInches());
                int encoderRight = encoderConverter.toCounts(p.getRightInches());
                dataLogger.log("Position Controller Group", positionControllerGroup);
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
		displayControllerStatus(sparks.getFrontLeft(),"FrontLeft");
		displayControllerStatus(sparks.getFrontRight(),"FrontRight");
		displayControllerStatus(sparks.getRearLeft(),"RearLeft");
		displayControllerStatus(sparks.getRearRight(),"RearRight");
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