package com.bhrobotics.temp;

import edu.wpi.first.wpilibj.Victor;

public class Intake {
	public static final double MAX_SPEED = 0.5;
	private double hingeSpeed = 0;
	private Victor hinge;
	private Victor rollerBottom;
	private Victor rollerTop;

	public Intake(Victor hinge, Victor rollerBottom, Victor rollerTop) {
		this.hinge = hinge;
		this.rollerBottom = rollerBottom;
		this.rollerTop = rollerTop;
	}

	public void turnOn() {
		rollerTop.set(-hingeSpeed);
		rollerBottom.set(hingeSpeed);
	}

	public void turnOff() {
		rollerTop.set(0.0);
		rollerBottom.set(0.0);
	}

	public void flush() {
		rollerTop.set(hingeSpeed);
		rollerBottom.set(-hingeSpeed);
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

	public double getHingeSpeed() {
		return hingeSpeed;
	}

	public void setHingeSpeed(double speed) {
		this.hingeSpeed = speed;
	}
}
