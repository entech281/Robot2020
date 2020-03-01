package frc.robot.controllers;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public abstract class BaseTalonController extends BaseController{

    protected TalonSRX talon = null;
    protected TalonSettings settings = null;
    protected boolean reversed = false;
    public BaseTalonController(TalonSRX talon, TalonSettings settings, boolean reversed) {
        super(reversed);
        this.talon = talon;
        this.settings = settings;
    }

    public void configure() {
        settings.configureTalon(talon);
    }

}
