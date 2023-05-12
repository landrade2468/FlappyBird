import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameScreen extends JPanel {

    private Bird bird;
    private ArrayList<Rectangle> rects;
    private Game fb;
    private Font scoreFont, pauseFont;
    public static final Color bg = new Color(113, 197, 207);
    private Image pipeHead, pipeLength;

    public GameScreen(Game fb, Bird bird, ArrayList<Rectangle> rects) {
        this.fb = fb;
        this.bird = bird;
        this.rects = rects;
        scoreFont = new Font("Comic Sans MS", Font.BOLD, 18);
        pauseFont = new Font("Arial", Font.BOLD, 48);

        try {
            pipeHead = ImageIO.read(new File("FlappyBirdGame/src/pipeHead.png"));
            pipeLength = ImageIO.read(new File("FlappyBirdGame/src/pipe_part.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(bg);
        g.fillRect(0,0, 640, 480);
        bird.update(g);
        g.setColor(Color.RED);
        for(Rectangle r : rects) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.GREEN);
            AffineTransform old = g2.getTransform();
            g2.translate(r.x+ 25, r.y+15);
            if(r.y < 240) {
                g2.translate(0, r.height);
                g2.rotate(Math.PI);
            }
            g2.drawImage(pipeHead, -25, -15, 50, 30, null);
            g2.drawImage(pipeLength, -25, 15, 50, r.height, null);
            g2.setTransform(old);
        }
        g.setFont(scoreFont);
        g.setColor(Color.BLACK);
        g.drawString("Score: "+fb.getScore(), 10, 20);

        if(fb.paused()) {
            g.setFont(pauseFont);
            g.setColor(new Color(0,0,0,170));
            g.drawString("PAUSED", 220, 140);
            g.drawString("PRESS SPACE TO BEGIN", 20, 290);
        }
    }
}