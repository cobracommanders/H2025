// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {
  public static final class DrivetrainConstants {
    public static final double MAX_VELOCITY_METERS_PER_SECOND = 5.94;
    public static final double MAX_ACCELERATION_METERS_PER_SECOND_SQUARED = 300;
  }

  public static final class OIConstants {
    public static final int DRIVER_CONTROLLER_ID = 0;
    public static final int OPERATOR_CONTROLLER_ID = 1;
  }

  // TODO: Define PID and motion magic constants for your intake wrist
  // These values will need to be tuned for your specific mechanism
  public static final class intakeWristConstants {
    public static final double P = 0.0;
    public static final double I = 0.0;
    public static final double D = 0.0;
    public static final double G = 0.0; // Gravity feedforward for arm
    public static final double MotionMagicAcceleration = 0.0;
    public static final double MotionMagicCruiseVelocity = 0.0;
    public static final double MotionMagicJerk = 0.0;
  }

  // TODO: Define constants for your intake rollers
  public static final class intakeRollersConstants {
    public static final double stallCurrent = 0.0; // Current threshold for game piece detection
  }
}
