package frc.robot.commands;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.StateMachine;
import frc.robot.subsystems.Scoring.ScoringState;
import frc.robot.subsystems.Scoring.ScoringSubsystem;

public class RobotManager extends StateMachine<RobotState> {
  private static final double MINIMUM_SHOT_TIME = 0.5;
  public final ScoringSubsystem scoringSubsystem;
  private final Timer shotTimer = new Timer();
  private boolean facingSpeakerAngle = false;
  private boolean facingFeedSpotAngle = false;

  private boolean confirmShotActive = false;

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
        // case INTAKING_BACK -> !queuer.hasNote() ? RobotState.INTAKING_FORWARD_PUSH :
        // currentState;
        // case INTAKING_FORWARD_PUSH -> {
        //   if (!queuer.atGoal()) {
        //     yield currentState;
        //   }

        //   if (confirmShotActive) {
        //     yield RobotState.SPEAKER_PREPARE_TO_SCORE;
        //   }

        //   yield RobotState.IDLE_WITH_GP;
        // }
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
        // case INTAKING_BACK -> {
        //   arm.setState(ArmState.IDLE);
        //   shooter.setState(ShooterState.IDLE_STOPPED);
        //   intake.setState(IntakeState.INTAKING_BACK);
        //   queuer.setState(QueuerState.INTAKING_BACK);
        //   swerve.setSnapsEnabled(false);
        //   swerve.setSnapToAngle(0);
        // }
        // case INTAKING_FORWARD_PUSH -> {
        //   arm.setState(ArmState.IDLE);
        //   shooter.setState(ShooterState.IDLE_STOPPED);
        //   intake.setState(IntakeState.INTAKING_FORWARD_PUSH);
        //   queuer.setState(QueuerState.INTAKING_FORWARD_PUSH);
        //   swerve.setSnapsEnabled(false);
        //   swerve.setSnapToAngle(0);
        // 

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


  public void stopShootingRequest() {
    // If we are actively taking a shot, ignore the request to avoid messing up shooting
    switch (getState()) {
      case SCORE-> {}

      default -> setStateFromRequest(RobotState.IDLE);
    }
  }
}