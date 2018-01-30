package NBprojekt.Arenzia.Object.Enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import NBprojekt.Arenzia.Main.Game;
import NBprojekt.Arenzia.Object.Animation;
import NBprojekt.Arenzia.Object.Enemy;
import NBprojekt.Arenzia.TileMap.Map;

public class Pet extends Enemy {
	private ArrayList < BufferedImage[] > sprites; 
	
	// Animation
	private final int WALK = 0;
	private final int DEAD = 1; 
	private final int FALL = 2;
	private final int HIT = 3; 
	private Healthbar healthbar;

	public Pet ( Map map, boolean dog ) {
		super( map );
		
		moveSpeed = 0.8;
		maxSpeed = 2.1;
		fallSpeed = 0.3;
		maxFallSpeed = 5.0;
		
		width = 173;
		height = 156;
		
		collisionWidth = 100;
		collisionHeight = 140;
		
		damage = 4;
		deadTimer = 10;
		health = maxHealth = 4; 
		
		facingRight = false;
		left = true; 
		remove = false;
		
		try {
			BufferedImage sprite;
			
			if ( dog ) {
				sprite = ImageIO.read( 
					getClass().getResourceAsStream( "/entity/enemy/dog.gif") );
				width = 200;
			}
			else 
				sprite = ImageIO.read( 
					getClass().getResourceAsStream( "/entity/enemy/cat.gif") );
			
			sprites = new ArrayList < BufferedImage[]> (); 
			for ( int i = 0; i < 4 ; i++ ) {
				BufferedImage [] bufferedImage;
				if (i == FALL)
					bufferedImage = new BufferedImage[8]; 
				else if (i == HIT)
					bufferedImage = new BufferedImage[3]; 
				else 
					bufferedImage = new BufferedImage[9]; 
				
				// read sprites
				for ( int j = 0; j < bufferedImage.length; j++ ){ 
					bufferedImage[ j ] = sprite.getSubimage( j * width, i * height, width, height);
				} 
				sprites.add( bufferedImage );
			}			
		} catch ( Exception e ) {
			System.out.println("Can not load the Pet \n");
			e.printStackTrace();
		}
		
		// animations
		animation = new Animation () ;
		animation.setFrames( sprites.get( WALK ) );
		animation.setDelay( 200 );
		animation.setRepeat( true );
		
		// Heatl bar
		healthbar = new Healthbar ( this );
	}

	
	@Override
	public void update () { 
		getNextPosition();
		checkMapCollision();
		
		if ( dead ) {
			animation.setFrames( sprites.get(DEAD) );
			animation.setDelay( 100 ); 
			animation.setRepeat( false );  
			setPosition( x, yTemp ); 
			// If dead 
			long elapsedDead =  (System.nanoTime() - deadTimer ) / 1000000;
			if ( elapsedDead > 400 )
				remove = true; 
		}
		else {
			// If alive move 
			 setPosition( xTemp, yTemp );
			
			if ( flinching ) {
				long elapsedFlinch =  (System.nanoTime() - flinchTimer ) / 1000000;
				if ( elapsedFlinch > 200 )
					flinching = false;
			}
			
			// walk left and right
			if ( left && xSpeed == 0 ){
				right = facingRight = true;
				left = false; 
			}
			else if ( right && xSpeed == 0 ) {
				left = true;
				right = facingRight = false;
			}
		}
		// Update animation and healthbar
		animation.update();
		healthbar.update( this );
	}
	
	@Override
	public void draw ( Graphics2D graphics ) {
		setMapPosition();
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
		
		// Draw healthbar
		healthbar.draw( graphics );
	}
}