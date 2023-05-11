import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bird {
    private float xPosition;
    private float yPosition;
    private float xVelocity;
    private float yVelocity;
    private Image bird;

    public Bird(int width, int height) throws IOException {
        this.xPosition = width / 2;
        this.yPosition = height / 2;
        setBird();
    }

    public void setBird() throws IOException {
        try {
            this.bird = ImageIO.read(new File("FlappyBirdGame/src/flappy-bird.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void birdMechanics() {
        xPosition = xPosition + xVelocity;
        yPosition = yPosition + yVelocity;
        yVelocity = yVelocity + 0.5f;
    }

    public void update(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(bird, Math.round(xPosition - 25), Math.round(yPosition - 25), 25, 25, null);
    }

    public void jump()  {
        yVelocity = -8;
    }

    public void reset() {
        xPosition = 320;
        yPosition = 320;
        xVelocity = 0;
        yVelocity = 0;
    }
}
