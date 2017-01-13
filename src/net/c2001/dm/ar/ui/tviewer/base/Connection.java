package net.c2001.dm.ar.ui.tviewer.base;

import java.awt.Color;

/**
 * Connection between {@link Nodes}
 * @author Lin Dong
 *
 */
public class Connection {
	/**
	 * Index of the left node.
	 */
	public int src;
	/**
	 * Index of the right node.
	 */
	public int dst;
	/**
	 * Color of this line.
	 */
	public Color color = null;
	/**
	 * Strength of this connection, between 0 and 1.
	 */
	public float strength;
	
}
