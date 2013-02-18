/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.bhrobotics.temp;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class ChessyBot extends IterativeRobot {

	private Joystick joystick;
	private MotorModule left;
	private MotorModule right;
	private Intake intake;
	private Shooter shooter;
	private DigitalInput valve;
	private Relay compressor;
	private CheesyDrive cheesy;
	private OneStickDrive stick;
	private ThrottleCalculator throttle;
	private TwistCalculator twist;
	private DriveStyle style;
	private DriveCalculator calculator;

	public void robotInit() {
		joystick = new Joystick(1);
		left = new MotorModule(1, 3, 1, 2);
		right = new MotorModule(2, 4, 3, 4);
		intake = new Intake(new Victor(1, 5), new Victor(1, 6), new Victor(1, 7));
		shooter = new Shooter(new Victor(1, 8), new Victor(1, 9), new Encoder(1, 2, 1, 3));
		valve = new DigitalInput(1, 1);
		compressor = new Relay(1, 1);
		cheesy = new CheesyDrive(joystick);
		stick = new OneStickDrive();
		throttle = new ThrottleCalculator(joystick);
		twist = new TwistCalculator(joystick);
		style = stick;
		calculator = twist;

	}

	/**
	 * This function is called periodically during operator control
	 */
	
	public void teleopPeriodic() {
		
		if (joystick.getRawButton(7)) {
			compressor.set(Relay.Value.kForward);
		} else {
			compressor.set(Relay.Value.kOff);
		}

		// setStyle
		if (joystick.getRawButton(9)) {
			style = cheesy;
			System.out.println("Switched to cheesy drive.");
		} else if (joystick.getRawButton(10)) {
			style = stick;
			System.out.println("Switched to normal drive");
		}

		// setTwist
		if (joystick.getRawButton(11)) {
			calculator = twist;
			System.out.println("Switched to twist");
		} else if (joystick.getRawButton(12)) {
			calculator = throttle;
			System.out.println("Switched to x-axis");
		}

		// gear shift
		if (joystick.getRawButton(5)) {
			left.setHighSpeed();
			right.setHighSpeed();
		} else {
			left.setLowSpeed();
			right.setLowSpeed();
		}

		// intake hinge

		intake.setHingeSpeed((1 - joystick.getRawAxis(4)) / 2);
		if (joystick.getRawButton(1)) {
			intake.bumpUp();
		} else if (joystick.getRawButton(6)) {
			intake.bumpDown();
		} else {
			intake.stop();
		}

		// intake rollers

		if (joystick.getRawButton(3)) {
			intake.turnOn();
		} else if (joystick.getRawButton(4)) {
			intake.flush();
		} else {
			intake.turnOff();
		}

		// shooter
		shooter.setSpeed((1 - joystick.getRawAxis(5)) / 2);
		if (joystick.getRawButton(2)) {
			shooter.turnOn();
		} else {
			shooter.turnOff();
		}

		// drive train
		double[] coordinates = style.drive(joystick.getRawButton(8), calculator);
		double x = coordinates[0];
		double y = coordinates[1];
		
		left.set(-y + x);
		right.set(y + x);

		//System.out.println(debugString());
	}

	public String debugString() {
		return "[left]:" + left.get() + "[right]:" + right.get() + "[shooter]:" + shooter.getSpeed() + "[hinge]:" + intake.getHingeSpeed();
	}
}