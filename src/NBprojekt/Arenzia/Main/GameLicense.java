package NBprojekt.Arenzia.Main;
 
import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameLicense { 
	private BufferedReader bufferedReader; 
	private ArrayList < String > text; 
	
	public GameLicense ( String filePath ) { 
		try { 
			bufferedReader  = new BufferedReader( new InputStreamReader ( 
					getClass().getResourceAsStream(filePath) ) );
			System.out.println("______________________________________\n");
		} catch(Exception e) {
			System.out.println("Cant fint file\n");
            e.printStackTrace();
        } 
		
        if( bufferedReader != null) {
            try {
            	bufferedReader.close();
            } catch(IOException e) {
            	System.out.println("Cant close file \n");
                e.printStackTrace();
            }
        } 
        
        text = new ArrayList < String > ();
        createBlocks ( bufferedReader ) ;
	}
	
	// Write every line in a textfile into a arraylist
	private void createBlocks ( BufferedReader br ) {
		String line = null;
		try { 
			while ( (line = br.readLine()) != null ) { 
				text.add( line );
				System.out.println(line);
			}
		} catch (IOException e) { 
			System.out.println("Cant read file \n");
			e.printStackTrace();
		}
	}
	
	// If called the next 10 lines will be displayed
	public void display ( ) { 
		if ( text == null )
			return;
		
		if ( text.size() >= 10 ) {
			for ( int i = 0; i < 10; i++ ) 
				System.out.println( text.get( i ) );
			for ( int i = 0; i < 10; i++ ) 
				text.remove( i );
		}
		else {
			for ( int i = 0; i < text.size(); i++ )
				System.out.println( text.get( i ) );
			text = null;
		}
	}
}
















































