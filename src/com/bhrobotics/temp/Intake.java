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
		rollerTop.set(-1.0);
		rollerBottom.set(-1.0);
	}
	
	public void turnOnTop() {
		rollerTop.set(-1.0);
	}
	
	public void turnOffTop() {
		rollerTop.set(0.0);
	}
	
	public void turnOnBottom() {
		rollerBottom.set(-1.0);
	}

	public void turnOffBottom() {
		rollerBottom.set(0.0);
	}
	
	public void turnOff() {
		rollerTop.set(0.0);
		rollerBottom.set(0.0);
	}

	public void flush() {
		rollerTop.set(1.0);
		rollerBottom.set(1.0);
	}

	public void bumpUp() {
		hinge.set(hingeSpeed);
	}

	public void bumpDown() {
		hinge.set(-hingeSpeed);
	}

	public void stop() {
		hinge.set(0.0);
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
			hinge.set(0);
		}
	}
	
	public void reset() {
		encoder.reset();
	}
}
