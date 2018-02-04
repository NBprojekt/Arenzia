/**
  * 		Arenzia is a 2D platformer game.
  *  	Copyright (C) 2017  Norbert Bartko
  *  	
  *  	
  *     This file is part of Arenzia.
  *
  *   Arenzia is free software: you can redistribute it and/or modify
  *   it under the terms of the GNU General Public License as published by
  *   the Free Software Foundation, either version 3 of the License, or
  *   (at your option) any later version.**
  *
  *   Arenzia is distributed in the hope that it will be useful,
  *   but WITHOUT ANY WARRANTY; without even the implied warranty of
  *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *   GNU General Public License for more details.
  *
  *   You should have received a copy of the GNU General Public License
  *   along with Arenzia.  
  *   If not, see <http://www.gnu.org/licenses/>. 
  */

/** Paketname */
package NBprojekt.Arenzia.Main;

import java.awt.AlphaComposite;
/** Importierete Pakete */ 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame; 
  

public class Game {
		/* Variablendeklaration */
	private static Font version; 
	public static boolean debugConsole;
	public static JFrame game;
	public static boolean gameStarted;
	public static int playerX, playerY, action;  
	public static String sex, facing;
	private static int TimeMin, TimeSec;
	private static int kunais, playerHealth;
	
	@SuppressWarnings("unused")
	private static SystemDetails sys;
		/* Main Methode */ 
	
	public static void main ( String [] args ){ 
		game = new JFrame(" The Arenzia adventures ");
		game.setContentPane( new GamePanel() );  
		game.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		game.setResizable(false);  
		game.pack();  
		game.setVisible(true); 
		
		InputStream imgStream = Game.class.getResourceAsStream("/game/icon.png" );
		BufferedImage ico = null;
		try {
			ico = ImageIO.read(imgStream);
		} catch (IOException e) { 
			System.out.println("Fail to load icon");
			e.printStackTrace();
		} 
		game.setIconImage(ico);
		
		game.addWindowListener(new GameListener()); 
		centerScreen(game); 
		version = new Font("Rockwell", Font.PLAIN, 15);
		debugConsole = gameStarted = false;
		
		playerX = playerY = 0;  
		
		TimeMin = TimeSec = 0;

		sys = new SystemDetails(); 
	}
	
	
		/* Methode um das Spiel zu Zentrieren */
	
	public static void centerScreen( Window window ) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - window.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - window.getHeight()) / 2);
	    window.setLocation(x, y);
	} 
	
	public static void setTime ( int min, int sec) {
		TimeMin = min;
		TimeSec = sec;
	} 
	 
	// update debug values 
	public static void updateDebug ( int x, int y , int currentAction, boolean male, boolean facingRight,
									 int amo, int health ) {
		playerHealth = health;
		kunais = amo;
		playerX = x;
		playerY = y;
		action = currentAction ;
		if ( male )
			sex = "male";
		else
			sex = "female";
		if ( facingRight )
			facing = "right";
		else
			facing = "left";
	}
	
	// Display some player attributes
	public static void drawDebug( Graphics2D graphics) {
		// Grey, transparent, rect to read the debug values better
		graphics.setColor( Color.LIGHT_GRAY ); 
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.56f));
		graphics.fillRect( 0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2);
		
		// Deactivate transparent again
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		graphics.setColor( Color.WHITE );
		graphics.setFont(new Font("Rockwell", Font.PLAIN, 15)); 
		
		if ( gameStarted ) {
			graphics.drawString("Position X : " + playerX, 30, 90);
			graphics.drawString("Position Y : " + playerY, 30, 120); 
			String string = "";
            switch ( action ) { 
                case  0 : string = "Idle";
                    break;
                case  1 : string = "KatanaAttack";
                    break;
                case  2 : string = "Climb";
                    break;
                case  3 : string = "Dead";
                    break;
                case  4 : string = "Gliding";
                    break;
                case  5 : string = "Jump";
                    break;
                case  6 : string = "JumpKatana";
                    break;
                case  7 : string = "JumpKunai";
                    break;
                case  8 : string = "Walk";
                    break;
                case  9 : string = "Slide";
                    break;
                case 10 : string = "KunaiAttack";
                    break;
                case 11 : string = "Fall";
            } 
			graphics.drawString("Current Action : " + string, 30, 150 );
			graphics.drawString("Gender : " + sex, 30, 180 );
			graphics.drawString("Direction : " + facing, 30, 210 );
			graphics.drawString("Playtime : " + TimeMin + " : " + TimeSec, 30, 240);
			graphics.drawString("Health : " + playerHealth, 280, 60);
			graphics.drawString("Kunais : " + kunais, 280, 90);
		}   		
		// Set collision box color 
			graphics.setColor( Color.ORANGE );
	}
	
	// Display arenzia version
	public static void drawVerison ( Graphics2D graphics ){
		graphics.setFont(version);
		graphics.setColor(Color.DARK_GRAY);
		graphics.drawString("version 1-0-0", 13, GamePanel.HEIGHT -20); 
		
		// And also draw the current fps 
		graphics.drawString("FPS : " + GamePanel.getFPS(), 13, GamePanel.HEIGHT -35); 
	}
		
	// Centere a string 	
	public static void drawCenteredString( String string, int width, int height, Graphics graphics, Font font ) {
		graphics.setFont(font);
	    FontMetrics fm = graphics.getFontMetrics();
	    int x = (width - fm.stringWidth(string)) / 2;
	    int y = height;
	    graphics.drawString(string, x, y);
	} 
} // End of Game