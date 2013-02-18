package com.bhrobotics.temp;

import com.sun.squawk.util.MathUtils;

import edu.wpi.first.wpilibj.Joystick;

public class TwistCalculator implements DriveCalculator {

	private double magnitude;
	private double angle;
	private Joystick joystick;

	public TwistCalculator(Joystick joystick) {
		this.joystick = joystick;
	}

	public void recalculate() {
		double y = joystick.getRawAxis(2);
		double x = joystick.getRawAxis(6);
		magnitude = Math.sqrt((y * y) + (x * x));
		if(magnitude != 0) {
			angle = MathUtils.acos(x / magnitude);
			if (y < 0) {
				angle = -angle;
			}
		} else {
			angle = 0;
		}
	}

	public double getMagnitude() {
		return magnitude;
	}

	public double getAngle() {
		return angle;
	}

}
