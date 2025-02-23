package frc.robot.commands;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.StateMachine;
import frc.robot.subsystems.Scoring.ScoringState;
import frc.robot.subsystems.Scoring.ScoringSubsystem;

public class RobotManager extends StateMachine<RobotState> {
  public final ScoringSubsystem scoringSubsystem;

  public RobotManager(
      ScoringSubsystem scoringSubsystem) {
    super(RobotState.IDLE);
    this.scoringSubsystem = scoringSubsystem;
  }

  @Override
  protected void collectInputs() {
  }

  @Override
  protected RobotState getNextState(RobotState currentState) {
    return switch (currentState) {
      case IDLE,
            // CLIMB,
            // UNCLIMB 
            SCORE ->
          currentState;
      };
    };
  

  @Override
  protected void afterTransition(RobotState newState) {
    switch (newState) {
      case IDLE -> {
        scoringSubsystem.setState(ScoringState.IDLE);
      }
      case SCORE -> {
        scoringSubsystem.setState(ScoringState.SCORE);
      }
      // case CLIMB -> {
      //   scoringSubsystem.setState(ScoringState.CLIMB);
      // }
      // case UNCLIMB -> {
      //   scoringSubsystem.setState(ScoringState.UNCLIMB);
      // }
      
      }
    }

  @Override
  public void periodic() {
    super.periodic(); 
  }

  public void idleRequest() {
    setStateFromRequest(RobotState.IDLE);
  }

  public void scoreRequest(){
    setStateFromRequest(RobotState.SCORE);
  }
  // public void climbRequest() {
  //   setStateFromRequest(RobotState.CLIMB);
  // }

  // public void unclimbRequest() {
  //   setStateFromRequest(RobotState.UNCLIMB);
  // }

  public void stopScoringRequest() {
    switch (getState()) {
      case SCORE-> {}
      // case CLIMB-> {}
      // case UNCLIMB -> {}

      default -> setStateFromRequest(RobotState.IDLE);
    }
  }
}