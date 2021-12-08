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
    static final int DELAY = 75;

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

    // our constructor
    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        // TODO later we can offer different options of the whole new game design with different
        //  colors
        this.setBackground(new Color(152,214,220));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {
        // drawing the grid on the field
        g.setColor(new Color(62, 189, 219));
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
        
        // drawing the snake
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

    public void gameOver(Graphics g) {

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

        }
    }
}
