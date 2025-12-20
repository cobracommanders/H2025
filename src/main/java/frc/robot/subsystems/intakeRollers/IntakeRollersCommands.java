package frc.robot.subsystems.intakeRollers;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class IntakeRollersCommands {
    public Command scoreCommand() {
        return Commands.runOnce(()-> IntakeRollersSubsystem.getInstance().setState(IntakeRollersState.SCORE));
    }
    public Command intakeCommand() {
        return Commands.runOnce(()-> IntakeRollersSubsystem.getInstance().setState(IntakeRollersState.INTAKE));
    }
    public Command idleCommand() {
        return Commands.runOnce(()-> IntakeRollersSubsystem.getInstance().setState(IntakeRollersState.IDLE));
    }
}
