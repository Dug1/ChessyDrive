package com.bhrobotics.temp;

import com.sun.squawk.util.MathUtils;

import edu.wpi.first.wpilibj.Joystick;

public class ThrottleCalculator implements DriveCalculator {

	private double magnitude;
	private double angle;
	private Joystick joystick;

	public ThrottleCalculator(Joystick joystick) {
		this.joystick = joystick;
	}

	public void recalculate() {
		double y = joystick.getRawAxis(2);
		double x = joystick.getRawAxis(1);
		double magnitude = Math.sqrt((y * y) + (x * x));
		angle = MathUtils.acos(x / magnitude);
		if (y < 0) {
			angle = -angle;
		}
	}

	public double getMagnitude() {
		return magnitude;
	}

	public double getAngle() {
		return angle;
	}

}
