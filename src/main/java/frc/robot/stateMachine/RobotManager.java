package frc.robot.stateMachine;
import frc.robot.subsystems.intakeRollers.IntakeRollersSubsystem;
import frc.robot.subsystems.intakeWrist.IntakeWristSubsystem;

public class RobotManager extends StateMachine<RobotState> {
  public final IntakeRollersSubsystem intakeRollers;
  public final IntakeWristSubsystem intakeWrist;

  public final FlagManager<RobotFlag> flags = new FlagManager<>("RobotManager", RobotFlag.class);

  public RobotManager() {
      super(null); // TODO: Pass your initial state here once you define states
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
    //
    // Example:
    // for (RobotFlag flag : flags.getChecked()) {
    //   switch (flag) {
    //     case INTAKE:
    //       nextState = RobotState.PREPARE_INTAKE;
    //       break;
    //     case SCORE:
    //       if (currentState == RobotState.WAIT_SCORE) {
    //         nextState = RobotState.SCORING;
    //       }
    //       break;
    //     ...
    //   }
    // }

    // TODO: Implement automatic state transitions
    // Handle automatic transitions based on current state
    //
    // Example:
    // switch (currentState) {
    //   case PREPARE_INTAKE:
    //     if (intakeWrist.atGoal()) {
    //       nextState = RobotState.INTAKE;
    //     }
    //     break;
    //   case INTAKE:
    //     if (intakeRollers.hasCoral()) {
    //       nextState = RobotState.PREPARE_SCORE;
    //     }
    //     break;
    //   ...
    // }

    flags.clear();
    return nextState;
  }

  @Override
  protected void afterTransition(RobotState newState) {
    // TODO: Set subsystem states based on the new robot state
    // This is where you tell each subsystem what to do when the robot enters a new state
    //
    // Example:
    // switch (newState) {
    //   case PREPARE_INTAKE -> {
    //     intakeRollers.setState(IntakeRollersState.INTAKE);
    //     intakeWrist.setState(IntakeWristState.INTAKE);
    //   }
    //   case IDLE -> {
    //     intakeRollers.setState(IntakeRollersState.IDLE);
    //     intakeWrist.setState(IntakeWristState.IDLE);
    //   }
    //   ...
    // }
  }

  @Override
  public void periodic() {
    super.periodic();
  }

  // TODO: Define request methods for each flag you create
  // These methods are called from RobotCommands to trigger state changes
  //
  // Example:
  // public void intakeRequest() {
  //   flags.check(RobotFlag.INTAKE);
  // }
  //
  // public void scoreRequest() {
  //   flags.check(RobotFlag.SCORE);
  // }
  //
  // public void idleRequest() {
  //   flags.check(RobotFlag.IDLE);
  // }

  private static RobotManager instance;

  public static RobotManager getInstance() {
    if (instance == null) instance = new RobotManager();
    return instance;
  }
}
