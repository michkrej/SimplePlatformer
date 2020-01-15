package platform_game.player;

import platform_game.collison_handling.Collisions;
import platform_game.main.GamePanel;
import platform_game.mapping.Offset;
import platform_game.objects.Block;
import platform_game.objects.MovingBlock;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static platform_game.objects.BlockType.*;

/**
 * Code for player, mostly a lot of collision functions.
 */

// There are no actual magic numbers, we declare them all as variables

public class Player
{
    private List<Projectile> projectiles;

    private int score = 0;
    private int health = 3;
    private int points = 100;

    private boolean bottomCollision = false;
    private boolean jumping = false, falling = false;

    // right = 1, left = -1
    private int direction = 0;

    private double x, y;
    private int iY, iX;
    private final static int WIDTH = 50;
    private final static int HEIGHT = 50;
    private final static double MAX_FALL_DEPTH = 850;

    private int rightSide;
    private int leftSide;
    private int bottom;
    private int top;

    private static final double SPEED = 3.5;
    private static final double ACCELERATION = 0.1; //acceleration jump

    private static final double MAX_JUMP_SPEED = 5;
    private double currentJumpSpeed = MAX_JUMP_SPEED;

    private static final double MAX_FALL_SPEED = MAX_JUMP_SPEED;
    private double currentFallSpeed = 0;

    public Player() {
	x = WIDTH * 2;
	y = GamePanel.HEIGHT - HEIGHT * 4;
	this.iX = (int) x;
	this.iY = (int) y;
	this.projectiles = new ArrayList<>();

    }

    //what happens each tick
    public void update(Block[][] block, ArrayList<MovingBlock> movingBlocks, Offset offset) {
	rightSide = iX + (int) offset.getxOffset() + WIDTH;
	leftSide = iX + (int) offset.getxOffset();
	bottom = iY + (int) offset.getyOffset() + HEIGHT;
	top = iY + (int) offset.getyOffset();

	collisionPlatform(block);
	collisionEnemy(movingBlocks, offset);
	collisionMovingPlatform(movingBlocks);
	movement(offset);
	foundCoin(block);

	for (Projectile projectile : projectiles) {
	    if (projectile.projectileEnemy(movingBlocks)) {
		score += points;
	    }
	}
    }

    private void movement(Offset offset) {
	if (direction == 1) {
	    offset.addxOffset(SPEED);
	}

	if (direction == -1) {
	    if (offset.getxOffset() <= 0) {
		offset.subxOffset(offset.getxOffset());
	    } else {
		offset.subxOffset(SPEED);
	    }
	}

	if (jumping) {
	    offset.subyOffset(currentJumpSpeed);

	    currentJumpSpeed -= ACCELERATION;

	    if (currentJumpSpeed <= 0) {
		currentJumpSpeed = MAX_JUMP_SPEED;
		jumping = false;
		falling = true;
	    }
	}
	if (falling) {
	    offset.addyOffset(currentFallSpeed);

	    if (currentFallSpeed < MAX_FALL_SPEED) {
		currentFallSpeed += ACCELERATION;
	    }
	}

	if (!falling) {
	    currentFallSpeed = ACCELERATION;
	}

	if (!jumping && !bottomCollision) {
	    falling = true;
	}

	bottomCollision = false;
    }

    private void shoot(Offset offset) {
	Projectile projectile = new Projectile((int) offset.getxOffset(), (int) offset.getyOffset(), true);
	projectiles.add(projectile);
    }


    private void collisionPlatform(Block[][] block) {

	for (final Block[] blocks : block) {
	    for (int col = 0; col < block[0].length; col++) {
	        Block b = blocks[col];

		// Down
		if (Collisions.hasCollision(new Point(rightSide, bottom + 3), b, PLATFORM) ||
		    Collisions.hasCollision(new Point(leftSide, bottom + 3), b, PLATFORM)) {

		    bottomCollision = true;
		    falling = false;
		    break;

		}

		// Right
		if (Collisions.hasCollision(new Point(rightSide + 2, top), b, PLATFORM) ||
		    Collisions.hasCollision(new Point(rightSide + 2, bottom - 2), b, PLATFORM)) {

		    direction = 0;
		    break;
		}

		// Left
		if (Collisions.hasCollision(new Point(leftSide - 2, top), b, PLATFORM )||
		    Collisions.hasCollision(new Point(leftSide - 2, bottom - 2), b, PLATFORM)) {

		    direction = 0;
		    break;
		}
		// Up
		if (Collisions.hasCollision(new Point(rightSide, top - 2), b, PLATFORM) ||
		    Collisions.hasCollision(new Point(leftSide, top - 2), b, PLATFORM)) {

		    jumping = false;
		    falling = true;
		    break;
		}
	    }
	}
    }

