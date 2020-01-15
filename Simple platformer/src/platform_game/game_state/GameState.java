package platform_game.game_state;

import platform_game.mapping.Background;
import platform_game.mapping.Offset;

import java.awt.*;

/**
 * Abstact class for all the states, shows which methods all the state classes need to have.
 */
// xOffset and yOffset need to be public and static so we can access them in player.
public abstract class GameState
{
    protected GameStateManager gsm;

    protected Background background;

    protected GameState(GameStateManager gsm) {
	this.gsm = gsm;
	this.background = new Background("/BG.png");
    }

    public abstract void update(Offset offset);

    public abstract void draw(Graphics g, Offset offset);

    public abstract void keyPressed(int key, Offset offset);

    public abstract void keyReleased(int key);

}
