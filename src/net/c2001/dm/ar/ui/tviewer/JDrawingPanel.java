package net.c2001.dm.ar.ui.tviewer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javax.swing.JPanel;

import net.c2001.dm.ar.ui.tviewer.base.Drawings;

/**
 * {@link JPanel} for {@link Drawings}.
 * @author Lin Dong
 *
 */
public class JDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Info info = new Info();
	
	/**
	 * Set width of line to use.
	 * @param g {@link Graphics} object which is used to draw things.
	 * @param width line width, use dash line if it is less than 1.
	 * @param dashBase length of white part in dash line. Length of
	 * line equals to {@code dashBase*width}. {@code dashBase} should be
	 * larger than 1, the length of white part is at least 1.<br>
	 * Note: dashBase will be considered only if dash line is used.
	 */
	public static void setLineWidth(Graphics g, float width, float dashBase) {
		Graphics2D g2d = (Graphics2D) g;
		Stroke stroke = null;
		if(width >= 1) {
			stroke = new BasicStroke(width);
		}
		else if(width > 0) {
			float dash = dashBase;
			if(width < 0.3)
				width = 0.6f;
			else if(width < 0.6)
				width = 0.8f;
			else if(width < 0.9)
				width = 1f;
			float white = dashBase * width;
			if(white < 1)
				white = 1;
			stroke = new BasicStroke(1, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_ROUND,
					1.0f,new float[]{white, dash},0f);
		}
		g2d.setStroke(stroke);
	}
	
	/**
	 * Set the width of lines.
	 * @param g {@link Graphics} object which is used to draw things.
	 * @param width width line width, use dash line if it is less than 1.
	 * The white part of a dash line equals to 10*{@code width}, if
	 * this value is smaller than 1, 1 will be used.
	 */
	public static void setLineWidth(Graphics g, float width) {
		setLineWidth(g, width, 10f);
	}
	
	/**
	 * Save current font, color and line width of {@code g}.
	 * @param g {@link Graphics} object.
	 */
	public static void push(Graphics g) {
		info.pushed = true;
		info.font = g.getFont();
		info.color = g.getColor();
		info.stroke =  ((Graphics2D)g).getStroke();
	}
	
	/**
	 * Load font, color and line width for {@code g}. If 
	 * {@code pop()} is called before {@code push()}, a
	 * {@link NullPointerException} will be thrown.
	 * @param g {@link Graphics} object.
	 */
	public static void pop(Graphics g) {
		if(info.pushed == false)
			throw new NullPointerException("Pop before push.");
		info.pushed = false;
		g.setFont(info.font);
		g.setColor(info.color);
		((Graphics2D)g).setStroke(info.stroke);
	}
	
	/**
	 * Stores font, color and line width of a {@link Graphics} object.
	 * @author Lin Dong
	 *
	 */
	static class Info {
		boolean pushed = false;
		Font font;
		Color color;
		Stroke stroke;
		};
	
}
