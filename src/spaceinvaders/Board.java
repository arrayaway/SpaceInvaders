/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import spaceinvaders.Sprite.Alien;
import spaceinvaders.Sprite.Player;
import spaceinvaders.Sprite.Shot;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.SwingUtilities;
import spaceinvaders.Sprite.Alien.Bomb;

public class Board extends JPanel {

    private Dimension d;
    private List<Alien> aliens;
    private Player player;
    private List<Shot> shots;

    private int direction = -1;
    private int kills = 0;

    private boolean inGame = true;
    private String explImg = "src/images/explosion.png";
    private String message = "Game Over";

    private Timer timer;
    private AudioChannel audioChannel;

    public Board() {
        initBoard();
        gameInit();
    }
    
    private void initBoard() {
        addKeyListener(new TAdapter());
        
        setFocusable(true);
        requestFocusInWindow();
        d = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        setBackground(Color.black);

        timer = new Timer(Commons.DELAY, new GameCycle());
        timer.start();

        audioChannel = new AudioChannel();
        audioChannel.Initialize();
        
        gameInit();
    }


    private void gameInit() {
        shots = new ArrayList<>();
        aliens = new ArrayList<>();

        for (int i = 0; i < Commons.ALIEN_ROWS; i++) {
            for (int j = 0; j < Commons.ALIENS_PER_ROW; j++) {

                Alien alien = new Alien(Commons.ALIEN_INIT_X + Commons.ALIEN_INIT_SPACING_X * j,
                        Commons.ALIEN_INIT_Y + Commons.ALIEN_INIT_SPACING_Y * i);
                aliens.add(alien);
            }
        }

        player = new Player();
    }

    private void drawAliens(Graphics g) {

        for (Alien alien : aliens) {

            if (alien.isVisible()) {

                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {

                alien.die();
            }
        }
    }

    private void drawPlayer(Graphics g) {

        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {

            player.die();
            inGame = false;
        }
    }

    private void drawShots(Graphics g) {

        shots.forEach((s) -> {
            g.drawImage(s.getImage(), s.getX(), s.getY(), this);
        });
    }

    private void drawBombing(Graphics g) {

        for (Alien a : aliens) {

            Alien.Bomb b = a.getBomb();

            if (b != null && !b.isDestroyed()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (inGame) {

            g.drawLine(0, Commons.GROUND,
                    Commons.BOARD_WIDTH, Commons.GROUND);
            
            drawTop(g);
            drawAliens(g);
            drawPlayer(g);
            drawShots(g);
            drawBombing(g);

        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawTop(Graphics g){
        Font font = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics fontMetrics = this.getFontMetrics(font);
        g.setColor(Color.green);
        g.setFont(font);
        g.drawString("Kills: " + kills, 
                (Commons.BOARD_WIDTH - fontMetrics.stringWidth(message)), 20);
    }
    
    
    private void gameOver(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                Commons.BOARD_WIDTH / 2);
        
        drawTop(g);
    }

    private void update() {

        if (kills == Commons.NUMBER_OF_ALIENS_TO_DESTROY) {

            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        // player
        player.act();

        // shots
        Iterator<Shot> shotsItr = shots.iterator();
        while (shotsItr.hasNext())
        {
            Shot s = shotsItr.next();
            int shotX = s.getX();
            int shotY = s.getY();

            for (Alien alien : aliens) {

                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + Commons.ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + Commons.ALIEN_HEIGHT)) {

                        
                        ImageIcon ii = new ImageIcon(explImg);
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        kills++;
                        shotsItr.remove();
                    }
                }
            }

            int y = s.getY();
            y -= Commons.SHOT_SPEED;

            if (y < 0) {
                shotsItr.remove();
            } else {
                s.setY(y);
            }
        }
        

        // aliens

        for (Alien alien : aliens) {

            int x = alien.getX();

            if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && direction != -Commons.ALIEN_SPEED) {

                direction = -Commons.ALIEN_SPEED;

                Iterator<Alien> i1 = aliens.iterator();

                while (i1.hasNext()) {

                    Alien a2 = i1.next();
                    a2.setY(a2.getY() + Commons.GO_DOWN);
                }
            }

            if (x <= Commons.BORDER_LEFT && direction != Commons.ALIEN_SPEED) {

                direction = Commons.ALIEN_SPEED;

                Iterator<Alien> i2 = aliens.iterator();

                while (i2.hasNext()) {

                    Alien a = i2.next();
                    a.setY(a.getY() + Commons.GO_DOWN);
                }
            }
        }

        Iterator<Alien> it = aliens.iterator();

        while (it.hasNext()) {

            Alien alien = it.next();

            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > Commons.GROUND - Commons.ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }

                alien.act(direction);
            }
        }

        // bombs
        Random generator = new Random();

        for (Alien alien : aliens) {

            int shot = generator.nextInt(1500);
            
            if (shot == Commons.CHANCE && alien.isVisible()) {
                System.out.println("Trying to bomb");
                alien.tryDropBomb();
            }

            Bomb bomb = alien.getBomb();
            if (bomb == null) continue;
            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !bomb.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + Commons.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Commons.PLAYER_HEIGHT)) {

                    player.explode();
                    bomb.setDestroyed(true);
                }
            }

            if (!bomb.isDestroyed()) {

                bomb.setY(bomb.getY() + Commons.BOMP_DROP_SPEED);

                if (bomb.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT) {

                    bomb.setDestroyed(true);
                }
            }
        }
    }

    private void doGameCycle() {

        update();
        repaint();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private void ToMainMenu(){
        SpaceInvaders topFrame = (SpaceInvaders) SwingUtilities.getWindowAncestor(this);
        topFrame.GoToMainMenu();
    }
    
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
            
            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {

                if (inGame) {
                    if(shots.size() < 5)
                    {
                         audioChannel.PlayerShoot();
                        shots.add(new Shot(x,y));
                    }
                } else
                {
                    ToMainMenu();
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

            player.keyPressed(e);
        }
    }
}