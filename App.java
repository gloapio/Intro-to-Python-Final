import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int bdWidth = 600;
        int bdHeight = bdWidth;

        JFrame frame = new JFrame("Snakers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        SnakeGame snakeGame = new SnakeGame(bdWidth, bdHeight);
        frame.add(snakeGame);
        snakeGame.repaint(); // Force an initial repaint to see if it helps
        frame.pack(); // Size the frame to fit the panel
        frame.setVisible(true); // Make the frame visible
    }
}