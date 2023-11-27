package org.example;

import java.io.IOException;

public class Main {

    private static Player player;  // Make player static so it can be accessed in the static method
    private static View view;  // Declare playerView variable

    public static void main(String[] args) throws IOException {
        startGame();
    }

    public static void startGame() throws IOException {
        // Your existing code to initialize rooms, player, etc.

        // Initialize player and playerView
        player = new Player();  // Replace with your actual Player initialization
        view = new View(player);

        // Correct the class name to use camelCase
        Controller controller = new Controller(player, view);

        controller.startGame();
    }
}
