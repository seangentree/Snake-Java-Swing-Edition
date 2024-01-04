package Snake;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private static final char RIGHT_CHAR = 'R';
    private static final char LEFT_CHAR = 'L';
    private static final char UP_CHAR = 'U';
    private static final char DOWN_CHAR = 'D';

    protected static final int SCREEN_WIDTH = 600;
    protected static final int SCREEN_HEIGHT = 600;
    protected static final int UNIT_SIZE = 25;
    protected static final int GAME_UNITS = SCREEN_WIDTH * SCREEN_HEIGHT / UNIT_SIZE;
    protected static final int DELAY = 75;

    protected final int[] snakeX = new int[GAME_UNITS];
    protected final int[] snakeY = new int[GAME_UNITS];

    protected int bodyParts = 6;
    protected int applesEaten;
    protected int appleX;
    protected int appleY;
    private char direction = RIGHT_CHAR;
    protected boolean running = false;
    private Timer timer;
    private Random rand;

    public GamePanel() {
        rand = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    protected void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    /**
     * Override of JPanel painting
     * @param g the <code>Graphics</code> object to protect
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Draws the game, including the snake, apple, and score until game is over
     * @param g
     */
    protected void draw(Graphics g) {

        if (running) {
            /*
            // Grid for visibility
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0,i * UNIT_SIZE, SCREEN_HEIGHT);
            }
             */

            // Draw apple at appleX, appleY
            g.setColor(Color.red);
            g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);


            // Draw snake BLUE
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(0, 0, 163));
                } else {
                    g.setColor(new Color(0, 0, 118));
                }
                g.fillRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE);
            }

            /*
            // Draw snake random
            int red = rand.nextInt((int)(256 / 1.38));
            int green = rand.nextInt((int)(256 / 1.38));
            int blue = rand.nextInt((int)(256 / 1.38));
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color((int)(red * 1.38), (int)(green * 1.38), (int)(blue * 1.38)));
                } else {
                    g.setColor(new Color(red, green, blue));
                }
                g.fillRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE);
            }
             */

            // Write score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Apples munched: " + applesEaten,
                    (SCREEN_WIDTH - metrics.stringWidth("Apples munched: " + applesEaten)) / 2,
                    g.getFont().getSize());


        } else {
            gameOver(g);
        }
    }

    private void newApple() {

        appleX = rand.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = rand.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;

    }

    /**
     * Shifts each body part to the head as the moves to the direction
     */
    protected void move() {
        // For each body part,
        // shift toward previous body part
        for (int i = bodyParts; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        switch(direction) {
            case UP_CHAR:
                snakeY[0] = snakeY[0] - UNIT_SIZE;
                break;

            case DOWN_CHAR:
                snakeY[0] = snakeY[0] + UNIT_SIZE;
                break;

            case RIGHT_CHAR:
                snakeX[0] = snakeX[0] + UNIT_SIZE;
                break;

            case LEFT_CHAR:
                snakeX[0] = snakeX[0] - UNIT_SIZE;
                break;
        }
    }

    /**
     * Checks if the apple is where the player head is
     */
    protected void checkApple() {
        if ((snakeX[0] == appleX) && (snakeY[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    protected void checkCollisions() {
        // If head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if((snakeX[0] == snakeX[i])
              && (snakeY[0] == snakeY[i])) {
                running = false;
            }
        }

        // If head touches borders
        if ((snakeX[0] < 0 || snakeX[0] > SCREEN_WIDTH)
           || (snakeY[0] < 0 || snakeY[0] > SCREEN_HEIGHT)) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    /**
     * Sets up screen for game loss
     * @param g
     */
    protected void gameOver(Graphics g) {
        // Game over text
        g.setColor(Color.white);

        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics topMetrics = getFontMetrics(g.getFont());

        int middleY = SCREEN_HEIGHT / 2;

        g.drawString("Game over!",
                (SCREEN_WIDTH - topMetrics.stringWidth("Game over!")) / 2,
                middleY - topMetrics.getHeight() / 2);

        g.setFont(new Font("Ink Free", Font.BOLD, 45));
        FontMetrics bottomMetrics = getFontMetrics(g.getFont());
        g.drawString("Maybe next time champ!",
                (SCREEN_WIDTH - bottomMetrics.stringWidth("Maybe next time champ!")) / 2,
                middleY + bottomMetrics.getHeight() / 2);

        // Display score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Total apples munched: " + applesEaten,
                (SCREEN_WIDTH - metrics.stringWidth("Total apples munched: " + applesEaten)) / 2,
                g.getFont().getSize());

        GameFrame.updateHighScore(applesEaten);
        GameFrame.setRunningGame(false);
    }

    /**
     * Controls
     */
    protected class MyKeyAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
               && (direction != DOWN_CHAR)) {
                direction = UP_CHAR;

            } else if ((e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
                    && (direction != UP_CHAR)) {
                direction = DOWN_CHAR;

            } else if ((e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)
                    && (direction != LEFT_CHAR)) {
                direction = RIGHT_CHAR;

            } else if ((e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
                    && (direction != RIGHT_CHAR)) {
                direction = LEFT_CHAR;
            }
        }
    }

    /**
     * Keeps game running based on actionPerformed ticks
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {

        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }


}
