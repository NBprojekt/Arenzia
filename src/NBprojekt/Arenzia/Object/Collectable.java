package NBprojekt.Arenzia.Object;

import java.awt.Graphics2D;

import NBprojekt.Arenzia.TileMap.Map;

public abstract class Collectable extends Entity {
	private int points;
	private int fixedMinY, fixedMaxY; 
	private boolean down;
	private boolean collected;

	public Collectable( Map map, String spritePath ) {
		super( map );
		collected = false;
	}
	
	public int getX () {
		return (int) x;
	}
	public int getY () {
		return (int) y;
	} 
	public int getPoints () {
		return points;
	}
	public void setX ( int x ) {
		this.x = x;
	}
	public void setY ( int y ) {
		this.y = y;
		fixedMinY = y;
		fixedMaxY = y + 150;
	}

		// Update and draw
	public void update () {
		// left and right movement
				if(left) { 
					xSpeed -= moveSpeed ;
					if(xSpeed < -maxSpeed) {
						xSpeed = -maxSpeed;
					}  
				}
				else if(right) {  
					xSpeed += moveSpeed;
					if(xSpeed > -maxSpeed) {
						xSpeed = maxSpeed;
					} 
				} 
				// falling
				if(falling) {
					ySpeed += fallSpeed; 
					if ( ySpeed > maxFallSpeed ) 
						ySpeed = maxFallSpeed;
				}
		if (collected)
			return;
		checkMapCollision(); 
		setPosition( xTemp, yTemp );
		if (y < fixedMinY)
			y++;
		else if ( y > fixedMaxY)
			y--;
	}
	public void draw ( Graphics2D graphics ) {
		if (collected )
			return;
		
	}
}
