
package spaceinvaders.Sprite;

import interfaces.IExplode;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import spaceinvaders.Commons;

public class Alien extends Sprite implements IExplode {

    private Bomb bomb;

    public Alien(int x, int y) {
        initAlien(x, y);
    }

    private void initAlien(int x, int y) {

        this.x = x;
        this.y = y;
        
        setImage(scaleImage("src/images/alien.png", Commons.ALIEN_WIDTH, 
                Commons.ALIEN_HEIGHT));
    }

    public void act(int direction) {

        this.x += direction;
    }

    public Bomb getBomb() {

        return bomb;
    }

    public void tryDropBomb(){
        if (bomb != null && !bomb.isDestroyed()) return;
        
        bomb = new Bomb(x, y);
    }
    
    @Override
    public void explode() {
        this.setImage(scaleImage("src/images/explosion.png", Commons.ALIEN_WIDTH, 
                Commons.ALIEN_HEIGHT));
        setDying(true);
    }
    
    public class Bomb extends Sprite {

        private boolean destroyed = false;

        public Bomb(int x, int y) {
            initBomb(x, y);
            
        }

        private void initBomb(int x, int y) {

            setDestroyed(false);

            this.x = x;
            this.y = y;
            
            //setImage(scaleImage("src/images/bomb.png", Commons.BOMB_WIDTH, Commons.BOMB_HEIGHT));
            
            setBufferedImage();
        }

        private void setBufferedImage(){
            BufferedImage bufferedImg = null;
            try {
                // Here set the path to your image
                bufferedImg = ImageIO.read(new File("src/images/bomb.png"));
            } catch (IOException e) {}

            setImage(bufferedImg.getScaledInstance(Commons.BOMB_WIDTH, Commons.BOMB_HEIGHT, 100));
        }
        
        public void setDestroyed(boolean destroyed) {

            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {

            return destroyed;
        }
    }
}