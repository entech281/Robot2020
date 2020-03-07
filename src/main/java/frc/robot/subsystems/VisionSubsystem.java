/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.pose.*;
import frc.robot.vision.OpenMV;
import frc.robot.vision.VisionDataProcessor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SerialPort.Port;


/**
 *
 * @author aryan and plaba
 */
public class VisionSubsystem extends BaseSubsystem {

    private Timer timer = new Timer();

    private static final int BAUD_RATE = 115200;
    private static final int TIMEOUT = 2;
    private static final int WAIT_TO_DEPLOY_SCRIPT_SECONDS = 4; 
    private static final int WAIT_TO_START_READING_VISION_DATA_SECONDS = 4;
    private static final int FRAME_RATE_FPS = 20;
    private byte[] deployScript;

    private OpenMV openMV;

    private VisionData visionData = VisionData.DEFAULT_VISION_DATA;
    private VisionDataProcessor processor;

    private OpenMVState state = OpenMVState.NOT_CONNECTED;

    @Override
    public void initialize() {
        logger.log("initialized", true);
        ensureConnected();
        processor = new VisionDataProcessor();
        try{
            deployScript = VisionSubsystem.class.getResourceAsStream("/vision.py").readAllBytes();
        } catch(Exception e){
            logger.warn("Could not read resource /vision.py");
            e.printStackTrace();
        }
    }

    @Override
    public void periodic() {
        logger.driverinfo("Vision Status", state);

        switch (state) {
            case NOT_CONNECTED:
                break; // We could try reconnecting just in case we get vision halfway through the
                       // match
            case CONNECTED:
                try {
                    openMV.stopScript();
                    openMV.enableFb(1);
                    state = OpenMVState.STOPPED_SCRIPT;
                } catch (Exception e) {
                    state = OpenMVState.ERROR;
                }
            case STOPPED_SCRIPT:
                if(timer.hasPeriodPassed(WAIT_TO_DEPLOY_SCRIPT_SECONDS)){
                    try{
                        openMV.execScript(deployScript);
                    }catch (Exception e) {
                        state = OpenMVState.ERROR;
                    }
                } 
                break;
            case SCRIPT_RUNNING:
                if(timer.hasPeriodPassed(WAIT_TO_START_READING_VISION_DATA_SECONDS)){
                    try{
                        String reading = openMV.getSerialOutput().toString();
                        logger.log("Input", reading);
                        logger.log("Script is running", openMV.scriptRunning());
                        processor.addInput(reading);
                        visionData = processor.getCurrentVisionData();
                        logger.log("Vertical offset", visionData.getVerticalOffset());
                        logger.driverinfo("Horizontal Offset", visionData.getLateralOffset());
                    }catch (Exception e) {
                        state = OpenMVState.ERROR;
                    }
                } 
                break;
            case ERROR:
                break;
        }
    }

    public VisionData getVisionData() {
        return visionData;
    }

    public void ensureConnected(){
        if(state == OpenMVState.NOT_CONNECTED){
            try{
                openMV = new OpenMV(BAUD_RATE, Port.kUSB1, TIMEOUT);
                state = OpenMVState.CONNECTED;
            } catch(Exception e){
                state = OpenMVState.NOT_CONNECTED;
            }
        }
    }

    public boolean isConnected(){
        return state != OpenMVState.NOT_CONNECTED;
    }

    private enum OpenMVState{
        NOT_CONNECTED,
        CONNECTED,
        ERROR,
        STOPPED_SCRIPT,
        SCRIPT_RUNNING
    }

    

}
