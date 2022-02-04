package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import bricks.Modifier;

//lista di metodi per le entit� che il nostro gioco user�
public interface GameEntity {

    void update();

    void draw(Graphics2D g2);

    Rectangle getRect();

    void modify(Modifier m);

}
