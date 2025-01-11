package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.SetState;
import frc.robot.State;

public class Score extends SequentialCommandGroup{
    public Score(){
        super(
            
            //Set Intake and intakeRollers to state INTAKE
        new SetState(State.SCORE),
            new SetScoringSubsystemNextState()
                );
    }
}