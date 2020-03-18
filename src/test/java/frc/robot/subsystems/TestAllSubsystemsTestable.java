package frc.robot.subsystems;

import org.junit.Test;

public class TestAllSubsystemsTestable{
    @Test
    public void TestSubsystemManager(){
        SubsystemManager sm = new SubsystemManager();
        sm.initAllTestMode();
    }
}