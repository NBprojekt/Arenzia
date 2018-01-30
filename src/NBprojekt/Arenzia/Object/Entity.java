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

import java.awt.Point;
/** Importierete Pakete */ 
import java.awt.Rectangle;

import NBprojekt.Arenzia.Main.GamePanel;
import NBprojekt.Arenzia.TileMap.Map;
import NBprojekt.Arenzia.TileMap.Tile; 


public abstract class Entity {
		/* Variablendeklaration */
	protected Map map; 
	protected Animation animation;

	protected int width, height;
	protected int collisionWidth, collisionHeight; 
	protected int currRow, currCol;
	protected int tileSize;
	protected int currentAction, previousAction;
	 
	protected double xMap, yMap;
	protected double x, y;
	protected double xSpeed, ySpeed;
	protected double xDestination, yDestination;
	protected double xTemp, yTemp;
	
	protected boolean topLeft, topRight;
	protected boolean bottomLeft, bottomRight;
	protected boolean left, right, up, down;
	protected boolean jumping, falling;
	protected boolean facingRight;
	// Physics
	protected double moveSpeed;
	protected double maxSpeed, stopSpeed;
	protected double fallSpeed, maxFallSpeed;
	protected double jumpStart, stopJumpSpeed;
	protected boolean dead;
	
		/* Standartkonstruktor */
	
	public Entity ( Map map ) {
		this.map = map;
		tileSize = map.getTileSize();
		dead = false;
	} 
	
		/* Set- und Get-Methoden*/
	
	public void setPosition ( double x, double y ) {
		if ( dead ) 
			return;
		this.x = x;
		this.y = y ;
	}
	public void setPosition ( Point position){
		if ( dead ) 
			return;
		this.x = position.getX();
		this.y = position.getY();
	}
	public void setVector ( double xSpeed, double ySpeed ){
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	public void setMapPosition () {
		xMap = map.getX();
		yMap = map.getY();
	}
	public void setLeft ( boolean bool ){
		left = bool;
	}
	public void setRight ( boolean bool ){
		right = bool;
	}
	public void setUp ( boolean bool ){
		up = bool;
	}
	public void setDown ( boolean bool ){
		down = bool;
	}
	public void setJumping ( boolean bool ){
		jumping = bool;
	}
		
	public int getX () {
		return (int) x;
	}
	public int getY () {
		return (int) y;
	}
	public int getWidth () {
		return width; 
	}
	public int getHeight () {
		return height;
	}
	public int getCollisionWidth () {
		return collisionWidth;
	}
	public int getCollisionHeight () {
		return collisionHeight;
	}
	
		/* Sonstige benötigte Methoden */
	 
	public boolean checkTouch ( Entity other  ) { 
		return newRectangle().intersects( other.newRectangle() ); 
	}
	
	public Rectangle newRectangle () {
		return new Rectangle ( (int) ( x - collisionWidth ), (int) ( y - collisionHeight ), 
								collisionWidth, collisionHeight);
	}
	
	public boolean notOnScreen () {
		return x + xMap + width < 0 || x + xMap - width > GamePanel.WIDTH || 
		       y + yMap + height < 0 || y + yMap - height > GamePanel.HEIGHT;
	}
	
	public void checkMapCollision () {
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		
		xDestination = x + xSpeed;
		yDestination = y + ySpeed;
		
		xTemp = x;
		yTemp = y;
		
		calculateCollision(x, yDestination);
		if(ySpeed < 0) {
			if(topLeft || topRight) {
				ySpeed = 0;
				yTemp = currRow * tileSize + collisionHeight / 2;
			}
			else {
				yTemp += ySpeed;
			}
		}
		if(ySpeed > 0) {
			if(bottomLeft || bottomRight) {
				ySpeed = 0;
				falling = false;
				yTemp = (currRow + 1) * tileSize - collisionHeight / 2;
			}
			else {
				yTemp += ySpeed;
			}
		}
		
		calculateCollision(xDestination, y);
		if(xSpeed < 0) {
			if(topLeft || bottomLeft) {
				xSpeed = 0;
				xTemp = currCol * tileSize + collisionWidth / 2;
			}
			else {
				xTemp += xSpeed;
			}
		}
		if(xSpeed > 0) {
			if(topRight || bottomRight) {
				xSpeed = 0;
				xTemp = (currCol + 1) * tileSize - collisionWidth / 2;
			}
			else {
				xTemp += xSpeed;
			}
		}
		 
		if(!falling) {
			calculateCollision(x, yDestination + 1);
			if(!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
	}
	
	public void calculateCollision ( double x, double y ) {
		
        int leftTile = (int) ( x - collisionWidth / 2 ) / tileSize;
        int rightTile = (int)( x + collisionWidth / 2 - 1 ) / tileSize;
        int topTile = (int)( y - collisionHeight / 2 ) / tileSize;
        int bottomTile = (int)( y + collisionHeight / 2 - 1 ) / tileSize;
        
        if(topTile < 0 || bottomTile >= map.getNumRows() || leftTile < 0 || rightTile >= map.getNumCols()) {
	        topLeft = topRight = bottomLeft = bottomRight = false;
	        return;
        }
        
        int tl = map.getType(topTile, leftTile);
        int tr = map.getType(topTile, rightTile);
        int bl = map.getType(bottomTile, leftTile);
        int br = map.getType(bottomTile, rightTile);
        
        topLeft = tl == Tile.BLOCK;
        topRight = tr == Tile.BLOCK;
        bottomLeft = bl == Tile.BLOCK;
        bottomRight = br == Tile.BLOCK; 
    } 	
} // End of Entity
