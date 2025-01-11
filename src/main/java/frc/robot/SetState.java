package frc.robot;

import frc.robot.State;


import edu.wpi.first.wpilibj2.command.InstantCommand;

public class SetState extends InstantCommand {
    private final StateController stateController = StateController.getInstance();

    private State state;

    public SetState(State state) {
        this.state = state;
    }

    @Override
    public void initialize() {
        stateController.setState(state);
    }
}

