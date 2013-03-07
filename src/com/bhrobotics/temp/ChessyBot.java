/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.bhrobotics.temp;

import java.util.Timer;
import java.util.TimerTask;

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

	private Joystick driverJoystick;
	private Joystick intakeJoystick;
	private MotorModule left;
	private MotorModule right;
	private Intake intake;
	private Shooter shooter;
	//private DigitalInput valve;
	private DigitalInput reset;
	private Relay compressor;
	private CheesyDrive cheesy;
	private OneStickDrive stick;
	//private ThrottleCalculator throttle;
	private TwistCalculator twist;
	private DriveStyle style;
	private DriveCalculator calculator;
	private DigitalInput input;
	private DigitalInput other;
	private boolean runningAuto = false;
	private Timer timer;
	private DigitalInput valve;
	private double turningScale;
	private double normalTurning = 0.75;
	private double reallySlowTurning = 0.2;
	

	public void robotInit() {
		driverJoystick = new Joystick(2);
		intakeJoystick = new Joystick(1);
		left = new MotorModule(1, 3, 1, 2);
		right = new MotorModule(2, 4, 3, 4);
		intake = new Intake(new Victor(1, 5), new Victor(1, 6), new Victor(1, 7), new Encoder(new DigitalInput(1,1), new DigitalInput(1,2)));
		shooter = new Shooter(new Victor(1, 8), new Victor(1, 9), null);
		valve = new DigitalInput(1, 3);
		compressor = new Relay(1, 1);
		cheesy = new CheesyDrive(driverJoystick);
		stick = new OneStickDrive();
		//throttle = new ThrottleCalculator(driverJoystick);
		twist = new TwistCalculator(driverJoystick);
		style = stick;
		calculator = twist;
		reset = new DigitalInput(1,9);
		shooter.start();
	}

	public void autonomousInit() {
		Timer timer = new Timer(); 

		//stop all functions
		timer.schedule(new TimerTask() {
			public void run() {
				shooter.setSpeed(0);
				intake.setHingeSpeed(0);
				intake.setGoalValue(Intake.START_POSITION);
				intake.setHingePosition();
			}
		}, 10000);

		//turn on shooter
		timer.schedule(new TimerTask() {

			public void run() {
				shooter.setSpeed(Shooter.AUTO_SPEED);
			}

		}, 1000);

		intake.setHingeSpeed(Intake.MAX_SPEED);
	}

	private class StopMovementTask extends TimerTask {

		public void run() {
			left.set(0);
			right.set(0);
			runningAuto = false;
		}
	}
	/**
	 * This function is called periodically during operator control
	 */

	public void teleopInit() {
	}

	public void teleopPeriodic() {
		//Compressor
		if (driverJoystick.getRawButton(7) && !valve.get()) {
			compressor.set(Relay.Value.kForward);
		} else {
			compressor.set(Relay.Value.kOff);
		}

		// setStyle
		if (driverJoystick.getRawButton(9)) {
			style = cheesy;
			System.out.println("Switched to cheesy drive.");
		} else if (driverJoystick.getRawButton(10)) {
			style = stick;
			System.out.println("Switched to normal drive.");
		}

		// setTwist
		if (driverJoystick.getRawButton(11)) {
			calculator = twist;
			System.out.println("Switched to twist");
		} /*else if (driverJoystick.getRawButton(12)) {
			calculator = throttle;
			System.out.println("Switched to x-axis");
		}*/

		// gear shift
		if (driverJoystick.getRawButton(5)) {
			left.setLowSpeed();
			right.setLowSpeed();
		} else {
			left.setHighSpeed();
			right.setHighSpeed();
		}

		// intake hinge

		if (intakeJoystick.getRawButton(9) && !intake.topPressed()) {
			intake.setHingeMotor(-1.0);                    //start position
		} else if (intakeJoystick.getRawButton(11) && !intake.bottomPressed()) {
			intake.setHingeMotor(1.0);                     //ground position
		} else if (intakeJoystick.getRawButton(1)) {
			intake.setHingeMotor(intakeJoystick.getY() * intake.MAX_SPEED);
		}
		//intake.setHingePosition();

		//reset intake
		if (reset.get()) {
			intake.reset();
		}

		// intake rollers

		if(!intakeJoystick.getRawButton(12)) {
			if (intakeJoystick.getRawButton(5)) {
				intake.turnOnTop();
			} else {
				intake.turnOffTop();
			}
			
			if(intakeJoystick.getRawButton(3)) {
				intake.turnOnBottom();
			} else {
				intake.turnOffBottom();
			}
		} else {
			intake.flush();
		}

		// shooter
		shooter.setSpeed((1 - driverJoystick.getRawAxis(5)) / 2);
		if (driverJoystick.getRawButton(2)) {
			shooter.turnOn();
		} else {
			shooter.turnOff();
		}
		
		// turning scale
		if (driverJoystick.getRawButton(6)) {
			turningScale = reallySlowTurning;
		} else {
			turningScale = normalTurning;
		}

		//		if(!runningAuto) {
		//			TimerTask t = new StopMovementTask();
		//			if(driverJoystick.getRawButton(3)) {
		//				left.set(1);
		//				right.set(-1);
		//				timer.schedule(t, 1000);
		//				runningAuto = true;
		//			} else if(driverJoystick.getRawButton(4)) {
		//				left.set(1);
		//				right.set(-1);
		//				timer.schedule(t,2000);
		//				runningAuto = true;
		//			} else if(driverJoystick.getRawButton(5)) {
		//				left.set(1);
		//				right.set(-1);
		//				timer.schedule(t, 3000);
		//				runningAuto = true;
		//			} else if(driverJoystick.getRawButton(1)) {
		//				left.set(1);
		//				right.set(-1);
		//				timer.schedule(t, 4000);
		//				runningAuto = true;
		//			} else if(driverJoystick.getRawButton(6)) {
		//				left.set(1);
		//				right.set(-1);
		//				timer.schedule(t, 5000);
		//				runningAuto = true;
		//			}
		//		}


		//if (!runningAuto) {
		// drive train
		double[] coordinates = style.drive(driverJoystick.getRawButton(8), calculator);
		double x = coordinates[0];
		double y = coordinates[1];
		if (Math.abs(x) < Math.abs(0.5 * y)) {
			x = 0;
		} else if (Math.abs(y) < Math.abs(0.5 * x)) {
			y = 0;
		}
		x *= turningScale;
		left.set(-y + x);
		right.set(y + x);
		//}

		//System.out.println(debugString());
	}

	public String debugString() {
		return "[hinge]:" + intake.getHingeDistance();
	}
}