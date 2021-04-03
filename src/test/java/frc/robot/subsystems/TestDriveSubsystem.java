// package frc.robot.subsystems;

// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.CommandScheduler;
// import edu.wpi.first.wpilibj2.command.InstantCommand;
// import frc.robot.utils.RunWhenDisabledCommandDecorator;

// import org.junit.Test;
// import org.mockito.Mockito;
// import static org.mockito.Mockito.*;

// public class TestDriveSubsystem {
 
//     protected CommandScheduler scheduler= CommandScheduler.getInstance();
// //    @Test
//     public void testDriveSubsystemPosition(){

//         DriveSubsystem drive = Mockito.mock(DriveSubsystem.class);
 
//         Command d = new RunWhenDisabledCommandDecorator ( 
//                 new InstantCommand ( () -> drive.switchToCoastMode())
//         );

//         scheduler.schedule(d);
        
//         for ( int i=0;i<5;i++){
//              scheduler.run();
//         }
//         verify(drive).switchToCoastMode();
//     }
   
// }