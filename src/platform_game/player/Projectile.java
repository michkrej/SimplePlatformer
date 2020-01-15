package platform_game.player;

import platform_game.collison_handling.Collisions;
import platform_game.main.GamePanel;
import platform_game.mapping.Offset;
import platform_game.objects.Block;
import platform_game.objects.MovingBlock;

import java.awt.*;
import java.util.ArrayList;


import static platform_game.objects.BlockType.ENEMY;
import static platform_game.objects.BlockType.PLATFORM;

/**
 * Class for projectile. Makes the projectile move and checks if it is visible and if it has hit a platform or a enemy.
 */
public class Projectile
{
    private boolean visible;

    private int width = 7;
    private int height = 7;

    private int x, y;
    private final static int START_Y_OFFSET = 565;

    private int rightSide;
    private int bottom;
    private int top;

    public Projectile(int x, int y, boolean visible) {
	this.x = x + (GamePanel.WIDTH / 7);
	this.y = y + START_Y_OFFSET;
	this.visible = visible;
    }

    public void update(Block[][] block, ArrayList<MovingBlock> movingBlocks) {
	if (visible) {
	    movement();
	    projectileEnemy(movingBlocks);
	    projectilePlatform(block);
	}
	rightSide = x + width;
	bottom = y + height;
	top = y;
    }

    public void draw(Graphics g, Offset offset) {
	if (visible) {
	    g.setColor(Color.BLACK);
	    g.fillOval(x - (int) offset.getxOffset(), y - (int) offset.getyOffset(), width, height);
	}
    }

    private void movement() {
	final int speed = 4;
	x += speed;
    }

    protected boolean projectileEnemy(ArrayList<MovingBlock> movingBlocks) {

	if (visible) {

	    for (int block = 0; block < movingBlocks.size(); block++) {
		MovingBlock mb=movingBlocks.get(block);

		if (Collisions.hasCollisionMovingBlock(new Point(rightSide, top), mb, ENEMY) ||
		    Collisions.hasCollisionMovingBlock(new Point(rightSide, bottom), mb, ENEMY)) {

		    movingBlocks.remove(block);
		    visible = false;
		    return true;
		}
	    }
	}
	return false;
    }

    private void projectilePlatform(Block[][] block) {

	for (final Block[] blocks : block) {
	    for (int col = 0; col < block[0].length; col++) {
		Block b = blocks[col];

		// Right
		if (Collisions.hasCollision(new Point(rightSide, top), b, PLATFORM) ||
		    Collisions.hasCollision(new Point(rightSide, bottom - 2), b, PLATFORM)) {

		    visible = false;
		    break;
		}
	    }
	}
    }
}


