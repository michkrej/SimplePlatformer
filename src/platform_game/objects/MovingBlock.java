package platform_game.objects;

import platform_game.mapping.Offset;

import java.awt.*;

/**
 * Code for the moving blocks, both platforms and monsters.
 */

public class MovingBlock extends Rectangle
{

    private int xLimit;
    private int xStart;
    private int direction = 1;

    private BlockType type;

    public MovingBlock(int x, int y, BlockType type, int size) {
	setBounds(x, y, size, size);
	this.type = type;

	xStart = x;
	xLimit = x + size*2;
    }

    public void update() {
	movementEnemy();
    }

    public void draw(final Graphics g, Offset offset) {
	if (type == BlockType.ENEMY) {
	    g.setColor(Color.RED);
	    g.fillRect(x - (int) offset.getxOffset(), y - (int) offset.getyOffset(), width, height);
	}
	if (type == BlockType.PLATFORM) {
	    g.setColor(Block.PLATFORM_COLOR);
	    g.fillRect(x - (int) offset.getxOffset(), y - (int) offset.getyOffset(), width, height);
	}
    }

    private void movementEnemy() {

	int xChange = 1;

	if (x == xLimit) {
	    direction = -1;
	}

	if (direction == 1) {
	    x += xChange;
	}

	if (direction == -1) {
	    if (x != xStart) x -= xChange;
	    else {
		direction = 1;
	    }
	}
    }

    public BlockType getType() {
	return type;
    }
}
