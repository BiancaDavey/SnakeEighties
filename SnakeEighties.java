import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Font;
import java.awt.Graphics;

public class SnakeEighties extends javax.swing.JFrame {

    /**
     The SnakeEighties class provides a Java Swing program being an eighties synthwave themed version of the classic snake game.
     */

    // Variables setting the square numbers and dimensions for the game panel.
    public final int GAME_WIDTH = 20 * 30;
    public final int GAME_HEIGHT = 20 * 30;
    public final int SQUARE_SIZE = 20;
    public final int SQUARES = 900;

    // Variable controlling the real time speed of the snake movement.
    public final int TIME_DELAY = 60;

    // Variables representing the location of the snake and food on the X and Y axes.
    public int[] snakeX = new int[SQUARES];
    public int[] snakeY = new int[SQUARES];
    public int foodX;
    public int foodY;

    // Setting snake's initial length.
    public int snakeLength = 1;

    // Setting default font and custom font.
    Font defaultFont = new Font("Arial", Font.BOLD, 30);
    Font eightiesFont = new Font("Courier", Font.BOLD, 30);

    // Variables to represent the key currently pressed by the user and most recently pressed by the user.
    int currentKey = KeyEvent.VK_DOWN;
    int previousKey;

    // Boolean setting the game state as running.
    public boolean gameStart = true;

    public static void main(String[] args) {
        SnakeEighties launchGame = new SnakeEighties();
    }

    public SnakeEighties() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setLocation(50, 50);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.toFront();
        this.requestFocus();
        GamePanel newGame = new GamePanel();
        addKeyListener(newGame);
        add(newGame);
        setVisible(true);
        Timer gameTimer = new Timer(TIME_DELAY, newGame);
        gameTimer.start();
    }

    public class GamePanel extends JPanel implements KeyListener, ActionListener {
        GamePanel() {
            // Setting game panel, background and title.
            setBackground(new Color(0, 0, 0));
            setTitle("SnakeEighties");

            // Setting snake starting coordinates.
            for (int i = 0; i < snakeLength; i++) {
                snakeY[i] = 140 - (i * 30);
                snakeX[i] = 140;
            }

            // Generating food for snake to consume.
            generateFuel();
        }

        // Drawing food and snake graphics on game panel when game is running.
        public void paintComponent(Graphics gameGraphic) {
            super.paintComponent(gameGraphic);

            // Drawing fuel on game panel.
            if (gameStart) {
                gameGraphic.setColor(new Color(255, 20, 147));
                gameGraphic.fillOval(foodX, foodY, SQUARE_SIZE, SQUARE_SIZE);

                // Drawing snake on game panel.
                for (int i = 0; i < snakeLength; i++) {
                    if (i == 0) {
                        gameGraphic.setColor(new Color(0, 255, 255));
                    } else {
                        gameGraphic.setColor(new Color(138, 43, 226));
                    }
                    gameGraphic.fillOval(snakeX[i], snakeY[i], SQUARE_SIZE, SQUARE_SIZE);
                }
            }
            else {
                gameEnd(gameGraphic);
            }
        }

        // Game Over notification when gameEnd is true.
        public void gameEnd(Graphics gameOverMessage) {
            gameOverMessage.setFont(eightiesFont);
            gameOverMessage.setColor(new Color(57, 225, 20));
            gameOverMessage.drawString(("Game Over!"), GAME_WIDTH / 3, GAME_HEIGHT / 3);
        }

        // Methods to implement game response to keys pressed by user, with the previous key being set to the current key to enable continuous movement.
        public void keyPressed(KeyEvent e) {
            previousKey = currentKey;
            currentKey = e.getKeyCode();
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }

        public void actionPerformed(ActionEvent e) {
            boundarySet();
            snakeKeyControl();
            repaint();
        }

        // Extend snake length if consumes fuel and end game if user guides snake into a boundary.
        public void boundarySet() {
            if (snakeX[0] > GAME_WIDTH || snakeX[0] < 0 || snakeY[0] > GAME_HEIGHT || snakeY[0] < 0) {
                gameStart = false;
            }
            if ((snakeX[0] == foodX) && (snakeY[0] == foodY)) {
                snakeLength++;
                generateFuel();
            }
        }

        // Set random location on game panel for fuel.
        public void generateFuel() {
            int xLocation = (int) (Math.random() * Math.sqrt(SQUARES) - 1);
            foodX = ((xLocation * SQUARE_SIZE));
            xLocation = (int) (Math.random() * Math.sqrt(SQUARES) - 1);
            foodY = ((xLocation * SQUARE_SIZE));
        }

        // Move location of snake according to the key pressed by the user.
        public void snakeKeyControl() {
            for (int i = snakeLength; i > 0; i--) {
                snakeX[i] = snakeX[(i - 1)];
                snakeY[i] = snakeY[(i - 1)];
            }
            if (currentKey == KeyEvent.VK_LEFT) {
                snakeX[0] -= SQUARE_SIZE;
            }
            if (currentKey == KeyEvent.VK_RIGHT) {
                snakeX[0] += SQUARE_SIZE;
            }
            if (currentKey == KeyEvent.VK_DOWN) {
                snakeY[0] += SQUARE_SIZE;
            }
            if (currentKey == KeyEvent.VK_UP) {
                snakeY[0] -= SQUARE_SIZE;
            }
        }
    }
}
