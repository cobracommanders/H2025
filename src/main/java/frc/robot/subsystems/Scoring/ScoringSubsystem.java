package frc.robot.subsystems.Scoring;
import javax.sound.sampled.Port;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.StateMachine;

public class ScoringSubsystem extends StateMachine<ScoringState>{
    
    // private final TalonFX lMotor;
    // private final TalonFX rMotor;
    private final SparkMax scoringMotor;
    
    private ScoringState currentState;
    private double setpoint;
    private double GEAR_RATIO = 224.0/16200.0;
    private double manualSpeed;
    
    private boolean isActivated = true;
    
    public ScoringSubsystem() {
        super(ScoringState.IDLE);
        // lMotor = new TalonFX(42);
        // rMotor = new TalonFX(43);
        scoringMotor = new SparkMax(42, MotorType.kBrushless);
        //(Added second Spark max for algae scorer)
        
        currentState = ScoringState.IDLE;
    }

    public void setState(ScoringState newState) {
        setStateFromRequest(newState);
      }

      @Override
      protected void afterTransition(ScoringState newState) {
        switch (newState) {
          // case IDLE -> {
          //   lMotor.set(0.0);
          //   rMotor.set(0.0);
          // }
          case IDLE -> {
            scoringMotor.set(0.0);
          }
          // case CLIMB -> {
          //   lMotor.set(-0.2);
          //   rMotor.set(0.2);
          // }
          // case UNCLIMB  -> {
          //   lMotor.set(0.2);
          //   rMotor.set(-0.2);
          case SCORE -> {
            scoringMotor.set(-0.3);
          }
          // }
          default -> {}
        }
      }

    public void set(double speed) {
        // lMotor.set(speed);
        // rMotor.set(speed);
        scoringMotor.set(speed);
    }

    private static ScoringSubsystem instance;

    public static ScoringSubsystem getInstance() {
        if (instance == null) instance = new ScoringSubsystem(); // Make sure there is an instance (this will only run once)
        return instance;
    }
}



