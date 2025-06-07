// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.intakeWrist.IntakeWristSubsystem;

public final class Constants {
  public static final class DrivetrainConstants {
    public static final double MAX_VELOCITY_METERS_PER_SECOND = 5.94;
    public static final double MAX_ACCELERATION_METERS_PER_SECOND_SQUARED = 300;
  }
  public static final class OIConstants {
    public static final int DRIVER_CONTROLLER_ID = 0;
    public static final int OPERATOR_CONTROLLER_ID = 1;
}

  public static final class intakeWristConstants{
    public static final double P = 25;
    public static final double I = 0;
    public static final double D = 2;
    public static final double G = 0.3;
    public static final double MotionMagicAcceleration = 25;
    public static final double MotionMagicCruiseVelocity = 1000;
    public static final double MotionMagicJerk = 1000;
  }

  public static final class intakeRollersConstants{
    public static final double stallCurrent = 150;
  }

  public static final class ClimberConstants{
    public static final double P = 320;
    public static final double I = 0;
    public static final double D = 0;
    public static final double G = 0;
    public static final double DeployMotionMagicAcceleration = 100; //100
    public static final double DeployMotionMagicCruiseVelocity = 250; //250
    public static final double RetractMotionMagicAcceleration = 0.44;
    public static final double RetractMotionMagicCruiseVelocity = 0.44;
    public static final double MotionMagicJerk = 200;
  }

}

