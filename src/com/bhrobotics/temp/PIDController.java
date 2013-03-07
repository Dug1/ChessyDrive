package com.bhrobotics.temp;


public class PIDController {
	
	private double derivative;
	private double integral;
	private double proportional;
	private double goal;
	private double sum = 0;
	private double oldError = 0;
	
	public PIDController(double p, double i, double d) {
		this.setProportional(p);
		this.setIntegral(i);
		this.setDerivative(d);
		this.setGoal(0);
	}
	
	public PIDController() {
		this(1, 0, 0);
	}

	public double getDerivative() {
		return derivative;
	}

	public void setDerivative(double derivative) {
		this.derivative = derivative;
	}

	public double getIntegral() {
		return integral;
	}

	public void setIntegral(double integral) {
		this.integral = integral;
	}

	public double getProportional() {
		return proportional;
	}

	public void setProportional(double proportional) {
		this.proportional = proportional;
	}

	public double getGoal() {
		return goal;
	}

	public void setGoal(double goal) {
		this.goal = goal;
	}
	
	public double adjust(double current) {
		double error = (goal - current);
		double pTerm = error * proportional;
		double iTerm = sum + (error * integral);
		double dTerm = (oldError - error) * derivative;
		oldError = error;
		
		return current + pTerm + iTerm - dTerm; 
	}
}
