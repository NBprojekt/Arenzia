/**
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
package NBprojekt.Arenzia.GameState.Map;
 
/** Importierete Pakete */  
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import NBprojekt.Arenzia.GameState.GameState;
import NBprojekt.Arenzia.GameState.GameStateManager;
import NBprojekt.Arenzia.Main.Crypt;
import NBprojekt.Arenzia.Main.Game;
import NBprojekt.Arenzia.Main.GamePanel;
import NBprojekt.Arenzia.Object.Collectable;
import NBprojekt.Arenzia.Object.Enemy;
import NBprojekt.Arenzia.Object.Ninja;
import NBprojekt.Arenzia.Object.Teleport;
import NBprojekt.Arenzia.Object.Enemies.Slime;
import NBprojekt.Arenzia.Object.Enemies.Water;
import NBprojekt.Arenzia.TileMap.Background;
import NBprojekt.Arenzia.TileMap.Map; 


public class Graveyard_01 extends GameState{ 
		// Map
	private Map map;
	private Background background;
	private HUD hud;
	private boolean exit;   
	
		// Entities
	private Ninja ninja; 
	private ArrayList < Enemy > enemies; 
	private ArrayList < Collectable > collectable;
	private Teleport teleport;

	public Graveyard_01 (GameStateManager gameStateManager){
		this.gameStateManager = gameStateManager; 
		init();
	}

	// Initialisierungs-Methode
	@Override
	public void init() { 
			// Map and backgound 
		map = new Map(100); 
			// Map Files
		map.loadTiles("/map/graveyard/graveyardTiles.gif"); 
		map.loadMap("/map/graveyard/Graveyard_01.map"); 
		map.setPosition( 0, 0 );  
			// Backgroundfile
		background = new Background("/map/graveyard/graveyardBackground.gif", 0.0);  
		exit = false;
		 
			// Player 
		ninja = new Ninja ( map, false);
		ninja.setPosition( 600, 500);  

			// HUD  
		hud = new HUD ( ninja ); 
		
			// Enemies
		enemies  = new ArrayList < Enemy >();
			// Collectable
		collectable = new ArrayList < Collectable >();
			// Teleport
		teleport = new Teleport(map);
		teleport.setPosition(23900, 698);
		 
		// Place enemies
		Point [] slimes = new Point []{
				new Point(1600,300), new Point(6500,600),
				new Point(7800,500), new Point(14100,500),
				new Point(10700,400), new Point(14000,1900),
				new Point(13000,1900), new Point(9900,2400),
				new Point(2600,3100), new Point(17800,4000),
				new Point(10100,4200) 
		};
		Point [] water = new Point []{
				new Point(3500,1000), new Point(3700,1000),
				new Point(7900,600), new Point(8000,600),
				new Point(8100,600), new Point(8200,600),
				new Point(10300,2500), new Point(10200,2500),
				new Point(10100,2500), new Point(4400,2500),
				new Point(4500,2500), new Point(4600,2500),
				new Point(4700,2500), new Point(6900,4500),
				new Point(7000,4500), new Point(7100,4500),
				new Point(15400,4200), new Point(15500,4200),
				new Point(15600,4200), new Point(3600,1000)
		};
		for ( int i = 0 ; i < slimes.length; i++) {
			Slime slime;
			if (i% 2 == 0) 
				slime = new Slime(map, true);
			else 
				slime = new Slime(map, false);
			slime.setPosition(slimes[i].getX(), slimes[i].getY());
			enemies.add(slime);
		}   
		for (int i = 0; i < water.length; i++) {
			Water w = new Water(map);
			w.setPosition(water[i].getX() + 51, water[i].getY() + 51);
			enemies.add(w);
		}
		
		System.out.println(""+ Crypt.randomString(15));
		HUD.Coin1 = HUD.Coin2 = HUD.Coin3 = HUD.Coin4 = HUD.Coin5 = HUD.Coin6 = true;
	}
	
	
		/* Methoden fuer das Tastatur mitlesen */

