package com.bhrobotics.temp;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class Intake {
	public static final double MAX_SPEED = 0.5;
	public static final double FEEDER_POSITION = 0.0;
	public static final double GROUND_POSITION = 0.0;
	public static final double START_POSITION = 0.0;

	private double hingeSpeed = 0;
	private Victor hinge;
	private Victor rollerBottom;
	private Victor rollerTop;
	private Encoder encoder;
	private double goalValue;
	private double goValue = 1.0;
	private double backValue = -1.0;
	private double stop = 0.0;

	public Intake(Victor hinge, Victor rollerBottom, Victor rollerTop, Encoder encoder) {
		this.hinge = hinge;
		this.rollerBottom = rollerBottom;
		this.rollerTop = rollerTop;
		this.encoder = encoder;
		encoder.setDistancePerPulse(0.001);
		encoder.setMinRate(0);
		startEncoder();
	}

	public void startEncoder() {
		encoder.start();
	}

	public void stopEncoder() {
		encoder.stop();
	}

	public void turnOn() {
		rollerTop.set(backValue);
		rollerBottom.set(backValue);
	}
	
	public void turnOnTop() {
		rollerTop.set(backValue);
	}
	
	public void turnOffTop() {
		rollerTop.set(stop);
	}
	
	public void turnOnBottom() {
		rollerBottom.set(backValue);
	}

	public void turnOffBottom() {
		rollerBottom.set(stop);
	}
	
	public void turnOff() {
		rollerTop.set(stop);
		rollerBottom.set(stop);
	}

	public void flush() {
		rollerTop.set(goValue);
		rollerBottom.set(goValue);
	}

	public void bumpUp() {
		hinge.set(hingeSpeed);
	}

	public void bumpDown() {
		hinge.set(-hingeSpeed);
	}

	public void stop() {
		hinge.set(stop);
	}

	public boolean getHingeDistance() {
		encoder.start();
		return encoder.getStopped();
	}

	public void setHingeSpeed(double speed) {
		this.hingeSpeed = speed;
	}

	public void setHingeMotor(double speed) {
		hinge.set(speed);
	}
	
	public void setGoalValue(double updateValue) {
		goalValue = updateValue;
	}
	
	public double getGoalValue() {
		return goalValue;
	}

	public void setHingePosition() {
		if (encoder.getDistance() < goalValue) {
			hinge.set(hingeSpeed);
		} else if (encoder.getDistance() > goalValue) {
			hinge.set(-hingeSpeed);
		} else {
			hinge.set(stop);
		}
	}
	
	public void reset() {
		encoder.reset();
	}
}
