package org.usfirst.frc.team7052.robot.commands;

import org.usfirst.frc.team7052.robot.OI;
import org.usfirst.frc.team7052.robot.OIMap;
import org.usfirst.frc.team7052.robot.subsystems.ElevatorLift.LiftPosition;

public class ControlElevatorCommand extends CommandBase {
    public OI oi;
    boolean toggledManual = false;
    public ControlElevatorCommand(OI oi) {
        this.oi = oi;
        requires(elevatorLift);
    }

    @Override
    protected void initialize() {
    	
    }

    @Override
    protected void execute() {
		int dPadVal = oi.getDPad();
		boolean emergStop = oi.getInput(OIMap.buttonB) == 1;
		boolean reset = oi.getInput(OIMap.buttonA) == 1;
		if (emergStop) {
			toggledManual = true;
			elevatorLift.setPercentSpeed(0);
			dPadVal = -1;
		}
		
		if (reset) {
			// zero current
			elevatorLift.zeroCurrentPosition();
		}
		
		// if user hits the left or right  bumpers
		boolean leftPressed = Math.abs(oi.getInput(OIMap.leftBumper2)) > 0.1;
		boolean rightPressed = Math.abs(oi.getInput(OIMap.rightBumper2)) > 0.1;
		
		if (dPadVal == 0) {
			elevatorLift.setLiftPosition(LiftPosition.top);
			toggledManual = false;			
		}
		else if (dPadVal == 180) {
			elevatorLift.setLiftPosition(LiftPosition.bottom);
			toggledManual = false;
		}
		else if (dPadVal != -1) {
			elevatorLift.setLiftPosition(LiftPosition.middle);
			toggledManual = false;
		}
		// if user needs to manually adjust position by a bit
		if (leftPressed || rightPressed) toggledManual = true;
		
		
		if (toggledManual) {
			elevatorLift.setLiftPosition(LiftPosition.custom);
			if (leftPressed) {
				elevatorLift.setPercentSpeed(-0.5);
			}
			else if (rightPressed) {
				elevatorLift.setPercentSpeed(0.9);
			}
			else {
				elevatorLift.setPercentSpeed(0);
			}
		}
		else {
			// if not toggled manual
			/*double currentPosition = elevatorLift.getRawPosition();
			double targetPosition = elevatorLift.targetRawPosition;
			if (Math.abs(targetPosition - currentPosition) > 4096 * 2) {
				// see if it is reached
				if (targetPosition > currentPosition) { 
					elevatorLift.setPercentSpeed(-0.8);
				}
				else {
					elevatorLift.setPercentSpeed(1.0);
				}
			}
			else {
				elevatorLift.setPercentSpeed(0);
			}
			System.out.println(currentPosition + " " + targetPosition);*/
			elevatorLift.setPercentSpeed(elevatorLift.motorPIDOutputSpeed);
			
		}
		
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

