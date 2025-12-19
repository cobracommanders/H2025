# FRC Robot Programming Hackathon Starter

Welcome to the FRC Robot Programming Hackathon! This project provides a skeleton codebase for programming an FRC robot with an intake mechanism. Your task is to design and implement the control logic to make the robot functional.

## Project Overview

This robot has two main subsystems you'll be working with:

1. **Intake Rollers** - Motors that spin to intake and eject game pieces
2. **Intake Wrist** - A pivoting arm that positions the intake at different angles

The robot uses a **state machine architecture** to coordinate these subsystems. This means the robot can be in different "states" (like IDLE, INTAKE, SCORE), and each subsystem behaves differently depending on the current state.

## Your Tasks

Look for `TODO` comments throughout the codebase. These mark the areas where you need to add code. The files contain example comments showing values and patterns from a previous robot implementation.

### 1. Design Your State Machine

Before writing any code, think about what states your robot needs. Consider:
- What positions does the intake need to be in?
- What actions does the robot perform? (intaking, scoring, idling, etc.)
- What triggers transitions between states?

### 2. Define Your States and Flags

**Files to modify:**
- `src/main/java/frc/robot/stateMachine/RobotState.java` - Define high-level robot states
- `src/main/java/frc/robot/stateMachine/RobotFlag.java` - Define input flags that trigger transitions
- `src/main/java/frc/robot/subsystems/intakeRollers/IntakeRollersState.java` - Define roller states
- `src/main/java/frc/robot/subsystems/intakeWrist/IntakeWristState.java` - Define wrist states

These enum files are currently empty. Add the states your robot needs based on your design.

### 3. Define Motor Speeds and Positions

**Files to modify:**
- `src/main/java/frc/robot/subsystems/intakeRollers/IntakeRollersSpeeds.java`
- `src/main/java/frc/robot/subsystems/intakeWrist/IntakeWristPositions.java`

These files contain example values in comments. Define constants for:
- Intake speed (how fast to spin when picking up game pieces)
- Scoring speed (how fast to spin when ejecting)
- Idle speed (should the motors hold position or coast?)
- Wrist positions for each state

### 4. Implement Subsystem Logic

**Files to modify:**
- `src/main/java/frc/robot/subsystems/intakeRollers/IntakeRollersSubsystem.java`
- `src/main/java/frc/robot/subsystems/intakeWrist/IntakeWristSubsystem.java`

For each subsystem, you need to:
- Set the initial state in the constructor
- Configure the motors (PID, motion magic, etc.)
- Implement `setIntakeRollerSpeeds()` / `setIntakePosition()` to command the motors
- Implement `atGoal()` to check if the mechanism has reached its target
- Implement `afterTransition()` to set the correct speed/position for each state
- Implement `hasCoral()` for game piece detection (IntakeRollers only)

### 5. Implement the Robot State Machine

**Files to modify:**
- `src/main/java/frc/robot/stateMachine/RobotManager.java`

The RobotManager coordinates the overall robot state. You need to:
- Set the initial state in the constructor
- Implement flag-triggered transitions in `getNextState()`
- Implement automatic transitions (e.g., move to INTAKE state when wrist reaches position)
- Set subsystem states in `afterTransition()`
- Define request methods for each flag (e.g., `intakeRequest()`, `scoreRequest()`)

### 6. Create Commands and Bind Controls

**Files to modify:**
- `src/main/java/frc/robot/commands/RobotCommands.java` - Define commands that trigger state changes
- `src/main/java/frc/robot/Controls.java` - Bind controller buttons to commands

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
│   ├── StateMachine.java   # Base state machine class (provided)
│   ├── FlagManager.java    # Flag management (provided)
│   ├── RobotManager.java   # Main robot state machine
│   ├── RobotState.java     # Robot state enum (empty - you define)
│   └── RobotFlag.java      # Input flags enum (empty - you define)
├── subsystems/
│   ├── intakeRollers/
│   │   ├── IntakeRollersSubsystem.java
│   │   ├── IntakeRollersState.java  # (empty - you define)
│   │   └── IntakeRollersSpeeds.java # (empty - you define)
│   ├── intakeWrist/
│   │   ├── IntakeWristSubsystem.java
│   │   ├── IntakeWristState.java    # (empty - you define)
│   │   └── IntakeWristPositions.java # (empty - you define)
│   └── drivetrain/         # Swerve drive (already configured)
└── drivers/
    └── Xbox.java           # Xbox controller wrapper
```

## How the State Machine Works

1. **Controller inputs** trigger **flags** (e.g., pressing a button calls `robot.intakeRequest()`)
2. **RobotManager** checks flags in `getNextState()` and determines the next **state**
3. When the state changes, `afterTransition()` is called
4. `afterTransition()` tells each **subsystem** what state to be in
5. Each subsystem's `afterTransition()` sets its motors based on its state

## Building and Deploying

```bash
# Build the project
./gradlew build

# Deploy to the robot
./gradlew deploy
```

## Tips

- Start by defining your states on paper before writing code
- Implement one subsystem at a time and test it before moving on
- Use the Driver Station and SmartDashboard/DogLog to debug
- Check the example comments in each file for guidance
- Ask mentors for help with hardware-specific values (motor directions, gear ratios, etc.)

Good luck!
