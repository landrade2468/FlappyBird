import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game implements ActionListener, KeyListener {

    private Bird bird;
    private JFrame frame;
    private JPanel panel;
    private ArrayList<Rectangle> pipes;
    private int time, scroll;
    private Timer t;

    private boolean paused;

    public void go() {
        frame = new JFrame("Flappy Bird");
        bird = new Bird();
        pipes = new ArrayList<>();
        panel = new GameScreen(this, bird, pipes);
        frame.add(panel);

        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);

        paused = true;

        t = new Timer(16, this);
        t.start();
    }
    public static void main(String[] args) {
        new Game().go();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
        if(!paused) {
            bird.birdMechanics();
            if(scroll % 90 == 0) {
                Rectangle r = new Rectangle(640, 0, 50, (int) ((Math.random()*480)/5f + (0.2f)*480));
                int h2 = (int) ((Math.random()*480)/5f + (0.2f)*480);
                Rectangle r2 = new Rectangle(640, 480 - h2, 50, h2);
                pipes.add(r);
                pipes.add(r2);
            }
            ArrayList<Rectangle> toRemove = new ArrayList<>();
            boolean game = true;
            for(Rectangle r : pipes) {
                r.x-=3;
                if(r.x + r.width <= 0) {
                    toRemove.add(r);
                }
                if(r.contains(bird.xPosition, bird.yPosition)) {
                    JOptionPane.showMessageDialog(frame, "You lose!\n"+"Your score was: "+time+".");
                    game = false;
                }
            }
            pipes.removeAll(toRemove);
            time++;
            scroll++;

            if(bird.yPosition > 480 || bird.yPosition + 25 < 0) {
                game = false;
            }

            if(!game) {
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

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP) {
            bird.jump();
        }
        else if(e.getKeyCode()==KeyEvent.VK_SPACE) {
            paused = false;
        }
    }
    public void keyReleased(KeyEvent e) {

    }
    public void keyTyped(KeyEvent e) {

    }

    public boolean paused() {
        return paused;
    }
}