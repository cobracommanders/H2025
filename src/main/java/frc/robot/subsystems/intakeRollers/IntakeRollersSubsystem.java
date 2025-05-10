package frc.robot.subsystems.intakeRollers;
import com.ctre.phoenix6.hardware.TalonFX;
import dev.doglog.DogLog;
import frc.robot.Ports;
import frc.robot.Constants.intakeRollersConstants;
import frc.robot.stateMachine.StateMachine;

public class IntakeRollersSubsystem extends StateMachine<IntakeRollersState>{
    
    // private final TalonFX lMotor;
    // private final TalonFX rMotor;
    private final TalonFX lMotor;
    private final TalonFX rMotor;
    
    private IntakeRollersState currentState;
    private double setpoint;
    private double GEAR_RATIO = 3/1;
    private double manualSpeed;
    private double motorStatorCurrent;
    
    private boolean isActivated = true;
    
    public IntakeRollersSubsystem() {
        super(IntakeRollersState.IDLE);
        rMotor = new TalonFX(Ports.IntakeRollersPorts.rMotor);
        lMotor = new TalonFX(Ports.IntakeRollersPorts.lMotor);        
        currentState = IntakeRollersState.IDLE;
    }

    @Override
    protected void collectInputs() {
      motorStatorCurrent = lMotor.getStatorCurrent().getValueAsDouble();
      DogLog.log(getName() + "/Left Motor Stator Current", motorStatorCurrent);
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
      lMotor.set(IntakeRollersSpeeds);
      rMotor.set(-IntakeRollersSpeeds);
    }

      @Override
      protected void afterTransition(IntakeRollersState newState) {
        switch (newState) {
          case IDLE -> {
            setIntakeRollerSpeeds(IntakeRollersSpeeds.IDLE);
          }
          case SCORE_L1 -> {
            setIntakeRollerSpeeds(IntakeRollersSpeeds.L1);
          }
          case INTAKE -> {
            setIntakeRollerSpeeds(IntakeRollersSpeeds.INTAKE);
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



