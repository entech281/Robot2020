package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.controllers.TestSpeedController;
import frc.robot.controllers.digitialinput.BaseDigitalInput;
import frc.robot.controllers.solenoid.BaseDoubleSolenoidController;
import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author plaba
 */
public class IntakeSubsystemTest {
    
    private final Field intakeMotorField;
    private final Field elevatorMotorField;
    private final Field deployPneumaticsField;
    private final Field ballSensorField;
   
    private static final double INTAKE_ON = IntakeSubsystem.INTAKE_ON;
    private static final double INTAKE_OFF = IntakeSubsystem.INTAKE_OFF;
    
    public IntakeSubsystemTest() throws NoSuchFieldException{
        intakeMotorField = IntakeSubsystem.class.getDeclaredField("intakeMotorController");
        elevatorMotorField = IntakeSubsystem.class.getDeclaredField("elevatorMotorController");
        deployPneumaticsField = IntakeSubsystem.class.getDeclaredField("deployIntakeSolenoids");
        ballSensorField = IntakeSubsystem.class.getDeclaredField("intakeBallSensor");
        intakeMotorField.setAccessible(true);
        elevatorMotorField.setAccessible(true);
        deployPneumaticsField.setAccessible(true);
        ballSensorField.setAccessible(true);

    }
    
    
    
    public TestSpeedController getIntakeMotorController(IntakeSubsystem i) throws IllegalArgumentException, IllegalAccessException{
        return (TestSpeedController) intakeMotorField.get(i);
    }
    
    public TestSpeedController getElevatorMotorController(IntakeSubsystem i) throws IllegalArgumentException, IllegalAccessException{
        return (TestSpeedController) elevatorMotorField.get(i);
    }
    
    public BaseDoubleSolenoidController getDeployPneumatics(IntakeSubsystem i) throws IllegalArgumentException, IllegalAccessException{
        return (BaseDoubleSolenoidController) deployPneumaticsField.get(i);
    }
    
    public BaseDigitalInput getBallSensor(IntakeSubsystem i) throws IllegalArgumentException, IllegalAccessException{
        return (BaseDigitalInput) ballSensorField.get(i);
    }
    
    private IntakeSubsystem createTestSubsystem(){
        var ans = new IntakeSubsystem();
        ans.initializeForTest();
        return ans;
    }

    /**
     * Test of deployAndStart method, of class IntakeSubsystem.
     */
    @Test
    public void testDeployAndStart() throws IllegalArgumentException, IllegalAccessException {
        var i = createTestSubsystem();
        i.deployAndStart();
        assertEquals(Value.kForward,getDeployPneumatics(i).get());
        assertEquals(INTAKE_ON,getIntakeMotorController(i).getDesiredSpeed(), 0.01);
    }

    /**
     * Test of raiseAndStop method, of class IntakeSubsystem.
     */
    @Test
    public void testRaiseAndStop() throws IllegalArgumentException, IllegalAccessException {
        var i = createTestSubsystem();
        i.raiseAndStop();
        assertEquals(Value.kReverse,getDeployPneumatics(i).get());
        assertEquals(INTAKE_OFF,getIntakeMotorController(i).getDesiredSpeed(), 0.01);
    }

    /**
     * Test of deployIntakeArms method, of class IntakeSubsystem.
     */
    @Test
    public void testDeployIntakeArms() throws IllegalArgumentException, IllegalAccessException {
        var i = createTestSubsystem();
        i.deployIntakeArms();
        assertEquals(Value.kForward,getDeployPneumatics(i).get());
        assertEquals(INTAKE_OFF,getIntakeMotorController(i).getDesiredSpeed(), 0.01);
    }

    /**
     * Test of raiseIntakeArms method, of class IntakeSubsystem.
     */
    @Test
    public void testRaiseIntakeArms() throws IllegalArgumentException, IllegalAccessException {
        var i = createTestSubsystem();
        i.raiseIntakeArms();
        assertEquals(Value.kReverse,getDeployPneumatics(i).get());
        assertEquals(INTAKE_OFF,getIntakeMotorController(i).getDesiredSpeed(), 0.01);
    }

    /**
     * Test of intakeOn method, of class IntakeSubsystem.
     */
    @Test
    public void testIntakeOn() throws IllegalArgumentException, IllegalAccessException {
        var i = createTestSubsystem();
        i.intakeOn();
        assertEquals(INTAKE_ON,getIntakeMotorController(i).getDesiredSpeed(), 0.01);
    }

    /**
     * Test of intakeOff method, of class IntakeSubsystem.
     */
    @Test
    public void testIntakeOff() throws IllegalArgumentException, IllegalAccessException {
        var i = createTestSubsystem();
        i.intakeOff();
        assertEquals(INTAKE_OFF,getIntakeMotorController(i).getDesiredSpeed(), 0.01);
    }

    /**
     * Test of toggleIntakeArms method, of class IntakeSubsystem.
     */
    @Test
    public void testToggleIntakeArms() throws IllegalArgumentException, IllegalAccessException {
        var i = createTestSubsystem();
        Value start = getDeployPneumatics(i).get();
        assertEquals(INTAKE_OFF,getIntakeMotorController(i).getDesiredSpeed(), 0.01);
        i.toggleIntakeArms();
        assertNotEquals(start, getDeployPneumatics(i).get());
    }

    /**
     * Test of isBallAtIntake method, of class IntakeSubsystem.
     */
    @Test
    public void testIsBallAtIntake() throws IllegalArgumentException, IllegalAccessException {
        var i = createTestSubsystem();
        BaseDigitalInput input = getBallSensor(i); 
        input.set(Boolean.TRUE);
        assertFalse(i.isBallAtIntake());
        input.set(Boolean.FALSE);
        assertTrue(i.isBallAtIntake());
    }

    /**
     * Test of setIntakeMotorSpeed method, of class IntakeSubsystem.
     */
    @Test
    public void testSetIntakeMotorSpeed() throws IllegalArgumentException, IllegalAccessException {
        var i = createTestSubsystem();
        i.setIntakeMotorSpeed(0.57);
        assertEquals(0.57,getIntakeMotorController(i).getDesiredSpeed(), 0.01);
    }

    /**
     * Test of setElevatorSpeed method, of class IntakeSubsystem.
     */
    @Test
    public void testSetElevatorSpeed() throws IllegalArgumentException, IllegalAccessException {
        var i = createTestSubsystem();
        i.setIntakeMotorSpeed(0.57);
        assertEquals(0.57,getIntakeMotorController(i).getDesiredSpeed(), 0.01);
    }

}
