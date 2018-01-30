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
package NBprojekt.Arenzia.TileMap;

import java.awt.image.BufferedImage; 

public class Tile {
		/* Variablendeklaration */
	
	private BufferedImage image;
	private int type;
	
	public static final int NORMAL = 0;
	public static final int BLOCK  = 1;
	
		/* Standartkonstruktor */
	
	public Tile ( BufferedImage image, int type ) {
		this.image = image;
		this.type = type;
	}
	
		/* Set- und Get-Methoden*/
	
	public BufferedImage getBufferedImage () { 
		return image;
	}
	
	public int getType () {
		return type;
	}

} // End of Tile 