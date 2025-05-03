package frc.robot.subsystems.intakeRollers;
import javax.sound.sampled.Port;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import dev.doglog.DogLog;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.intakeRollersConstants;
import frc.robot.stateMachine.StateMachine;

public class IntakeRollersSubsystem extends StateMachine<IntakeRollersState>{
    
    // private final TalonFX lMotor;
    // private final TalonFX rMotor;
    private final TalonFX intakeRollerMotor;
    
    private IntakeRollersState currentState;
    private double setpoint;
    private double GEAR_RATIO = 3/1;
    private double manualSpeed;
    private double motorStatorCurrent;
    
    private boolean isActivated = true;
    
    public IntakeRollersSubsystem() {
        super(IntakeRollersState.IDLE);
        intakeRollerMotor = new TalonFX(0);        
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
      intakeRollerMotor.set(IntakeRollersSpeeds);
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



