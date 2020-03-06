/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.controllers;

public abstract class BaseController {

    private  boolean reversed = false;
    public BaseController(boolean reversed){
        this.reversed = reversed;
    }
    public boolean isReversed(){
        return reversed;
    }

    protected double correctDirection(double input){
        if ( reversed ){
            return -input;
        }
        else{
            return input;
        }
    }
}