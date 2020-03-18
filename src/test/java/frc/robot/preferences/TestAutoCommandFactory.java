package frc.robot.preferences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import frc.robot.subsystems.CommandFactory;
import frc.robot.subsystems.SubsystemManager;

public class TestAutoCommandFactory{
    @Test
    public void TestSelectedNeverNull(){
        var sb = new SubsystemManager();
        sb.initAllTestMode();
        AutoCommandFactory acf = new AutoCommandFactory(new CommandFactory(sb));
        assertTrue("3 ball auto is null", null != acf.getSelectedCommand(AutoOption.ShootAndBackUp));
        //assertTrue("Left 7 ball auto is null", null != acf.getSelectedCommand(AutoOption.LeftSevenBall)); //Not implemented
        assertTrue("Middle 6 Ball Auto is null", null != acf.getSelectedCommand(AutoOption.MiddleSixBall));
    }
}