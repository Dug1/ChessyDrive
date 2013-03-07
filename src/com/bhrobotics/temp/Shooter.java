package com.bhrobotics.temp;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public class Shooter {
	public static final double MAX_SPEED = -1.0;
	protected static final double AUTO_SPEED = -1.0;
	private double speed = 0;
	private double stop = 0.0;

	private Victor fast;
	private Victor slow;
	private Encoder encoder;
	private PIDController controller;
	private Thread thread;
	private boolean stopped = false;
	private Solenoid topSolenoid = new Solenoid(1,1);
	private Solenoid bottomSolenoid = new Solenoid(2,2);
	private DigitalInput limitSwitch = new DigitalInput(1);
	//private Thread pistonThread;
	
	private class PIDThread implements Runnable {

		public PIDController controller;
		public Victor victor;
		private Encoder encoder;
		
		public PIDThread(PIDController controller, Victor victor, Encoder encoder) {
			this.controller = controller;
			this.victor = victor;
			this.encoder = encoder;
		}
		
		public void run() {
			while(!stopped) {
				victor.set(controller.adjust(encoder.getRate()));
			}
		}
		
	}
	
	private class PistonThread implements Runnable {
		
		private Solenoid topSolenoid;
		private Solenoid bottomSolenoid;
		
		public PistonThread(Solenoid topSolenoid, Solenoid bottomSolenoid) {
			this.topSolenoid = topSolenoid;
			this.bottomSolenoid = bottomSolenoid;
		}
		
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//ignore
			}
			if (topSolenoid.get() && !bottomSolenoid.get()) {
				topSolenoid.set(false);
				bottomSolenoid.set(true);
			} else {
				topSolenoid.set(true);
				bottomSolenoid.set(false);
			}
		}
	}
	
	public Shooter(Victor fast, Victor slow, Encoder encoder) {
		this.fast = fast;
		this.slow = slow;
		this.encoder = encoder;
		this.controller = new PIDController(1.3,0.1,0);
		topSolenoid.set(true);
		bottomSolenoid.set(false);
	}
	
	public void turnOn() {
		controller.setGoal(speed);
		slow.set(speed);
		if (limitSwitch.get()) {
			topSolenoid.set(true);
			bottomSolenoid.set(false);
		} else {
			topSolenoid.set(false);
			bottomSolenoid.set(true);
		}
		//pistonThread = new Thread(new PistonThread(topSolenoid, bottomSolenoid));
		//pistonThread.start();
		//pistonThread.sleep(1000);
	}
	
	public void turnOff() {
		topSolenoid.set(true);
		bottomSolenoid.set(false);
		controller.setGoal(0.0);
		slow.set(0.0);
		//pistonThread.interrupt();
	}

	public double getSpeed() {
		return fast.get();
	}

	public void setSpeed(double speed) {
		this.speed = -speed;
	}
	
	public void start() {
		stopped = false;
		encoder.start();
		thread = new Thread(new PIDThread(controller, fast, encoder));
		thread.start();
	}
	
	public void stop() {
		stopped = true;
		encoder.stop();
		thread = null;
	}
}
