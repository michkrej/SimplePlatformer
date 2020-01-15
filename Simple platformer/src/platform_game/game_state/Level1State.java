package platform_game.game_state;

import platform_game.mapping.Map;
import platform_game.mapping.Offset;
import platform_game.player.Player;
import platform_game.player.Projectile;

import java.awt.*;
import java.awt.event.KeyEvent;

import static platform_game.game_state.MenuState.FONT_SIZE;

/**
 * The state for the first level. Updates all the objects, draws them out and listens after key presses.
 */
public class Level1State extends GameState
{
    private Player player;
    private Map map;

    private final static int STATUS_FONT_SIZE = 30;


    public Level1State(GameStateManager gsm) {
	super(gsm);
	map = new Map("/MAP.txt");
	player = new Player();
    }

    public void update(Offset offset) {
	player.update(map.getBlocks(), map.getMovingBlocks(), offset);
	map.update();

	if (player.reachedFinish(map.getBlocks())) {
	    gsm.states.add(new MenuState(gsm));
	}
	if (player.gameOver(offset)) {
	    gsm.states.add(new GameOverState(gsm));
	}

	for (Projectile projectile : player.getProjectiles()) {
	    projectile.update(map.getBlocks(), map.getMovingBlocks());
	}

    }

    public void draw(final Graphics g, Offset offset) {
	background.draw(g);
	map.draw(g, offset);
	player.draw(g);

	for (Projectile projectile : player.getProjectiles()) {
	    projectile.draw(g, offset);
	}

	g.setColor(Color.BLACK);
	g.setFont(new Font("Calabri", Font.PLAIN, STATUS_FONT_SIZE));
	g.drawString("Score:  " + player.getScore(), FONT_SIZE*3, STATUS_FONT_SIZE + 5);
	g.drawString("Lives:  " + player.getHealth(), 10, STATUS_FONT_SIZE + 5);

    }

    public void keyPressed(int key, Offset offset) {
	player.keyPressed(key, offset);

	if (key == KeyEvent.VK_ENTER) {
	    gsm.states.add(new PausState(gsm));
	}
    }

    public void keyReleased(final int key) {
	player.keyReleased(key);
    }
}
