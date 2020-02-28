/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author stste
 */
public class AudioThread extends Thread
{
    @Override
    public void run(){
        try
        { 
            // Displaying the thread that is running 
            System.out.println ("Thread " + 
                  Thread.currentThread().getId() + 
                  " is running"); 
  
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Exception is caught"); 
        } 
    }
    
    public void ShootSound(){
        InputStream sound;
        String filepath = "src/sounds/playerLaser.wav";
        try 
        {
            sound = new FileInputStream(new File(filepath));
            AudioStream audios = new AudioStream(sound);
            AudioPlayer.player.start(audios);
        }
        catch (Exception e)
        {
            System.out.println("Error playing player shot sound.");
        }
    }
}
