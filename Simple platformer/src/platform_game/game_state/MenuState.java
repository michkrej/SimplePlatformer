package platform_game.game_state;

import platform_game.main.GamePanel;
import platform_game.mapping.Background;
import platform_game.mapping.Offset;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * The state for the starter menu and the finish menu.
 */
public class MenuState extends GameState
{
    private String[] optionsStart = { "Start", "Quit" };
    private int selected = 0;

    /**
     * Standard font size
     */
    public static final int FONT_SIZE = 50;

    /**
     * Standard font
     */
    public static final Font STANDARD_FONT = new Font("Calibri", Font.PLAIN, FONT_SIZE);

    /**
     * Standard font bold
     */
    public static final Font STANDARD_FONT_BOLD = new Font("Calibri", Font.BOLD, FONT_SIZE);


    public MenuState(GameStateManager gsm) {
	super(gsm);

	if (gsm.states.isEmpty()) {
	    background = new Background("/StarterMenu.png");
	} else {
	    background = new Background("/YouWon.png");
	}
    }

    public void update(Offset offset) {

    }

    public void draw(final Graphics g, Offset offset) {
	background.draw(g);

	if (gsm.states.size() != 1) {
	    drawMenu(g);
	} else {
	    drawMenu(g);
	}

    }

    private void drawMenu(Graphics g) {
	for (int n = 0; n < optionsStart.length; n++) {
	    if (n == selected) {
		g.setFont(STANDARD_FONT_BOLD);
	    } else {
		g.setFont(STANDARD_FONT);
	    }
	    g.setColor(Color.WHITE);
	    g.drawString(optionsStart[n], GamePanel.WIDTH / 2 - FONT_SIZE, (GamePanel.HEIGHT / 2 - FONT_SIZE) + n * 100);
	}
    }

    public void keyPressed(int key, Offset offset) {
	if (key == KeyEvent.VK_DOWN) {
	    selected++;
	    if (selected >= optionsStart.length) {
		selected = 0;
	    }
	} else if (key == KeyEvent.VK_UP) {
	    selected--;
	    if (selected < 0) {
		selected = optionsStart.length - 1;
	    }
	}
	if (key == KeyEvent.VK_ENTER) {
	    if (selected == 0) {
		gsm.states.add(new Level1State(gsm));

	    } else {
		System.exit(0);
	    }
	}

    }

    public void keyReleased(int key) {

    }
}
