package net.c2001.dm.ar.ui.tviewer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.security.InvalidParameterException;
import java.util.Collection;

import net.c2001.dm.ar.ui.tviewer.base.Connection;


/**
 * {@link JDrawingPanel} to draw connections on it.
 * 
 * @author Lin Dong
 *
 */
public class ConnectionPanel extends JDrawingPanel {
	private static final long serialVersionUID = -6639233474710100536L;
	/**
	 * Connections.
	 */
	private Collection<Connection> connections = null;
	/**
	 * Width of this panel.
	 */
	private int width;
	/**
	 * Line width, no less than 5. The line width of a connection
	 * equals to width*strength.
	 */
	private float lineWidth;
	/**
	 * Panel to draw left and right nodes.
	 */
	private TypePanel left, right;
	
	/**
	 * Create a {@link ConnectionPanel}.
	 * @param width width of this panel, no less than 30. Please create
	 * @param lineWidth line width, no less than 5. The line 
	 * width of a connection equals to width*strength.
	 * @param left left nodes.
	 * @param right right nodes.
	 */
	public ConnectionPanel(int width, float lineWidth, TypePanel left, TypePanel right) {
		super();
		if(left == null || right == null)
			throw new NullPointerException("TypePanel should not be null");
		if(width > 30)
			this.width = width;
		else
			this.width = 30;
		if(lineWidth > 5)
			this.lineWidth = lineWidth;
		else
			this.lineWidth = 5;
		this.setPreferredSize(new Dimension(width, 30));
		this.setSize(width, 30);
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Set connections to draw.
	 * @param connections list of connections to draw. If the list is
	 * empty or {@code null} a {@link InvalidParameterException} will
	 * be thrown.
	 */
	public void setConnections(Collection<Connection> connections ) {
		if(connections == null || connections.size() == 0) 
			throw new InvalidParameterException("Empty connection list.");
		this.connections = connections;
	}
	
	@Override
	public void paint(Graphics g) {
		if(connections == null)
			return;
		this.setSize(width, Math.max(left.getHeight(),right.getHeight()));
		this.setPreferredSize(this.getSize());
		for (Connection connection : this.connections) {
			if(connection.color != null)
				setForeground(connection.color);
			setLineWidth(g, lineWidth*connection.strength);
			g.drawLine(left.getLeftConnectPointsX(), 
					left.getLeftConnectPointsY()[connection.src], 
					right.getRightConnectPointsX(),
					right.getRightConnectPointsY()[connection.dst]);
		}
	}
	
}
