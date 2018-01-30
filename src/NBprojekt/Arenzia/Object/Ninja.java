package NBprojekt.Arenzia.Object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import NBprojekt.Arenzia.Main.Game;
import NBprojekt.Arenzia.Main.GamePanel;
import NBprojekt.Arenzia.TileMap.Map;

public class Ninja extends Entity{
	
	// Needed vars
	private int health, maxHealth;
	private int damageKatana, damageKunai, katanaRange;
	private int numKunais, maxKunais;
	private boolean attackingKatana, attackingKunai, gliding;
	private double doubleJumpStart, slideStop;
	private boolean alreadyDoubleJump, doubleJump; 

	// Points
	private int numGems, numKeys, numKarpardor; 
	
	// Hitted
	private boolean flinching, climb, slide;
	private long flinchingTimer; 	
	private Font gameOver;
	
	private ArrayList < Kunai > kunais;
	private final int WIDTH [] = { 104, 191, 131, 203, 210, 144, 169, 164, 136, 163, 134, 144 };
	private final int HEIGHT [] = { 180, 180, 180, 190, 180, 180, 190, 180, 180, 165, 180, 180};
	
	private boolean male;
	private ArrayList < BufferedImage[] > sprites;
	
	private static final int IDLE 		=  0;
	private static final int ATTACK 	=  1;
	private static final int CLIMB 		=  2;
	private static final int DEAD 		=  3;
	private static final int GLIDE 		=  4;
	private static final int JUMP 		=  5; 
	private static final int JUMPATTACK =  6;
	private static final int JUMPTHROW 	=  7;
	private static final int WALK 		=  8;
	private static final int SLIDE 		=  9;
	private static final int THROW 		= 10;
	private static final int FALL 		= 11;
	

	public Ninja(Map map, boolean male) {
		super(map);
		width = 170;
		height = 180; 
		
		// Hitbox
		collisionWidth = 120;
		collisionHeight = 160;
		
		// Moving
		moveSpeed = 2.1; // 2.1
		maxSpeed = 6; // 6
		stopSpeed = 0.9;
		fallSpeed = 0.3;
		maxFallSpeed = 9.0;
		jumpStart = -10.8; // -10.8
		stopJumpSpeed = 6;
		doubleJumpStart = -8;
		
		facingRight = false;
		this.male = male;
		health = maxHealth = 12; //12
		numKunais = maxKunais = 6;
		kunais = new ArrayList < Kunai > ();
		
		// Attacking
		attackingKatana = attackingKunai = gliding = climb = slide = false;
		damageKatana = 5;
		damageKunai = 3;
		katanaRange = 140;
		 
			// Collectable   
		
		gameOver = new Font("Rockwell", Font.PLAIN, 110);  
		try {
			BufferedImage sprite;
			
			if ( !male )
				sprite = ImageIO.read( 
					getClass().getResourceAsStream( "/entity/player/femaleNinja.gif" ) );
			else 
				sprite = ImageIO.read( 
					getClass().getResourceAsStream( "/entity/player/maleNinja.gif" ) );
			
			sprites = new ArrayList < BufferedImage[]> ();
			int cutHeight = 0;
			for ( int i = 0; i < 12 ; i++ ) {
				BufferedImage [] bufferedImage;
				if ( i != FALL )
					bufferedImage = new BufferedImage[ 10 ];
				else 
					bufferedImage = new BufferedImage[ 1 ];
				if (i != 0 )
					cutHeight += HEIGHT[ i - 1];
				// read sprites
				for ( int j = 0; j < bufferedImage.length; j++ ){ 
					bufferedImage[ j ] = sprite.getSubimage( j * WIDTH[ i ], cutHeight, WIDTH[ i ], HEIGHT[ i ] );
				} 
				sprites.add( bufferedImage );
			} 
		} catch ( Exception e ) {
			System.out.println("Can not load the ninja \n");
			e.printStackTrace();
		}
		
		// Animation
		animation = new Animation ();
		currentAction = IDLE;
		animation.setFrames( sprites.get( IDLE ));
		animation.setDelay( 100 );
		width = WIDTH[ IDLE ];
		height = HEIGHT[ IDLE ]; 	 
	}
	
	// SET  	
	public void setThrowing () {
		attackingKunai = true; 
	}
	public void setKatana () {
		attackingKatana = true;
	}
	public void setGliding ( boolean bool ) {
		gliding = bool;
	}
	public void setSliding ( boolean bool ) {
		slide = bool;
	}
	public void setClimbing ( boolean bool ) {
		climb = bool;
	} 
	public void setHit ( int damage ) {
		if (flinching)
			return;
		health -= damage;
		if ( health < 0 )
			health = 0;
		 
		flinching = true;
		flinchingTimer = System.nanoTime();
	}
	@Override
	public void setJumping ( boolean bool ) { 
		if( bool && !jumping && falling && !alreadyDoubleJump && !gliding) {
			doubleJump = true;
		} 
		jumping = bool;
	}
	
