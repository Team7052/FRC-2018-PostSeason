package org.usfirst.frc.team7052.robot.subsystems;

import org.usfirst.frc.team7052.robot.Constants;
import org.usfirst.frc.team7052.robot.DrivingState;
import org.usfirst.frc.team7052.robot.OI;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends Subsystem {
    public DifferentialDrive drive;
    public SpeedControllerGroup leftGroup;
    public SpeedControllerGroup rightGroup;

    private DriveTrain() {
        //TODO: Tune PID Values
        leftGroup = new SpeedControllerGroup(new Spark(Constants.kMotorFrontLeft), new Spark(Constants.kMotorBackLeft));
        rightGroup = new SpeedControllerGroup(new Spark(Constants.kMotorFrontRight), new Spark(Constants.kMotorBackRight));
        leftGroup.setInverted(true);
        rightGroup.setInverted(true);
        drive = new DifferentialDrive(leftGroup, rightGroup);
    }

    @Override
    protected void initDefaultCommand() {
    }

    public static DriveTrain instance;

    public static DriveTrain getInstance() {
        if (instance == null) instance = new DriveTrain();
        return instance;
    }

    public void stop() {
        leftGroup.set(0);
        rightGroup.set(0);
        drive.stopMotor();
    }
    
    public void tankDrive(double leftSpeed, double rightSpeed) {
    		drive.tankDrive(leftSpeed, rightSpeed);
    }

    public double getNormalizedSpeed(double speed, DrivingState state) {
        //speed is always between -1 -> -kSlowestRobotSpeed or 0 or kSlowestRobotSpeed -> 1
        speed *= 1 - Constants.kSlowestRobotSpeed;
        if (Math.abs(speed) < 0.1) return 0;
        if (speed > 0) speed = speed + Constants.kSlowestRobotSpeed;
        else speed = speed - Constants.kSlowestRobotSpeed;

        if (state == DrivingState.careful) speed *= 0.6;
        else if (state == DrivingState.regular) speed *= 0.7;
        else if (state == DrivingState.turbo) speed *= 0.95;

        if (speed > 0 && speed < Constants.kSlowestRobotSpeed) speed = Constants.kSlowestRobotSpeed;
        else if (-Constants.kSlowestRobotSpeed < speed && speed < 0) speed = -Constants.kSlowestRobotSpeed;
        return speed;
    }
}