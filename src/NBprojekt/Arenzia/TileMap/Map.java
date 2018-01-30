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

/** Importierete Pakete */ 
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader; 
import java.io.InputStreamReader; 
import javax.imageio.ImageIO; 
import NBprojekt.Arenzia.Main.GamePanel;
 

public class Map {
		/* Variablendeklaration */
	
	private double x, y, tween;
	private double xMin, yMin, xMax, yMax;  
	
	private int [][] map;
	private int tileSize;
	private int numRows, numCols;
	private int width, height;
	
	private BufferedImage tile;
	private int numTiles;
	private Tile [][] tiles; 
	
	private int rowOffset, colOffset;
	private int numDrawRows, numDrawCols;
	
	private BufferedImage bufferedImage; 
	private BufferedReader bufferedReader;
	
		/* Standartkonstruktor */
	
	public Map( int tileSize ) {
		this.tileSize = tileSize ;
	
		numDrawRows = GamePanel.HEIGHT / this.tileSize + 3;
		numDrawCols = GamePanel.WIDTH / this.tileSize + 3;
		tween = 0.04;
	}

		/* Set- und Get-Methoden*/
	
	public void setPosition ( double x, double y ) {
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;
		hotFix();
		
		colOffset = (int) -this.x / tileSize ;
		rowOffset = (int) -this.y / tileSize ;
	}
	public void setTween ( double tween ) {
		this.tween = tween;
	}
	
	public int getTileSize () {
		return tileSize;
	}
	
	public double getX () {
		return  x;
	}
	
	public double getY () {
		return  y;
	}
	
	public int getWidth () {
		return width;
	}
	
	public int getHeight () {
		return height;
	}
	
	public int getType ( int row, int col ) {
		int rowCol = map [row] [col] ;
		int r = rowCol / numTiles;
		int c = rowCol % numTiles;
		
		return tiles [r] [c] .getType();
	}
	
	public int getNumRows() { 
		return numRows; 
	}
	public int getNumCols() { 
		return numCols; 
	}

		/* Sonstige benoetigte Methoden */

	public void loadTiles ( String tilesLink ) { 
		try { 
			tile = ImageIO.read(getClass().getResourceAsStream(tilesLink));
			numTiles = tile.getWidth() / tileSize;
			
			tiles = new Tile [2] [numTiles];

			
			for (int col = 0 ; col < numTiles ; col++) {
				bufferedImage = tile.getSubimage(col * tileSize, 0, tileSize, tileSize); 
				tiles [0] [col] = new Tile ( bufferedImage, Tile.NORMAL); 
				
				bufferedImage = tile.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles [1] [col] = new Tile ( bufferedImage, Tile.BLOCK);
			}
		} catch ( Exception e ){
			System.out.println("Can't load the tiles.");
			e.printStackTrace(); 
		}
	} 
	
	public void loadMap ( String mapLink ) {
		try {  
			bufferedReader  = new BufferedReader( 
					new InputStreamReader ( getClass().getResourceAsStream(mapLink) ) );
			numCols = Integer.parseInt( bufferedReader.readLine() );
			numRows = Integer.parseInt( bufferedReader.readLine() );

			map = new int [numRows] [numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			xMin = GamePanel.WIDTH - width;
			yMin = GamePanel.HEIGHT - height; 
			
			xMax = yMax = 0;
			
			String delims = "\\s+";
			for ( int row = 0 ; row < numRows ; row++ ){
				String line = bufferedReader.readLine();
				String [] tokens = line.split( delims );
				
				for ( int col = 0 ; col < numCols ; col ++){
					map [row] [col] = Integer.parseInt( tokens [col] );
				}
			}
		} catch ( Exception e ){
			System.out.println("Can't load the map.");
			e.printStackTrace(); 
		}
	}
	
	private void hotFix () {
		if ( x < xMin )
			x = xMin;
		if ( x > xMax )
			x = xMax;
		
		if ( y < yMin )
			y = yMin;
		if ( y > yMax )
			y = yMax;
	}
	
		/* Aktualisierungs- und Zeichnugs-Methoden */ 
	
	public void draw(Graphics2D graphics ){
		for ( int row = rowOffset ; row < rowOffset + numDrawRows ; row++) {
			if ( row >= numRows )
				break;
			for ( int col = colOffset ; col < colOffset + numDrawCols ; col++) {
				if ( col >= numCols )
					break;
				 
				if ( map [row] [col] == 0 )
					continue;
				
				int rowCol = map [row] [col] ;
				int r = rowCol / numTiles;
				int c = rowCol % numTiles; 
				graphics.drawImage( tiles [r] [c].getBufferedImage(), 
						(int) x + col * tileSize , (int) y + row * tileSize , null );
			}
		}
	}  	
} // End of Map