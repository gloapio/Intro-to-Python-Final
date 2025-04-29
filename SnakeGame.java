import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private int cellSize = 20;
    private ArrayList<Point> snake;
    private Point food;
    private int directionX;
    private int directionY;
    private boolean isRunning = false;
    private Timer timer;
    private Random random;
    private int gameWidth;
    private int gameHeight;
    private int score; // Add a score variable

    public SnakeGame(int width, int height) {
        this.gameWidth = width;
        this.gameHeight = height;
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.pink);
        this.setFocusable(true);
        this.addKeyListener(this);
        random = new Random();
        startGame();
    }

    public void startGame() {
        snake = new ArrayList<>();
        snake.add(new Point(gameWidth / (2 * cellSize), gameHeight / (2 * cellSize)));
        directionX = 1;
        directionY = 0;
        spawnFood();
        isRunning = true;
        timer = new Timer(150, this);
        timer.start();
        score = 0; // Reset score on game start
    }

    public void resetGame() {
        startGame();
        gameOver = false;
        repaint();
    }

    public void spawnFood() {
        food = new Point(random.nextInt(gameWidth / cellSize), random.nextInt(gameHeight / cellSize));
    }

    public void move() {
        if (isRunning) {
            Point head = snake.get(0);
            Point newHead = new Point(head.x + directionX, head.y + directionY);
            snake.add(0, newHead);

            if (newHead.equals(food)) {
                score++; // Increment score when food is eaten
                spawnFood();
            } else {
                snake.remove(snake.size() - 1);
            }
        }
    }

    public void checkCollisions() {
        if (isRunning) {
            Point head = snake.get(0);

            if (head.x < 0 || head.x >= gameWidth / cellSize || head.y < 0 || head.y >= gameHeight / cellSize) {
                isRunning = false;
            }

            for (int i = 1; i < snake.size(); i++) {
                if (head.equals(snake.get(i))) {
                    isRunning = false;
                    break;
                }
            }

            if (!isRunning) {
                timer.stop();
            }
        }
    }

    private boolean gameOver = false;

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        String gameOverText = "Snaked!"; 
        int textWidth = g.getFontMetrics().stringWidth(gameOverText);
        g.drawString(gameOverText, (gameWidth - textWidth) / 2, gameHeight / 2 - 20); // Adjust vertical position

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.ITALIC, 20));
        String resetText = "Press 'R' to try again "; 
        int resetTextWidth = g.getFontMetrics().stringWidth(resetText);
        g.drawString(resetText, (gameWidth - resetTextWidth) / 2, gameHeight / 2 + 20);

        // Display the final score
        g.setColor(Color.yellow);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        String scoreText = "Final Score: " + score;
        int scoreWidth = g.getFontMetrics().stringWidth(scoreText);
        g.drawString(scoreText, (gameWidth - scoreWidth) / 2, gameHeight / 2 + 60);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw grid
        g.setColor(Color.DARK_GRAY);
        for (int x = 0; x < gameWidth / cellSize; x++) {
            g.drawLine(x * cellSize, 0, x * cellSize, gameHeight);
        }
        for (int y = 0; y < gameHeight / cellSize; y++) {
            g.drawLine(0, y * cellSize, gameWidth, y * cellSize);
        }

        // Draw food
        g.setColor(Color.orange);
        g.fillRect(food.x * cellSize, food.y * cellSize, cellSize, cellSize);

        // Draw snake
        g.setColor(Color.red);
        for (Point p : snake) {
            g.fillRect(p.x * cellSize, p.y * cellSize, cellSize, cellSize);
        }

        // Display current score during the game
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("Score: " + score, 10, 20); // Display score in the top-left

        if (!isRunning) {
            gameOver(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        checkCollisions();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_LEFT) && directionX != 1) {
            directionX = -1;
            directionY = 0;
        } else if ((key == KeyEvent.VK_RIGHT) && directionX != -1) {
            directionX = 1;
            directionY = 0;
        } else if ((key == KeyEvent.VK_UP) && directionY != 1) {
            directionX = 0;
            directionY = -1;
        } else if ((key == KeyEvent.VK_DOWN) && directionY != -1) {
            directionX = 0;
            directionY = 1;
        } else if (key == KeyEvent.VK_R) {
            resetGame();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}