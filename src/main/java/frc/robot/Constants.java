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
    public static final double P = 60;//40
    public static final double I = 2;
    public static final double D = 2;//1
    public static final double G = 0.275;//0.5
    public static final double MotionMagicAcceleration = 50;//100
    public static final double MotionMagicCruiseVelocity = 100;//100
    public static final double MotionMagicJerk = 200;//100
  }

  public static final class intakeRollersConstants{
    public static final double stallCurrent = 150;
  }

  public static class ClimberConstants {
    // TODO: Find the values for these.
    public static final double DEPLOY_MOTION_MAGIC_CRUISE_VELOCITY = 0;
    public static final double DEPLOY_MOTION_MAGIC_ACCELERATION = 0;
    public static final double DEPLOY_MOTION_MAGIC_JERK = 0;
    public static final double CAGE_DETECECTION_CURRENT = 60;
    public static final double ClimberGearRatio = 0.0;
    public static final double EncoderOffset = 0.314;
}

}

