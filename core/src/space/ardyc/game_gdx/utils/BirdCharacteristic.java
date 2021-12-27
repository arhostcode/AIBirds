package space.ardyc.game_gdx.utils;

public class BirdCharacteristic {

    private static final Point DEFAULT_POSITION = new Point(50, 255);
    private static final int DEFAULT_WIDTH = 75;
    private static final int DEFAULT_HEIGHT = 45;

    private Point startPosition;
    private int width;
    private int height;

    public BirdCharacteristic(Point startPosition, int width, int height) {
        this.startPosition = startPosition;
        this.height = height;
        this.width = width;
    }

    public static BirdCharacteristic createDefaultCharacteristic() {
        return new BirdCharacteristic(DEFAULT_POSITION, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Point getStartPosition() {
        return startPosition;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
