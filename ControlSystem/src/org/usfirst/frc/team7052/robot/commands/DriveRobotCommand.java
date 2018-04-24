package org.usfirst.frc.team7052.robot.commands;

import org.usfirst.frc.team7052.robot.Constants;
import org.usfirst.frc.team7052.robot.DrivingState;
import org.usfirst.frc.team7052.robot.OI;
import org.usfirst.frc.team7052.robot.OIMap;
import org.usfirst.frc.team7052.robot.subsystems.ElevatorLift.LiftPosition;

public class DriveRobotCommand extends CommandBase {
    public OI oi;

    DrivingState drivingState = DrivingState.regular;
    public DriveRobotCommand(OI oi) {
        this.oi = oi;
        requires(driveTrain);
    }

    @Override
    protected void initialize() {
    		
    }

    @Override
    protected void execute() {
        if (Math.abs(oi.getInput(OIMap.leftBumper)) > 0.01) {
            drivingState = DrivingState.careful;
        }
        else if (Math.abs(oi.getInput(OIMap.rightBumper)) > 0.01) {
            drivingState = DrivingState.turbo;
        }
        else {
            drivingState = DrivingState.regular;
        }

        double xAxis = oi.getInput(OIMap.rightAxisX);
        double yAxis = oi.getInput(OIMap.leftAxisY);
        // if the elevator lift is up, then don't allow the lift to go any higher
        if (elevatorLift.liftPosition == LiftPosition.top) {
        		drivingState = DrivingState.careful;
        }

        double speed = yAxis;

        double leftSpeed = speed;
        double rightSpeed = speed;

        double turnValue = xAxis;

        if (turnValue > 0) { // turningRight
            rightSpeed *= 1 - turnValue * (1 - Constants.kSlowestRobotSpeed);
        }
        else if (turnValue < 0) { // turning left
            leftSpeed *= turnValue * (1 - Constants.kSlowestRobotSpeed) + 1;
        }

        if (Math.abs(speed) < 0.1  && turnValue != 0) {
            leftSpeed = -turnValue;
            rightSpeed = turnValue;
        }
        leftSpeed = driveTrain.getNormalizedSpeed(leftSpeed, drivingState);
        rightSpeed = driveTrain.getNormalizedSpeed(rightSpeed, drivingState);

        //slowest speed = kSlowestRobotSpeed
        //normalize speed so that it is always between kSlowestRobotSpeed and 1, -Constants.kSlowestRobotSpeed and -1, or 0

        driveTrain.tankDrive(leftSpeed, rightSpeed);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected void end() {
        //stop driveTrain
        driveTrain.stop();
    }
}