package frc.robot.operatorpanel;

public class OperatorInstruction {

    enum IntakeDirection{
        IN,
        OUT
    }
    
    enum ShooterMode{
        AUTO,
        MANUAL
    }
    
    enum IntakeArmPosition{
        UP,
        DOWN
    }

    public double getShooterSpeedRPM() {
        return shooterSpeedRPM;
    }

    public void setShooterSpeedRPM(double shooterSpeed) {
        this.shooterSpeedRPM = shooterSpeed;
    }

    public double getHoodAngleDegrees() {
        return hoodAngleDegrees;
    }

    public void setHoodAngleDegrees(double hoodAngleDegrees) {
        this.hoodAngleDegrees = hoodAngleDegrees;
    }

    public IntakeArmPosition getIntakeArmPosition() {
        return intakeArmPosition;
    }

    public void setIntakeArmPosition(IntakeArmPosition intakeArmPosition) {
        this.intakeArmPosition = intakeArmPosition;
    }

    public ShooterMode getShooterMode() {
        return shooterMode;
    }

    public void setShooterMode(ShooterMode shooterMode) {
        this.shooterMode = shooterMode;
    }

    public double getForwardCommand() {
        return forwardCommand;
    }

    public void setForwardCommand(double forwardCommand) {
        this.forwardCommand = forwardCommand;
    }

    public double getTurnCommand() {
        return turnCommand;
    }

    public void setTurnCommand(double turnCommand) {
        this.turnCommand = turnCommand;
    }
    
    double shooterSpeedRPM = 0.0;
    double hoodAngleDegrees = 0.0;    
    IntakeArmPosition intakeArmPosition;
    ShooterMode shooterMode;
    double forwardCommand = 0.0;
    double turnCommand = 0.0;
    
}
