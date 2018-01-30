package NBprojekt.Arenzia.Object;

import java.awt.Graphics2D; 
import NBprojekt.Arenzia.TileMap.Map;

public abstract class Enemy extends Entity {
	
	protected int health, maxHealth;
	protected int damage;
	protected boolean remove;
	
	protected long deadTimer;
	protected boolean dead;
	
	protected boolean flinching ;
	protected long flinchTimer;

	public Enemy( Map map ) {
		super(map); 
	}
	
	public boolean isDead () {
		return dead;
	}
	public boolean shouldtRemove(){
		return remove;
	}
	public int getXMap () {
		return (int) xMap;
	}
	public int getYMap () {
		return (int) yMap;
	} 
	public int getHealth () {
		return health;
	}
	public int getMaxHealth () {
		return maxHealth;
	}
	public int getDamage () {
		return damage;
	}
	public void getHit ( int damage ){
		// Get hit
		if(dead)
			deadTimer = System.nanoTime(); 
		health -= damage;
		if ( health <= 0 ){
			dead = remove = true;
			health = 0;
			return;
		}
		
		// Flinching
		flinching = true;
		flinchTimer = System.nanoTime();
	}


	// Basic movement	
	protected void getNextPosition () {
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
	}
	
	public abstract void update ();
	public abstract void draw ( Graphics2D graphics ) ; 
}


















