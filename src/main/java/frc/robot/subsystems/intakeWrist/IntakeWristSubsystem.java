package frc.robot.subsystems.intakeWrist;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;

import dev.doglog.DogLog;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.Ports;
import frc.robot.Constants.intakeWristConstants;
import frc.robot.stateMachine.StateMachine;
import frc.robot.subsystems.intakeRollers.IntakeRollersState;

public class IntakeWristSubsystem extends StateMachine<IntakeWristState>{
    
  public final TalonFX lMotor;
  private final TalonFX rMotor;
  public final DutyCycleEncoder encoder;
  private final TalonFXConfiguration motor_config = new TalonFXConfiguration().withSlot0(new Slot0Configs().withKP(intakeWristConstants.P).withKI(intakeWristConstants.I).withKD(intakeWristConstants.D).withKG(intakeWristConstants.G).withGravityType(GravityTypeValue.Arm_Cosine)).withFeedback(new FeedbackConfigs().withSensorToMechanismRatio((8.0357/1.0)));
  private double intakePosition;
  private final double tolerance;
  private double motorCurrent;
  private Follower right_motor_request = new Follower(Ports.IntakeWristPorts.lMotor, true);

  private MotionMagicVoltage left_motor_request = new MotionMagicVoltage(0).withSlot(0);
  
  public IntakeWristSubsystem() {
    super(IntakeWristState.IDLE);
    motor_config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
    motor_config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    lMotor = new TalonFX(Ports.IntakeWristPorts.lMotor);
    rMotor = new TalonFX(Ports.IntakeWristPorts.rMotor);
    motor_config.MotionMagic.MotionMagicCruiseVelocity = intakeWristConstants.MotionMagicCruiseVelocity;
    motor_config.MotionMagic.MotionMagicAcceleration = intakeWristConstants.MotionMagicAcceleration;
    motor_config.MotionMagic.MotionMagicJerk = intakeWristConstants.MotionMagicJerk;
    lMotor.getConfigurator().apply(motor_config);
    rMotor.getConfigurator().apply(motor_config);
    encoder = new DutyCycleEncoder(0);
    tolerance = 0.1;
  }

  public void setState(IntakeWristState newState) {
    setStateFromRequest(newState);
  }
  
  protected IntakeWristState getNextState(IntakeWristState currentState) {
    return currentState;
  }

   public boolean atGoal() {
    return switch (getState()) {
      case IDLE -> 
        MathUtil.isNear(IntakeWristPositions.IDLE, intakePosition, tolerance);
      case CAGE_IDLE -> 
        MathUtil.isNear(IntakeWristPositions.CAGE_IDLE, intakePosition, tolerance);
      case INTAKE -> 
        MathUtil.isNear(IntakeWristPositions.INTAKE, intakePosition, tolerance);
      case CORAL_STATION_INTAKE -> 
        MathUtil.isNear(IntakeWristPositions.CORAL_STATION_INTAKE, intakePosition, tolerance);
      case L1_ROW_1 ->
        MathUtil.isNear(IntakeWristPositions.L1_ROW_1, intakePosition, tolerance);
      case L1_ROW_2 ->
        MathUtil.isNear(IntakeWristPositions.L1_ROW_2, intakePosition, tolerance);
      };
  }

  public void increaseSetpoint(){
    System.out.println("Setpoints increased to " + (IntakeWristPositions.INTAKE + 0.005));
    switch (getState()) {
      case L1_ROW_1 -> {
        IntakeWristPositions.L1_ROW_1 += 0.005;
        setIntakePosition(IntakeWristPositions.L1_ROW_1);
        break;
      }
      case L1_ROW_2 -> {
        IntakeWristPositions.L1_ROW_2 += 0.005;
        setIntakePosition(IntakeWristPositions.L1_ROW_2);
        break;
      }
      case INTAKE-> {
        IntakeWristPositions.INTAKE += 0.005;
        setIntakePosition(IntakeWristPositions.INTAKE);
        break;
      }
      case CORAL_STATION_INTAKE->{
        IntakeWristPositions.CORAL_STATION_INTAKE += 0.005;
        setIntakePosition(IntakeWristPositions.CORAL_STATION_INTAKE);
        break;
      }
    }
  }

  public void decreaseSetpoint(){
    System.out.println("Setpoints decreased to " + (IntakeWristPositions.INTAKE - 0.005));
    switch (getState()) {
      case L1_ROW_1 -> {
        IntakeWristPositions.L1_ROW_1 -= 0.005;
        setIntakePosition(IntakeWristPositions.L1_ROW_1);
        break;
      }
      case L1_ROW_2 -> {
        IntakeWristPositions.L1_ROW_2 -= 0.005;
        setIntakePosition(IntakeWristPositions.L1_ROW_2);
        break;
      }
      case INTAKE-> {
        IntakeWristPositions.INTAKE -= 0.005;
        setIntakePosition(IntakeWristPositions.INTAKE);
        break;
      }
      case CORAL_STATION_INTAKE->{
        IntakeWristPositions.CORAL_STATION_INTAKE -= 0.005;
        setIntakePosition(IntakeWristPositions.CORAL_STATION_INTAKE);
        break;
      }
}
}

  public boolean isIdle() {
    return getState() == IntakeWristState.IDLE;
  }

  @Override
  public void collectInputs() {
    intakePosition = lMotor.getPosition().getValueAsDouble();
    motorCurrent = lMotor.getStatorCurrent().getValueAsDouble();
    DogLog.log(getName() + "/Intake Position", intakePosition);
    //DogLog.log(getName() + "/Intake wrist motor current", motorCurrent);
    //DogLog.log(getName() + "/Intake wrist AtGoal", atGoal());
  }

  @Override
  public void periodic() {
    // System.out.println(encoder.get());ph
    super.periodic();
    }

  public void setIntakePosition(double position) {
    rMotor.setControl(right_motor_request);
    lMotor.setControl(left_motor_request.withPosition(position));
    //DogLog.log(getName() + "/Elbow Setpoint", position);
  }

    @Override
    protected void afterTransition(IntakeWristState newState) {
      switch (newState) {
        case IDLE -> {
          setIntakePosition(IntakeWristPositions.IDLE);
        }
        case CAGE_IDLE -> {
          setIntakePosition(IntakeWristPositions.CAGE_IDLE);
        }
        case INTAKE-> {
          setIntakePosition(IntakeWristPositions.INTAKE);
        }
        case CORAL_STATION_INTAKE-> {
          setIntakePosition(IntakeWristPositions.CORAL_STATION_INTAKE);
        }
        case L1_ROW_1 -> {
          setIntakePosition(IntakeWristPositions.L1_ROW_1);
        }
        case L1_ROW_2 -> {
          setIntakePosition(IntakeWristPositions.L1_ROW_2);
        }
      }
    }

  private static IntakeWristSubsystem instance;

  public static IntakeWristSubsystem getInstance() {
      if (instance == null) instance = new IntakeWristSubsystem(); // Make sure there is an instance (this will only run once)
      return instance;
  }
}