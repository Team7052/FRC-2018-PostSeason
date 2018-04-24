package org.usfirst.frc.team7052.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team7052.robot.Constants;
import org.usfirst.frc.team7052.robot.subsystems.Arm;
import org.usfirst.frc.team7052.robot.subsystems.DriveTrain;
import org.usfirst.frc.team7052.robot.subsystems.ElevatorLift;


public class CommandBase extends Command {

	public static DriveTrain driveTrain;
    public static ElevatorLift elevatorLift;
    public static Arm arm;

    public static void init() {
        driveTrain = DriveTrain.getInstance();
        elevatorLift = ElevatorLift.getInstance();
        arm = Arm.getInstance();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
	
}
