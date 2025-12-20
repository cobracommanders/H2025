package frc.robot.subsystems.intakeWrist;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
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
import frc.robot.Ports;
import frc.robot.Constants.intakeWristConstants;
import frc.robot.stateMachine.StateMachine;

public class IntakeWristSubsystem extends StateMachine<IntakeWristState>{

  // Motor controllers for the wrist mechanism
  public final TalonFX lMotor;
  private final TalonFX rMotor;

  // Motor configuration - you'll need to set up PID, motion magic, etc.
  private final TalonFXConfiguration motor_config = new TalonFXConfiguration().withSlot0(new Slot0Configs().withKP(intakeWristConstants.P).withKI(intakeWristConstants.I).withKD(intakeWristConstants.D).withKG(intakeWristConstants.G).withGravityType(GravityTypeValue.Arm_Cosine));

  // Current position reading from the motor
  private double intakePosition;

  // Tolerance for determining if we've reached our target position
  private final double tolerance;

  // Control requests for the motors
  private Follower right_motor_request = new Follower(Ports.IntakeWristPorts.lMotor, true);
  private MotionMagicVoltage left_motor_request = new MotionMagicVoltage(0).withSlot(0);

  public IntakeWristSubsystem() {
    super(IntakeWristState.INIT); // TODO: Pass your initial state here once you define states

    // TODO: Initialize your motors here
    // You need to create TalonFX motors using ports from Ports.IntakeWristPorts

    motor_config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
    motor_config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    lMotor = new TalonFX(Ports.IntakeWristPorts.lMotor);
    rMotor = new TalonFX(Ports.IntakeWristPorts.rMotor);

    motor_config.MotionMagic.MotionMagicAcceleration = 1;
    motor_config.MotionMagic.MotionMagicCruiseVelocity = 1;
    motor_config.MotionMagic.MotionMagicJerk = 1;

    lMotor.getConfigurator().apply(motor_config);
    rMotor.getConfigurator().apply(motor_config);

    // Set the tolerance for position checking
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
      case INIT ->
        MathUtil.isNear(IntakeWristState.INIT.getPosition(), intakePosition, tolerance);
      case IDLE ->
        MathUtil.isNear(IntakeWristState.IDLE.getPosition(), intakePosition, tolerance);
      case INTAKE ->
        MathUtil.isNear(IntakeWristState.INTAKE.getPosition(), intakePosition, tolerance);
    };
  }

  @Override
  public void collectInputs() {
    intakePosition = lMotor.getPosition().getValueAsDouble();
    DogLog.log(getName() + "/Intake Position", intakePosition);
  }

  @Override
  public void periodic() {
    super.periodic();
  }

  public void setIntakePosition(double position) {
    rMotor.setControl(right_motor_request);
    lMotor.setControl(left_motor_request.withPosition(position));
  }

  @Override
  protected void afterTransition(IntakeWristState newState) {
    switch (newState) {
      case INIT -> setIntakePosition(IntakeWristState.INIT.getPosition());
      case INTAKE -> setIntakePosition(IntakeWristState.INTAKE.getPosition());
      case IDLE -> setIntakePosition(IntakeWristState.IDLE.getPosition());
    }
  }

  private static IntakeWristSubsystem instance;

  public static IntakeWristSubsystem getInstance() {
      if (instance == null) instance = new IntakeWristSubsystem();
      return instance;
  }
}
