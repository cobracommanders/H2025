package frc.robot.subsystems.intakeWrist;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import dev.doglog.DogLog;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Ports;
import frc.robot.Constants.intakeWristConstants;
import frc.robot.stateMachine.StateMachine;
import frc.robot.subsystems.intakeRollers.IntakeRollersState;

public class IntakeWristSubsystem extends StateMachine<IntakeWristState>{
    
  private final TalonFX motor;
  private final TalonFXConfiguration motor_config = new TalonFXConfiguration().withSlot0(new Slot0Configs().withKP(intakeWristConstants.P).withKI(intakeWristConstants.I).withKD(intakeWristConstants.D).withKG(intakeWristConstants.G).withGravityType(GravityTypeValue.Arm_Cosine)).withFeedback(new FeedbackConfigs().withSensorToMechanismRatio((3.0/1.0)));
  private double intakePosition;
  private final double tolerance;
  private boolean brakeModeEnabled;
  private double motorCurrent;

  private MotionMagicVoltage motor_request = new MotionMagicVoltage(0).withSlot(0);
  
  public IntakeWristSubsystem() {
    super(IntakeWristState.IDLE);
    motor_config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    motor = new TalonFX(Ports.IntakeWristPorts.motor);
    motor.getConfigurator().apply(motor_config);
    motor_config.MotionMagic.MotionMagicCruiseVelocity = intakeWristConstants.MotionMagicCruiseVelocity;
    motor_config.MotionMagic.MotionMagicAcceleration = intakeWristConstants.MotionMagicAcceleration;
    motor_config.MotionMagic.MotionMagicJerk = intakeWristConstants.MotionMagicJerk;
    tolerance = 0.04;
    brakeModeEnabled = false;
  }

  public void setState(IntakeWristState newState) {
    setStateFromRequest(newState);
}
  
  protected IntakeRollersState getNextState(IntakeRollersState currentState) {
      return currentState;
    }

   public boolean atGoal() {
    return switch (getState()) {
      case IDLE -> 
        MathUtil.isNear(IntakeWristPositions.IDLE, intakePosition, tolerance);
      case INTAKE -> 
        MathUtil.isNear(IntakeWristPositions.INTAKE, intakePosition, tolerance);
      case L1 ->
        MathUtil.isNear(IntakeWristPositions.L1, intakePosition, tolerance);
     };
  }

  public boolean isIdle() {
    return getState() == IntakeWristState.IDLE;
  }

  @Override
  public void collectInputs() {
    intakePosition = motor.getPosition().getValueAsDouble();
    motorCurrent = motor.getStatorCurrent().getValueAsDouble();
    DogLog.log(getName() + "/Intake Position", intakePosition);
    DogLog.log(getName() + "/Intake wrist motor current", motorCurrent);
    DogLog.log(getName() + "/Intake wrist AtGoal", atGoal());
  }

  @Override
  public void periodic() {
    super.periodic();

    if (DriverStation.isDisabled() && brakeModeEnabled == true) {
      motor_config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      motor.getConfigurator().apply(motor_config);
      brakeModeEnabled = false;
      }
    else if (DriverStation.isEnabled() && brakeModeEnabled == false)  {
      motor_config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      motor.getConfigurator().apply(motor_config);
      brakeModeEnabled = true;
    }
    }

  public void setIntakePosition(double position) {
    motor.setControl(motor_request.withPosition(position));
    DogLog.log(getName() + "/Elbow Setpoint", position);
  }

    @Override
    protected void afterTransition(IntakeWristState newState) {
      switch (newState) {
        case IDLE -> {
          setIntakePosition(IntakeWristPositions.IDLE);
        }
        case INTAKE-> {
          setIntakePosition(IntakeWristPositions.INTAKE);
        }
        case L1 -> {
          setIntakePosition(IntakeWristPositions.L1);
        }
      }
    }

  private static IntakeWristSubsystem instance;

  public static IntakeWristSubsystem getInstance() {
      if (instance == null) instance = new IntakeWristSubsystem(); // Make sure there is an instance (this will only run once)
      return instance;
  }
}