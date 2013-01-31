/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.bhrobotics.temp;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class ChessyBot extends IterativeRobot {
    Joystick joystick;
    MotorModule left;
    MotorModule right;
    
    public void robotInit() {
	joystick = new Joystick(3);
	left = new MotorModule(1,2,1,2);
	right = new MotorModule(9,10,3,4);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
	double angle = joystick.getDirectionRadians();
	double magnitude = 0;
	if(joystick.getMagnitude() > 0.1) {
	    magnitude = (1 - joystick.getRawAxis(3))/2;
	}
	double x = magnitude * Math.sin(angle);
	double y = magnitude * Math.cos(angle);
	
	if(joystick.getRawButton(3)) {
	    left.setHighSpeed();
	    right.setHighSpeed();
	} else if(joystick.getRawButton(4)) {
	    left.setLowSpeed();
	    right.setLowSpeed();
	} else {}
	
	left.set(y - x);
	right.set(y + x);
	System.out.println(debugString());
    }
    
    public String debugString() {
	return "[left]:" + left.get() + "[right]:" + right.get();  
    }
}