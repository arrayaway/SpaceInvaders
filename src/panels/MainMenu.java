/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import spaceinvaders.Commons;
import spaceinvaders.SpaceInvaders;

/**
 *
 * @author stste
 */
public class MainMenu extends JPanel {
    private Dimension dimension;
    private Timer timer;
    private int TextX;
    private int TextY;
    private MenuItem activeOption;
    
    public MainMenu(){
        initMenu();
    }
    
    
    private void initMenu(){
        activeOption = MenuItem.Start;
        System.out.println("create adater");
        addKeyListener(new MenuAdapter());
        
        setFocusable(true);
        dimension = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        setBackground(Color.black);
        
        timer = new Timer(Commons.DELAY, new MenuCycle());
        timer.start();
        
        TextX = 50;
    }
    
    private void playGame(){
        SpaceInvaders topFrame = (SpaceInvaders) SwingUtilities.getWindowAncestor(this);
        topFrame.GoToGame();
    }
    
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        doDrawing(graphics);
    }

    private void doDrawing(Graphics graphics) {
        drawStartButton(graphics);
        drawLeaderboardButton(graphics);
        drawOptionsButton(graphics);
        drawQuitButton(graphics);
    }
    
    private Graphics ToBlueText(Graphics graphics)
    {
        Font font = new Font("Helvetica", Font.BOLD, 20);
        graphics.setColor(Color.blue);
        graphics.setFont(font);
        return graphics;
    }
    
    private void drawStartButton(Graphics graphics){
        String message = "Start Game";
        if (activeOption == MenuItem.Start) 
            DrawActiveButtonBorder(graphics, TextX, 300);
        ToBlueText(graphics).drawString(message, TextX, 300);
    }
    
    private void drawLeaderboardButton(Graphics graphics){
        String message = "Leaderboard";
        if (activeOption == MenuItem.Leaderboard) 
            DrawActiveButtonBorder(graphics, TextX, 325);
        ToBlueText(graphics).drawString(message, TextX, 325);
    }

    private void drawOptionsButton(Graphics graphics){
        String message = "Options";
        if (activeOption == MenuItem.Options) 
            DrawActiveButtonBorder(graphics, TextX, 350);
        ToBlueText(graphics).drawString(message, TextX, 350);
    }
    
    private void drawQuitButton(Graphics graphics){
        String message = "Quit Game";
        if (activeOption == MenuItem.Quit) 
            DrawActiveButtonBorder(graphics, TextX, 375);
        ToBlueText(graphics).drawString(message, TextX, 375);
    }
    
    private void DrawActiveButtonBorder(Graphics graphics, int x, int y) {
        graphics.setColor(new Color(0, 32, 48));
        graphics.fillRect(x - 5, y - 25, 200, 30);
        graphics.setColor(Color.white);
        graphics.drawRect(x - 5, y - 25, 200, 30);
    }
    
    private void doMenuCycle() {
        repaint();
    }

    private class MenuCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doMenuCycle();
        }
    }
    
    private enum MenuItem
    {
        Start,
        Leaderboard,
        Options,
        Quit;
    }
    
    private class MenuAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            switch (key) {
                case KeyEvent.VK_UP:
                    
                    switch (activeOption) {
                        case Leaderboard: activeOption = MenuItem.Start; break;
                        case Options: activeOption = MenuItem.Leaderboard; break;
                        case Quit: activeOption = MenuItem.Options; break;
                    }   break;
                case KeyEvent.VK_DOWN:
                    switch (activeOption) {
                        case Start: activeOption = MenuItem.Leaderboard; break;
                        case Leaderboard: activeOption = MenuItem.Options; break;
                        case Options: activeOption = MenuItem.Quit; break;
                    }   break;
                case KeyEvent.VK_ENTER:
                    switch (activeOption) {
                        case Start: playGame(); break;
                        case Leaderboard: activeOption = MenuItem.Options; break;
                        case Options: activeOption = MenuItem.Quit; break;
                        case Quit: System.exit(1); break;
                    }   break;
                default:
                    break;
            }
        }
    }
}
