package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import dev.doglog.DogLog;
import frc.robot.Ports;
import frc.robot.stateMachine.StateMachine;



public class ShooterSubsystem extends StateMachine<ShooterState>{
    public final TalonFX lMotor;
    public final TalonFX rMotor;
    private final TalonFXConfiguration left_motor_config = new TalonFXConfiguration();
    
    public ShooterSubsystem() {
      super(ShooterState.OFF);
      lMotor = new TalonFX(Ports.ShooterPorts.lMotor);
      left_motor_config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
      left_motor_config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      lMotor.getConfigurator().apply(left_motor_config);

      rMotor = new TalonFX(Ports.ShooterPorts.lMotor);
      rMotor.getConfigurator().apply(left_motor_config);

      rMotor.setControl(new Follower(Ports.ShooterPorts.lMotor, true));
    }

    protected ShooterState getNextState(ShooterState currentState) {
      return currentState;
    }

    @Override
    public void collectInputs(){
      //rollerStatorCurrent = rollerMotor.getStatorCurrent().getValueAsDouble();
      //DogLog.log(getName() + "/Intake Roller Motor Stator Current", rollerStatorCurrent);
    }
  
    public void setState(ShooterState newState) {
        setStateFromRequest(newState);
    }
  
    public void setShooterSpeed(double shooterSpeed){
      DogLog.log(getName() + "/ shooter speed", shooterSpeed);
      lMotor.set(shooterSpeed);
    }
  
      @Override
      protected void afterTransition(ShooterState newState) {
        switch (newState) {
          case OFF-> {
            setShooterSpeed(ShooterSpeeds.off);
          }
          case ON -> {
            setShooterSpeed(ShooterSpeeds.on);
          }
          default -> {}
        }
      }
  
    private static ShooterSubsystem instance;
  
    public static ShooterSubsystem getInstance() {
        if (instance == null) instance = new ShooterSubsystem(); // Make sure there is an instance (this will only run once)
        return instance;
    }
  }