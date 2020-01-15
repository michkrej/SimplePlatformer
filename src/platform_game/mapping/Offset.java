package platform_game.mapping;

public class Offset
{

    private static final double START_Y_OFFSET = 600.0;

    // Måste göra OFFSET till en lista -> [x-offset, y-offset]
    // Skapa en Offset i GameState som man skickar in i update, den måste sedan skickas in i alla updates om är under den, även om de inte använder Offseten
    // TACK NYLANDER

       /**
        * The x offset
        */
       public double xOffset;
       /**
        * The y offset
        */
       public double yOffset;

    public Offset() {
        super();
        this.xOffset = 0.0;
        this.yOffset = START_Y_OFFSET;
    }

    public void resetOffset() {
        xOffset = 0.0;
        yOffset = START_Y_OFFSET;
    }

    public void addxOffset(double num) {
        xOffset += num;
    }

    public void addyOffset(double num) {
        yOffset += num;
    }

    public void subxOffset(double num) {
        xOffset -= num;
    }

    public void subyOffset(double num) {
        yOffset -= num;
    }

    public double getxOffset() {
	return xOffset;
    }

    public double getyOffset() {
	return yOffset;
    }
}
