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

public class Water extends Enemy {
	private BufferedImage sprites; 

	public Water( Map map) {
		super( map );
		
		moveSpeed = 0.0;
		maxSpeed = 0.0;
		fallSpeed = 999.0;
		maxFallSpeed = 999.0;
		
		width = 100;
		height = 100;
		
		collisionWidth = 100;
		collisionHeight = 75;
		
		damage = 999;
		deadTimer = 10;
		health = maxHealth = 999;
		
		facingRight = false;
		left = true; 
		remove = false;
		
		try {
			sprites = ImageIO.read( getClass().getResourceAsStream( "/entity/enemy/water.gif" ) );	
		} catch ( Exception e ) {
			System.out.println("Can not load the water \n");
			e.printStackTrace();
		}
	}

	
	@Override
	public void update () { 
		getNextPosition();
		checkMapCollision();
	}
	
	@Override
	public void draw ( Graphics2D graphics ) {
		setMapPosition(); 
		graphics.drawImage( sprites, (int) (x + xMap - width / 2 ),
						    (int) ( y + yMap - height / 2), null); 
		// Draw hitbox
		graphics.setColor( Color.ORANGE );
		if ( Game.debugConsole )
			graphics.drawRect((int) (x + xMap - collisionWidth / 2), 
					(int) (y + yMap - collisionHeight / 2), collisionWidth, collisionHeight);
	}
}