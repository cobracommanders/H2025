package frc.robot.vision;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.PoseEstimator;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.vision.LimelightHelpers;


public class LimelightLocalization{
  private final SwerveDrivePoseEstimator PoseEstimator;
  private final Pigeon2 gyro;
  private boolean rejectLeftData;
  private boolean rejectRightData;
  public LimelightLocalization(SwerveDrivePoseEstimator PoseEstimator, Pigeon2 gyro) {

    this.PoseEstimator = PoseEstimator;
    this.gyro = gyro;
    // int[] validIDs = {3,4};
    // LimelightHelpers.SetFiducialIDFiltersOverride("limelight-left", validIDs);
    // LimelightHelpers.SetFiducialIDFiltersOverride("limelight-right", validIDs);
  }

  public void update(){
    rejectLeftData = false;
    rejectRightData = false;
    LimelightHelpers.SetRobotOrientation("limelight-left", CommandSwerveDrivetrain.getInstance().getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
    LimelightHelpers.SetRobotOrientation("limelight-right", CommandSwerveDrivetrain.getInstance().getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
    LimelightHelpers.PoseEstimate mt2l = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight-left");
    LimelightHelpers.PoseEstimate mt2r = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight-right");
    if(CommandSwerveDrivetrain.getInstance().isMoving()) // if our angular velocity is greater than 720 degrees per second, ignore vision updates
    {
      rejectLeftData = true;
      rejectRightData = true;
    }
    if(mt2r.tagCount == 0)
    {
      rejectRightData = true;
    }
    if(mt2l.tagCount == 0)
    {
      rejectLeftData = true;
    }
    if(!rejectRightData)
    {
      CommandSwerveDrivetrain.getInstance().setVisionMeasurementStdDevs( VecBuilder.fill(.5,.5,9999999));

      CommandSwerveDrivetrain.getInstance().addVisionMeasurement(
          mt2r.pose,
          mt2r.timestampSeconds);
    }
    if(!rejectLeftData)
    {
      CommandSwerveDrivetrain.getInstance().setVisionMeasurementStdDevs( VecBuilder.fill(.5,.5,9999999));
      CommandSwerveDrivetrain.getInstance().addVisionMeasurement(
          mt2l.pose,
          mt2l.timestampSeconds);
    }
    if(DriverStation.isAutonomous())
    {
      rejectLeftData = true;
    }
  }
}
