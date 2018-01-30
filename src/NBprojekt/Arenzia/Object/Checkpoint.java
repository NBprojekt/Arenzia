package NBprojekt.Arenzia.Object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import NBprojekt.Arenzia.Main.Game;
import NBprojekt.Arenzia.TileMap.Map;

public class Checkpoint extends Entity{

	private ArrayList < BufferedImage[] > flags;  
	private boolean checked, change;
	
	public final static int NOTCHEKCED = 0;
	public final static int CHANGING = 1;
	public final static int CHECKED = 2;

	public Checkpoint(Map map) {
		super(map); 
		width = 128;
		height = 192;
		checked = change = false; 
		try {
			BufferedImage sprite; 
			sprite = ImageIO.read( 
					getClass().getResourceAsStream( "/map/checkpoint.gif" ) ); 
			
			flags = new ArrayList < BufferedImage[]> (); 
			for ( int i = 0; i < 3 ; i++ ) {
				BufferedImage [] bufferedImage = new BufferedImage[8]; 
				
				// read sprites
				for ( int j = 0; j < bufferedImage.length; j++ ){ 
					bufferedImage[ j ] = sprite.getSubimage( j * width, i * height, width, height);
				} 
				flags.add( bufferedImage );
			}	 
			animation = new Animation();

			animation.setFrames( flags.get( NOTCHEKCED ) );
			animation.setDelay( 300 );
			animation.setRepeat( true );
		} catch ( Exception e ) {
			System.out.println("Can not load the Checkpoint \n");
			e.printStackTrace();
		}
	} 
	
	public Point getLocation(){
		return new Point((int)x, (int)y);
	}
	public boolean isChecked(){
		return checked;
	}
	public void setChecked(){
		change  = true; 
	}
	
	public void update () {  
		xMap = map.getX();
		yMap = map.getY();  
		checkMapCollision();  
		if (change){
			animation.setFrames( flags.get( CHANGING ) );   
			animation.setDelay( 150 );
			change = false;
			checked = true;
		}
		else if (animation.hasPlayed() && checked){ 
			animation.setFrames( flags.get( CHECKED ) ); 
			animation.setDelay( 300 );
		}
		animation.update();
	}
	
	public void draw (Graphics2D graphics) {
		setMapPosition();
		graphics.drawImage( animation.getImage(), (int) (x + xMap - width / 2 ),
			    (int) ( y + yMap - height / 2  - 10), null); 
		
		graphics.setColor( Color.ORANGE );
		if ( Game.debugConsole )
			graphics.drawRect((int) (x + xMap - collisionWidth / 2), 
					(int) (y + yMap - collisionHeight / 2), collisionWidth, collisionHeight);
	}
}