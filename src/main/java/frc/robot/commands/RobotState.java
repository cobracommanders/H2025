package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.subsystems.LED.LEDState;
import frc.robot.subsystems.LED.Patterns;

public enum RobotState {
  IDLE(new LEDState(Color.kYellow, Patterns.SOLID)),
  // CLIMB(new LEDState(Color.kPurple, Patterns.SOLID)),
  // UNCLIMB(new LEDState(Color.kPurple, Patterns.FAST_BLINK));
  SCORE(new LEDState(Color.kPurple, Patterns.FAST_BLINK));

  public final LEDState ledState;

  RobotState(LEDState ledState){
    this.ledState = ledState;
  }

}
