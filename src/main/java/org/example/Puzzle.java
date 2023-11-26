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
    private boolean killsPlayer;
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
                  @JsonProperty("killsPlayer") boolean killsPlayer) {
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

    public boolean checkAnswer(String answer) {
        String cleanPuzzleA = puzzleA.replaceAll("\\s*,\\s*", "");
        String cleanAnswer = answer.replaceAll("\\s*,\\s*", "");

        return cleanAnswer.equalsIgnoreCase(cleanPuzzleA) && !cleanAnswer.matches(".*[\\s,]{2,}.*");
    }

//    public boolean checkConditions(String answer) throws InvalidItemException {
//        String[] conditions = answer.split("\\s+", 2);
//
//        if (conditions.length > 0) {
//            String condition = conditions[0];
//            String itemName = (conditions.length > 1) ? conditions[1] : "";
//            Item item = player.map.getItem(i)
//            //item.toLowerCase();
//            System.out.println("DEBUG item: " + item);
//
//            switch (condition.toLowerCase()) {
//                case "use":
//                    if(player.getInventory().contains()){
//                        System.out.println("You used the " + item);
//                        return true;
//                    }
//                    else {
//                        System.out.println("You are not currently carrying that item.");
//                        return false;
//                    }
//                case "look":
//                    //will implement later
//                    break;
//                case "drop":
//                    if(player.getInventory().contains(item)){
//                        player.dropItem(item);
//                        return true;
//                    }
//                    else {
//                        System.out.println("You are not currently carrying that item.");
//                        return false;
//                    }
//                default:
//                    return true;
//
//            }
//        }
//
//        return false;
//    }



}
