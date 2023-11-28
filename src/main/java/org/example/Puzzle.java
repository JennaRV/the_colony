package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Puzzle implements Serializable {
    private String puzzleID;
    private String puzzleQ;
    private String puzzleA;
    private boolean isSolved;
    private int numAttempts;

    public Puzzle(@JsonProperty("puzzleID")String puzzleID,
                  @JsonProperty("puzzleQ")String puzzleQ,
                  @JsonProperty("puzzleA")String puzzleA,
                  @JsonProperty("isSolved")boolean isSolved,
                  @JsonProperty("numAttempts")int numAttempts) {
        this.puzzleID = puzzleID;
        this.puzzleQ = puzzleQ;
        this.puzzleA = puzzleA;
        this.isSolved = isSolved;
        this.numAttempts = numAttempts;
    }
    public String getPuzzleID() {return puzzleID;}

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
}
