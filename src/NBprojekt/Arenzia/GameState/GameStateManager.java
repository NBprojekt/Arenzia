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
package NBprojekt.Arenzia.GameState;

/** Importierete Pakete */ 
import java.awt.Graphics2D; 
import java.util.ArrayList;

import NBprojekt.Arenzia.GameState.Map.*;


public class GameStateManager {
		/* Variablendeklaration */
	
	private ArrayList<GameState> gameStates;
	private int currentState;
	
	// Die verschiedenen Level und Fenster
	public static final int MENU 	 	 = 0;
	public static final int STORY	 	 = 1; 
	public static final int LEVELSELECT	 = 2;
	public static final int HELP		 = 3; 
	public static final int CREDITS		 = 4; 
	public static final int DESERT		 = 5;
	public static final int GRAVEYARD	 = 6;
	
	
		/* Standartkonstruktor */
	
	public GameStateManager() {  
		currentState = MENU; 
		gameStates = new ArrayList<GameState>();
		 
		gameStates.add(new Menu( this ));  
		gameStates.add(new Tutorial( this ));
		gameStates.add(new LevelSelect( this )); 
		gameStates.add(new Credits( this ));
		gameStates.add(new Help( this ));
		gameStates.add(new Desert_01( this ));
		gameStates.add(new Graveyard_01( this ));
	}
		/* Set- und Get-Methoden*/
	
	public void setState( int state ){ 
		currentState = state;
		gameStates.get(currentState).init();
	} 
	 
	
		/* Methoden fuer das Tastatur mitlesen */
	
	public void keyPressed( int key) {
		gameStates.get(currentState).keyPressed(key); 
	}

	public void keyReleased( int key) {
		gameStates.get(currentState).keyReleased(key);
	}
	
		/* Aktualisierungs- und Zeichnugs-Methoden */
	
	public void update() {
		gameStates.get(currentState).update();
	}
	
	public void draw(Graphics2D graphics) {
		gameStates.get(currentState).draw(graphics);
	} 	
} // End of GameStateManager
