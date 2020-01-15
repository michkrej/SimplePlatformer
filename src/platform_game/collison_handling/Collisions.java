package platform_game.collison_handling;

import platform_game.objects.Block;
import platform_game.objects.BlockType;
import platform_game.objects.MovingBlock;

import java.awt.*;

/**
 * Utility class that checks if a block in the game window contains a certain point.
 */

// Doesn't need an constructor, we never make an object out of it.
public final class Collisions
{
    private Collisions() {}

    // Using point since the blocks extend Rectangle which is a part of the Graphics library.
    //borrowedcode, that we could use Point
    public static boolean hasCollision(Point p, Block b, BlockType type) {
	return b.contains(p) && b.getType() == type;
    }

    public static boolean hasCollisionMovingBlock(Point p, MovingBlock b, BlockType type) {
	return b.contains(p) && b.getType() == type;
    }

}
