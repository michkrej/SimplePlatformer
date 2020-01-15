package platform_game.objects;

import platform_game.mapping.Background;
import platform_game.mapping.Offset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

import static platform_game.game_state.MenuState.STANDARD_FONT;

/**
 * Class for the color and size of blocks.
 */
// the "free" only numbers are used to create a color
public class Block extends Rectangle
{
    /**
     * Size of blocks
     */
    public static final int BLOCK_SIZE = 50;
    private BlockType type;

    /**
     * Color of platforms
     */
    public static final Color PLATFORM_COLOR = new Color(42, 194, 121);
    

    public Block(int x, int y, BlockType type) {
	setBounds(x, y, BLOCK_SIZE, BLOCK_SIZE);
	this.type = type;

    }

    public void draw(Graphics g, Offset offset) {
	g.setColor(PLATFORM_COLOR);
	if (type == BlockType.PLATFORM) {
	    g.fillRect(x - (int) offset.getxOffset(), y - (int) offset.getyOffset(), width, height);
	}
	if (type == BlockType.COIN) {

	    g.setColor(Color.YELLOW);
	    g.fillOval(x - (int) offset.getxOffset(), y - (int) offset.getyOffset(), width, height);
	}
	if (type == BlockType.FINISH) {
	    g.setFont(STANDARD_FONT);
	    g.setColor(Color.WHITE);
	    g.drawString("FINISH", x - (int) offset.getxOffset(), y - (int) offset.getyOffset());
	}
    }

    public BlockType getType() {
	return type;
    }

    public void setType(final BlockType type) {
	this.type = type;
    }


}
