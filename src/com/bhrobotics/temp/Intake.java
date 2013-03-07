package com.bhrobotics.temp;

import edu.wpi.first.wpilibj.DigitalInput;
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
	private DigitalInput topLimit;
	private DigitalInput bottomLimit;
	private static double GO_VALUE = 1.0;
	private static double BACK_VALUE = -1.0;
	private static double STOP = 0.0;

	public Intake(Victor hinge, Victor rollerBottom, Victor rollerTop, DigitalInput top, DigitalInput  bottom) {
		this.hinge = hinge;
		this.rollerBottom = rollerBottom;
		this.rollerTop = rollerTop;
		topLimit = top;
		bottomLimit = bottom;
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
		rollerTop.set(BACK_VALUE);
		rollerBottom.set(BACK_VALUE);
	}
	
	public void turnOnTop() {
		rollerTop.set(BACK_VALUE);
	}
	
	public void turnOffTop() {
		rollerTop.set(STOP);
	}
	
	public void turnOnBottom() {
		rollerBottom.set(BACK_VALUE);
	}

	public void turnOffBottom() {
		rollerBottom.set(STOP);
	}
	
	public void turnOff() {
		rollerTop.set(STOP);
		rollerBottom.set(STOP);
	}

	public void flush() {
		rollerTop.set(GO_VALUE);
		rollerBottom.set(GO_VALUE);
	}

	public void bumpUp() {
		hinge.set(hingeSpeed);
	}

	public void bumpDown() {
		hinge.set(-hingeSpeed);
	}

	public void stop() {
		hinge.set(STOP);
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
			hinge.set(STOP);
		}
	}
	
	public void reset() {
		encoder.reset();
	}
	
	public boolean topPressed() {
		return topLimit.get();
	}
	
	public boolean bottomPressed() {
		return bottomLimit.get();
	}
}
