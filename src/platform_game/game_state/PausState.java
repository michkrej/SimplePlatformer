package platform_game.game_state;


import platform_game.main.GamePanel;
import platform_game.mapping.Offset;

import java.awt.*;
import java.awt.event.KeyEvent;

import static platform_game.game_state.MenuState.*;

/**
 * State for paus menu.
 */
public class PausState extends GameState
{

    private String[] options = { "Continue", "Start Over", "Quit" };
    private int selected = 0;

    public PausState(final GameStateManager gsm) {
	super(gsm);
    }

    public void update(Offset offset) { }

    public void draw(final Graphics g, Offset offset) {
	background.draw(g);

	for (int n = 0; n < options.length; n++) {
	    if (n == selected) {
		g.setFont(STANDARD_FONT_BOLD);
	    } else {
		g.setFont(STANDARD_FONT);
	    }
	    g.setColor(Color.WHITE);
	    g.drawString(options[n], GamePanel.WIDTH / 2 - FONT_SIZE * 2,
			 (GamePanel.HEIGHT / 2 - FONT_SIZE) + n * FONT_SIZE * 2);
	}
    }

    public void keyPressed(final int key, Offset offset) {
	if (key == KeyEvent.VK_DOWN) {
	    selected++;
	    if (selected >= options.length) {
		selected = 0;
	    }
	}
	if (key == KeyEvent.VK_UP) {
	    selected--;
	    if (selected < 0) {
		selected = options.length - 1;
	    }
	}
	if (key == KeyEvent.VK_ENTER) {
	    if (selected == 0) {
		gsm.returnToLastState();
	    }
	    if (selected == 1) {
		gsm.restartlevel();
	    }
	    if (selected == 2) {
		System.exit(0);
	    }
	}

    }

    public void keyReleased(final int key) {

    }
}