	@SuppressWarnings("static-access")
	@Override
	public void keyPressed(int key) { 
		
		if ( key == KeyEvent.VK_F3){
			if (Game.debugConsole)
				Game.debugConsole = false;
			else 
				Game.debugConsole = true;
		}  
			// Ninja
		// Basic movement
		if ( key == KeyEvent.VK_LEFT ) 
			ninja.setLeft(true);
		if ( key == KeyEvent.VK_RIGHT )
			ninja.setRight(true); 
		if ( key == KeyEvent.VK_UP ) 
			ninja.setClimbing(true); 
		if ( key == KeyEvent.VK_DOWN ) 
			ninja.setSliding(true); 
		
		// Jump Stuf
		if ( key == KeyEvent.VK_SPACE ) 
			ninja.setJumping(true);
		if ( key == KeyEvent.VK_SHIFT )
			ninja.setGliding(true);
		
		// Attacks
		if ( key == KeyEvent.VK_Q )
			ninja.setKatana();
		if ( key == KeyEvent.VK_E )
			ninja.setThrowing(); 
		
		// Escape
		if ( key == KeyEvent.VK_ESCAPE )
			gameStateManager.setState( gameStateManager.MENU );
	}

	@Override
	public void keyReleased(int key) {  
		
			// Ninja
		if ( key == KeyEvent.VK_LEFT ) 
			ninja.setLeft(false);
		if ( key == KeyEvent.VK_RIGHT )
			ninja.setRight(false); 
		if ( key == KeyEvent.VK_UP ) 
			ninja.setClimbing(false); 
		if ( key == KeyEvent.VK_DOWN ) 
			ninja.setSliding(false); 
		
		if ( key == KeyEvent.VK_SPACE ) 
			ninja.setJumping(false);
		if ( key == KeyEvent.VK_SHIFT )
			ninja.setGliding(false);
	}
	
//	private Enemy randomEnemy(){
//		int random = new Random().nextInt(0, 12);
//	}
		/* Aktualisierungs- und Zeichnugs-Methoden */
	@Override
	public void update() { 
		if ( !exit ) { 
			// Player
			ninja.update();
			// Map
			map.setPosition( GamePanel.WIDTH / 2 - ninja.getX(), GamePanel.HEIGHT / 2 - ninja.getY()); 
			background.setPosition( map.getX(), map.getY() ); 	
			teleport.update();
			// Enemy attack
			ninja.attackEnemy(enemies);
			// Enemies
			for ( int i = 0; i < enemies.size(); i++ ){
				enemies.get( i ).update();
				if (enemies.get(i).shouldtRemove()){
					enemies.remove(i);
					continue;
				}
			}
			// Next map if (ninja.getX() == 23910 && ninja.getY() == 720)
//			
			if ((ninja.getX() <= 23950 && ninja.getX() >= 23850) && ninja.getY() == 720)
				gameStateManager.setState(GameStateManager.STORY);
		} 
	}

	@Override
	public void draw(Graphics2D graphics) { 
		// Drawing background
		background.draw(graphics); 
//		graphics.setColor( Color.getHSBColor(1, 30, 1));
//		graphics.fillRect(0, (int) (GamePanel.HEIGHT * 0.90), GamePanel.WIDTH, (int)(GamePanel.HEIGHT * 0.10));
		
		// Draw map and player
		map.draw(graphics); 
		teleport.draw(graphics);
		ninja.draw(graphics);
		
		// Draw hud and enemies
		hud.draw(graphics); 

		for ( int i = 0; i < enemies.size(); i++ )
			enemies.get( i ).draw( graphics );
		
		// Debug-Console and game version
		if (Game.debugConsole)
			Game.drawDebug(graphics); 
		Game.drawVerison(graphics);
	} 
} // End of Tutorial
