package frc.robot.subsystems.intakeRollers;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Ports;
import frc.robot.Constants.intakeRollersConstants;
import frc.robot.stateMachine.StateMachine;

public class IntakeRollersSubsystem extends StateMachine<IntakeRollersState>{
    private final TalonFX motor;
    private double motorStatorCurrent;

    public IntakeRollersSubsystem() {
        super(IntakeRollersState.IDLE); 
        
        motor = new TalonFX(Ports.IntakeRollersPorts.motor);
    }

    @Override
    protected void collectInputs() {
      motorStatorCurrent = motor.getStatorCurrent().getValueAsDouble();
    }

    public void setState(IntakeRollersState newState) {
        setStateFromRequest(newState);
    }

    public boolean hasCoral(){
      if(motorStatorCurrent > intakeRollersConstants.stallCurrent){
        return true;
      }
      return false;
    }

    public void setIntakeRollerSpeeds(double speed){
      //motor speed is fed through from intakeSpeeds
    }

    public boolean atGoal() {
      return true;
    }

    @Override
    protected void afterTransition(IntakeRollersState newState) {
      switch (newState) {
        case IDLE -> setIntakeRollerSpeeds(IntakeRollersState.IDLE.getSpeed());
        case INTAKE -> setIntakeRollerSpeeds(IntakeRollersState.INTAKE.getSpeed());
        case SCORE -> setIntakeRollerSpeeds(IntakeRollersState.SCORE.getSpeed());
      }
    }

    public Command intakeCommand() {
      return Commands.runOnce(()-> IntakeRollersSubsystem.getInstance().setState(IntakeRollersState.INTAKE));
  }

    private static IntakeRollersSubsystem instance;

    public static IntakeRollersSubsystem getInstance() {
        if (instance == null) instance = new IntakeRollersSubsystem();
        return instance;
    }
}
