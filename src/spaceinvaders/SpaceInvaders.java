package spaceinvaders;

import java.awt.EventQueue;
import javax.swing.JFrame;
import panels.MainMenu;

public class SpaceInvaders extends JFrame  {

    public SpaceInvaders() {
        GoToMainMenu();
    }

    public void GoToMainMenu(){
        EventQueue.invokeLater(() -> {
            getContentPane().removeAll();
            getContentPane().invalidate();
            
            MainMenu panel = new MainMenu();
            add(panel);
            setTitle("Space Invaders");

            setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);
            setLocationRelativeTo(null);
            validate();
            setVisible(true);
            
            panel.requestFocus();
        });
    }
    
    public void GoToGame(){
        EventQueue.invokeLater(() -> {
            getContentPane().removeAll();
            getContentPane().invalidate();
            
            Board panel = new Board();
            add(panel);
            setTitle("Space Invaders");

            setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);
            setLocationRelativeTo(null);
            validate();
            setVisible(true);
            
            panel.requestFocus();
        });
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SpaceInvaders ex = new SpaceInvaders();
            ex.setVisible(true);
        });
    }
}