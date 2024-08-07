package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame {

    private JButton rainbowButton;
    private JButton blueButton;
    private JPanel buttonPanel;
    private static JPanel titlePanel;
    private SnakeColor colorChoice;
    private static boolean runningGame = false;
    private static int highScore = 0;

    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;

    public GameFrame() {
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        initializeMenu();
        this.setVisible(true);
    }

    public static void setRunningGame(boolean runningGame) {
        GameFrame.runningGame = runningGame;
    }

    public static void updateHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            titlePanel.repaint();
        }
    }

    private void playChoice() {
        JFrame snakeWindow = new JFrame("Snake");
        switch (colorChoice) {
            case BLUE -> snakeWindow.add(new GamePanel());
            case RAINBOW -> snakeWindow.add(new RainbowGamePanel());
        }

        snakeWindow.pack();
        snakeWindow.setDefaultCloseOperation(snakeWindow.DISPOSE_ON_CLOSE);
        snakeWindow.setResizable(false);
        snakeWindow.setLocationRelativeTo(null);
        snakeWindow.setVisible(true);

        runningGame = true;

    }

    /**
     * Generates start menu with buttons meant to take player straight to game
     */
    private void initializeMenu() {


        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);

        blueButton = createButton("Blue (Default)", SnakeColor.BLUE);
        rainbowButton = createButton("Rainbow (Fun, but epilepsy warning)", SnakeColor.RAINBOW);

        buttonPanel.add(blueButton);
        buttonPanel.add(rainbowButton);

        titlePanel = new JPanel() {

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMenu(g);
                drawHighScore(g);
            }
        };
        titlePanel.setBackground(Color.BLACK);

        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(titlePanel, BorderLayout.CENTER);
    }

    /**
     * Draws highScore at center of screen
     * @param g Graphics object used to alter menu panel
     */
    private void drawHighScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(g.getFont());

        if (highScore == 1) {
            g.drawString("Highscore: " + highScore + " apple!",
                    (SCREEN_WIDTH - metrics.stringWidth("Highscore: " + highScore + " apple!")) / 2,
                    (SCREEN_HEIGHT - metrics.getHeight()) / 2);
        } else {
            g.drawString("Highscore: " + highScore + " apples!",
                    (SCREEN_WIDTH - metrics.stringWidth("Highscore: " + highScore + " apples!")) / 2,
                    (SCREEN_HEIGHT - metrics.getHeight()) / 2);
        }
    }

    /**
     * Draws the start menu for the snake game
     * @param g Graphics object used to alter menu panel
     */
    private void drawMenu(Graphics g) {

        g.setColor(Color.WHITE);

        Font titleFont = new Font("Ink Free", Font.BOLD, 40);
        Font subtitleFont = new Font("Ink Free", Font.BOLD, 20);

        g.setFont(titleFont);
        FontMetrics metrics = getFontMetrics(titleFont);

        int titleY = metrics.getHeight() * 2;

        g.drawString("Welcome to Snake: Java Edition",
                (SCREEN_WIDTH - metrics.stringWidth("Welcome to Snake: Java Edition")) / 2,
                titleY);

        g.setFont(subtitleFont);
        metrics = getFontMetrics(subtitleFont);

        int subtitleY = titleY + metrics.getHeight();

        g.drawString("By Sean Gentry",
                (SCREEN_WIDTH - metrics.stringWidth("By Sean Gentry")) / 2,
                subtitleY);

        // Just above the buttons panel in BorderLayout.SOUTH
        int instructionsY = SCREEN_HEIGHT - metrics.getHeight() * 9 / 2;
        g.drawString("Now pick your color below and begin!",
                (SCREEN_WIDTH - metrics.stringWidth("Now pick your color below and begin!")) / 2,
                instructionsY);
    }

    /**
     * Makes the buttons at the bottom of the start menu
     * @param text Text to be shown with button
     * @param color color button is assigned to
     * @return Button created
     */
    private JButton createButton(String text, SnakeColor color) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setFont(new Font("Ink Free", Font.BOLD, 20));
        button.setMargin(new Insets(10, 10, 5, 10));

        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (!runningGame) {
                    colorChoice = color;
                    playChoice();
                }
            }
        });

        return button;
    }
}
