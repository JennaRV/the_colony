package org.example;

public class Puzzle {

    private String puzzleName;
    private String puzzleQ;
    private String puzzleA;
    private boolean isSolved;
    private int numAttempts;

    public Puzzle(String puzzleName, String puzzleQ, String puzzleA, boolean isSolved, int numAttempts) {
        this.puzzleName = puzzleName;
        this.puzzleQ = puzzleQ;
        this.puzzleA = puzzleA;
        this.isSolved = isSolved;
        this.numAttempts = numAttempts;
    }

    public String getPuzzleName(){
        return puzzleName;
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
}
