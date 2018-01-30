package NBprojekt.Arenzia.GameState.Map;
 
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList; 

import javax.imageio.ImageIO;

import NBprojekt.Arenzia.Main.Game;
import NBprojekt.Arenzia.Main.GamePanel;
import NBprojekt.Arenzia.Object.Ninja;

public class HUD {
	
	private Ninja ninja;
	
	private BufferedImage playerIcon, playerIconDead, subImage;
	private BufferedImage spriteHealth;
	private BufferedImage spriteKey, kunai; 
	private BufferedImage allCoins;
	private BufferedImage background;
	private ArrayList < BufferedImage  > health; 
	private ArrayList < BufferedImage > key; 
	private ArrayList < BufferedImage [] > coins; 

	private final int HITLEFT = 0;
	private final int HITRIGHT = 1;
	private final int HPLEFT = 2;
	private final int HPRIGHT = 3; 
	 
	private int min, sek; 
	private long startTime = System.nanoTime();        
	private long elapsed =0; 
	
	public static boolean Coin1 = false;
	public static boolean Coin2 = false;
	public static boolean Coin3 = false;
	public static boolean Coin4 = false;
	public static boolean Coin5 = false;
	public static boolean Coin6 = false;
	public static boolean Key = false;
	
	public HUD ( Ninja ninja ) {
		this.ninja = ninja ;   
		key = new ArrayList < BufferedImage> ();
		health = new ArrayList < BufferedImage> (); 
		coins = new ArrayList < BufferedImage [] >();
		min = sek = 0;
		
		try { 
			// Player icon
			if ( ninja.getMale() ){
				playerIcon = ImageIO.read( getClass().getResourceAsStream( "/map/hud/femaleIcon.gif" ) );
				playerIconDead = ImageIO.read( getClass().getResourceAsStream( "/map/hud/femaleIconDead.gif" ) );
			}
			else {
				playerIcon = ImageIO.read( getClass().getResourceAsStream( "/map/hud/femaleIcon.gif" ) );
				playerIconDead = ImageIO.read( getClass().getResourceAsStream( "/map/hud/femaleIconDead.gif" ) );
			}
			
			// Kunais 
		    kunai = ImageIO.read( getClass().getResourceAsStream( "/map/hud/kunaiHUD.gif" ) );
			
			// Health 
			spriteHealth = ImageIO.read( getClass().getResourceAsStream( "/map/hud/healthHUD.gif") );   
			subImage = null;
			for ( int i = 0; i < 4; i++ ){ 
				subImage = spriteHealth.getSubimage( i * 25, 0, 25, 47 );
				health.add(subImage);
			}  
			
			// Key 
			spriteKey = ImageIO.read( getClass().getResourceAsStream( "/map/hud/key.gif") );  
			subImage = null;
			for ( int i = 0; i < 2; i++ ){ 
				subImage = spriteKey.getSubimage( i * 107, 0, 107, 52 );
				key.add(subImage);
			}  
			
			// Coins 
			allCoins = ImageIO.read( getClass().getResourceAsStream( "/map/hud/coins.gif") );   
			for ( int i = 0; i < 6; i++ ){ 
				BufferedImage [] tmp = new BufferedImage[]{
							allCoins.getSubimage(  0, i * 70, 70, 70 ),
							allCoins.getSubimage( 70, i * 70, 70, 70 )
						}; 
				coins.add(tmp);
			}   
		} catch ( Exception e ) {
			System.out.println("Fail to load HUD");
			e.printStackTrace();
		} 
	} 
			
