/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.Sprite;

import interfaces.IExplode;
import spaceinvaders.Commons;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
 

public class Player extends Sprite implements IExplode {
    public Player() {

        initPlayer();
    }

    private void initPlayer() {
        setImage(scaleImage("src/images/player.png", Commons.PLAYER_WIDTH, 
                Commons.PLAYER_HEIGHT));

        setX(Commons.PLAYER_START_X);
        setY(Commons.PLAYER_START_Y);
    }

    public void act() {

        x += dx;

        if (x <= Commons.PLAYER_WIDTH) {

            x = Commons.PLAYER_WIDTH;
        }

        if (x >= Commons.BOARD_WIDTH - Commons.PLAYER_WIDTH) {

            x = Commons.BOARD_WIDTH - (2*Commons.PLAYER_WIDTH);
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -Commons.PLAYER_MOVEMENT_SPEED;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = Commons.PLAYER_MOVEMENT_SPEED;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }
    }

    @Override
    public void explode() {
        setImage(scaleImage("src/images/explosion.png", Commons.PLAYER_WIDTH, 
                     Commons.PLAYER_HEIGHT));
        setDying(true);
    }
}