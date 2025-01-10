package frc.robot;

import frc.robot.subsystems.ScoringSubsystem;

public enum State {
    IDLE(ScoringSubsystem.IDLE),
    SCORE(ScoringSubsystem.SCORE);

    public final ScoringSubsystem scoringSubsystem;

    State(ScoringSubsystem scoringSubsystem) {
        // this.climber = climber;
        this.scoringSubsystem = scoringSubsystem;
    }
    public enum ScoringSubsystem{
        IDLE(0),
        SCORE(0);

        public final double speed;

        ScoringSubsystem(double speed){
            this.speed = speed;
        }
        
    
    }
}

