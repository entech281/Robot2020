package frc.robot.path;

public interface PositionSource {

    public Position getCurrentPosition();

    public boolean hasNextPosition();

    public void next();
}
