package bricks;

public class Modifier extends Brick {

    protected boolean isFalling;
    protected double dy;

    public Modifier(int x, int y) {

        super(x, y);
        dy = (Math.random() * 4 + 1);
        isFalling = false;
    }

    public void setIsFalling(boolean falling) {

        isFalling = falling;

    }

    public void update() {

        if (isFalling) {
            y += dy;
        }

    }

}
