package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.stateMachine.RobotManager;

import java.util.List;

public class RobotCommands {
  private final RobotManager robot;
  private final Subsystem[] requirements;

  public RobotCommands(RobotManager robot) {
    this.robot = robot;
    var requirementsList = List.of(robot.intakeRollers, robot.intakeWrist);
    requirements = requirementsList.toArray(Subsystem[]::new);
  }

  // TODO: Define commands that trigger state machine transitions
  // Each command should call the corresponding request method on RobotManager
  //
  // Example:
  // public Command intakeCommand() {
  //   return Commands.runOnce(robot::intakeRequest, requirements);
  // }
  //
  // public Command scoreCommand() {
  //   return Commands.runOnce(robot::scoreRequest, requirements);
  // }
  //
  // public Command idleCommand() {
  //   return Commands.runOnce(robot::idleRequest, requirements)
  //       .andThen(robot.waitForState(RobotState.IDLE));
  // }
}
