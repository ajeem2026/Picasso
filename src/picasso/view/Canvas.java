package picasso.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import picasso.model.Pixmap;

/**
 * The canvas on which to present the image.
 * 
 * @author Robert Duvall (rcd@cs.duke.edu)
 *
 */
@SuppressWarnings("serial")
public class Canvas extends JPanel {

	/** keep track of the frame that contains this container */
	private JFrame myContainer;

	/** the pixel map of the displayed image */
	private Pixmap myPixmap;

	/**
	 * 
	 * @param container
	 */
	public Canvas(JFrame container) {
		this(container, null);
	}

	/**
	 * 
	 * @param container
	 * @param pixName
	 */
	public Canvas(JFrame container, String pixName) {
		setBorder(BorderFactory.createLoweredBevelBorder());
		myContainer = container;
		myPixmap = new Pixmap(pixName);
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				myPixmap.setSize(getSize());
			}
		});
		refresh();
	}

	public Pixmap getPixmap() {
		return myPixmap;
	}

	public void refresh() {
		if (!myPixmap.getSize().equals(getSize())) {
			setSize(myPixmap.getSize());
			myContainer.setTitle(myPixmap.getName());
			myContainer.pack();
		}
		repaint();
	}

	public void paintComponent(Graphics pen) {
		super.paintComponent(pen);
		myPixmap.paint(pen);
	}

	public void setSize(Dimension size) {
		setPreferredSize(size);
		setMinimumSize(size);
		super.setSize(size);
		myPixmap.setSize(size);
		
		
	}
}