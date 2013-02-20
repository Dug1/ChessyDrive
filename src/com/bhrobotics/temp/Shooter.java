package com.bhrobotics.temp;

import edu.wpi.first.wpilibj.Victor;

public class Shooter {
	public static final double MAX_SPEED = -1.0;
	public static final double AUTO_SPEED = -0.4;
	private double speed = 0;

	private Victor fast;
	private Victor slow;
	//private Encoder encoder;

	public Shooter(Victor fast, Victor slow) {
		this.fast = fast;
		this.slow = slow;
		//this.encoder = encoder;
		//encoder.setDistancePerPulse(1000);
	}

	public void turnOn() {
		fast.set(speed);
		slow.set(speed);
	}

	public void turnOff() {
		fast.set(0.0);
		slow.set(0.0);
	}

	public double getSpeed() {
		return 0;
		//return Math.abs(encoder.getRate());
	}

	public void setSpeed(double speed) {
		this.speed = -speed;
	}
}
