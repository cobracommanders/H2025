package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.subsystems.LED.LEDState;
import frc.robot.subsystems.LED.Patterns;

public enum RobotState {
  IDLE(new LEDState(Color.kYellow, Patterns.SOLID)),
  SCORE(new LEDState(Color.kPurple, Patterns.SOLID));

  public final LEDState ledState;

  RobotState(LEDState ledState){
    this.ledState = ledState;
  }

}
