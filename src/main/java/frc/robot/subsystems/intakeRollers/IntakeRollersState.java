package frc.robot.subsystems.intakeRollers;

import dev.doglog.DogLog;
import edu.wpi.first.networktables.DoubleSubscriber;

// TODO: Define the states for your intake rollers subsystem
// Think about what different modes the rollers need to be in
public enum IntakeRollersState {
    IDLE(-0.6),
    INTAKE(0.5),
    SCORE(0.0);


    private final double defaultPosition;
    private final DoubleSubscriber tunablePosition;

    IntakeRollersState(double position) {
        this.defaultPosition = position;
        this.tunablePosition = DogLog.tunable("Arm/State/" + name(), defaultPosition);
    }

    public double getSpeed() {
        return tunablePosition.get();
    }
}
