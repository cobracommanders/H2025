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
    
    private final SparkMax motor;
    
    private ScoringState currentState;
    private double setpoint;
    private double GEAR_RATIO = 224.0/16200.0;
    private double manualSpeed;
    
    private boolean isActivated = true;
    
    public ScoringSubsystem() {
        super(ScoringState.IDLE);
        // motor = new LazySparkMax(Ports.IntakePorts.LMOTOR, MotorType.kBrushless);
        motor = new SparkMax(43, MotorType.kBrushless);
        
        currentState = ScoringState.IDLE;
    }

    public void setState(ScoringState newState) {
        setStateFromRequest(newState);
      }

      @Override
      protected void afterTransition(ScoringState newState) {
        switch (newState) {
          case IDLE -> {
            motor.set(0.0);
          }
          case SCORE -> {
            motor.set(-0.2);
          }
          default -> {}
        }
      }

    @Override
    public void periodic() {
        
        // We will use this variable to keep track of our desired speed
      
        
        // set(speed);
        // if (Shooter.getInstance().getAngle() < ShooterConstants.AngleConstants.MIN_ANGLE - 1) //Tolerance for Intake Angle
        //     speed = 0;
    }
    public void set(double speed) {
        motor.set(speed);
    }

    /**
     * sets state for speed and sets PID controller to setpoint
     */


    // Getter method to retrieve current State


    
    // Using static instances to reference the flywheel object ensures that we only use ONE FLywheel throughout the code 
    // This makes it very easy to access the flywheel object
    private static ScoringSubsystem instance;

    public static ScoringSubsystem getInstance() {
        if (instance == null) instance = new ScoringSubsystem(); // Make sure there is an instance (this will only run once)
        return instance;
    }
}



