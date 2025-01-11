package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.StateController;
import frc.robot.StateController.ScoringOption;
import frc.robot.subsystems.ScoringSubsystem;

public class SetScoringSubsystemNextState extends Command{
    private final ScoringSubsystem scoringSubsystem = ScoringSubsystem.getInstance();

    public SetScoringSubsystemNextState() {
        addRequirements(scoringSubsystem);
    }

    @Override
    public void initialize() {
        scoringSubsystem.setState(StateController.getInstance().getState().scoringSubsystem);
    }
}
