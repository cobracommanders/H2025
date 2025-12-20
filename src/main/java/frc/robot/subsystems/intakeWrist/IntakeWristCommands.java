package frc.robot.subsystems.intakeWrist;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.intakeRollers.IntakeRollersState;
import frc.robot.subsystems.intakeRollers.IntakeRollersSubsystem;

public class IntakeWristCommands {

    public Command intakeCommand() {
        return Commands.runOnce(()-> IntakeWristSubsystem.getInstance().setState(IntakeWristState.INTAKE));
    }

    public Command scoreCommand() {
        return Commands.runOnce(()-> IntakeWristSubsystem.getInstance().setState(IntakeWristState.IDLE));
    }

    public Command idleCommand() {
        return Commands.runOnce(()-> IntakeWristSubsystem.getInstance().setState(IntakeWristState.IDLE));
    }
}
