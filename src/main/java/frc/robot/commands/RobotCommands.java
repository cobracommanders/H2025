package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.stateMachine.RobotManager;
import frc.robot.stateMachine.RobotState;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.subsystems.intakeWrist.IntakeWristPositions;

import static edu.wpi.first.wpilibj2.command.Commands.none;

import java.util.List;

public class RobotCommands {
  private final RobotManager robot;
  private final Subsystem[] requirements;

  public RobotCommands(RobotManager robot) {
    this.robot = robot;
    var requirementsList = List.of(robot.intakeRollers, robot.intakeWrist, robot.climber);
    requirements = requirementsList.toArray(Subsystem[]::new);
  }

  public Command scoreCommand() {
    return Commands.runOnce(robot::scoreRequest, requirements);
        //.andThen(robot.waitForState(RobotState.SCORE_L1));
  }

  public Command idleCommand() {
    return Commands.runOnce(robot::idleRequest, requirements)
        .andThen(robot.waitForState(RobotState.IDLE));
  }

  public Command intakeIdleCommand(){
    return new ConditionalCommand(idleCommand(), none(), ()->robot.getState() == RobotState.INTAKE);
  }

  public Command intakeCommand(){
    return Commands.runOnce(robot::intakeRequest, requirements);
    // /.andThen(robot.waitForState(RobotState.INTAKE));
  }

  public Command coralStationIntakeCommand(){
    return Commands.runOnce(robot::coralStationIntakeRequest, requirements)
    .andThen(robot.waitForState(RobotState.CORAL_STATION_INTAKE));
  }

  public Command L1Row1Command(){
    return Commands.runOnce(robot::prepareL1Row1Request, requirements)
    .andThen(robot.waitForState(RobotState.WAIT_L1_ROW_1));
  }

  public Command L1Row2Command(){
    return Commands.runOnce(robot::prepareL1Row2Request, requirements)
    .andThen(robot.waitForState(RobotState.WAIT_L1_ROW_2));
  }

  public Command climbCommand() {
    return (Commands.runOnce(robot::climbRequest, requirements))
    .andThen(robot.waitForState(RobotState.DEEP_CLIMB_WAIT));
}

public Command climbUnClimbCommand() {
  return new ConditionalCommand(Commands.runOnce(robot::climbUnclimbRequest), climbCommand(), () -> (RobotManager.getInstance().getState() == RobotState.DEEP_CLIMB_WAIT) || (RobotManager.getInstance().getState() == RobotState.DEEP_CLIMB_CLIMB));
}

public Command climbIdleCommand() {
  return new ConditionalCommand(Commands.runOnce(robot::climbIdleRequest), climbCommand(), () -> RobotManager.getInstance().getState() == RobotState.DEEP_CLIMB_WAIT || RobotManager.getInstance().getState() == RobotState.DEEP_CLIMB_CLIMB || RobotManager.getInstance().getState() == RobotState.DEEP_CLIMB_UNCLIMB);
}

public Command climbUpCommand() {
  return new ConditionalCommand(Commands.runOnce(robot::climbClimbRequest), 
  climbCommand(), 
  () -> (RobotManager.getInstance().getState() == RobotState.DEEP_CLIMB_WAIT) || (RobotManager.getInstance().getState() == RobotState.DEEP_CLIMB_CLIMB));
}

  public Command climb(){
    return Commands.runOnce(robot::climbRequest, requirements);
  }

  public Command climbUnclimb(){
    return Commands.runOnce(robot::climbUnclimbRequest, requirements);
  }

  public Command climbClimb(){
    return Commands.runOnce(robot::climbClimbRequest, requirements);
  }

  public Command climbIdle(){
    return Commands.runOnce(robot::climbIdleRequest, requirements);
  }

   public Command changeClimberPID(){
    return Commands.runOnce(() -> ClimberSubsystem.getInstance().setRetractConfig());
  }
}
