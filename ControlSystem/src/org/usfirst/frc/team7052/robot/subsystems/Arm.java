package org.usfirst.frc.team7052.robot.subsystems;

import org.usfirst.frc.team7052.robot.Constants;
import org.usfirst.frc.team7052.robot.OI;
import org.usfirst.frc.team7052.robot.OIMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {
    private static Arm instance;
    private Spark verticalLiftMotor;
    private Spark rotatingLeftMotor;
    private Spark rotatingRightMotor;
    private Spark leftWheelsMotor;
    private Spark rightWheelsMotor;

    private Arm() {
    	verticalLiftMotor = new Spark(Constants.kMotorArmVerticalLift);
        rotatingLeftMotor = new Spark(Constants.kMotorArmRotatingLeft);
        rotatingRightMotor = new Spark(Constants.kMotorArmRotatingRight);
        leftWheelsMotor = new Spark(Constants.kMotorArmWheelsLeft);
        rightWheelsMotor = new Spark(Constants.kMotorArmWheelsRight);
    }
    public static Arm getInstance() {
        if (instance == null) instance = new Arm();
        return instance;
    }

    //TODO: Find Arm positions when opened and closed

    /* Functions for arm manipulation */
    public void spinWheels(OI oi) {
	    	boolean spinIn = oi.getInput(OIMap.buttonX) == 1;
	    	boolean spinOut = oi.getInput(OIMap.buttonY) == 1;
	    	
	    	if (spinIn && !spinOut) {
	    		leftWheelsMotor.set(-0.65);
		    	rightWheelsMotor.set(-0.65);
	    	}
	    	else if (spinOut && !spinIn) {
	    		leftWheelsMotor.set(0.5);
	    		rightWheelsMotor.set(0.5);
	    	}
	    	else {
	    		leftWheelsMotor.set(0);
	    		rightWheelsMotor.set(0);
	    	}
    }

    public void openRotatingArms() {
	    	rotatingLeftMotor.set(0.2);
	    	rotatingRightMotor.set(0.2);
    }
    
    public void closeRotatingArms() {
	    	rotatingLeftMotor.set(0);
	    	rotatingRightMotor.set(0);
    }
    
    public void squeezeCube() {
	    	rotatingLeftMotor.set(-0.2);
	    	rotatingRightMotor.set(0.2);
    }
    
    public void liftArm() {
        verticalLiftMotor.set(-0.5);
    }
    public void lowerArm() {
        verticalLiftMotor.set(0.4);
    }
    public void hoverArm() {
        verticalLiftMotor.set(-0.1);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