    private void collisionMovingPlatform(ArrayList<MovingBlock> movingBlocks) {


	for (int block = 0; block < movingBlocks.size(); block++) {

	    MovingBlock mb=movingBlocks.get(block);

	    // Down
	    if (Collisions.hasCollisionMovingBlock(new Point(rightSide, bottom + 3), mb, PLATFORM) ||
		Collisions.hasCollisionMovingBlock(new Point(leftSide, bottom + 3), mb, PLATFORM)) {

		falling = false;
		break;

	    }

	    // Right
	    if (Collisions.hasCollisionMovingBlock(new Point(rightSide + 2, top), mb, PLATFORM) ||
		Collisions.hasCollisionMovingBlock(new Point(rightSide + 2, bottom - 2), mb, PLATFORM)) {

		direction = 0;
		break;
	    }

	    // Left
	    if (Collisions.hasCollisionMovingBlock(new Point(leftSide - 2, top), mb, PLATFORM) ||
		Collisions.hasCollisionMovingBlock(new Point(leftSide - 2, bottom - 2), mb, PLATFORM)) {

		direction = 0;
		break;
	    }
	    // Up
	    if (Collisions.hasCollisionMovingBlock(new Point(rightSide, top - 2), mb, PLATFORM) ||
		Collisions.hasCollisionMovingBlock(new Point(leftSide, top - 2), mb, PLATFORM)) {

		jumping = false;
		falling = true;
		break;
	    }
	}
    }

    private void collisionEnemy(ArrayList<MovingBlock> movingBlocks, Offset offset) {

	for (int block = 0; block < movingBlocks.size(); block++) {
	    MovingBlock mb = movingBlocks.get(block);

	    // Down
	    if (Collisions.hasCollisionMovingBlock(new Point(leftSide, bottom), mb, ENEMY) ||
		Collisions.hasCollisionMovingBlock(new Point(rightSide, bottom), mb, ENEMY)) {

		score += points;
		movingBlocks.remove(block);
		break;
	    }

	    // Right
	    final int offsetHitEnemy = 40;
	    if (Collisions.hasCollisionMovingBlock(new Point(rightSide + 2, top), mb, ENEMY) ||
		Collisions.hasCollisionMovingBlock(new Point(rightSide + 2, bottom), mb, ENEMY)) {

		health -= 1;
		offset.subxOffset(offsetHitEnemy);
		break;
	    }

	    // Left
	    if (Collisions.hasCollisionMovingBlock(new Point(leftSide, top), mb, ENEMY) ||
		Collisions.hasCollisionMovingBlock(new Point(leftSide, bottom), mb, ENEMY)) {

		health -= 1;
		offset.subxOffset(offsetHitEnemy);
		break;

	    }
	}
    }

    public boolean reachedFinish(Block[][] block) {

	for (final Block[] blocks : block) {
	    for (int col = 0; col < block[0].length; col++) {
	        Block b = blocks[col];

		// Down
		if (Collisions.hasCollision(new Point(leftSide - 2, bottom + 2), b,  FINISH) ||
		    Collisions.hasCollision(new Point(rightSide + 2, bottom + 2), b, FINISH)) {

		    return true;
		}

		// Right
		if (Collisions.hasCollision(new Point(rightSide + 2, top),  b,  FINISH) ||
		    Collisions.hasCollision(new Point(rightSide + 2, bottom),  b,  FINISH)) {

		    return true;

		}
	    }
	}

	return false;
    }

    private void foundCoin(Block[][] block) {

	for (final Block[] blocks : block) {
	    for (int col = 0; col < block[0].length; col++) {
		Block b = blocks[col];

		// Down
		if (Collisions.hasCollision(new Point(leftSide, bottom + 2), b, COIN) ||
		    Collisions.hasCollision(new Point(rightSide, bottom + 2),b, COIN)) {

		    score += 100;
		    blocks[col].setType(EMPTY);
		    break;
		}

		// Right
		if (Collisions.hasCollision(new Point(rightSide + 2, top), b, COIN) ||
		    Collisions.hasCollision(new Point(rightSide + 2, bottom), b, COIN)) {

		    score += 100;
		    blocks[col].setType(EMPTY);
		    break;
		}

		// Left
		if (Collisions.hasCollision(new Point(leftSide - 2, top), b, COIN) ||
		    Collisions.hasCollision(new Point(leftSide - 2, bottom),b, COIN)) {

		    score += 100;
		    blocks[col].setType(EMPTY);
		    break;
		}

		// Up
		if (Collisions.hasCollision(new Point(rightSide, top - 2), b, COIN) ||
		    Collisions.hasCollision(new Point(leftSide, top - 2),b, COIN)) {

		    score += 100;
		    blocks[col].setType(EMPTY);
		    break;
		}
	    }
	}
    }

    public boolean gameOver(Offset offset) {
	return (falling && offset.getyOffset() > MAX_FALL_DEPTH || health == 0);
    }

    public void draw(Graphics g) {
	g.setColor(Color.BLACK);
	g.fillRect((int) x, (int) y, WIDTH, HEIGHT);
    }

    public void keyPressed(int key, Offset offset) {
	if (key == KeyEvent.VK_RIGHT) {
	    direction = 1;
	}
	if (key == KeyEvent.VK_LEFT) {
	    direction = -1;
	}
	if (key == KeyEvent.VK_UP && !jumping && !falling) {
	    jumping = true;
	}
	if (key == KeyEvent.VK_SPACE) {
	    shoot(offset);
	}
    }

    public void keyReleased(int key) {
	if (key == KeyEvent.VK_RIGHT) {
	    direction = 0;
	}
	if (key == KeyEvent.VK_LEFT) {
	    direction = 0;
	}

    }

    public int getScore() {
	return score;
    }

    public int getHealth() {
	return health;
    }

    public Iterable<Projectile> getProjectiles() {
	return projectiles;
    }
}
