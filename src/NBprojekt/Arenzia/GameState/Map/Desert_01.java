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
import NBprojekt.Arenzia.Object.Checkpoint;
import NBprojekt.Arenzia.Object.Collectable;
import NBprojekt.Arenzia.Object.Enemy;
import NBprojekt.Arenzia.Object.Ninja;
import NBprojekt.Arenzia.Object.Teleport;
import NBprojekt.Arenzia.Object.Enemies.Bird;
import NBprojekt.Arenzia.Object.Enemies.Pet;
import NBprojekt.Arenzia.Object.Enemies.Slime;
import NBprojekt.Arenzia.Object.Enemies.Spike;
import NBprojekt.Arenzia.TileMap.Background;
import NBprojekt.Arenzia.TileMap.Map; 


public class Desert_01 extends GameState{ 
		// Map
	private Map map;
	private Background background;
	private HUD hud;
	private boolean exit;
	private boolean male = false;
	
		// Entities
	private Ninja ninja; 
	private ArrayList < Enemy > enemies; 
	private ArrayList < Collectable > collectable;
	private ArrayList <Checkpoint> checkPoints;
 	private Teleport teleport;
	private Point spawnPoint;

	public Desert_01 (GameStateManager gameStateManager){
		this.gameStateManager = gameStateManager; 
		init();
	}

	// Initialisierungs-Methode
	@Override
	public void init() { 
			// Map and backgound 
		map = new Map(100); 
			// Map Files
		map.loadTiles("/map/desert/desertTiles.gif"); 
		map.loadMap("/map/desert/Desert_01.map"); 
		map.setPosition( 0, 0 );  
			// Backgroundfile
		background = new Background("/map/desert/desertBackground.gif", 0.0);  
		exit = false;
		 
			// Player 
		spawnPoint = new Point(600, 500);
		ninja = new Ninja ( map, male);
		ninja.setPosition(spawnPoint);  

			// HUD  
		hud = new HUD ( ninja );

		// Load every other entity stuff
		initEntities();
		
		System.out.println(""+ Crypt.randomString(15));
		HUD.Coin1 = HUD.Coin2 = HUD.Coin3 = HUD.Coin4 = HUD.Coin5 = HUD.Coin6 = true;
	}
	private void initEntities(){

		// Enemies
	enemies  = new ArrayList < Enemy >(); 
	collectable = new ArrayList < Collectable >();
	checkPoints = new ArrayList < Checkpoint >();
		// Teleport
	teleport = new Teleport(map);
	teleport.setPosition(23900, 698);
	 
	// Set checkpoints 
	Point [] checks = new Point[]{ 
		new Point(5961,80), new Point(14846, 480)
	}; 
	for (int i = 0; i < checks.length; i++) {
		Checkpoint checkpoint = new Checkpoint(map);
		checkpoint.setPosition(checks[i].getX() + 50, checks[i].getY() + 43);
		checkPoints.add(checkpoint);
	} 

	// Place enemies
	Point [] slimes = new Point[]{ 
		new Point(1700,800), new Point(3300, 700),
		new Point(7200,900), new Point(7000, 900),
		new Point(6500,140), new Point(9400, 1200),
		new Point(14470, 1010), new Point(19696, 1430),
		new Point(23484, 1410)
	};
	Point [] pets = new Point[]{  
		new Point(17400, 1200), new Point(17600, 1200),
		new Point(11100, 1000), new Point(14300, 1000),
		new Point(14500, 1000), new Point(23100, 1300), 
		new Point(17465, 1210), new Point(14470, 970),
		new Point(23484, 1270)
	}; 
	Point [] spikes = new Point[]{ 
		new Point(10400, 1100), new Point(900, 700),
		new Point(12100, 900), new Point(12200, 900),
		new Point(18200, 1300)
	};
	for ( int i = 0 ; i < slimes.length ; i++) {
		Enemy enemy;
		if (i% 2 == 0) {
			enemy = new Slime(map, true); 
			enemy.setPosition(slimes[i].getX(), slimes[i].getY());
		}
		else {
			enemy = new Slime(map, false); 
			enemy.setPosition(slimes[i].getX(), slimes[i].getY());
		}
		enemies.add(enemy);
	}  
	for ( int i = 0 ; i < pets.length ; i++) {
		Enemy enemy;
		if (i% 2 == 0) {
			enemy = new Pet(map, false); 
			enemy.setPosition(pets[i].getX(), pets[i].getY());
		}
		else {
			enemy = new Bird(map, false); 
			enemy.setPosition(pets[i].getX(), pets[i].getY());
		}
		enemies.add(enemy);
	} 
	for (int i = 0; i < spikes.length; i++) {
		Spike spike = new Spike(map);
		spike.setPosition(spikes[i].getX() + 50, spikes[i].getY() + 43);
		enemies.add(spike);
	}
	}
	
	private void respawn(){
		// chekc respawn points
		for (int i = 0; i < checkPoints.size(); i++){
			if (ninja.getLocation().getX() >= checkPoints.get(i).getLocation().getX() -100 && 
				ninja.getLocation().getX() <= checkPoints.get(i).getLocation().getX() +100 && 
				ninja.getLocation().getY() >= checkPoints.get(i).getLocation().getY() -100 && 
				ninja.getLocation().getY() <= checkPoints.get(i).getLocation().getY() +100 &&
				!checkPoints.get(i).isChecked())
				checkPoints.get(i).setChecked();
//			System.out.println("x: " + ninja.getX() + " | " + ninja.getLocation().getY());
//			System.out.println("x: " + checkPoints.get(i).getLocation().getX() 
//					+ " | " + checkPoints.get(i).getLocation().getY());
		} 
		// Update chekpoint
		for( int i = 0; i < checkPoints.size(); i++){
			if (checkPoints.get(i).isChecked()) 
				spawnPoint = new Point(
						(int)checkPoints.get(i).getLocation().getX(),
						(int)checkPoints.get(i).getLocation().getY() - 50
				);
		}
		
		// Respawn player
		if (ninja.getHealth() == 0){
			ninja = new Ninja ( map, male);
			ninja.setPosition(spawnPoint);  
			int time[] = hud.getTime();
			hud = new HUD(ninja); 
			hud.setTime(time[0], time[1]);
		}
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
		if ( key == KeyEvent.VK_SPACE || key == KeyEvent.VK_UP) 
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
				gameStateManager.setState(GameStateManager.GRAVEYARD);
			for (int i = 0; i < checkPoints.size(); i++)
				checkPoints.get(i).update();
			respawn();
		} 
	}

	@Override
	public void draw(Graphics2D graphics) { 
		// Drawing background
		background.draw(graphics); 
//		graphics.setColor( Color.getHSBColor(1, 30, 1));
//		graphics.fillRect(0, (int) (GamePanel.HEIGHT * 0.90), GamePanel.WIDTH, (int)(GamePanel.HEIGHT * 0.10));
		
		// Draw map 
		map.draw(graphics); 
		// Draw the teleport and chekcpoint behind the player
		teleport.draw(graphics); 
		for (int i = 0; i < checkPoints.size(); i++)
			checkPoints.get(i).draw(graphics);
		// Draw player
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
