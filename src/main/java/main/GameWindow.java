package main;

import javax.swing.JFrame;

public class GameWindow {

    public static final int WIDTH = 680;
    public static final int HEIGHT = 480;


    public static void main(String[] args) {

        JFrame theWindow = new JFrame("Brick Breaker");
        GamePanel thePanel = new GamePanel();
        theWindow.setSize(WIDTH, HEIGHT);
        theWindow.setResizable(false);
        //setta la finestra in centro con null.
        theWindow.setLocationRelativeTo(null);
        theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        theWindow.add(thePanel);
        theWindow.setVisible(true);

        thePanel.playGame();

    }
}
