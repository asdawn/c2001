package net.c2001.dm.ar.ui.tviewer.base;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.security.InvalidParameterException;

import net.c2001.dm.ar.ui.tviewer.JDrawingPanel;


/**
 * Drawing region used to draw a group of nodes.
 * One land cover type at a certain time is a node,
 * {@link Nodes} is the nodes of the same time.
 * 
 * @author Lin Dong
 *
 */
public class Nodes extends Drawings {

	private int width, height, gap;
	/**
	 * Names of nodes, the land cover types.
	 */
	private String[] types;
	/**
	 * Fonts of texts in this drawing region.
	 */
	private Font font;
	/**
	 * Background and foreground colors of nodes. 
	 */
	private Color[] bg,fg;
	/**
	 * Line width.
	 */
	private float lineWidth;
	
	
	/**
	 * Create a {@link Nodes}.
	 * @param width the width of a node(a box with text in it). This value should be
	 * no less than 20.
	 * @param height the width of a node(a box with text in it). This value should be
	 * no less than 20.
	 * @param gap the gap between nodes, no less than 0.
	 * @param types content to display in nodes, the land cover types.
	 * @param bg background colors of nodes.
	 * @param fg foreground colors of nodes.
	 * @param font fonts of nodes.
	 * @param lineWidth line width to use, no less than 1.
	 */	
	public Nodes(int width, int height, int gap, String[] types,
			Color[] bg, Color[] fg, 
			Font font, float lineWidth) {
		init(width, height, gap, types, bg, fg, font, lineWidth);
	}
	
	/**
	 * Create {@link Nodes}, use default font, color and line width.
	 * @param width width the width of a node(a box with text in it). This value should be
	 * no less than 20.
	 * @param height height the width of a node(a box with text in it). This value should be
	 * no less than 20.
	 * @param gap gap the gap between nodes, no less than 0.
	 * @param types content to display in nodes, the land cover types.
	 */
	public Nodes(int width, int height, int gap, String[] types) {
		init(width, height, gap, types, null, null, null, 1);
	}
	
	/**
	 * Create {@link Nodes}, use default font, color and line width.
	 * The width of a node is 40, the height is 30, and the gap 
	 * between nodes is 4.
	 */
	public Nodes(String[] types) {
		init(40, 30, 4, types, null,null,null, 1);
	}
	
	/**
	 * Initialize nodes.
	 * @param width the width of a node(a box with text in it). This value should be
	 * no less than 20.
	 * @param height the width of a node(a box with text in it). This value should be
	 * no less than 20.
	 * @param gap the gap between nodes, no less than 0.
	 * @param types content to display in nodes, the land cover types.
	 * @param bg background colors of nodes.
	 * @param fg foreground colors of nodes.
	 * @param font fonts of texts.
	 * @param lineWidth line width to use, no less than 1.
	 */
	private void init(int width, int height, int gap, String[] types,
			Color[] bg, Color[] fg, 
			Font font, float lineWidth) {
		if(types == null || types.length ==0)
			throw new InvalidParameterException("Type should not be empty.");
		if((bg != null && bg.length != types.length) ||
				(fg != null && fg.length != types.length)
			)
			throw new InvalidParameterException("Type should not be empty.");
		this.types = types;
		if(width > 20)
			this.width = width;
		if(height > 10)
			this.height = height;
		if(gap > 0)
			this.gap = gap;
		if(font != null)
			this.font = font;
		if(bg != null)
			this.bg = bg;
		if(fg != null)
			this.fg = fg;
		if(lineWidth > 1)
			this.lineWidth = lineWidth;
		else
			this.lineWidth = 1;
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return types.length*height + (types.length-1)*gap;
	}
	
	/**
	 * The y coordinates of left connection points of nodes. 
	 * Connections to nodes in the left will connect to these points.
	 * @return y coordinate of left connection point of nodes.
	 */
	public int[] getLeftConnectPointsY() {
		int[] ys = new int[types.length];
		int y = height/2 + this.y;
		for(int i =0; i < ys.length; i++) {
			ys[i] = y;
			y += (height+gap);
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
		return this.x;
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
		return width + this.x;
	}
	
	@Override
	public void draw(Graphics g) {
		JDrawingPanel.push(g);
		JDrawingPanel.setLineWidth(g, lineWidth);
		int x0 = x, y0 = y;
		int textX, textY;
		int i = 0;
		for (String type : types) {
			if(bg != null) {
				g.setColor(bg[i]);
				g.fillRect(x0, y0, width, height);
			}
			g.setColor(Color.BLACK);
			g.drawRect(x0, y0, width, height);
			if(fg != null) {
				g.setColor(fg[i]);
			}
			if(font != null) {
				g.setFont(font);
			}		
			int wordWidth = g.getFontMetrics().stringWidth(type);
			int wordHeight = g.getFontMetrics().getHeight();
			textY = y0 + wordHeight/2 +height/2 - (int)lineWidth;
			if(wordWidth > width)
				textX = x0;
			else
				textX = (width-wordWidth)/2 + x0;
			g.drawString(type, textX, textY);
			y0 += height+gap;
			i++;
		}
		JDrawingPanel.pop(g);
	}

	/**
	 * Get background colors of nodes.
	 * @return background colors of nodes.
	 */
	public Color[] getBackgroundColor() {
		return bg;
	}

	/**
	 * Get foreground colors of nodes.
	 * @return foreground colors of nodes.
	 */
	public Color[] getForegroundColor() {
		return fg;
	}
}
