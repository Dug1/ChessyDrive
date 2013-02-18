package com.bhrobotics.temp;

public interface DriveStyle {

	public void drive(boolean reverse, double angle, double magnitude, MotorModule left, MotorModule right);

}
