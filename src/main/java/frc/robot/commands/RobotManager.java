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
      
      }
    }

  @Override
  public void periodic() {
    super.periodic(); 
  }

  public void idleRequest() {
    setStateFromRequest(RobotState.IDLE);
  }

  public void scoreRequest() {
    setStateFromRequest(RobotState.SCORE);
  }


  public void stopScoringRequest() {
    switch (getState()) {
      case SCORE-> {}

      default -> setStateFromRequest(RobotState.IDLE);
    }
  }
}