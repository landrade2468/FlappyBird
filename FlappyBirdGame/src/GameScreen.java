import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameScreen extends JPanel {
    private Bird bird;
    private ArrayList<Rectangle> pipes;
    private Game game;
    private Image pipeImage;

    public GameScreen(Game game, Bird bird, ArrayList<Rectangle> pipes) {
        this.game = game;
        this.bird = bird;
        this.pipes = pipes;
        setPipe();
    }

    public void setPipe() {
        try {
            this.pipeImage = ImageIO.read(new File("FlappyBirdGame/src/flappy-bird-pipe.png"));
        } catch(IOException e){
                e.printStackTrace();
            }
        }

    public void paintComponent(Graphics g) {
        g.setColor(new Color (113, 197, 207));
        g.fillRect(0, 0, 640, 480);
        bird.update(g);
        g.setColor(Color.RED);
        for (Rectangle pipe : pipes) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(113, 191, 46));
            AffineTransform old = g2.getTransform();
            g2.translate(pipe.x + 25, pipe.y + 15);
            if (pipe.y < 240) {
                g2.translate(0, pipe.height);
                g2.rotate(Math.PI);
                g2.drawImage(pipeImage, -25, -15, 50, 30, null);
                g2.transform(old);
            }
            g.setFont(new Font("04B_19__.TTF", Font.BOLD, 18));
            g.setColor(new Color(101, 78, 86));
            g.drawString("Score: " + game.getScore(), 10, 20);
            if (game.paused()) {
                g.setFont(new Font("font.ttf", Font.BOLD, 48));
                g.setColor(new Color(0, 0, 0, 170));
                g.drawString("Paused", 220, 140);
                g.drawString("Press Space to Begin", 20, 290);
            }
        }
    }
}
