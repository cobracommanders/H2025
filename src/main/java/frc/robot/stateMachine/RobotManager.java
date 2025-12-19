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
    for (RobotFlag flag : flags.getChecked()) {
      switch (flag) {
        case SCORE:
          switch (nextState) {
            case WAIT_L1_ROW_1:
              nextState = RobotState.SCORE_L1_ROW_1;
                break;
            case WAIT_L1_ROW_2:
              nextState = RobotState.SCORE_L1_ROW_2;
              break;
            case PREPARE_L1_ROW_1:
              nextState = RobotState.SCORE_L1_ROW_1;
              break;
            case PREPARE_L1_ROW_2:
              nextState = RobotState.SCORE_L1_ROW_2;
              break;
            default:
              break;
            }
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
        case L1_ROW_1:
          nextState = RobotState.PREPARE_L1_ROW_1;
          break;
        case L1_ROW_2:
          nextState = RobotState.PREPARE_L1_ROW_1;
          break;
      }
    }

  switch (currentState) {
    case WAIT_L1_ROW_1:
    case WAIT_L1_ROW_2:
    case IDLE:
    break;
    case PREPARE_CORAL_STATION_INTAKE:
      if(intakeWrist.atGoal()){
        nextState = RobotState.CORAL_STATION_INTAKE;
      }
      break;
    case PREPARE_L1_ROW_1:
      if(intakeWrist.atGoal()){
        nextState = RobotState.WAIT_L1_ROW_1;
      }
      break;
    case PREPARE_L1_ROW_2:
      if(intakeWrist.atGoal()){
        nextState = RobotState.WAIT_L1_ROW_2;
      }
      break;
    case PREPARE_IDLE:
      if(intakeWrist.atGoal()){
        nextState = RobotState.IDLE;
      }
      break;
    case PREPARE_INTAKE:
      if(intakeWrist.atGoal()){
        nextState = RobotState.INTAKE;
      }
      break;
    case SCORE_L1_ROW_1:
      if (timeout(2)){
        nextState = RobotState.PREPARE_IDLE;
      }
      break;
    case SCORE_L1_ROW_2:
      if (timeout(2)){
        nextState = RobotState.PREPARE_IDLE;
      }
      break;
    case INTAKE:
      if(IntakeRollersSubsystem.getInstance().hasCoral()){
        nextState = RobotState.PREPARE_L1_ROW_1;
      }
      break;
    case CORAL_STATION_INTAKE:
      if(IntakeRollersSubsystem.getInstance().hasCoral()){
        nextState = RobotState.PREPARE_L1_ROW_1;
      }
  }
  flags.clear();
  return nextState;
};
  

  @Override
  protected void afterTransition(RobotState newState) {
    switch (newState) {
      case PREPARE_INTAKE -> {
        intakeRollers.setState(IntakeRollersState.INTAKE);
        intakeWrist.setState(IntakeWristState.INTAKE);
      }
      case PREPARE_CORAL_STATION_INTAKE -> {
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.CORAL_STATION_INTAKE);
      }
      case INTAKE -> {
        intakeRollers.setState(IntakeRollersState.INTAKE);
        intakeWrist.setState(IntakeWristState.INTAKE);
      }
      case CORAL_STATION_INTAKE -> {
        intakeRollers.setState(IntakeRollersState.CORAL_STATION_INTAKE);
        intakeWrist.setState(IntakeWristState.CORAL_STATION_INTAKE);
      }
      case PREPARE_IDLE ->{
        intakeRollers.setState(IntakeRollersState.INTAKE);
        intakeWrist.setState(IntakeWristState.IDLE);
      }
      case IDLE -> {
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.IDLE);
      }
      case PREPARE_L1_ROW_1 -> {
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.L1_ROW_1);
      }
      case PREPARE_L1_ROW_2 -> {
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.L1_ROW_2);
      }
      case WAIT_L1_ROW_1 -> {
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.L1_ROW_1);
      }
      case WAIT_L1_ROW_2 -> {
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.L1_ROW_2);
      }
      case SCORE_L1_ROW_1 -> {
        intakeRollers.setState(IntakeRollersState.SCORE_L1_ROW_1);
      }
      case SCORE_L1_ROW_2 -> {
        intakeRollers.setState(IntakeRollersState.SCORE_L1_ROW_2);
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

  public void prepareL1Row1Request() {
    flags.check(RobotFlag.L1_ROW_1);
  }

  public void prepareL1Row2Request() {
    flags.check(RobotFlag.L1_ROW_2);
  }

  public void scoreRequest(){
    flags.check(RobotFlag.SCORE);
  }

  // public void stopScoringRequest() {
  //   switch (getState()) {
  //     case SCORE_L1_ROW_1-> {}
  //     default -> setStateFromRequest(RobotState.IDLE);
  //   }
  // }

  private static RobotManager instance;

  public static RobotManager getInstance() {
    if (instance == null) instance = new RobotManager(); // Make sure there is an instance (this will only run once)
    return instance;
}
}