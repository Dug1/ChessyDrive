package com.bhrobotics.temp;

import edu.wpi.first.wpilibj.Joystick;

public class CheesyDrive implements DriveStyle {

	private Joystick joystick;

	public CheesyDrive(Joystick joystick) {
		this.joystick = joystick;
	}

	public void drive(boolean reverse, double angle, double magnitude, MotorModule left, MotorModule right ) {
		double direction = 1.0;
		if (reverse) {
			direction = -1.0;
		}

		if (joystick.getMagnitude() > 0.1) {
			magnitude = (1 - joystick.getRawAxis(3)) / 2;
		}

		double x = magnitude * Math.cos(angle);
		double y = magnitude * Math.sin(angle);
		left.set(direction * (y - x));
		right.set(direction * (y + x));
	}

}
