/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

/**
 *
 * @author stste
 */
public class AudioChannel {
    private AudioThread thread;
    
    public void Initialize(){
        thread = new AudioThread();
        thread.start();
    }
    
    public void PlayerShoot(){
        thread.ShootSound();
    }
}
