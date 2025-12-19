package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.RobotCommands;
import frc.robot.stateMachine.RobotManager;
import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.intakeRollers.IntakeRollersSubsystem;
import frc.robot.subsystems.intakeWrist.IntakeWristSubsystem;

import java.util.Optional;


public class Robot extends TimedRobot{
    // public static final double DEFAULT_PERIOD = 0.02;
    // public final Timer setupTimer = new Timer();
    // public double setupTime = 0;

    public static RobotManager robotManager = new RobotManager();
    public static RobotCommands robotCommands = new RobotCommands(robotManager);
    // public static int coordinateFlip = 1;
    // public static int rotationOffset = 0;

    public static Optional<Alliance> alliance = Optional.empty();
    public static final Controls controls = new Controls();


    @Override
    public void robotInit() {
        controls.configureDefaultCommands();
        controls.configureDriverCommands();
        controls.configureOperatorCommands();

        IntakeWristSubsystem.getInstance().lMotor.setPosition(0);

        CommandSwerveDrivetrain.getInstance();
        IntakeRollersSubsystem.getInstance();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        if (alliance.isEmpty()) {
            alliance = DriverStation.getAlliance();
        }
    }

    @Override
    public void disabledPeriodic() {
        alliance = DriverStation.getAlliance();
    }


    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {
    }


    @Override
    public void teleopExit() {
        controls.driver.rumble(0);
    }

    @Override
    public void disabledInit() {
        //setupTimer.restart();
        // drivetrain.enableBrakeMode(false);
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }


    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    public static void main(String... args) {
        RobotBase.startRobot(Robot::new);
        }
    }