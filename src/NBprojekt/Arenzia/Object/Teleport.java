package NBprojekt.Arenzia.Object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import NBprojekt.Arenzia.Main.Game;
import NBprojekt.Arenzia.TileMap.Map;

public class Teleport extends Entity{

	private ArrayList < BufferedImage[] > sprites; 
	
	public final static int OPEN = 0;
	public final static int CLOSED = 1;

	public Teleport(Map map) {
		super(map); 
		width = 140;
		height = 220;
		
		try {
			BufferedImage sprite; 
			sprite = ImageIO.read( 
					getClass().getResourceAsStream( "/map/teleport.gif" ) ); 
			
			sprites = new ArrayList < BufferedImage[]> (); 
			for ( int i = 0; i < 2 ; i++ ) {
				BufferedImage [] bufferedImage = new BufferedImage[4]; 
				
				// read sprites
				for ( int j = 0; j < bufferedImage.length; j++ ){ 
					bufferedImage[ j ] = sprite.getSubimage( j * width, i * height, width, height);
				} 
				sprites.add( bufferedImage );
			}	
			System.out.println("" + sprites.get(0).length);
			animation = new Animation();

			animation.setFrames( sprites.get( CLOSED ) );
			animation.setDelay( 400 );
			animation.setRepeat( true );
		} catch ( Exception e ) {
			System.out.println("Can not load the ninja \n");
			e.printStackTrace();
		}
	} 
	public void update () {  
		animation.update();
	}
	public void draw (Graphics2D graphics) {
		setMapPosition();
		graphics.drawImage( animation.getImage(), (int) (x + xMap - width / 2 ),
			    (int) ( y + yMap - height / 2 ), null);
		
		// Draw hitbox
//		graphics.setColor( Color.ORANGE );
//		if ( Game.debugConsole )
//			graphics.drawRect((int) (x + xMap - collisionWidth / 2), 
//					(int) (y + yMap - collisionHeight / 2), collisionWidth, collisionHeight);
	}
}
