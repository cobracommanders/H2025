package frc.robot.subsystems;
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
import frc.robot.State;

public class ScoringSubsystem extends SubsystemBase{
    
    private final SparkMax motor;
    
    private State.ScoringSubsystem currentState;
    private double setpoint;
    private double GEAR_RATIO = 224.0/16200.0;
    private double manualSpeed;
    
    private boolean isActivated = true;
    
    public ScoringSubsystem() {
        // motor = new LazySparkMax(Ports.IntakePorts.LMOTOR, MotorType.kBrushless);
        motor = new SparkMax(43, MotorType.kBrushless);
        
        currentState = State.ScoringSubsystem.IDLE;
        setpoint = currentState.speed;
    }
    @Override
    public void periodic() {
        // We will use this variable to keep track of our desired speed
        double speed = setpoint;
        if (isActivated) {
            // double rotation = CommandSwerveDrivetrain.getInstance().getState().Speeds.omegaRadiansPerSecond;
            //double driveAccel = Drivetrain.getInstance().getRobotRelativeYAcceleration();
            SmartDashboard.putNumber("Intake Speed", speed);
            set(speed);
            
        }
        
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
    public void setState(State.ScoringSubsystem state) {
        SmartDashboard.putNumber("Intake Setpoint", setpoint);
        isActivated = true;
        currentState = state;
        setpoint = state.speed; // update state
    }


    // Getter method to retrieve current State
    public State.ScoringSubsystem getState() {
        return currentState;
    }


    
    // Using static instances to reference the flywheel object ensures that we only use ONE FLywheel throughout the code 
    // This makes it very easy to access the flywheel object
    private static ScoringSubsystem instance;

    public static ScoringSubsystem getInstance() {
        if (instance == null) instance = new ScoringSubsystem(); // Make sure there is an instance (this will only run once)
        return instance;
    }
}



