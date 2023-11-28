package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.swing.plaf.synth.SynthStyleFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Puzzle implements Serializable {

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
    private boolean killsPlayer;
    private String part2ID;
    private boolean numAns;
    private boolean anyOrder;
    public Puzzle(@JsonProperty("puzzleName") String puzzleName,
                  @JsonProperty("puzzleID") String puzzleID,
                  @JsonProperty("puzzleQ") String puzzleQ,
                  @JsonProperty("puzzleA") String puzzleA,
                  @JsonProperty("isSolved") boolean isSolved,
                  @JsonProperty("numAttempts") int numAttempts,
                  @JsonProperty("hints") ArrayList<String> hints,
                  @JsonProperty("successMessage") String successMessage,
                  @JsonProperty("failureMessage") String failureMessage,
                  @JsonProperty("puzzleDrops") ArrayList<String> puzzleDrops,
                  @JsonProperty("killsPlayer") boolean killsPlayer,
                  @JsonProperty("part2ID") String part2ID,
                  @JsonProperty("numAns") boolean numAns,
                  @JsonProperty("anyOrder") boolean anyOrder) {
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
        this.killsPlayer = killsPlayer;
        this.part2ID = part2ID;
        this.numAns = numAns;
        this.anyOrder = anyOrder;
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

    public boolean killsPlayer() {
        return killsPlayer;
    }

    public String getPart2ID() {
        return part2ID;
    }

    public boolean isNumAns() {
        return numAns;
    }

    public boolean checkAnswer(String answer) {
        if(anyOrder){
            String cleanedUserAnswer = answer.replaceAll("[,\\s]+", "");

            Set<Character> userAnswerSet = new HashSet<>();
            Set<Character> expectedAnswerSet = new HashSet<>();

            for (char c : cleanedUserAnswer.toCharArray()) {
                userAnswerSet.add(c);
            }
            for (char c : answer.toCharArray()) {
                expectedAnswerSet.add(c);
            }
            return userAnswerSet.equals(expectedAnswerSet);
        }
        if (numAns){
            String filteredNumericAnswer = filterNumericAnswer(answer);
            return filteredNumericAnswer.equalsIgnoreCase(puzzleA);
        }
        else {
            String cleanPuzzleA = puzzleA.replaceAll("\\s*,\\s*", "");
            String cleanAnswer = answer.replaceAll("\\s*,\\s*", "");


            return cleanAnswer.equalsIgnoreCase(cleanPuzzleA) && !cleanAnswer.matches(".*[\\s,]{2,}.*");
        }
    }

    private String filterNumericAnswer(String answer) {
        // Keep only numeric characters
        return answer.replaceAll("[^1-5]", "");
    }



    @Override
    public String toString() {
        return "Puzzle{" +
                "puzzleName='" + puzzleName + '\'' +
                '}';
    }
}
