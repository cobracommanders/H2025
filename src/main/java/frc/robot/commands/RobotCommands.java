package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.stateMachine.RobotManager;
import frc.robot.stateMachine.RobotState;

import static edu.wpi.first.wpilibj2.command.Commands.none;

import java.util.List;

public class RobotCommands {
  private final RobotManager robot;
  private final Subsystem[] requirements;

  public RobotCommands(RobotManager robot) {
    this.robot = robot;
    var requirementsList = List.of(robot.intakeRollers, robot.intakeWrist);
    requirements = requirementsList.toArray(Subsystem[]::new);
  }

  public Command scoreCommand() {
    return Commands.runOnce(robot::scoreRequest, requirements)
        .andThen(robot.waitForState(RobotState.PREPARE_IDLE));
  }

  public Command idleCommand() {
    return Commands.runOnce(robot::idleRequest, requirements)
        .andThen(robot.waitForState(RobotState.IDLE));
  }

  public Command intakeIdleCommand(){
    return new ConditionalCommand(idleCommand(), none(), robot.getState() == RobotState.INTAKE);
  }

  public Command intakeCommand(){
    return Commands.runOnce(robot::intakeRequest, requirements)
    .andThen(robot.waitForState(RobotState.PREPARE_L1));
  }

  public Command L1Command(){
    return Commands.runOnce(robot::prepareL1Request, requirements)
    .andThen(robot.waitForState(RobotState.WAIT_L1));
  }
}
