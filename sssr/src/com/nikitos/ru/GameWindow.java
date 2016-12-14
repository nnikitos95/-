package com.nikitos.ru;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GameWindow extends JFrame{
    private static GameWindow gameWindow;
    private static Image background;
    private static Image gameover;
    private static Image drop;
    private static  float drop_left = 200;
    private static float drop_top = -100;
    private static long last_frame_time;
    private static float drop_ware = 400;
    private static int score =0;

    private static void oneRepaint(Graphics graphics){
        long current_time = System.nanoTime();
        float delta_time = (current_time-last_frame_time)*0.0000000001f;
        last_frame_time = current_time;
        graphics.drawImage(background,0,0,null);
        graphics.drawImage(drop,(int)drop_left,(int)drop_top,null);
        drop_top+=drop_ware*delta_time;
        if (drop_top>gameWindow.getHeight())
       graphics.drawImage(gameover,280,120,null);

    }
    private static class GameField extends JPanel{
        @Override
        protected void paintComponent(Graphics graphics){
           super.paintComponent(graphics);
            oneRepaint(graphics);
            repaint();
        }
    }

    public static void main(String[] args) throws IOException{
        background = ImageIO.read(GameWindow.class.getResourceAsStream("background.png"));
        gameover = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
        drop = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"));

        gameWindow = new GameWindow();
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setLocation(200,100);
        gameWindow.setSize(906,478);
        gameWindow.setResizable(true);
        last_frame_time = System.nanoTime();
        GameField gameField = new GameField();
        gameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float drop_right = drop_left + drop.getWidth(null);
                float drop_bottom = drop_right + drop.getHeight(null);
                boolean is_drop = x >= drop_left && x<= drop_right && y>= drop_top && y <= drop_bottom;
                if(is_drop) {
                    drop_top = -100;
                    drop_left = (int)(Math.random()*(gameField.getWidth()-drop.getWidth(null)));
                    drop_ware += 150;
                    score++;
                    gameWindow.setTitle("Score:"+score +"   Speed:"+drop_ware);
                }

            }
        });
        gameWindow.add(gameField);
        gameWindow.setVisible(true);

    }
}
