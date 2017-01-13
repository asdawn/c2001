package net.c2001.dm.ar.ui.tviewer.base;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.security.InvalidParameterException;
import javax.swing.JPanel;

/**
 * {@link JPanel} to draw nodes and connections. Nodes and connections
 * are subclass of {@link Drawings}, so parameter of this panel is 
 * an array of {@link Drawings}. 
 * @author Lin Dong
 *
 */
public class JConnectionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private int width, height;
	Drawings[] drawings = null;
	/**
	 * Create a {@link JConnectionPanel} to draw given {@link Drawings}.
	 * @param drawings {@link Drawings} objects, the order should be
	 * {@link Nodes}, {@link Connections}, {@link Nodes}, ... ,
	 * {@link Nodes}, {@link Connections}, {@link Nodes}. That is,
	 * the starting and ending should be {@link Nodes}, and 
	 * {@link Connections} should be placed between {@link Nodes}.
	 * If the count of parameters is less than 3 or parameters are
	 * not in the right order, an {@link InvalidParameterException}
	 * will be thrown. If there are {@code null} in the parameters,
	 * an {@link NullPointerException} will be thrown.
	 */
	public JConnectionPanel(Drawings ...drawings) {
		init(drawings);		
	}
	
	/**
	 * Initialize this {@link JConnectionPanel}.
	 * @param drawings {@link Drawings} objects, the order should be
	 * {@link Nodes}, {@link Connections}, {@link Nodes}, ... ,
	 * {@link Nodes}, {@link Connections}, {@link Nodes}. That is,
	 * the starting and ending should be {@link Nodes}, and 
	 * {@link Connections} should be placed between {@link Nodes}.
	 * If the count of parameters is less than 3 or parameters are
	 * not in the right order, an {@link InvalidParameterException}
	 * will be thrown. If there are {@code null} in the parameters,
	 * an {@link NullPointerException} will be thrown.
	 */
	private void init(Drawings ...drawings) {
		if(drawings.length < 3 || drawings.length%2 != 1)
			throw new InvalidParameterException("Invalid number of Drawing objects.");
		for(int i = 0; i < drawings.length; i++) {
			if(drawings[i] == null)
				throw new NullPointerException("Drawings should not be null.");
			if((i%2 == 0 && drawings[i] instanceof Nodes == false) ||
				(i%2 == 1) && drawings[i] instanceof Connections == false)
				throw new InvalidParameterException("Connections should be braced by Nodes.");
		}
		this.drawings = drawings;
		width = 0;
		height = 0;
		for(int i = 0; i < drawings.length; i++) {
			width = drawings[i].getWidth();
			//Set height according to Nodes, the largest one is used.
			if(i%2 == 0) {
				if(height < drawings[i].getHeight())
					height = drawings[i].getHeight();
			}
		}
		//TODO adjust the gap if necessary.
		this.setSize(width, height);
		this.setPreferredSize(this.getSize());
		//Update x, y coordinates of the upper-left corner
		int x0 = 0;
		int y0 = 0;
		for (int i = 0; i < drawings.length; i++) {
			if(drawings[i].getHeight() == height)
				y0 = 0;
			else
				y0 = (height - drawings[i].getHeight())/2;
			drawings[i].x = x0;
			drawings[i].y = y0;
			x0+=drawings[i].getWidth();			
		}
		
		/*
		//for-each should not be used here, though it works.
		//
		for (Drawings drawing : drawings) {
			if(drawing.getHeight() == height)
				y0 = 0;
			else
				y0 = (height - drawing.getHeight())/2;
			drawing.x = x0;
			drawing.y = y0;
			x0+=drawing.getWidth();			
		}
		*/
	}
	
	/**
	 * Use anti-aliasing to make the drawings clearer.
	 * @param g {@link Graphics2D} used to draw nodes and connections.
	 */
	private void antiAliasing(Graphics2D g){
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	@Override
	public void paint(Graphics g) {
		antiAliasing((Graphics2D) g);
		//Draw connections, then draw nodes.
		int i = 0;
		for (Drawings drawing : drawings) {
			if(i % 2 == 1)
				drawing.draw(g);
			i++;
		}
		i = 0;
		for (Drawings drawing : drawings) {
			if(i % 2 == 0)
				drawing.draw(g);
			i++;
		}
	}
}
