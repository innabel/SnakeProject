import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    // how big we want object to appear in our game:
    static final int UNIT_SIZE = 20;
    // how many object we can actually fit in this game:
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    // the higher the number, the slower the game is
    // TODO we can offer to choose the speed of this game later
    static final int DELAY = 100;

    // arrays that hold all the coordinates of all the parts of snake's body
    final int x[] = new int[GAME_UNITS]; // all x coordinates
    final int y[] = new int[GAME_UNITS]; // all y coordinates
    int bodyParts = 6; // we begin with 6 parts of the snake
    int applesEaten;
    int appleX; // x-coordinate of where the apple will appear == random
    int appleY; // y-coordinate
    char direction = 'R'; // we will have 4 - R(igth), L(eft), U(p), D(own);
    boolean running = false;
    Timer timer;
    Random random;
    String fontTitle = "Freestyle Script";
    boolean randomSnakeColor = true;

    // our constructor
    GamePanel() throws InterruptedException {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        // TODO later we can offer different options of the whole new game design with different
        //  colors
        this.setBackground(new Color(206,238,238));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() throws InterruptedException {

        Thread.sleep(600);
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            draw(g);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics g) throws InterruptedException {
        if (running) {
            // drawing the grid on the field
            g.setColor(new Color(129, 218, 238));
            for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
                // drawing vertical lines
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                // drawing horizontal lines
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            // drawing the apple
            g.setColor(new Color(232, 36, 39));
            // TODO can change that later with object in the shape of an apple;
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // drawing the snake - random color is off
            if(!randomSnakeColor) {
                for (int i = 0; i < bodyParts; i++) {
                    // case when we are dealing with the head of the snake
                    if(i == 0) {
                        g.setColor(new Color(44, 100, 32));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                        g.setColor(new Color(123, 169, 96));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }
            }

            // drawing the snake - random color is on
            if(randomSnakeColor) {
                for (int i = 0; i < bodyParts; i++) {
                    // case when we are dealing with the head of the snake
                    if(i == 0) {
                        g.setColor(new Color(random.nextInt(255), random.nextInt(255),
                                random.nextInt(255)));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                        g.setColor(new Color(random.nextInt(255), random.nextInt(255),
                                random.nextInt(255)));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }
            }

            // drawing the score
            g.setColor(new Color(144, 86, 172));
            g.setFont(new Font(fontTitle, Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " +
                            "" + applesEaten))/2, g.getFont().getSize());

        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        // generating the coordinates of the new apple;
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;

    }

    // moving the snake across our field
    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if((x[0] == appleX) & (y[0]) == appleY) {
            bodyParts++;
            applesEaten++; // functions as the score
            newApple();
        }
    }

    public void checkCollisions() {
        // checking if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false; // triggers the gameOver method
            }
        }
        // checking if head touches left border
        if(x[0] < 0) {
            running = false;
        }
        // checking if head touches right border
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // checking if head touches top border
        if(y[0] < 0) {
            running = false;
        }
        // checking if head touches bottom border
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        // stopping the timer
        if(!running) {
            timer.stop();
        }

    }

    public void gameOver(Graphics g) throws InterruptedException {
        // Game Over text
        Thread.sleep(600);
        g.setColor(new Color(144, 86, 172));
        g.setFont(new Font(fontTitle, Font.PLAIN, 100));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics1.stringWidth("Game Over"))/2,
                SCREEN_HEIGHT/2);

        // displaying the score
        g.setColor(new Color(144, 86, 172));
        g.setFont(new Font(fontTitle, Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " +
                "" + applesEaten))/2, g.getFont().getSize());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            // limiting the movements to 90 degrees only
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
