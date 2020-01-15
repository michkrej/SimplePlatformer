package platform_game.main;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Runs the game, uses a timer to update content in the game window.
 */
public final class Game
{
    private Game() {}

    public static void main(String[] args) {

	GamePanel panel = new GamePanel();
	JFrame window = new JFrame("Simple Platformer");
	window.setContentPane(panel);
	window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	window.setResizable(false);
	window.pack();
	window.setLocationRelativeTo(null);
	window.setVisible(true);

	final Action update = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		panel.update();
	    }
	};

	final Timer clockTimer = new Timer(13, update);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }
}
