package frc.robot.controllers;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public abstract class BaseTalonController extends BaseController{

    protected TalonSRX talon = null;
    protected TalonSettings settings = null;
    protected boolean reversed = false;
    private boolean enabled = true;
    public BaseTalonController(TalonSRX talon, TalonSettings settings, boolean reversed) {
        super(reversed);
        this.talon = talon;
        this.settings = settings;
    }
    
    public boolean isEnabled(){
        return enabled;
    }
    
    public boolean isReversed(){
        return reversed;
    }

    public void configure() {
        settings.configureTalon(talon);
        ErrorCode err = talon.configPeakCurrentLimit(0);
        if ( err ==  ErrorCode.OK){
            settings.configureTalon(talon);
            this.enabled = true;
        }
        else{
            this.enabled = false;
        }
    }
}