	private void setTimer () { 
		elapsed = System.nanoTime() - startTime;
		String string = "" + elapsed;
		
		if (string.length() - 8 > 0)
			string = string.substring(0, string.length() - 8);
		else 
			return;

		if (string.length() <= 1 )
			return;
		sek = Integer.parseInt(string.substring(0, string.length()-1)) ;  
		
		if (sek == 60) {
			min++; 
			elapsed = 0;
			startTime = System.nanoTime();
		}
	} 
	public int[] getTime(){
		return new int[]{min,sek};
	}
	public void setTime(int min, int sek){ 
		elapsed = sek;  
		startTime = System.nanoTime() - sek * 1000000;
		this.min = min;
		this.sek = sek;
	}
	public void draw ( Graphics2D graphics ) {
		// Draw background first
		graphics.drawImage( background, 0, 0, null);       
		
			// Player stats
		// Player Icon
		if ( ninja.getHealth() != 0 )
			graphics.drawImage( playerIcon, 5, 10, null);
		else 
			graphics.drawImage( playerIconDead, 5, 10, null);
		
		// Health
		for ( int i = 0; i < ninja.getMaxHealth(); i++ ){
			if ( i < ninja.getHealth() ){
				if ( (i % 2) == 0) // even 
					graphics.drawImage( health.get( HPLEFT ), i * 25 + 180, 30, 25, 47, null);
				else // !even 
					graphics.drawImage( health.get( HPRIGHT ), i * 25 + 180, 30, 25, 47, null);
			}
			else {
				if ( (i % 2) == 0) // even 
					graphics.drawImage( health.get( HITLEFT ), i * 25 + 180, 30, 25, 47, null);
				else // !even 
					graphics.drawImage( health.get( HITRIGHT ), i * 25 + 180, 30, 25, 47, null);
			}
		}
		
		// Kunais
		for ( int i = 0; i < ninja.getMaxKunais() ; i++ ){
			if ( i < ninja.getNumKunais() )
				graphics.drawImage( kunai, i * 50 + 180, 100, 37, 50, null); 
		}
		
		// Play time 
		setTimer();
		String min = "00", sek = "00";
		if (this.min < 10)
			min = "0"+ this.min;
		else
			min = ""+ this.min;
		if (this.sek < 10)
			sek = "0"+ this.sek;
		else
			sek = ""+ this.sek;
		Game.drawCenteredString( min + " : " + sek , GamePanel.WIDTH, 45, 
				graphics, new Font("Rockwell", Font.PLAIN, 25));
		
			// Collectable stuff
		// Key
		if ( Key)
			graphics.drawImage( key.get( 1 ),  GamePanel.WIDTH / 2 - 107 / 2, 70, 107, 52, null); 
		else 
			graphics.drawImage( key.get( 0 ),  GamePanel.WIDTH / 2 - 107 / 2, 70, 107, 52, null);
		
			// Coins 1 to 6
		if ( Coin1 ) graphics.drawImage( coins.get(0)[1],  GamePanel.WIDTH - 80 * 3, 10, 70, 70, null); 
		else graphics.drawImage( coins.get(0)[0],  GamePanel.WIDTH - 80 * 3, 10, 70, 70, null);
		if ( Coin2 ) graphics.drawImage( coins.get(1)[1],  GamePanel.WIDTH - 80 * 2, 10, 70, 70, null); 
		else graphics.drawImage( coins.get(1)[0],  GamePanel.WIDTH - 80 * 2, 10, 70, 70, null);
		if ( Coin3 ) graphics.drawImage( coins.get(2)[1],  GamePanel.WIDTH - 80 * 1, 10, 70, 70, null); 
		else graphics.drawImage( coins.get(2)[0],  GamePanel.WIDTH - 80 * 1, 10, 70, 70, null);
		if ( Coin4 ) graphics.drawImage( coins.get(3)[1],  GamePanel.WIDTH - 80 * 3, 75, 70, 70, null); 
		else graphics.drawImage( coins.get(3)[0],  GamePanel.WIDTH - 80 * 3, 75, 70, 70, null);
		if ( Coin5 ) graphics.drawImage( coins.get(4)[1],  GamePanel.WIDTH - 80 * 2, 75, 70, 70, null); 
		else graphics.drawImage( coins.get(4)[0],  GamePanel.WIDTH - 80 * 2, 75, 70, 70, null);
		if ( Coin6 ) graphics.drawImage( coins.get(5)[1],  GamePanel.WIDTH - 80 * 1, 75, 70, 70, null); 
		else graphics.drawImage( coins.get(5)[0],  GamePanel.WIDTH - 80 * 1, 75, 70, 70, null);
	}
}













