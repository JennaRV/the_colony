package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class Puzzle {

    private String puzzleName;
    private String puzzleID;
    private String puzzleQ;
    private String puzzleA;
    private boolean isSolved;
    private int numAttempts;
    private ArrayList<String> hints;
    private String successMessage;
    private String failureMessage;
    private ArrayList<String> puzzleDrops;
    public Puzzle(@JsonProperty("puzzleName") String puzzleName,
                  @JsonProperty("puzzleID") String puzzleID,
                  @JsonProperty("puzzleQ") String puzzleQ,
                  @JsonProperty("puzzleA") String puzzleA,
                  @JsonProperty("isSolved") boolean isSolved,
                  @JsonProperty("numAttempts") int numAttempts,
                  @JsonProperty("hints") ArrayList<String> hints,
                  @JsonProperty("successMessage") String successMessage,
                  @JsonProperty("failureMessage") String failureMessage,
                  @JsonProperty("puzzleDrops") ArrayList<String> puzzleDrops) {
        this.puzzleName = puzzleName;
        this.puzzleID = puzzleID;
        this.puzzleQ = puzzleQ;
        this.puzzleA = puzzleA;
        this.isSolved = isSolved;
        this.numAttempts = numAttempts;
        this.hints = hints;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.puzzleDrops = puzzleDrops;
    }

    public String getPuzzleName(){
        return puzzleName;
    }

    public String getPuzzleID() {
        return puzzleID;
    }

    public String getPuzzleQ() {
        return puzzleQ;
    }

    public String getPuzzleA() {
        return puzzleA;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public int getNumAttempts() {
        return numAttempts;
    }

    public ArrayList<String> getHints() {
        return hints;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public ArrayList<String> getPuzzleDrops() {
        return puzzleDrops;
    }
}
