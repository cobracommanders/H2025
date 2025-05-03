package frc.robot.stateMachine;
import static edu.wpi.first.wpilibj2.command.Commands.none;

import dev.doglog.DogLog;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.stateMachine.FlagManager;
import frc.robot.subsystems.intakeRollers.IntakeRollersState;
import frc.robot.subsystems.intakeRollers.IntakeRollersSubsystem;
import frc.robot.subsystems.intakeWrist.IntakeWristState;
import frc.robot.subsystems.intakeWrist.IntakeWristSubsystem;

public class RobotManager extends StateMachine<RobotState> {
  public final IntakeRollersSubsystem intakeRollers;
  public final IntakeWristSubsystem intakeWrist;

  public final FlagManager<RobotFlag> flags = new FlagManager<>("RobotManager", RobotFlag.class);

  public RobotManager() {
      super(RobotState.IDLE);
      this.intakeRollers = IntakeRollersSubsystem.getInstance();
      this.intakeWrist = IntakeWristSubsystem.getInstance();
    
  }

  @Override
  protected void collectInputs() {
  }

  @Override
  protected RobotState getNextState(RobotState currentState) {
    flags.log();
    RobotState nextState = currentState;
    for (RobotFlag flag : flags.getChecked()) {
      switch (flag) {
        case INTAKE:
            currentState = RobotState.PREPARE_INTAKE;
          break;
        case IDLE:
            currentState = RobotState.PREPARE_IDLE;
          break;
        case L1:
            currentState = RobotState.PREPARE_L1;
          break;
        case SCORE:
            currentState = RobotState.SCORE_L1;
          break;
        default:
          break;
      }
    }

  switch (currentState) {
    case WAIT_L1:
    case IDLE:
    break;

    case PREPARE_L1:
      if(intakeWrist.atGoal())
        nextState = RobotState.WAIT_L1;
      break;
    case PREPARE_IDLE:
      if(intakeWrist.atGoal())
        nextState = RobotState.IDLE;
      break;
    case PREPARE_INTAKE:
      if(intakeWrist.atGoal())
        nextState = RobotState.INTAKE;
      break;
    case SCORE_L1:
      if (timeout(2)){
        nextState = RobotState.PREPARE_IDLE;
    }
      break;
    case INTAKE:
      if(IntakeRollersSubsystem.getInstance().hasCoral()){
        nextState = RobotState.PREPARE_L1;
    }
      break;
  }
  flags.clear();
  return nextState;
};
  

  @Override
  protected void afterTransition(RobotState newState) {
    switch (newState) {
      case PREPARE_INTAKE -> {
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.INTAKE);
      }
      case INTAKE -> {
        intakeRollers.setState(IntakeRollersState.INTAKE);
        intakeWrist.setState(IntakeWristState.INTAKE);
      }
      case PREPARE_IDLE ->{
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.IDLE);
      }
      case IDLE -> {
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.IDLE);
      }
      case PREPARE_L1 -> {
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.L1);
      }
      case WAIT_L1 -> {
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.L1);
      }
      case SCORE_L1 -> {
        intakeRollers.setState(IntakeRollersState.SCORE_L1);
        intakeWrist.setState(IntakeWristState.L1);
      }
      
      }
    }

  @Override
  public void periodic() {
    super.periodic(); 
  }

  public void intakeRequest(){
    flags.check(RobotFlag.INTAKE);
  }

  public void idleRequest() {
    flags.check(RobotFlag.IDLE);
  }

  public void prepareL1Request(){
    flags.check(RobotFlag.L1);
  }

  public void scoreRequest(){
    flags.check(RobotFlag.SCORE);
  }

  public void stopScoringRequest() {
    switch (getState()) {
      case SCORE_L1-> {}
      default -> setStateFromRequest(RobotState.IDLE);
    }
  }
}