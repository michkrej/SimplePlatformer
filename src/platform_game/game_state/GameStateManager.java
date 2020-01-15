package platform_game.game_state;

import platform_game.mapping.Offset;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * How we manage our gamestates. The manager is used to update the current state.
 */
public class GameStateManager
{
    protected List<GameState> states;
    private Offset offset;

    public GameStateManager() {
	this.states = new ArrayList<>();
	this.offset = new Offset();
	states.add(new MenuState(this));
    }

    public void update() {
	states.get(states.size() - 1).update(offset);

    }

    public void returnToLastState() {
	states.remove(states.size() - 1);
	update();
    }

    public void draw(Graphics g) {
	states.get(states.size() - 1).draw(g, offset);

    }
    public void restartlevel() {
        states.add(new Level1State(this));
        offset.resetOffset();
        update();
    }

    public void keyPressed(int n) {
	states.get(states.size() - 1).keyPressed(n, offset);

    }

    public void keyReleased(int n) {
	states.get(states.size() - 1).keyReleased(n);
    }
}