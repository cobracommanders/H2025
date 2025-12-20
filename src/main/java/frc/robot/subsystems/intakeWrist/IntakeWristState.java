package frc.robot.subsystems.intakeWrist;

import dev.doglog.DogLog;
import edu.wpi.first.networktables.DoubleSubscriber;

// TODO: Define the states for your intake wrist subsystem
// Think about what different positions the wrist needs to be in
public enum IntakeWristState {
    INIT(0.0),
    INTAKE(0.0),
    IDLE(0.0);

    private final double defaultPosition;
    private final DoubleSubscriber tunablePosition;

    IntakeWristState(double position) {
        this.defaultPosition = position;
        this.tunablePosition = DogLog.tunable("Arm/State/" + name(), defaultPosition);
    }

    public double getPosition() {
        return tunablePosition.get();
    }
}       
