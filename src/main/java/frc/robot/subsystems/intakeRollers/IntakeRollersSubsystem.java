package frc.robot.subsystems.intakeRollers;
import com.ctre.phoenix6.hardware.TalonFX;

import dev.doglog.DogLog;
import frc.robot.Ports;
import frc.robot.Constants.intakeRollersConstants;
import frc.robot.stateMachine.StateMachine;

public class IntakeRollersSubsystem extends StateMachine<IntakeRollersState>{
    
    // private final TalonFX lMotor;
    // private final TalonFX rMotor;
<<<<<<< Updated upstream
    private final TalonFX intakeRollerMotor;
  
=======
    private final TalonFX lMotor;
    //private final TalonFX rMotor;
    
>>>>>>> Stashed changes
    private IntakeRollersState currentState;
    private double setpoint;
    private double GEAR_RATIO = 3/1;
    private double manualSpeed;
    private double motorStatorCurrent;
    
    private boolean isActivated = true;
    
    public IntakeRollersSubsystem() {
        super(IntakeRollersState.IDLE);
<<<<<<< Updated upstream
        intakeRollerMotor = new TalonFX(Ports.IntakeRollersPorts.rollerMotor);
=======
        //rMotor = new TalonFX(Ports.IntakeRollersPorts.rMotor);
        lMotor = new TalonFX(Ports.IntakeRollersPorts.lMotor);        
>>>>>>> Stashed changes
        currentState = IntakeRollersState.IDLE;
    }

    @Override
    protected void collectInputs() {
      motorStatorCurrent = intakeRollerMotor.getStatorCurrent().getValueAsDouble();
      DogLog.log(getName() + "/Motor Stator Current", motorStatorCurrent);
    }

    public void setState(IntakeRollersState newState) {
        setStateFromRequest(newState);
      }

      public boolean hasCoral(){
        if (motorStatorCurrent > intakeRollersConstants.stallCurrent){
          return true;
        } else {
          return false;
        }
      }

    public void setIntakeRollerSpeeds(double IntakeRollersSpeeds){
      DogLog.log(getName() + "/Intake Roller speed", IntakeRollersSpeeds);
<<<<<<< Updated upstream
      intakeRollerMotor.set(IntakeRollersSpeeds);
=======
      lMotor.set(IntakeRollersSpeeds);
     // rMotor.set(-IntakeRollersSpeeds);
>>>>>>> Stashed changes
    }

      @Override
      protected void afterTransition(IntakeRollersState newState) {
        switch (newState) {
          case IDLE -> {
            setIntakeRollerSpeeds(IntakeRollersSpeeds.IDLE);
          }
          case SCORE_L1_ROW_1 -> {
            setIntakeRollerSpeeds(IntakeRollersSpeeds.L1_ROW_1);
          }
          case SCORE_L1_ROW_2 -> {
            setIntakeRollerSpeeds(IntakeRollersSpeeds.L1_ROW_2);
          }
          case INTAKE -> {
            setIntakeRollerSpeeds(IntakeRollersSpeeds.INTAKE);
          }
          case CORAL_STATION_INTAKE -> {
            setIntakeRollerSpeeds(IntakeRollersSpeeds.CORAL_STATION_INTAKE);
          }
          default -> {}
        }
      }

    private static IntakeRollersSubsystem instance;

    public static IntakeRollersSubsystem getInstance() {
        if (instance == null) instance = new IntakeRollersSubsystem(); // Make sure there is an instance (this will only run once)
        return instance;
    }
}



