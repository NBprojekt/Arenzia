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

public class Bird extends Enemy {
	private ArrayList < BufferedImage[] > sprites; 
	
	// Animation
	private final int FLY = 0; 
	private final int DEAD = 1; 
	private Healthbar healthbar;

	public Bird ( Map map, boolean blue ) {
		super( map );
		
		moveSpeed = 0.8;
		maxSpeed = 1.8;
		fallSpeed = 0.1;
		maxFallSpeed = 1.0;
		
		width = 111;
		height = 111;
		
		collisionWidth = 100;
		collisionHeight = 160;
		
		damage = 1;
		deadTimer = 10;
		health = maxHealth = 3;
		
		facingRight = false;
		left = true; 
		remove = false;
		
		try {
			BufferedImage sprite;
			
			if ( blue )
				sprite = ImageIO.read( 
					getClass().getResourceAsStream( "/entity/enemy/blueBird.gif") );
			else 
				sprite = ImageIO.read( 
					getClass().getResourceAsStream( "/entity/enemy/greenBird.gif") );
			
			sprites = new ArrayList < BufferedImage[]> ();  
			BufferedImage [] bufferedImage = new BufferedImage[ 4 ];  
			for ( int i = 0; i < 4 ; i++ ) 
				bufferedImage[ i ] = sprite.getSubimage( i * width, 0, width, height); 
			 
			sprites.add( bufferedImage );
			sprites.add(new BufferedImage[]{sprite.getSubimage(0, height, width, height)});
		} catch ( Exception e ) {
			System.out.println("Can not load the bird \n");
			e.printStackTrace();
		}
		
		// animations
		animation = new Animation () ;
		animation.setFrames( sprites.get( FLY ) );
		animation.setDelay( 300 );
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
			setPosition( x, yTemp); 
			// If dead 
			long elapsedDead =  (System.nanoTime() - deadTimer ) / 1000000;
			if ( elapsedDead > 400 )
				remove = true; 
		}
		else { 
			// If alive move
			setPosition( xTemp, yTemp); 
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
							    (int) (y + yMap - height / 2 ) - 40, null);
		else 
			graphics.drawImage( animation.getImage(), (int) (x + xMap - width / 2 + width),
							    (int) (y + yMap - height / 2 ) - 40, - width, height, null);
		
		// Draw hitbox
		if ( Game.debugConsole ) { 
			graphics.setColor( Color.ORANGE );
			graphics.drawRect((int) (x + xMap - collisionWidth / 2), 
					(int) (y + yMap - collisionHeight / 2), collisionWidth, collisionHeight);
		}
		
		// Draw healthbar
		healthbar.draw( graphics );
	}
}