package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ScoringSubsystem;
import frc.robot.SetState;
import frc.robot.State;
import frc.robot.StateController;

public class ReturnToIdle extends SequentialCommandGroup{
    public ReturnToIdle(){
        super(
            
            //Set Intake and intakeRollers to state INTAKE
        new SetState(State.IDLE),
            new SetScoringSubsystemNextState()
                );
    }
}
