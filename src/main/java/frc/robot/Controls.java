package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.RobotCommands;
import frc.robot.stateMachine.RobotManager;
import frc.robot.drivers.Xbox;
import frc.robot.subsystems.climber.ClimberState;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.drivetrain.TunerConstants;
import frc.robot.subsystems.intakeWrist.IntakeWristPositions;
import frc.robot.subsystems.intakeWrist.IntakeWristSubsystem;

import static edu.wpi.first.wpilibj2.command.Commands.*;

import java.util.function.Supplier;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

public class Controls {
    private  double MaxSpeed = TunerConstants.kSpeedAt12Volts; // Initial max is true top speed
    private final double TurtleSpeed = 0.1; // Reduction in speed from Max Speed, 0.1 = 10%
    private final double MaxAngularRate = Math.PI * 3.5; // .75 rotation per second max angular velocity.  Adjust for max turning rate speed.
    private final double TurtleAngularRate = Math.PI * 0.5; // .75 rotation per second max angular velocity.  Adjust for max turning rate speed.
    private double AngularRate = MaxAngularRate; // This will be updated when turtle and reset to MaxAngularRate

    CommandSwerveDrivetrain drivetrain = CommandSwerveDrivetrain.getInstance();

     SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage)
      .withDeadband(MaxSpeed * 0.1) // Deadband is handled on input
      .withRotationalDeadband(AngularRate * 0.1);

    public final Xbox driver = new Xbox(OIConstants.DRIVER_CONTROLLER_ID);
    public final Xbox operator = new Xbox(OIConstants.OPERATOR_CONTROLLER_ID);

    public Controls() {
        driver.setDeadzone(0.15);
        driver.setTriggerThreshold(0.2);
        operator.setDeadzone(0.2);
        operator.setTriggerThreshold(0.2);
    }
    private Supplier<SwerveRequest> controlStyle;
    private void newControlStyle () {
        controlStyle = () -> drive.withVelocityX(-driver.leftY() * driver.leftY() * driver.leftY() * MaxSpeed) // Drive forward -Y
            .withVelocityY(-driver.leftX() * driver.leftX() * driver.leftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(driver.rightX() * AngularRate); // Drive counterclockwise with negative X (left)
    }


     
    public void configureDefaultCommands() {
        newControlStyle();
         CommandSwerveDrivetrain.getInstance().setDefaultCommand(repeatingSequence( // Drivetrain will execute this command periodically
         runOnce(()-> CommandSwerveDrivetrain.getInstance().driveFieldRelative(new ChassisSpeeds(-driver.leftY() * driver.leftY() * driver.leftY() * MaxSpeed, -driver.leftX() * driver.leftX() * driver.leftX() * MaxSpeed, driver.rightX() * AngularRate)), CommandSwerveDrivetrain.getInstance())));
  }

    public void configureDriverCommands() {
        driver.A().onTrue(runOnce(() -> CommandSwerveDrivetrain.getInstance().setYaw(Robot.alliance.get())));
        driver.rightTrigger().onTrue(Robot.robotCommands.scoreCommand());
        driver.rightTrigger().onFalse(Robot.robotCommands.idleCommand());
        //driver.leftBumper().onTrue(Robot.robotCommands.coralStationIntakeCommand());
        //driver.leftBumper().onFalse(Robot.robotCommands.intakeIdleCommand());
        driver.leftTrigger().onTrue(Robot.robotCommands.intakeCommand());
        driver.leftTrigger().onFalse(Robot.robotCommands.intakeIdleCommand());
        driver.X().whileTrue(runOnce(() -> drivetrain.applyRequest(() -> drivetrain.brake)));
        driver.Y().whileTrue(drivetrain.applyRequest(() -> drivetrain.point.withModuleDirection(new Rotation2d(-driver.leftY(), -driver.leftX()))));
        driver.POV0().onTrue(Robot.robotCommands.climbUpCommand());
    }

    public void configureOperatorCommands(){
        operator.Y().onTrue(Robot.robotCommands.L1Row1Command());
        operator.A().onTrue(Robot.robotCommands.L1Row2Command());
        operator.leftBumper().onTrue(Robot.robotCommands.idleCommand());
        operator.POV90().onTrue(runOnce(()-> IntakeWristSubsystem.getInstance().increaseSetpoint()));
        operator.POVMinus90().onTrue(runOnce(()-> IntakeWristSubsystem.getInstance().decreaseSetpoint()));
        operator.POV0().onTrue(Robot.robotCommands.climbCommand());
    }
}
