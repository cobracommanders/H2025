package frc.robot.stateMachine;
import frc.robot.subsystems.climber.ClimberState;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.subsystems.climberwheel.ClimberWheelSpeeds;
import frc.robot.subsystems.climberwheel.ClimberWheelState;
import frc.robot.subsystems.climberwheel.ClimberWheelSubsystem;
import frc.robot.subsystems.intakeRollers.IntakeRollersState;
import frc.robot.subsystems.intakeRollers.IntakeRollersSubsystem;
import frc.robot.subsystems.intakeWrist.IntakeWristState;
import frc.robot.subsystems.intakeWrist.IntakeWristSubsystem;

public class RobotManager extends StateMachine<RobotState> {
  public final IntakeRollersSubsystem intakeRollers;
  public final IntakeWristSubsystem intakeWrist;
  public final ClimberSubsystem climber;
  public final ClimberWheelSubsystem climberWheel;

  public final FlagManager<RobotFlag> flags = new FlagManager<>("RobotManager", RobotFlag.class);

  public RobotManager() {
      super(RobotState.IDLE);
      this.climber = ClimberSubsystem.getInstance();
      this.intakeRollers = IntakeRollersSubsystem.getInstance();
      this.intakeWrist = IntakeWristSubsystem.getInstance();
      this.climberWheel = ClimberWheelSubsystem.getInstance();
    
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
        case DEEP_CLIMB:
            nextState = RobotState.PREPARE_DEEP_CLIMB;
          break;
        case CLIMB_UNCLIMB:
          nextState = RobotState.DEEP_CLIMB_UNCLIMB;
          break;
        case CLIMB_IDLE:
          nextState = RobotState.DEEP_CLIMB_WAIT;
          break;
        case CLIMB_CLIMB:
          nextState = RobotState.DEEP_CLIMB_CLIMB;
          break;
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
          currentState = RobotState.PREPARE_INTAKE;
          break;
        case CORAL_STATION_INTAKE:
          currentState = RobotState.PREPARE_CORAL_STATION_INTAKE;
          break;
        case IDLE:
          currentState = RobotState.PREPARE_IDLE;
          break;
        case L1_ROW_1:
          currentState = RobotState.PREPARE_L1_ROW_1;
          break;
        case L1_ROW_2:
          currentState = RobotState.PREPARE_L1_ROW_1;
          break;
      }
    }

  switch (currentState) {
    case WAIT_L1_ROW_1:
    case WAIT_L1_ROW_2:
    case IDLE:
    case DEEP_CLIMB_UNCLIMB:
    case DEEP_CLIMB_CLIMB:
    break;
    case PREPARE_CORAL_STATION_INTAKE:
      if(intakeWrist.atGoal()){
        nextState = RobotState.CORAL_STATION_INTAKE;
      }
      break;
    case DEEP_CLIMB_DEPLOY:
      if(ClimberSubsystem.getInstance().climberDeployed()){
        if (timeout(0.4)) {
          nextState = RobotState.DEEP_CLIMB_WAIT;
        }
      } 
      break;
    case PREPARE_DEEP_CLIMB:
      if(intakeWrist.atGoal()){
        nextState = RobotState.DEEP_CLIMB_DEPLOY;
      }
      break;
    case DEEP_CLIMB_WAIT:
      if (ClimberWheelSpeeds.INTAKE_CAGE == ClimberWheelSpeeds.STATIC_INTAKE_CAGE) {
        ClimberWheelSubsystem.getInstance().hasCage();
      }
      if (ClimberWheelSubsystem.getInstance().hasCage()) {
        nextState = RobotState.DEEP_CLIMB_CLIMB;
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
      case PREPARE_DEEP_CLIMB -> {
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.CAGE_IDLE);
      }
      case DEEP_CLIMB_DEPLOY -> {
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.CAGE_IDLE);
        climber.setState(ClimberState.DEEP_CLIMB_DEPLOY);
      }
      case DEEP_CLIMB_WAIT -> {
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.CAGE_IDLE);
        climber.setState(ClimberState.IDLE);
        climberWheel.setState(ClimberWheelState.INTAKE_CAGE);
      }
      case DEEP_CLIMB_UNCLIMB -> {
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.CAGE_IDLE);
        climber.setState(ClimberState.DEEP_CLIMB_UNWIND);
      }
      case DEEP_CLIMB_CLIMB -> {
        intakeRollers.setState(IntakeRollersState.IDLE);
        intakeWrist.setState(IntakeWristState.CAGE_IDLE);
        climberWheel.setState(ClimberWheelState.INTAKE_CAGE);
        climber.setState(ClimberState.DEEP_CLIMB_RETRACT);
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

  public void climbRequest(){
    flags.check(RobotFlag.DEEP_CLIMB);
  }

  public void climbUnclimbRequest(){
    flags.check(RobotFlag.CLIMB_UNCLIMB);
  }

  public void climbIdleRequest(){
    flags.check(RobotFlag.CLIMB_IDLE);
  }

  public void climbClimbRequest(){
    flags.check(RobotFlag.CLIMB_CLIMB);
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