	// Get-methodes
	public int getHealth () {
		return health;
	}
	public int getMaxHealth () {
		return maxHealth;
	}
	public int getNumKunais () {
		return numKunais;
	}
	public int getMaxKunais () {
		return maxKunais;
	}
	public boolean getMale () {
		return male;
	}
	public int getNumGems() {
		return numGems;
	} 
	public int getNumKeys(){
		return numKeys;
	} 
	public int getNumKarpardor() {
		return numKarpardor;
	}
	public Point getLocation(){
		return new Point((int)x, (int)y);
	}
	
	// Basic movement
	private void getNextPosition() {
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
		else { 
			if(xSpeed > 0) {
				xSpeed -= stopSpeed;
				if(xSpeed < 0) {
					xSpeed = 0;
				}
			}
			else if( xSpeed < 0) {
				xSpeed += stopSpeed;
				if( xSpeed > 0) {
					xSpeed = 0;
				}
			}
		} 
		if ( (currentAction == THROW || currentAction == ATTACK ) && !( jumping || falling )) {
			xSpeed = 0;
		} 
				
		if( jumping && !falling && !gliding) {  
			ySpeed = jumpStart;
			falling = true; 
		}
		
		if( doubleJump && !gliding ) {
			ySpeed = doubleJumpStart;
			alreadyDoubleJump = true;
			doubleJump = false;  
		}
		 
		if( !falling ) 
			alreadyDoubleJump = false; 
			
		if( falling ) {
			if ( gliding )
				ySpeed += fallSpeed * 0.1;
			else 
				ySpeed += fallSpeed;
			
			if	( ySpeed > 0 ) 
				jumping = false;
			if ( ySpeed < 0 && !jumping ) 
				ySpeed += stopJumpSpeed;
			if ( ySpeed > maxFallSpeed ) 
				ySpeed = maxFallSpeed;
		}
	}
	public void attackEnemy( ArrayList<Enemy> enemies){
		for ( int e = 0; e < enemies.size(); e++){
			Enemy enemy = enemies.get(e);
			// Mele
			if ( attackingKatana) {
				if (facingRight) {  
					if ( enemy.getX() > x && enemy.getX() < x + katanaRange && 
							enemy.getY() > y - height / 2 && enemy.getY() < y + height / 2) {
						enemy.getHit(damageKatana);
					} 
				}
				else {  
					if ( enemy.getX() < x && enemy.getX() > x - katanaRange && 
							enemy.getY() > y - height / 2 && enemy.getY() < y + height / 2) {
						enemy.getHit(damageKatana);
					} 
				}
			}
			// Range
			if ( attackingKunai)
			for( int i = 0; i < kunais.size(); i++) {
				if(kunais.get(i).checkTouch(enemy)){
					enemy.getHit(damageKunai);
					kunais.get(i).hit();
					break;
				}
			}
			// get hit 
			if (checkTouch(enemy))
				setHit(enemy.getDamage());
		} 
	}
	private void checkDead () {
		if ( dead ) 
			return;
		
		// Fall out of map
		if ( y >= map.getHeight() * 2 - map.getHeight() * 2 * 0.32) {
			dead = true; 
		}
		// No health
		if ( health == 0 ) {
			dead = true;
		}
	}
	
