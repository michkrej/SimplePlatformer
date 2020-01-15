package platform_game.main;

import platform_game.game_state.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Class for the game window, here we set the size and have functions to create and paint the components in the window.
 */
public class GamePanel extends JPanel implements KeyListener
{

    /**
     * Width of window
     */
    public static final int WIDTH = 1024;
    /**
     * Height of window
     */
    public static final int HEIGHT = 768;

    private GameStateManager gsm;

    public GamePanel() {
	super();
	gsm = new GameStateManager();

	setPreferredSize(new Dimension(WIDTH, HEIGHT));

	addKeyListener(this);
	setFocusable(true);
	requestFocus();

    }

    public void update() {
	repaint();
	gsm.update();
    }

    public void paintComponent(Graphics g) {
	super.paintComponent(g);

	gsm.draw(g);
	g.dispose();
    }

    public void keyTyped(final KeyEvent key) {
	// don't actually need this one but since we implement KeyListener it has to be here
    }

    public void keyPressed(final KeyEvent key) {
	gsm.keyPressed(key.getKeyCode());
    }

    public void keyReleased(final KeyEvent key) {
	gsm.keyReleased(key.getKeyCode());
    }
}
