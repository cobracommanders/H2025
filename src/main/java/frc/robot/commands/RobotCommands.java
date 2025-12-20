package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.stateMachine.RobotManager;
import frc.robot.subsystems.intakeRollers.IntakeRollersCommands;
import frc.robot.subsystems.intakeRollers.IntakeRollersState;
import frc.robot.subsystems.intakeRollers.IntakeRollersSubsystem;
import frc.robot.subsystems.intakeRollers.intakeRollersCommands;
import frc.robot.subsystems.intakeWrist.IntakeWristCommands;
import frc.robot.subsystems.intakeWrist.IntakeWristState;
import frc.robot.subsystems.intakeWrist.IntakeWristSubsystem;
import frc.robot.subsystems.intakeWrist.intakeWristCommands;

import static edu.wpi.first.wpilibj2.command.Commands.runOnce;
import static edu.wpi.first.wpilibj2.command.Commands.sequence;

import java.util.List;

public class RobotCommands {
  private final RobotManager robot;
  private final IntakeWristCommands intakeWrist;
  private final IntakeRollersCommands intakeRollers;
  private final Subsystem[] requirements;

  public RobotCommands(RobotManager robot, IntakeWristCommands intakeWrist, IntakeRollersCommands intakeRollers) {
    this.intakeRollers = intakeRollers;
    this.intakeWrist = intakeWrist;
    this.robot = robot;
    var requirementsList = List.of(robot.intakeRollers, robot.intakeWrist);
    requirements = requirementsList.toArray(Subsystem[]::new);
  }

  // TODO: Define commands that trigger state machine transitions
  // Each command should call the corresponding request method on RobotManager
  //
  // Example:
  public Command intakeCommand() {
   return sequence(
    intakeRollers.intakeCommand(),
    intakeWrist.intakeCommand());
  }

  public Command scoreCommand() {
    return sequence(
     intakeRollers.scoreCommand(),
     intakeWrist.scoreCommand());
   }

  public Command idleCommand() {
    return sequence(
     intakeRollers.idleCommand(),
     intakeWrist.idleCommand());
   }
  
  // public Command scoreCommand() {
  //   return Commands.runOnce(robot::scoreRequest, requirements);
  // }
  
  // public Command idleCommand() {
  //   return Commands.runOnce(robot::idleRequest, requirements)
  //       .andThen(robot.waitForState(RobotState.IDLE));
  // }
}
