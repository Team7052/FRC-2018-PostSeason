package org.usfirst.frc.team7052.robot.subsystems;

import org.usfirst.frc.team7052.robot.Constants;
import org.usfirst.frc.team7052.robot.subclasses.LiftMotor;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class ElevatorLift extends PIDSubsystem {
	public enum LiftPosition {
		bottom, middle, top, custom;
	}
	private static ElevatorLift instance;

    private static LiftMotor liftMotor;
    public LiftPosition liftPosition;
    public LiftPosition targetPosition;
    public double targetRawPosition = 0;
    
    public double motorPIDOutputSpeed = 0.0;
    
    private ElevatorLift() {
    		super(0.1,0,0.1);
        liftMotor = new LiftMotor(Constants.kMotorElevatorLift, 0, 0, 10);
        
        // lift stage starts at the bottom always
        liftPosition = LiftPosition.bottom;
        this.setAbsoluteTolerance(1);
        this.enable();
    }

    public static ElevatorLift getInstance() {
        if (instance == null) instance = new ElevatorLift();
        return instance;
    }
    
    public void setPercentSpeed(double percent) {
    		liftMotor.set(ControlMode.PercentOutput, percent);
    }
    
    public double getPercentSpeed() {
    		return liftMotor.getMotorOutputPercent();
    }
    
    public void setLiftPosition(LiftPosition position) {
    		if (position == LiftPosition.bottom) {
    			setRawPosition(0);
    			this.setSetpoint(0);
    		}
    		else if (position == LiftPosition.middle) {
    			setRawPosition(-4096 * 120);
    			this.setSetpoint(-120);
    		}
    		else if (position == LiftPosition.top) {
    			setRawPosition(-4096 * 360);
    			this.setSetpoint(-360);
    			
    		}
    }
    
    public void setRawPosition(double position) {
    		this.targetRawPosition = position;
    }
    
    public double getRawPosition() {
    		return liftMotor.getSelectedSensorPosition(0);
    }
    
    public void zeroCurrentPosition() {
    		liftMotor.zero();
    }
    
    @Override
    protected void initDefaultCommand() {
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return this.getRawPosition() / 4096.0;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		motorPIDOutputSpeed = -output;
		System.out.println(-output + " " + this.getSetpoint() + " " + this.returnPIDInput());
	}
}
