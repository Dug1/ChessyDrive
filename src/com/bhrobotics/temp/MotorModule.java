package com.bhrobotics.temp;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public class MotorModule {
    private Victor[] controllers = new Victor[2];
    private Solenoid out;
    private Solenoid in;
    
    public MotorModule(int motorPortOne, int motorPortTwo, int solenoidPortOne, int solenoidPortTwo) {
	controllers[0] = new Victor(1, motorPortOne);
	controllers[1] = new Victor(1, motorPortTwo);
	in = new Solenoid(1, solenoidPortOne);
	out = new Solenoid(1, solenoidPortTwo);
	//figure out default config
    }

    public Victor[] getControllers() {
	return controllers;
    }
    
    public void set(double value) {
	getControllers()[0].set(value);
	getControllers()[1].set(value);
    }
    
    public double get() {
	return (getControllers()[0]).get();
    }
    
    public void setHighSpeed() {
	in.set(true);
	out.set(false);
    }
    
    public void setLowSpeed() {
	in.set(false);
	out.set(true);
    }
    
    public boolean isLowSpeed() {
	return out.get();
    }
    
    public boolean isHighSpeed() {
	return in.get();
    }
}