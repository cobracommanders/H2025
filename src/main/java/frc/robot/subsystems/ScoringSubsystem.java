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
    private final DutyCycle encoder;

    private final PIDController pidController;
    private final ArmFeedforward gravityFeedforward;
    private final SimpleMotorFeedforward rotationFeedforward;
    
    private State.ScoringSubsystem currentState;
    private double setpoint;
    private double GEAR_RATIO = 224.0/16200.0;
    private double manualSpeed;
    
    private boolean isActivated = true;
    
    public ScoringSubsystem() {
        // motor = new LazySparkMax(Ports.IntakePorts.LMOTOR, MotorType.kBrushless);
        motor = new SparkMax(0, MotorType.kBrushless);
        encoder = new DutyCycle(new DigitalInput(1));
        
        pidController = new PIDController(0.7, 0, 0.0); //0.038
        gravityFeedforward = new ArmFeedforward(0, 0.1, 0);
        rotationFeedforward = new SimpleMotorFeedforward(0,0, 0);
        // Instantiate variables to intitial values
        currentState = State.ScoringSubsystem.IDLE;
        setpoint = currentState.speed;

        pidController.setTolerance(0.3);
        pidController.reset();
        pidController.setSetpoint(setpoint);
    }
    @Override
    public void periodic() {
        // We will use this variable to keep track of our desired speed
        double speed = 0;
        if (isActivated) {
            double rotation = CommandSwerveDrivetrain.getInstance().getState().Speeds.omegaRadiansPerSecond;
            //double driveAccel = Drivetrain.getInstance().getRobotRelativeYAcceleration();

            double initialPID = pidController.calculate(getPosition(), this.setpoint);
            double gravityOffset = gravityFeedforward.calculate(getPosition(), 0);
            //double driveOffset = driveFeedforward.calculate(driveAccel);
            double rotationOffset = rotationFeedforward.calculate(Math.abs(rotation));

            speed = initialPID + gravityOffset; // adjust for feedback error using proportional gain
            SmartDashboard.putNumber("Intake Speed", speed);
            set(-speed);
            
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
        pidController.setSetpoint(this.setpoint); // update pController
    }


    // Getter method to retrieve current State
    public State.ScoringSubsystem getState() {
        return currentState;
    }

    public void setSpeed(double currentManualSpeed) {
        manualSpeed = currentManualSpeed;
                // We are going up but top limit is not tripped so go at commanded speed
    }

    public double getSpeed() {
        return ScoringSubsystem.getInstance().manualSpeed;
    }

    /**
     *returns PID controller when it reaches setpoint
     */
    public boolean atSetpoint(){
        return pidController.atSetpoint();
    }

    /**
     * returns encoder angle
     */
    public double getPosition() {
        return -(motor.getAbsoluteEncoder().getPosition() * GEAR_RATIO * Math.PI * 2) + 1.617; // -(getRawEncoder())
    }

    public double getRawEncoder(){
            double angle = encoder.getOutput() + 0.8;
            if (angle < 1)
                angle += 1;
    
            return angle + 0.4;
    }
    
    // Using static instances to reference the flywheel object ensures that we only use ONE FLywheel throughout the code 
    // This makes it very easy to access the flywheel object
    private static ScoringSubsystem instance;

    public static ScoringSubsystem getInstance() {
        if (instance == null) instance = new ScoringSubsystem(); // Make sure there is an instance (this will only run once)
        return instance;
    }
}



