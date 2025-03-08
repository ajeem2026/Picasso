package picasso;

import java.awt.Dimension;

import picasso.view.Frame;

//For splash screen
import picasso.view.SplashScreen;

/**
 * Starting point for Picasso.
 * 
 * @author Robert Duvall (rcd@cs.duke.edu)
 */
public class Main {
	public static final Dimension SIZE = new Dimension(600, 600);

	public static void main(String[] args) {
		
		// Show the splash screen
        SplashScreen splash = new SplashScreen();
        splash.showSplash();
        
		Frame frame = new Frame(SIZE);
		frame.setVisible(true);
	}
}