	public void update () {
		// If the player is dead dont update
		if ( dead ) {
			if ( currentAction != DEAD ) {
				currentAction = DEAD;
				animation.setFrames( sprites.get( DEAD ) );
				animation.setDelay( 100 );
				animation.setRepeat( false );
				width = WIDTH[ DEAD ];
				height = HEIGHT[ DEAD ];
			}
			else if( !animation.hasPlayed()) 
				animation.update();
			else 
				animation.setFrame(9);
			health = 0 ;
			numKunais = 0 ; 

			getNextPosition();
			setPosition( x, yTemp );
			return;
		}
		getNextPosition();
		checkMapCollision(); 
		setPosition( xTemp, yTemp );
		checkDead(); 
		
		// player alive 
		if ( ( currentAction == ATTACK || currentAction == JUMPATTACK ) && animation.hasPlayed() )
			attackingKatana = false;
		if ( ( currentAction == THROW || currentAction == JUMPTHROW ) && animation.hasPlayed() )
			attackingKunai = false;
			
		// Kunais
		if ( numKunais > maxKunais )
			numKunais = maxKunais; 
		if ( attackingKunai && currentAction != THROW && currentAction != JUMPTHROW ) {
			// set the max kunais to two too save some fps
			if ( numKunais > 0 && !( kunais.size() > 2 )) {
				numKunais--;
				Kunai kunai = new Kunai( map, facingRight, 0, (int) y );
				kunai.setPosition(x, y);
				kunais.add(kunai);
			}
		}
		for ( int i = 0; i < kunais.size(); i++ ){
			kunais.get(i).update();  
			if ( kunais.get(i).shouldRemove() ){
				kunais.remove(i);
				i++;				
			}
		} 
		
		// got hit
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchingTimer) / 1000000;
			if ( elapsed > 1200)
				flinching = false;
		}
		
		// All animations  
		if ( ySpeed > 0 ) { 
			if ( attackingKatana ){
				if ( currentAction != JUMPATTACK  ) {
					currentAction = JUMPATTACK;
					animation.setFrames( sprites.get( JUMPATTACK ));
					animation.setDelay( 36 );
					width = WIDTH[ JUMPATTACK ];
					height = HEIGHT[ JUMPATTACK ];
				}
			}
			else if ( attackingKunai && ( numKunais > 0 || kunais.size() != 0 )){
				if ( currentAction != JUMPTHROW ) {
					currentAction = JUMPTHROW;
					animation.setFrames( sprites.get( JUMPTHROW ));
					animation.setDelay( 40 );
					width = WIDTH[ JUMPTHROW ];
					height = HEIGHT[ JUMPTHROW ];
				}
			}
			else if ( gliding ) {
				if ( currentAction != GLIDE ) {
					currentAction = GLIDE;
					animation.setFrames( sprites.get( GLIDE ));
					animation.setDelay( 100 );
					width = WIDTH[ GLIDE ];
					height = HEIGHT[ GLIDE ];
				}
			}
			else {
				if ( currentAction != FALL ) { 
					currentAction = FALL;
					animation.setFrames( sprites.get( FALL ));
					animation.setDelay( -1 );
					width = WIDTH[ FALL ];
					height = HEIGHT[ FALL ];
				}
			}
		}
		else if ( attackingKatana ) {
			if ( currentAction != ATTACK ) {
				currentAction = ATTACK;
				animation.setFrames( sprites.get( ATTACK ));
				animation.setDelay( 36 );
				width = WIDTH[ ATTACK ];
				height = HEIGHT[ ATTACK ];
			}
		}
		else if ( attackingKunai && ( numKunais > 0 || kunais.size() != 0 )) {
			if ( currentAction != THROW ) {
				currentAction = THROW;
				animation.setFrames( sprites.get( THROW ));
				animation.setDelay( 40 );
				width = WIDTH[ THROW ];
				height = HEIGHT[ THROW ];
			}
		}
		else if ( ySpeed < 0 ) {
			if ( currentAction != JUMP ) {
				currentAction = JUMP;
				animation.setFrames( sprites.get( JUMP ));
				animation.setDelay( 100 );
				width = WIDTH[ JUMP ];
				height = HEIGHT[ JUMP ];
			}
		}
		else if ( left || right ) {
			if ( currentAction != WALK ) {
				currentAction = WALK;
				animation.setFrames( sprites.get( WALK ));
				animation.setDelay( 80 );
				width = WIDTH[ WALK ];
				height = HEIGHT[ WALK ];
			}
		}
		// Dont work at the moment
//		else if ( climb ) {
//			if ( currentAction != CLIMB ) {
//				currentAction = CLIMB;
//				animation.setFrames( sprites.get( CLIMB ));
//				animation.setDelay( 100 );
//				width = WIDTH[ CLIMB ];
//				height = HEIGHT[ CLIMB ];
//			}
//		}
//		else if ( slide ) {
//			if ( currentAction != SLIDE ) {
//				currentAction = SLIDE;
//				animation.setFrames( sprites.get( SLIDE ));
//				animation.setDelay( 100 );
//				width = WIDTH[ SLIDE ];
//				height = HEIGHT[ SLIDE ];
//			}
//		}
		else {
			if ( currentAction != IDLE ) {
				currentAction = IDLE;
				animation.setFrames( sprites.get( IDLE ));
				animation.setDelay( 100 );
				width = WIDTH[ IDLE ];
				height = HEIGHT[ IDLE ];
			}
		}	
		
		// Richtung 
		if ( currentAction != JUMPATTACK && currentAction != JUMPTHROW  &&
				currentAction != ATTACK && currentAction != THROW ){
			if ( right )
				facingRight = true;
			if ( left )
				facingRight = false;
		}
		
		// Update the Animations
		animation.update(); 
		// Debug
		Game.updateDebug( (int)x, (int)y, currentAction, male, facingRight, numKunais, health);
	}
	
	
	public void draw ( Graphics2D graphics ) {
		setMapPosition();
		if ( dead ) {
//			graphics.setColor( Color.RED );
//			Game.drawCenteredString("Game Over", GamePanel.WIDTH, 300, graphics, gameOver);
		}
		// If the player is alive
		else {
			// draw kunais
			for ( int i = 0; i < kunais.size(); i++ ){
				kunais.get(i).draw(graphics);
			}
			
			if ( flinching ) {
				long e = ( System.nanoTime() - flinchingTimer ) / 1000000;
				if ( e / 100 % 2 == 0 )
					return;
			}
		}
		// Draw player
		if ( facingRight ) 
			graphics.drawImage( animation.getImage(), (int) (x + xMap - width / 2 ),
							    (int) ( y + yMap - height / 2 ), null);
		else 
			graphics.drawImage( animation.getImage(), (int) (x + xMap - width / 2 + width),
							    (int) ( y + yMap - height / 2 ), - width, height, null);
		// Draw hitbox
		graphics.setColor( Color.ORANGE );
		if ( Game.debugConsole )
			graphics.drawRect((int) (x + xMap - collisionWidth / 2), 
					(int) (y + yMap - collisionHeight / 2), collisionWidth, collisionHeight);
	}
}