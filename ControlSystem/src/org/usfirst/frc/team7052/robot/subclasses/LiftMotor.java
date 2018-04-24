package org.usfirst.frc.team7052.robot.subclasses;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class LiftMotor extends TalonSRX {
	
	public int kTimeoutMs, kPIDLoopIdx, kSlotIdx;

	public LiftMotor(int deviceNumber, int kTimeoutMs, int kPIDLoopIdx, int kSlotIdx) {
		super(deviceNumber);
		this.kTimeoutMs = kTimeoutMs;
		this.kPIDLoopIdx = kPIDLoopIdx;
		this.kSlotIdx = kSlotIdx;
		
		this.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx , kTimeoutMs);
        //set relevant to frame periods to be as fast as period rate
        this.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
        this.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, kTimeoutMs);
        
        //set peak and nominal motor outputs
        this.configNominalOutputForward(0, kTimeoutMs);
        this.configNominalOutputReverse(0, kTimeoutMs);
        this.configPeakOutputForward(1, kTimeoutMs);
        this.configPeakOutputReverse(-1, kTimeoutMs);
        
        // set closed loop PID constants
        this.selectProfileSlot(kSlotIdx, kPIDLoopIdx);
        this.config_kF(0, 0.2, kTimeoutMs);
        this.config_kP(0,  0.2,  kTimeoutMs);
        this.config_kI(0, 0, kTimeoutMs);
        this.config_kD(0, 0, kTimeoutMs);
        
        // set acceleration and vcruise velocity
        this.configMotionCruiseVelocity(15000, kTimeoutMs);
        this.configMotionAcceleration(6000, kTimeoutMs);

        // zero the sensor; set the sensor position
        this.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs);
	}
	
	public void zero() {
		this.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs);
	}
}
