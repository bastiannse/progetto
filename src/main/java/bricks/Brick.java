package bricks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Brick {
    //campi
    protected int x, y, width, height, strength;
    protected boolean isActive;
    protected Color colore;

    public Brick(int x, int y) {

        this.x = x;
        this.y = y;
        width = 45;
        height = 35;
        strength = (int) (Math.random() * 3);
        isActive = true;
        colore = new Color(0, 255 - strength * 100, 255 - strength * 100);

    }

    public void update() {

    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public void gotHit() {

        strength--;
        if (strength < 0) {
            isActive = false;
        } else {
            colore = new Color(0, 255 - strength * 100, 255 - strength * 100);
        }


    }

    public Rectangle getRect() {

        return new Rectangle(x, y, width, height);

    }

    public void draw(Graphics2D g2) {

        g2.setColor(colore);
        g2.fillRoundRect(x, y, width, height, 30, 20);

    }

    public int getPosition() {

        return this.y;
    }
}


