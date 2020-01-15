package platform_game.mapping;

import platform_game.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Class to load in the background.
 */
public class Background
{

    private BufferedImage image = null;

    public Background(String path) {
	load(path);
    }

    private void load(String path) {
	try {
	    image = ImageIO.read(Background.class.getResourceAsStream(path));
	} catch (Exception e) {
	    System.out.println("The backgrund could not be loaded, make sure that the file " + path + " exists");
	    System.exit(1);
	    e.printStackTrace();
	}
    }

    public void draw(Graphics g) {
	g.drawImage(image, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);

    }
}
