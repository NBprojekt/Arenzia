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
import javax.imageio.ImageIO; 
import NBprojekt.Arenzia.Main.GamePanel;


public class Background {
		/* Variablendeklaration */
	
	private BufferedImage bufferedImage;
	
	private double x, y;
	private double dx, dy;
	
	private double moveScale; 
	
		/* Standartkonstruktor */
	
	public Background( String backgroundLocation, double ms ) { 
		try {
			bufferedImage = ImageIO.read(getClass().getResourceAsStream(backgroundLocation));
			moveScale = ms; 
		} catch( Exception e){ 
			e.printStackTrace();
		} 
	}
	
		/* Set- und Get-Methoden*/
	
	public void setPosition( double x, double y ) {
		this.x = ( x * moveScale ) % GamePanel.WIDTH ;
		this.y = ( y * moveScale ) % GamePanel.HEIGHT ;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	
		/* Aktualisierungs- und Zeichnugs-Methoden */
	
	public void update() {
		x += dx;
		y += dy;
	}
	
	public void draw(Graphics2D graphics) {
		graphics.drawImage(bufferedImage, (int) x, (int) y, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		
		// X
		if (x < 0) {
			graphics.drawImage(bufferedImage, (int) x + GamePanel.WIDTH, (int) y, 
					GamePanel.WIDTH, GamePanel.HEIGHT, null);
		}
		if (x > 0) {
			graphics.drawImage(bufferedImage, (int) x - GamePanel.WIDTH, (int) y,
					GamePanel.WIDTH, GamePanel.HEIGHT, null);
		}

		if (moveScale <= 0)
			return;
		// Y
		if (y < 0) {
			graphics.drawImage(bufferedImage, (int) x , (int) y + GamePanel.HEIGHT, 
					GamePanel.WIDTH, GamePanel.HEIGHT, null);
		}
		if (y > 0) {
			graphics.drawImage(bufferedImage, (int) x , (int) y - GamePanel.HEIGHT,
					GamePanel.WIDTH, GamePanel.HEIGHT, null);
		}
	} 	
} // End of Background
