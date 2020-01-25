package frc.robot.subsystems.drive;

public abstract class DriveFilter {
    private boolean enabled = true;

    public DriveFilter(boolean start_state) {
        this.enabled = start_state;
    }
    
    public void enable(){
        this.enabled = true;
        onEnable();
    }
    protected void onEnable() {}

    public void disable(){
        this.enabled = false;
        onDisable();
    }
    protected void onDisable() {}

    public boolean isEnabled(){
        return this.enabled;
    }
    // public DriveInput filter(DriveInput input){
    //     if ( this.enabled ) {
    //         return doFilter(input);
    //     }
    //     else{
    //         return input;
    //     }
    // }
    // abstract DriveInput doFilter(DriveInput input);
}