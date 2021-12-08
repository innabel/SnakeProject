import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame() {
        try {
            this.add(new GamePanel());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); // to make the window appear in the middle

    }
}
