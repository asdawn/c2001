package net.c2001.dm.ar.ui.tviewer.base;

import java.awt.Graphics;

/**
 * Drawing region to draw on a component. 
 * @author Lin Dong
 *
 */
public abstract class Drawings {
	/**
	 * 
	 */	
	protected int x;
	
	/**
	 * y coordinate of the upper-left corner of this region.
	 */
	protected int y;
	
	/**
	 * Returns the x coordinate of the upper-left corner of this region.
	 * @return the x coordinate of the upper-left corner of this region.
	 */
	public int getX() {
		return x;
	}
	/**
	 * Set the x coordinate of the upper-left corner of this region.
	 * @param x the x coordinate of the upper-left corner of this region.
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Returns the y coordinate of the upper-left corner of this region.
	 * @return the y coordinate of the upper-left corner of this region.
	 */
	public int getY() {
		return y;
	}
	/**
	 * Set the y coordinate of the upper-left corner of this region.
	 * @param y the y coordinate of the upper-left corner of this region.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Returns the width of this region.
	 * @return width of this region.
	 */
	abstract public int getWidth();
	
	/**
	 * Returns the height of this region.
	 * @return height of this region.
	 */
	abstract public int getHeight();
	
	/**
	 * Draw things using the given {@link Graphics} object.
	 * @param g {@link Graphics} object.
	 */
	abstract public void draw(Graphics g);
}
