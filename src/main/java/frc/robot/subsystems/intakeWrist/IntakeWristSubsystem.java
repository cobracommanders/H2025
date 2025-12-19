package frc.robot.subsystems.intakeWrist;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.Ports;
import frc.robot.Constants.intakeWristConstants;
import frc.robot.stateMachine.StateMachine;

public class IntakeWristSubsystem extends StateMachine<IntakeWristState>{

  // Motor controllers for the wrist mechanism
  public final TalonFX lMotor;
  private final TalonFX rMotor;

  // Encoder for position feedback
  public final DutyCycleEncoder encoder;

  // Motor configuration - you'll need to set up PID, motion magic, etc.
  private final TalonFXConfiguration motor_config = new TalonFXConfiguration();

  // Current position reading from the motor
  private double intakePosition;

  // Tolerance for determining if we've reached our target position
  private final double tolerance;

  // Control requests for the motors
  private Follower right_motor_request = new Follower(Ports.IntakeWristPorts.lMotor, true);
  private MotionMagicVoltage left_motor_request = new MotionMagicVoltage(0).withSlot(0);

  public IntakeWristSubsystem() {
    super(null); // TODO: Pass your initial state here once you define states

    // TODO: Initialize your motors here
    // You need to create TalonFX motors using ports from Ports.IntakeWristPorts
    lMotor = new TalonFX(Ports.IntakeWristPorts.lMotor);
    rMotor = new TalonFX(Ports.IntakeWristPorts.rMotor);

    // TODO: Configure your motors
    // Set up PID constants, motion magic parameters, neutral mode, etc.
    // Use values from Constants.intakeWristConstants
    // Apply the configuration to both motors using motor.getConfigurator().apply(config)

    // Initialize the encoder
    encoder = new DutyCycleEncoder(0);

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
    // TODO: Determine if the wrist has reached its target position
    // Compare the current position to the target position for the current state
    // Return true if within tolerance
    return false;
  }

  @Override
  public void collectInputs() {
    // TODO: Read sensor values here
    // Get the current position from the motor encoder
    intakePosition = lMotor.getPosition().getValueAsDouble();
    DogLog.log(getName() + "/Intake Position", intakePosition);
  }

  @Override
  public void periodic() {
    super.periodic();
  }

  public void setIntakePosition(double position) {
    // TODO: Command the motors to move to the specified position
    // Use Motion Magic or another control mode to smoothly move to position
    // Remember to set up the follower motor as well
  }

  @Override
  protected void afterTransition(IntakeWristState newState) {
    // TODO: Set the appropriate position for each state
    // Use a switch statement to handle each state in IntakeWristState
    // Call setIntakePosition() with values from IntakeWristPositions
    //
    // Example:
    // switch (newState) {
    //   case IDLE -> setIntakePosition(IntakeWristPositions.IDLE);
    //   case INTAKE -> setIntakePosition(IntakeWristPositions.INTAKE);
    //   ...
    // }
  }

  private static IntakeWristSubsystem instance;

  public static IntakeWristSubsystem getInstance() {
      if (instance == null) instance = new IntakeWristSubsystem();
      return instance;
  }
}
