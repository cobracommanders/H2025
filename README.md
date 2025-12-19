# FRC Robot Programming Hackathon Starter

Welcome to the FRC Robot Programming Hackathon! This project provides a skeleton codebase for programming an FRC robot with an intake mechanism. Your task is to implement the control logic to make the robot functional.

## Project Overview

This robot has two main subsystems you'll be working with:

1. **Intake Rollers** - Motors that spin to intake and eject game pieces
2. **Intake Wrist** - A pivoting arm that positions the intake at different angles

The robot uses a **state machine architecture** to coordinate these subsystems. This means the robot can be in different "states" (like IDLE, INTAKE, SCORE), and each subsystem behaves differently depending on the current state.

## Your Tasks

Look for `TODO` comments throughout the codebase. These mark the areas where you need to add code. Here's a summary of what needs to be done:

### 1. Configure Motor Speeds and Positions

**Files to modify:**
- `src/main/java/frc/robot/subsystems/intakeRollers/IntakeRollersSpeeds.java`
- `src/main/java/frc/robot/subsystems/intakeWrist/IntakeWristPositions.java`

Set appropriate values for:
- Intake speed (how fast to spin when picking up game pieces)
- Scoring speed (how fast to spin when ejecting)
- Idle speed (should the motors hold position or coast?)
- Wrist positions for each state

### 2. Implement Subsystem Logic

**Files to modify:**
- `src/main/java/frc/robot/subsystems/intakeRollers/IntakeRollersSubsystem.java`
- `src/main/java/frc/robot/subsystems/intakeWrist/IntakeWristSubsystem.java`

For each subsystem, you need to:
- Configure the motors in the constructor (PID, motion magic, etc.)
- Implement `setIntakeRollerSpeeds()` / `setIntakePosition()` to command the motors
- Implement `atGoal()` to check if the mechanism has reached its target
- Implement `afterTransition()` to set the correct speed/position for each state
- Implement `hasCoral()` for game piece detection (IntakeRollers only)

### 3. Implement State Machine Transitions

**Files to modify:**
- `src/main/java/frc/robot/stateMachine/RobotManager.java`

The RobotManager coordinates the overall robot state. You need to:
- Implement state transitions in `getNextState()`
- Set subsystem states in `afterTransition()`

### 4. (Optional) Customize States

**Files to modify:**
- `src/main/java/frc/robot/stateMachine/RobotState.java`
- `src/main/java/frc/robot/stateMachine/RobotFlag.java`
- `src/main/java/frc/robot/subsystems/intakeRollers/IntakeRollersState.java`
- `src/main/java/frc/robot/subsystems/intakeWrist/IntakeWristState.java`

You can add, remove, or rename states to match your robot's needs.

## Project Structure

```
src/main/java/frc/robot/
├── Robot.java              # Main robot class
├── Controls.java           # Controller button mappings
├── Constants.java          # Robot constants (PID values, etc.)
├── Ports.java              # Motor and sensor port numbers
├── commands/
│   └── RobotCommands.java  # Command definitions
├── stateMachine/
│   ├── StateMachine.java   # Base state machine class
│   ├── RobotManager.java   # Main robot state machine
│   ├── RobotState.java     # Robot state enum
│   └── RobotFlag.java      # Input flags enum
├── subsystems/
│   ├── intakeRollers/
│   │   ├── IntakeRollersSubsystem.java
│   │   ├── IntakeRollersState.java
│   │   └── IntakeRollersSpeeds.java
│   ├── intakeWrist/
│   │   ├── IntakeWristSubsystem.java
│   │   ├── IntakeWristState.java
│   │   └── IntakeWristPositions.java
│   └── drivetrain/         # Swerve drive (already configured)
└── drivers/
    └── Xbox.java           # Xbox controller wrapper
```

## How the State Machine Works

1. **Controller inputs** trigger **flags** (e.g., pressing a button sets `RobotFlag.INTAKE`)
2. **RobotManager** checks flags and determines the next **state**
3. When the state changes, `afterTransition()` is called
4. `afterTransition()` tells each **subsystem** what to do
5. Each subsystem sets its motors based on its own state

## Building and Deploying

```bash
# Build the project
./gradlew build

# Deploy to the robot
./gradlew deploy
```

## Tips

- Start by getting one subsystem working before moving to the next
- Use the Driver Station and SmartDashboard to debug
- Ask mentors for help with hardware-specific values (motor directions, gear ratios, etc.)

Good luck!
