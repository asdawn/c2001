package net.c2001.dm.ar.ui.tviewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.security.InvalidParameterException;
import java.util.Collection;


/**
 * {@link JDrawingPanel} to draw nodes.
 * @author Lin Dong
 *
 */
class TypePanel extends JDrawingPanel {

	private static final long serialVersionUID = 3935607636951030933L;
	/**
	 * Line width.
	 */
	private float lineWidth = -1f;
	/**
	 * Width, height, gap of a node (a rectangle with text in it).
	 */
	private int width = 20, height = 10, gap = 0;
	/**
	 * Content of a node.
	 */
	private Collection<String> types = null;
	
	/**
	 * Create a {@link TypePanel}.
	 * @param width width of nodes, no less than 20. It is also this panel's
	 * width.
	 * @param height height of a node, no less than 10.
	 * @param gap gap between nodes.
	 * @param types contents of nodes, land cover types. 
	 * @param color foreground colors of nodes.
	 * @param font font of nodes.
	 * @param lineWidth line width, no less than 1.
	 */	
	public TypePanel(int width, int height, int gap, Collection<String> types,
			Color color, Font font, float lineWidth) {
		super();
		init(width, height, gap, types, color, font, lineWidth);
	}
	
	/**
	 * Create a {@link TypePanel}, using default color and font, line width
	 * is set to 1.
	 * @param width width of nodes, no less than 20. It is also this panel's
	 * width.
	 * @param height height of a node, no less than 10.
	 * @param gap gap between nodes.
	 * @param types contents of nodes, land cover types. 
	 */
	public TypePanel(int width, int height, int gap, Collection<String> types) {
		super();
		init(width, height, gap, types, null, null, 1);
	}
	
	/**
	 * Create a {@link TypePanel}, using default color and font. The height
	 * of a node is 30, width is 40, gap between nodes is 4, line width is 1. 
	 */
	public TypePanel(Collection<String> types) {
		super();
		init(40, 30, 4, types, null, null, 1);
	}
	
	/**
	 * Initialize this panel.width of nodes, no less than 20. 
	 * It is also this panel's width.
	 * @param height height height of a node, no less than 10.
	 * @param gap gap between nodes.
	 * @param types types contents of nodes, land cover types. 
	 * @param color color foreground colors of nodes.
	 * @param font font of nodes.
	 * @param lineWidth line width, no less than 1.
	 */
	private void init(int width, int height, int gap, Collection<String> types,
			Color color, Font font, float lineWidth) {
		if(types == null || types.size() ==0)
			throw new  InvalidParameterException("Type should not be empty.");
		this.types = types;
		if(width > 20)
			this.width = width;
		if(height > 10)
			this.height = height;
		if(gap > 0)
			this.gap = gap;
		if(font != null)
			setFont(font);
		if(color != null)
			setForeground(color);
		if(lineWidth > 1)
			this.lineWidth = lineWidth;
		else
			this.lineWidth = 1;
		int w = width;
		int h = height*types.size()+gap*(types.size()-1);
		this.setPreferredSize(new Dimension(w, h));
		this.setSize(w,h);
	}
	
	@Override
	public void paint(Graphics g) {
		//super.paint(g);
		setLineWidth(g, lineWidth);
		int x0 = 0, y0 = 0;
		int textX, textY;
		for (String type : types) {
			g.drawRect(x0, y0, width, height);
			int wordWidth = g.getFontMetrics().stringWidth(type);
			int wordHeight = g.getFontMetrics().getHeight();
			textY = y0 + wordHeight/2 +height/2 - (int)lineWidth;
			if(wordWidth > width)
				textX = x0;
			else
				textX = (width-wordWidth)/2 + x0;
			g.drawString(type, textX, textY);
			y0 += height+gap;
		}		
	}
	
	/**
	 * The y coordinates of left connection points of nodes. 
	 * Connections to nodes in the left will connect to these points.
	 * @return y coordinate of left connection point of nodes.
	 */
	public int[] getLeftConnectPointsY() {
		int[] ys = new int[types.size()];
		int y = width/2;
		for(int i =0; i < ys.length; i++) {
			ys[i] = y;
			y += (width+gap);
		}
		return ys;
	}
	
	/**
	 * The x coordinate of left connection points of nodes. 
	 * Connections to nodes in the left will connect to this points. 
	 * It equals to the x coordinate of {@link Nodes}
	 * @return x coordinate of left connection point of nodes.
	 */
	public int getLeftConnectPointsX() {
		return this.getX();
	}
	
	/**
	 * The y coordinates of right connection points of nodes. 
	 * Connections to nodes in the right will connect to these points.
	 * @return y coordinate of right connection point of nodes.
	 */
	public int[] getRightConnectPointsY() {
		return getLeftConnectPointsY();
	}
	
	/**
	 * The x coordinate of right connection points of nodes. 
	 * Connections to nodes in the right will connect to this points. 
	 * It equals to the x coordinate of {@link Nodes}
	 * @return x coordinate of right connection point of nodes.
	 */
	public int getRightConnectPointsX() {
		return this.getX()+width;
	}

}
