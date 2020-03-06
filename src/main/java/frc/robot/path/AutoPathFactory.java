package frc.robot.path;


public class AutoPathFactory {

//    public static List<Position> getExamplePath() {
//        return PositionCalculator.builder().left(90).forward(48).left(90).forward(166).right(180).build();
//    }
//    
//    public static List<Position> turn90(){
//        return PositionCalculator.builder().forward(24).build();
//    }
//
//    
//    private SubsystemManager subsystemManager;
//    private CommandFactory commandFactory;
//    
//    public AutoPathFactory(SubsystemManager subsystemManager, CommandGroupFactory commandFactory){
//        this.subsystemManager = subsystemManager;
//        this.commandFactory = commandFactory;
//    }
//    
//    //Start middle shoot 3 balls and pick up 3 more (8 seconds)
//    public Command[] middleSixBallAuto(){
//        return AutoPathBuilder.builder(subsystemManager, commandFactory, false).zeroYaw().startShooterAndHomeHood().next().delayForSeconds(5).enableAutoShooterHoodAdjustment().next().fire().delayForSeconds(3).next().backward(75).startIntake().stopJustSpinningShooterWheel().next().right(90).next().forward(50).next().right(90).next().forward(109).next().backward(75).next().right(155).next().snapToTarget().next().startShooter().next().fire().next().build();
//    }
//    
//    public Command[] simplePath(){
//        return AutoPathBuilder.builder(subsystemManager, commandFactory, false).next().zeroYaw().startShooterAndHomeHood().next().delayForSeconds(5).preset().snapToTarget().next().fire().delayForSeconds(3).next()
//                .turnOffEverything().next().fire().delayForSeconds(3)
//                .next().backward(24).next().build();
//    }
//    
//    public Command[] backUp(){
//        return AutoPathBuilder.builder(subsystemManager, commandFactory, false).next().zeroYaw().backward(24).next().build();
//    }
//
//    public Command[] leftEightBallAuto(){
//        //85 -> 137
//        return AutoPathBuilder.builder(subsystemManager, commandFactory, true).zeroYaw().startIntake().next().startShooterAndHomeHood().forward(140).next().enableAutoShooterHoodAdjustment().right(155).next().snapToTarget().next().fire().delayForSeconds(2).next().nonRelativeTurn(180).stopShooter().next().forward(85).next().backward(85).startShooter().next().right(155).enableAutoShooterHoodAdjustment().next().snapToTarget().next().fire().next().build();
//    }
    
}
