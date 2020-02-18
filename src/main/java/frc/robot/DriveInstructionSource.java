package frc.robot;

/**
 * Marker interface for something that creates DriveInstructions. Of course the
 * OperatorInterface is the main implementation, but using this seam allows
 * testing commands without an OperatorInterface, which creates Joysticks and
 * stuff ( and thus cannot be used in unit tests )
 *
 * @author dcowden
 *
 */
public interface DriveInstructionSource {

    public DriveInstruction getNextInstruction();
}
