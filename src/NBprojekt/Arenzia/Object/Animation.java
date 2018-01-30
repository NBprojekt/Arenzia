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
package NBprojekt.Arenzia.Object;

/** Importierete Pakete */ 
import java.awt.image.BufferedImage;


public class Animation {
		/* Variablendeklaration */
	
	private BufferedImage [] frames;
	private int curentFrame;
	private long start, delay ;
	
	private boolean playedOnce, repeat;
	
		/* Standartkonstruktor */
	
	public Animation () {
		playedOnce = false;
		repeat = true;
	}

		/* Set- und Get-Methoden*/
	
	public void setFrames ( BufferedImage [] frames) {
		this.frames = frames;
		curentFrame = 0;
		start = System.nanoTime();
		playedOnce = false; 
	} 
	public void setDelay ( long d /* hi hi */){
		delay = d;
	} 
	public void setFrame ( int currentFrame ){
		this.curentFrame = currentFrame;
	}
	
	public int getFrame () {
		return curentFrame;
	}
	public BufferedImage getImage () {
		return frames[ curentFrame ];
	}
	public boolean hasPlayed () {
		return playedOnce;
	}
	
	public void setRepeat ( boolean bool ) {
		repeat = bool;
	}
	
		/* Aktualisierungs-Methode */
	
	public void update () {
		// if the delay is -1 the animation have just one frame
		if ( delay <= -1 )
			return;
		 
		long elapsed = ( System.nanoTime() - start ) / 1000000;
		if (elapsed > delay ) {
			curentFrame++;
			start = System.nanoTime();
		}
		
		if ( curentFrame == frames.length -1 ){
			curentFrame = 0;
			playedOnce = true; 
		}  
	}  
} // End of Aniamtion