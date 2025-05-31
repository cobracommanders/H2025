package frc.robot.subsystems.climber;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import dev.doglog.DogLog;
import frc.robot.Ports;
import frc.robot.stateMachine.StateMachine;

public class ClimberSubsystem extends StateMachine<ClimberState>{
    
  private final TalonFX lMotor;
  private final TalonFX rMotor;
  
  private ClimberState currentState;
  private final TalonFXConfiguration motor_config = new TalonFXConfiguration();
  private Follower right_motor_request = new Follower(Ports.IntakeWristPorts.lMotor, true);
  private double GEAR_RATIO = 100/1;
  
  public ClimberSubsystem() {
      super(ClimberState.IDLE);
      // motor = new LazySparkMax(Ports.IntakePorts.LMOTOR, MotorType.kBrushless);
      motor_config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
      motor_config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      lMotor = new TalonFX(Ports.ClimberPorts.LEFT_CLIMBER_MOTOR);
      rMotor = new TalonFX(Ports.ClimberPorts.RIGHT_CLIMBER_MOTOR);  
      lMotor.getConfigurator().apply(motor_config);
      rMotor.getConfigurator().apply(motor_config);    
  }

  public void setState(ClimberState newState) {
      setStateFromRequest(newState);
    }

    public void setClimberSpeeds(double ClimberSpeeds){
      //DogLog.log(getName() + "/Intake Roller speed", ClimberSpeeds);
      lMotor.set(ClimberSpeeds);
      rMotor.set(-ClimberSpeeds);
    }

    @Override
    protected void afterTransition(ClimberState newState) {
      switch (newState) {
        case IDLE -> {
          setClimberSpeeds(0);
        }
        case WAIT -> {
          setClimberSpeeds(0);
        }
        case CLIMB -> {
          setClimberSpeeds(0.5);
        }
        case DEPLOY -> {
          setClimberSpeeds(0.5);
        }
        case UNCLIMB -> {
          setClimberSpeeds(-0.5);
        }
        default -> {}
      }
    }

  @Override
  public void periodic() {

  }

  public boolean atGoal(){
    return true;
  }

  private static ClimberSubsystem instance;

  public static ClimberSubsystem getInstance() {
      if (instance == null) instance = new ClimberSubsystem(); // Make sure there is an instance (this will only run once)
      return instance;
  }
}