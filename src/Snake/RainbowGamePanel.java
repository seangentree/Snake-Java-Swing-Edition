package Snake;

import java.awt.*;
import java.awt.event.ActionEvent;

public class RainbowGamePanel extends GamePanel {

    private static int currRed = 255;
    private static int currBlue = 0;
    private static int currGreen = 0;
    private static Color currRainbowColor;

    public RainbowGamePanel() {
        super();
    }

    protected void draw(Graphics g) {

        if (super.running) {
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

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(currRainbowColor);
                } else {
                    g.setColor(currRainbowColor);
                }
                g.fillRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE);
            }





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

    public void actionPerformed(ActionEvent e) {

        // try +=/-= 5, 15, 85, and 255
        if (currRed == 255 && currBlue > 0) {
            currBlue -= 255;
        } else if (currRed == 255 && currGreen < 255) {
            currGreen += 255;
        } else if (currGreen == 255 && currRed > 0) {
            currRed -= 255;
        } else if (currGreen == 255 && currBlue < 255) {
            currBlue += 255;
        } else if (currBlue == 255 && currGreen > 0) {
            currGreen -= 255;
        } else if (currBlue == 255 && currRed < 255) {
            currRed += 255;
        }


        currRainbowColor = new Color(currRed, currGreen, currBlue);



        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
}
