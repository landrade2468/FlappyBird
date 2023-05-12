import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bird {
    public float xPosition;
    public float yPosition;
    public float xVelocity;
    public float yVelocity;
    private Image img;

    public Bird() {
        xPosition = 320;
        yPosition = 240;
        try {
            img = ImageIO.read(new File("FlappyBirdGame/src/flappyBird.jpg"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void birdMechanics() {
        xPosition += xVelocity;
        yPosition += yVelocity;
        yVelocity += 0.5f;
    }

    public void update(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawImage(img, Math.round(xPosition - 25),Math.round(yPosition - 25),50,50, null);
    }

    public void jump() {
        yVelocity = -8;
    }

    public void reset() {
        xPosition = 320;
        yPosition = 320;
        xVelocity = yVelocity = 0;
    }
}