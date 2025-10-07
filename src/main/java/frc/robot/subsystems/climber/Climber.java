package frc.robot.subsystems.climber;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import dev.doglog.DogLog;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Ports.ClimberPorts;
import frc.robot.Ports;
import frc.robot.stateMachine.StateMachine;

public class Climber extends StateMachine<ClimberStates>{
    public final DoubleSubscriber climberSpeed = DogLog.tunable("climb/Speed [-1, 1]", 0.0);
    private final String name = getName();
    private final TalonFX wheelMotor;
    private final TalonFX leftMotor;
    private final TalonFX rightMotor;
    private TalonFXConfiguration wheel_motor_config = new TalonFXConfiguration();
    private TalonFXConfiguration winch_motor_config = new TalonFXConfiguration();
    public double climberPosition;
    private MotionMagicVoltage winch_motor_request = new MotionMagicVoltage(0).withSlot(0);
    private double tolerance = 1;
    private double motorCurrent = 0.0;
    private Follower rightMotorRequest = new Follower(ClimberPorts.LEFT_MOTOR_PORT, true);
    public Climber(){
      super(ClimberStates.IDLE);
      //TODO: update configs
      winch_motor_config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      wheel_motor_config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      winch_motor_config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
      winch_motor_config.MotionMagic.MotionMagicCruiseVelocity = ClimberConstants.DEPLOY_MOTION_MAGIC_CRUISE_VELOCITY;
      winch_motor_config.MotionMagic.MotionMagicAcceleration = ClimberConstants.DEPLOY_MOTION_MAGIC_ACCELERATION;
      winch_motor_config.MotionMagic.MotionMagicJerk = ClimberConstants.DEPLOY_MOTION_MAGIC_JERK;
      rightMotor = new TalonFX(Ports.ClimberPorts.RIGHT_MOTOR_PORT);
      leftMotor = new TalonFX(Ports.ClimberPorts.LEFT_MOTOR_PORT);
      wheelMotor = new TalonFX(Ports.ClimberPorts.WHEEL_CLIMBER_MOTOR_PORT);
      leftMotor.getConfigurator().apply(winch_motor_config);
      rightMotor.getConfigurator().apply(winch_motor_config);
      climberPosition = 0; //fix this by linking it to absolute encoder
    }


  @Override
  public void collectInputs(){
    motorCurrent = wheelMotor.getStatorCurrent().getValueAsDouble();
    climberPosition = leftMotor.getPosition().getValueAsDouble();
    //TODO: update climberPosition
    //TODO: log important inputs
    DogLog.log(name + "/Climber position", climberPosition);
    DogLog.log(name + "/Cage detection", cageDetected());
    //implement input collection (these values will be used in state transitions)
  }


  public void setState(ClimberStates newState) {
    setStateFromRequest(newState);
  }

  public void setClimberSpeed() {
    leftMotor.set(climberSpeed.get());
    rightMotor.set(climberSpeed.get());
  }

  public ClimberStates getNextState(ClimberStates currentState){
      ClimberStates nextState = currentState;
      switch(currentState){
        case IDLE:
          //none
          break;
        case DEPLOYING:
          if (atGoal()){
            nextState = ClimberStates.WAIT_FOR_CAGE;
          }
        break;
        case WAIT_FOR_CAGE:
          if (atGoal()) {
            nextState = ClimberStates.KEEP_SUCKING;
          }
          break;
          case KEEP_SUCKING:
              if(timeout(0.5)){
                  nextState = ClimberStates.CLIMBING;
              }
        case CLIMBING:
           if (atGoal()){
            nextState = ClimberStates.CLIMBED;
           }
           break;
        case CLIMBED:
           //do nothing, robot is done
           break;
      }
      return nextState;
  }

  public boolean atGoal(){
    return switch(getState()){
      case DEPLOYING ->

        climberPosition < ClimberPositions.DEPLOYING;
      case CLIMBING ->
      climberPosition < ClimberPositions.CLIMBING;
      case WAIT_FOR_CAGE ->
        cageDetected();
      default ->
        false;
    };
  }

  @Override
  protected void afterTransition(ClimberStates currentState){
    switch(currentState){
      case IDLE:
        //do nothing
        break;
      case DEPLOYING:
        setWinchSpeed(WinchSpeeds.DEPLOYING);
      break;
      case WAIT_FOR_CAGE:
        //set climber position, set wheels
        setWinchSpeed(WinchSpeeds.IDLE);
        setClimberWheelSpeed(ClimberWheelSpeeds.INTAKE_CAGE);
        break;
      case CLIMBING:
        //set wheels, set climber position
        setWinchSpeed(WinchSpeeds.CLIMBING);
        setClimberWheelSpeed(ClimberWheelSpeeds.IDLE);
        break;
      case CLIMBED:
        setWinchSpeed(ClimberPositions.IDLE);
        //do nothing
        break;
    case KEEP_SUCKING:
        //do nothing
        break;
      default:
        break;
    }
  }


  public void setWinchSpeed(double winchSpeed){
    leftMotor.set(-winchSpeed);
    rightMotor.set(winchSpeed);
  }
   public void setClimberWheelSpeed(double speed){
    DogLog.log(name + "/Wheel Speed", speed);
    wheelMotor.set(speed);
   }

  private boolean cageDetected(){
    return motorCurrent >  ClimberConstants.CAGE_DETECECTION_CURRENT;
  }

  public static Climber instance;
  public static Climber getInstance(){
    if(instance == null){
      instance = new Climber();
    }
    return instance;
  }

}
