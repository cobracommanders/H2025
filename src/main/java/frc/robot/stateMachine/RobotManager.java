package frc.robot.stateMachine;
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

    // TODO: Implement state transition logic based on flags
    // Handle flag-triggered transitions
    for (RobotFlag flag : flags.getChecked()) {
      switch (flag) {
        case SCORE:
          // TODO: Determine when scoring should be allowed
          break;
        case INTAKE:
          nextState = RobotState.PREPARE_INTAKE;
          break;
        case CORAL_STATION_INTAKE:
          nextState = RobotState.PREPARE_CORAL_STATION_INTAKE;
          break;
        case IDLE:
          nextState = RobotState.PREPARE_IDLE;
          break;
        case PREPARE_SCORE:
          nextState = RobotState.PREPARE_SCORE;
          break;
      }
    }

    // TODO: Implement automatic state transitions
    // Handle automatic transitions based on current state
    switch (currentState) {
      case WAIT_SCORE:
      case IDLE:
        break;
      case PREPARE_CORAL_STATION_INTAKE:
        // TODO: Transition to CORAL_STATION_INTAKE when ready
        break;
      case PREPARE_SCORE:
        // TODO: Transition to WAIT_SCORE when ready
        break;
      case PREPARE_IDLE:
        // TODO: Transition to IDLE when ready
        break;
      case PREPARE_INTAKE:
        // TODO: Transition to INTAKE when ready
        break;
      case SCORING:
        // TODO: Transition back to PREPARE_IDLE after scoring
        break;
      case INTAKE:
        // TODO: Handle what happens after intaking a game piece
        break;
      case CORAL_STATION_INTAKE:
        // TODO: Handle what happens after intaking from coral station
        break;
    }

    flags.clear();
    return nextState;
  }

  @Override
  protected void afterTransition(RobotState newState) {
    // TODO: Set subsystem states based on the new robot state
    // This is where you tell each subsystem what to do when the robot enters a new state
    switch (newState) {
      case PREPARE_INTAKE -> {
        // TODO: Set intake rollers and wrist to intake configuration
      }
      case PREPARE_CORAL_STATION_INTAKE -> {
        // TODO: Set intake rollers and wrist for coral station intake
      }
      case INTAKE -> {
        // TODO: Activate intake
      }
      case CORAL_STATION_INTAKE -> {
        // TODO: Activate coral station intake
      }
      case PREPARE_IDLE -> {
        // TODO: Begin returning to idle position
      }
      case IDLE -> {
        // TODO: Set everything to idle
      }
      case PREPARE_SCORE -> {
        // TODO: Move to scoring position
      }
      case WAIT_SCORE -> {
        // TODO: Hold at scoring position
      }
      case SCORING -> {
        // TODO: Eject the game piece
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

  public void coralStationIntakeRequest(){
    flags.check(RobotFlag.CORAL_STATION_INTAKE);
  }

  public void idleRequest() {
    flags.check(RobotFlag.IDLE);
  }

  public void prepareScoreRequest() {
    flags.check(RobotFlag.PREPARE_SCORE);
  }

  public void scoreRequest(){
    flags.check(RobotFlag.SCORE);
  }

  private static RobotManager instance;

  public static RobotManager getInstance() {
    if (instance == null) instance = new RobotManager();
    return instance;
  }
}
