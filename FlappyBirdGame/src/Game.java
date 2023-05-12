import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

public class Game implements ActionListener, KeyListener {
    private Bird bird;
    private JFrame frame;
    private JPanel panel;
    private ArrayList<Rectangle> pipes;
    private int time;
    private int scroll;
    private Timer timer;
    private boolean paused;

    public static void main(String[] args) throws IOException {
        new Game().start();
    }

    public void start() throws IOException {
        frame = new JFrame("Flappy Bird Game");
        bird = new Bird();
        pipes = new ArrayList<>();
        panel = new GameScreen(this, bird, pipes);
        frame.add(panel);
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);
        paused = true;
        timer = new Timer(16, this);
        timer.start();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
        if (!paused) {
            bird.birdMechanics();
            if (scroll % 90 == 0) {
                Rectangle pipe = new Rectangle(640, 0, 50, (int) ((Math.random() * 30 / 5f + (0.2f) * 30)));
                int otherHeight = (int) (Math.random() * 30 / 5f + (0.2f) * 30);
                Rectangle otherPipe = new Rectangle(640, 480 - otherHeight, 50, otherHeight);
                pipes.add(pipe);
                pipes.add(otherPipe);
            }
            ArrayList<Rectangle> pipeRemoval = new ArrayList<>();
            boolean game = true;
            for (Rectangle pipe : pipes) {
                pipe.x -= 3;
                if (pipe.x + pipe.width <= 0) {
                    pipeRemoval.add(pipe);
                }
                if (pipe.contains(bird.xPosition, bird.yPosition)) {
                    JOptionPane.showMessageDialog(frame, "You lose!\n" + "Your score was: " + time + ".");
                    game = false;
                }
            }
            pipes.removeAll(pipeRemoval);
            time++;
            scroll++;
            if (bird.yPosition > 480 || bird.yPosition + 25 < 0) {
                game = false;
            }
            if (!game) {
                pipes.clear();
                bird.reset();
                time = 0;
                scroll = 0;
                paused = true;
            }
        }
    }

    public int getScore() {
        return time;
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            bird.jump();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            paused = false;
        }
    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {

    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    public boolean paused() {
        return paused;
    }
}
