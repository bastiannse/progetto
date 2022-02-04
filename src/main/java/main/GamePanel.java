package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import bricks.Brick;
import bricks.LargePaddle;
import bricks.Modifier;
import entity.Ball;
import entity.Paddle;

public class GamePanel extends JPanel {

    public final static int GRIDSIZE = 75;
    public final static int OFFSET = 40;
    public static int NUM_COLS = 8;
    public static int NUM_ROWS = 3;
    //campi
    private BufferedImage img;
    private Graphics2D g2;
    private boolean running;
    private Paddle thePaddle;
    private Ball theBall;
    //sfondo immagine
    private Brick[][] bricks;
    private int score;
    //paddle immagine
    private BufferedImage paddleImg;
    private BufferedImage sfondoImg;
    private boolean playing = true;
    private boolean win = false;

    public GamePanel() {

        init();

        this.addKeyListener(new KeyListener() {


            public void keyTyped(KeyEvent e) {


            }


            public void keyPressed(KeyEvent e) {


                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!running) {

                        init();

                    }


                }

            }


            public void keyReleased(KeyEvent e) {
            }


        });

        this.addMouseMotionListener(new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {

            }

            public void mouseMoved(MouseEvent e) {
                thePaddle.mouseMoved(e.getX());
            }

        });

        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

        });

    }

    public void init() {
        //loadResources();
        this.img = new BufferedImage(GameWindow.WIDTH, GameWindow.HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.g2 = (Graphics2D) img.getGraphics();
        this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.g2.setFont(new Font("Courier New", Font.BOLD, 20));
        this.running = true;
        this.thePaddle = new Paddle(paddleImg);
        this.theBall = new Ball();
        this.bricks = new Brick[NUM_ROWS][NUM_COLS];
        generateBricks();
        this.setFocusable(true);
        this.requestFocus();
        this.score = 0;

    }

   /* public void loadResources() {

        try {
            paddleImg = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("paddle.png")));
            sfondoImg = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("brick.jpg")));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    //update basato sul tempo
    public void update() {

        this.thePaddle.update();
        this.theBall.update();
        checkCollisions();
        this.theBall.setPosition();
        updateBricks();
        isGameOver();
    }

    public void updateBricks() {
        for (Brick[] row : bricks) {
            for (Brick b : row) {
                b.update();
            }
        }
    }


    // crea immagini in memoria
    public void draw() {
        this.g2.setColor(Color.BLACK);
        //this.g2.fillRect(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);
        this.g2.drawImage(sfondoImg, 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, null);
        //bordi
        this.g2.setColor(Color.RED);
        this.g2.fillRect(0, 0, 2, GameWindow.HEIGHT);
        this.g2.fillRect(0, 0, GameWindow.WIDTH, 2);
        this.g2.fillRect(GameWindow.HEIGHT + 182, 0, 2, GameWindow.WIDTH);
        this.g2.setColor(Color.RED);
        this.g2.drawString("Score: " + score, 20, 20);

        this.thePaddle.draw(g2);
        this.theBall.draw(g2);
        drawBricks();

        if (this.win) {

            drawWinner();
            this.running = false;

        }

        if (this.theBall.youLose()) {
            drawLoser();
            this.running = false;
        }
    }

    public void drawWinner() {

        this.g2.setColor(Color.RED);
        this.g2.setFont(new Font("Courier new", Font.BOLD, 25));
        this.g2.drawString("You win! , Score : " + score, 180, 200);

    }


    public void drawLoser() {

        this.g2.setColor(Color.RED);
        this.g2.setFont(new Font("Courier new", Font.BOLD, 30));
        this.g2.drawString("Game Over! , Score : " + score, 150, 200);
        this.g2.drawString("Premi invio per riavviare!", 100, 240);
    }


    //gameLOOP
    public void playGame() {

        while (this.playing) {

            if (this.running) {
                update();

                draw();

                repaint();
            }
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //mette immagini sulla finestra
    public void paintComponent(Graphics g) {

        g.drawImage(this.img, 0, 0, null);

    }

    public void drawBricks() {
        // tipo, nome temporale, su quale array andare
        for (Brick[] row : this.bricks) {
            for (Brick b : row) {
                if (b.getIsActive()) {
                    b.draw(this.g2);
                }
            }
        }
    }

    public void generateBricks() {

        for (int row = 0; row < this.bricks.length; row++) {
            for (int col = 0; col < this.bricks[row].length; col++) {
                int rand = (int) (Math.random() * 5);
                if (rand == 0) {
                    this.bricks[row][col] = new LargePaddle(col * GRIDSIZE + OFFSET, row * GRIDSIZE + OFFSET);
                } else {
                    this.bricks[row][col] = new Brick(col * GRIDSIZE + OFFSET, row * GRIDSIZE + OFFSET);
                }
            }
        }
    }


    public void checkCollisions() {
        this.theBall.checkCollisions(this.thePaddle.getRect());

        for (Brick[] row : this.bricks) {
            for (Brick b : row) {
                if (b.getIsActive()) {
                    if (this.theBall.checkCollisions(b.getRect())) {
                        this.score += 10;
                        if (b instanceof Modifier) {
                            this.score += 40;
                            ((Modifier) b).setIsFalling(true);
                        } else {
                            b.gotHit();
                        }
                        if (!b.getIsActive()) {
                            this.score += 100;
                        }
                    }
                }
                if (b.getRect().intersects(this.thePaddle.getRect())) {
                    b.setIsActive(false);
                    this.thePaddle.modify((Modifier) b);
                } else if (b.getPosition() > 430) {
                    ((Modifier) b).setIsActive(false);
                }
            }
        }
    }

    public void isGameOver() {
        this.win = true;
        for (Brick[] row : this.bricks) {
            for (Brick b : row) {
                if (b.getIsActive()) {
                    this.win = false;
                }
            }
        }
    }

}


