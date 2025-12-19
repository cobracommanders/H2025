package frc.robot.subsystems.intakeRollers;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Ports;
import frc.robot.Constants.intakeRollersConstants;
import frc.robot.stateMachine.StateMachine;

public class IntakeRollersSubsystem extends StateMachine<IntakeRollersState>{
    private final TalonFX motor;
    private double motorStatorCurrent;

    public IntakeRollersSubsystem() {
        super(null); // TODO: Pass your initial state here once you define states
        // TODO: Initialize your motor(s) here
        // You need to create a TalonFX motor using the port from Ports.IntakeRollersPorts
        motor = new TalonFX(Ports.IntakeRollersPorts.motor);
    }

    @Override
    protected void collectInputs() {
      // TODO: Read sensor values here
      // This method is called every loop to gather sensor data
      motorStatorCurrent = motor.getStatorCurrent().getValueAsDouble();
    }

    public void setState(IntakeRollersState newState) {
        setStateFromRequest(newState);
    }

    public boolean hasCoral(){
      // TODO: Implement game piece detection
      // Return true if a game piece is detected in the intake
      // Hint: You might use motor current, a beam break sensor, or other methods
      return false;
    }

    public void setIntakeRollerSpeeds(double speed){
      // TODO: Set the motor speed
      // This method should command the motor to spin at the given speed
    }

    public boolean atGoal() {
      // TODO: Determine if the subsystem has reached its goal state
      // For rollers, this might always return true since they don't have a position goal
      return true;
    }

    @Override
    protected void afterTransition(IntakeRollersState newState) {
      // TODO: Set the appropriate motor speed for each state
      // Use a switch statement to handle each state in IntakeRollersState
      // Call setIntakeRollerSpeeds() with values from IntakeRollersSpeeds
      //
      // Example:
      // switch (newState) {
      //   case IDLE -> setIntakeRollerSpeeds(IntakeRollersSpeeds.IDLE);
      //   case INTAKE -> setIntakeRollerSpeeds(IntakeRollersSpeeds.INTAKE);
      //   ...
      // }
    }

    private static IntakeRollersSubsystem instance;

    public static IntakeRollersSubsystem getInstance() {
        if (instance == null) instance = new IntakeRollersSubsystem();
        return instance;
    }
}
