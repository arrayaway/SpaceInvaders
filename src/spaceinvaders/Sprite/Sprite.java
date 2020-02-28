/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.Sprite;

import java.awt.Image;
import javax.swing.ImageIcon;
import spaceinvaders.Commons;

public class Sprite {

    private boolean visible;
    private Image image;
    private boolean dying;
    
    int x;
    int y;
    int dx;

    public Sprite() {

        visible = true;
    }

    public void die() {

        visible = false;
    }

    public boolean isVisible() {

        return visible;
    }

    protected void setVisible(boolean visible) {

        this.visible = visible;
    }

    public void setImage(Image image) {

        this.image = image;
    }

    public Image getImage() {

        return image;
    }

    public Image scaleImage(String imageSource, int newWidth, int newHeight) {
        ImageIcon imageIcon = new ImageIcon(imageSource);
        Image baseImage = imageIcon.getImage();
        Image scaledImage = baseImage.getScaledInstance(newWidth, newHeight,  
                java.awt.Image.SCALE_SMOOTH); 
        return scaledImage;
    }
    
    public void setX(int x) {

        this.x = x;
    }

    public void setY(int y) {

        this.y = y;
    }

    public int getY() {

        return y;
    }

    public int getX() {

        return x;
    }

    public void setDying(boolean dying) {

        this.dying = dying;
    }

    public boolean isDying() {

        return this.dying;
    }
}