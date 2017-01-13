package net.c2001.dm.ar.ui.tviewer.base;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;

import net.c2001.dm.ar.ui.tviewer.JDrawingPanel;

/**
 * Drawing region used to draw connections between two set of nodes. 
 * 
 * @author Lin Dong
 * 
 */
public class Connections extends Drawings {

	private int width, height;
	private Collection<Connection> connections = null;
	private float lineWidth;
	private Nodes left, right;
	

	/**
	 * Create a {@link Connection}.
	 * 
	 * @param width
	 *            width of this drawing region.
	 * @param left
	 *            {@link Nodes} on the left side.
	 * @param right
	 *            {@link Nodes} on the right side.
	 * @param connections connections to draw.
	 * @param lineWidth width of line, dash line will be used
	 * if it is less than 1.
	 */
	public Connections(int width, Nodes left, Nodes right,
			Collection<Connection> connections, float lineWidth) {
		if (left == null || right == null || connections == null)
			throw new NullPointerException(
					"Connections or Nodes should not be null.");
		this.left = left;
		this.right = right;
		if (lineWidth > 5)
			this.lineWidth = lineWidth;
		else
			this.lineWidth = 5;

		this.connections = connections;
		if (width > 30)
			this.width = width;
		else
			this.width = 30;
		this.height = Math.max(left.getHeight(), right.getHeight());
		System.out.println(connections.size());
	}

	@Override
	public void draw(Graphics g) {
		Color defaultColor = g.getColor();
		for (Connection connection : connections) {
			if (connection.color != null)
				g.setColor(connection.color);
			else
				g.setColor(defaultColor);
			float lw = lineWidth * connection.strength;
			if (lw > 0) {				
				JDrawingPanel.setLineWidth(g, lw);
				g.drawLine(left.getRightConnectPointsX() + (int) lw / 2,
						left.getRightConnectPointsY()[connection.src],
						right.getLeftConnectPointsX() - (int) lw / 2,
						right.getLeftConnectPointsY()[connection.dst]);
			}
		}
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
}