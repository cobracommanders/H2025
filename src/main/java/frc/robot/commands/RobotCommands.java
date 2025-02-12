package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.List;

public class RobotCommands {
  private final RobotManager robot;
  private final Subsystem[] requirements;

  public RobotCommands(RobotManager robot) {
    this.robot = robot;
    var requirementsList = List.of(robot.scoringSubsystem);
    requirements = requirementsList.toArray(Subsystem[]::new);
  }

  public Command climbCommand() {
    return Commands.runOnce(robot::climbRequest, requirements)
        .andThen(robot.waitForState(RobotState.CLIMB));
  }
  public Command unclimbCommand() {
    return Commands.runOnce(robot::unclimbRequest, requirements)
        .andThen(robot.waitForState(RobotState.UNCLIMB));
  }

  public Command idleCommand() {
    return Commands.runOnce(robot::idleRequest, requirements)
        .andThen(robot.waitForState(RobotState.IDLE));
  }
}
