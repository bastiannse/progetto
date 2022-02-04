package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import bricks.Modifier;
import main.GameWindow;

public class Ball implements GameEntity {
//campi

    public static final int SIZE = 15;
    public double x, y, dx, dy; //dx , dy velocit� di direzione x e y.
    //collisioni
    public double xdest, ydest, xtemp, ytemp;
    public boolean topLeft, topRight, bottomRight, bottomLeft;
    private Color colore;


    public Ball() {

        x = GameWindow.WIDTH / 2 + 20;
        y = GameWindow.HEIGHT / 2 + 20;
        //velocit�.
        dx = 10;
        dy = 10;
        colore = Color.WHITE;

        xdest = x;
        ydest = y;
        ytemp = y;
        xtemp = x;

        topLeft = topRight = bottomLeft = bottomRight = false;

    }


    public void update() {

        if (x < 0) {
            dx = -dx;
        }
        if (y < 0) {
            dy = -dy;
        }
        if (x > GameWindow.WIDTH - SIZE - 20) {
            dx = -dx;
        }
        if (y > GameWindow.HEIGHT - SIZE - 45) {
            dy = -dy;
        }
    }

    public void calculateCorners(double xpos, double ypos, Rectangle r) {

        Point tl = new Point((int) xpos, (int) ypos);
        Point tr = new Point((int) xpos + SIZE, (int) ypos);
        Point bl = new Point((int) xpos, (int) ypos + SIZE);
        Point br = new Point((int) xpos + SIZE, (int) ypos + SIZE);

        topLeft = topRight = bottomLeft = bottomRight = false;

        topLeft = r.contains(tl);
        topRight = r.contains(tr);
        bottomLeft = r.contains(bl);
        bottomRight = r.contains(br);

    }

    public boolean checkCollisions(Rectangle r) {

        boolean collisionDetected = false;

        xdest = x + dx;
        ydest = y + dy;

        xtemp = x;
        ytemp = y;

        //collisioni orizzontali.
        calculateCorners(xdest, y, r);
        //la palla sta andando verso destra
        if (dx > 0) {
            if (topRight || bottomRight) {
                dx = -dx;
                collisionDetected = true;
                xtemp = r.getMinX() - SIZE - 1;
            }
        }

        if (dx < 0) {
            if (topLeft || bottomLeft) {
                dx = -dx;
                collisionDetected = true;
                xtemp = r.getMaxX() + 1;
            }
        }

        calculateCorners(x, ydest, r);

        if (dy > 0) {
            if (bottomRight || bottomLeft) {
                dy = -dy;
                collisionDetected = true;
                ytemp = r.getMinY() - SIZE - 1;
            }
        }

        if (dy < 0) {
            if (topRight || topLeft) {
                dy = -dy;
                collisionDetected = true;
                ytemp = r.getMaxY() + 1;
            }
        }

        return collisionDetected;
    }

    public void setPosition() {

        xtemp += dx;
        ytemp += dy;
        x = xtemp;
        y = ytemp;

    }

    public Rectangle getRect() {
        return new Rectangle((int) x, (int) y, SIZE, SIZE);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(colore);
        g2.fillOval((int) x, (int) y, SIZE, SIZE);
    }

    public boolean youLose() {

        boolean loser = false;

        if (y > 430 - SIZE) {
            loser = true;
        }

        return loser;
    }


    public void modify(Modifier m) {

    }

}
