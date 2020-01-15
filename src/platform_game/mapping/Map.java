package platform_game.mapping;

import platform_game.objects.Block;
import platform_game.objects.BlockType;
import platform_game.objects.MovingBlock;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Class that reads in the map from a text file. We read whole rows before moving on to the next one.
 */
public class Map
{
    private String filePath;

    private Block[][] blocks = null;
    private ArrayList<MovingBlock> movingBlocks = null;

    public Map(String path) {
	this.filePath = path;
	load();
    }

    private void load() {

	// The two lines underneath is borrowedcode from Mariusz Wrozek (ONLY TWO LINES)
	try (InputStream inputStream = Map.class.getResourceAsStream(filePath)) {
	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

		// reads static blocks
	        final int mapWidth = Integer.parseInt(reader.readLine());
		final int mapHeight = Integer.parseInt(reader.readLine());

		blocks = new Block[mapWidth][mapHeight];

		for (int row = 0; row < mapHeight; row++) {
		    String[] splitter = reader.readLine().split(" ");
		    for (int col = 0; col < mapWidth; col++) {
			blocks[col][row] = new Block(col * Block.BLOCK_SIZE,
						     row * Block.BLOCK_SIZE,
						     BlockType.getName(Integer.parseInt(splitter[col])));
		    }
		}

		//reads moving blocks
		reader.readLine(); //reads empty line
		int movingBlocksLength = Integer.parseInt(reader.readLine());
		movingBlocks = new ArrayList<>();

		for (int i = 0; i < movingBlocksLength; i++) {
		    String[] splitter = reader.readLine().split(" ");

		    movingBlocks.add(new MovingBlock(Integer.parseInt(splitter[0]) * Block.BLOCK_SIZE,
						     Integer.parseInt(splitter[1]) * Block.BLOCK_SIZE,
						     BlockType.getName(Integer.parseInt(splitter[2])),
						     Integer.parseInt(splitter[3])));
		}
	    } catch (IOException | NumberFormatException e) {
		System.out.println("Couldn't read the map from " + filePath );
		System.exit(1);
		e.printStackTrace();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void update() {
	for (MovingBlock movingBlock : movingBlocks) {
	    movingBlock.update();
	}
    }

    public void draw(Graphics g, Offset offset) {

	for (int row = 0; row < blocks[0].length; row++) {
	    for (final Block[] block : blocks) {
		block[row].draw(g, offset);
	    }
	}

	for (MovingBlock movingBlock : movingBlocks) {
	    movingBlock.draw(g, offset);
	}

    }

    public Block[][] getBlocks() {
	return blocks;
    }

    public ArrayList<MovingBlock> getMovingBlocks() {
	return movingBlocks;
    }
}

