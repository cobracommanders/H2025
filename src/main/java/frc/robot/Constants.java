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

  public static final class shooterConstants {
    public static final double P = 60;
    public static final double I = 2;
    public static final double D = 2;
    public static final double G = 0.275;
    public static final double MotionMagicAcceleration = 3;
    public static final double MotionMagicCruiseVelocity = 10;
    public static final double MotionMagicJerk = 10;
  }
}
