package frc.robot;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import frc.robot.State;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class StateController extends SubsystemBase {
    private State currentState = State.IDLE;

    private ScoringOption nextScoringOption;
    public enum ScoringOption{SCORE}

    public void setState(State state){
        //Updates the current state
        currentState = state;
    }

    public State getState(){
        return currentState;
    }

    public State getNextScoringState() {
        State state;

        //Sets State to the next Scoring Option
        state = switch (nextScoringOption) {

            case SCORE -> State.SCORE;
        };

        return state;
    }


    public void setNextScoringOption(ScoringOption scoringOption){
        nextScoringOption = scoringOption;
    }

    //returns nextScoringOption
    public ScoringOption getNextScoringOption(){
        return nextScoringOption;
    }

    private static StateController instance;

    public static StateController getInstance() {
        if (instance == null) instance = new StateController(); // Make sure there is an instance (this will only run once)
        return instance;
    }
}