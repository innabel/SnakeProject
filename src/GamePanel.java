import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    // how big we want object to appear in our game:
    static final int UNIT_SIZE = 25;
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

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        // TODO later we can offer different options of the whole new game design with different
        //  colors
        this.setBackground(Color.white);
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
        g.setColor(Color.lightGray);
        for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
            // drawing vertical lines
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            // drawing horizontal lines
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);

        }
    }

    public void newApple() {

    }

    public void move() {

    }

    public void checkApple() {

    }

    public void checkCollisions() {

    }

    public void gameOver(Graphics g) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

        }
    }
}
