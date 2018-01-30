package NBprojekt.Arenzia.Object.Enemies;

import java.awt.Color;
import java.awt.Graphics2D;

import NBprojekt.Arenzia.Object.Enemy;

public class Healthbar { 
	private int health, maxHealth;
	private int width, height;
	private double x, y;
	private int xMap, yMap;
	private int cWidth, cHeight;
	private Color red, green;
	
	public Healthbar ( Enemy enemy ) { 
		red = Color.RED;
		green = Color.GREEN;
		height = 5; 

		width = cWidth = enemy.getCollisionWidth();  
		cHeight = enemy.getCollisionHeight();
		health = maxHealth = enemy.getMaxHealth();  
	} 
	
	public void update ( Enemy enemy ) {
		// Calculaete healht in %
		health = enemy.getHealth(); 
		health = health * 100 / maxHealth;  

		// Position
		x = enemy.getX();
		y = enemy.getY();
		xMap = enemy.getXMap();
		yMap = enemy.getYMap();
	}
	
	public void draw ( Graphics2D graphics ) { 
		// Hit 
		graphics.setColor( red );
		graphics.fillRect( (int) ( x + xMap - cWidth / 2 ), (int) y + yMap - cHeight / 2 , 
				width, height);
		
		// Health point
		graphics.setColor( green );
		graphics.fillRect( (int) ( x + xMap - cWidth / 2 ), (int) y + yMap - cHeight / 2 , 
				width * health / 100, height);
	}
}


















