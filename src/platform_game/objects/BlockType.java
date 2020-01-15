package platform_game.objects;

/**
 * Enum types for blocks. We assign each number in the MAP.txt file to a blockType to make things easier to read.
 */
public enum BlockType
{

    /**
     * The enum type for empty blocks
     */
    EMPTY,

    /**
     * The enum type for platforms
     */
    PLATFORM,

    /**
     * The enum type for enemies
     */
    ENEMY,

    /**
     * The enum type for projectiles
     */
    PROJECTILE,

    /**
     * The enum type for the finish
     */
    FINISH,

    /**
     * The enum type for coins
     */
    COIN;

    private BlockType() {
    }

    public static BlockType getName(int n) {
	BlockType selected = null;
	switch (n) {
	    case 0:
		selected = EMPTY;
		break;
	    case 1:
		selected = PLATFORM;
		break;
	    case 2:
		selected = ENEMY;
		break;
	    case 3:
		selected = PROJECTILE;
		break;
	    case 4:
		selected = FINISH;
		break;
	    case 5:
		selected = COIN;
	}
	return selected;
    }


}
