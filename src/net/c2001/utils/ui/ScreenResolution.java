package net.c2001.utils.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JFrame;


/**
 * Operations about screen resolution and alignments.
 * @author Lin Dong
 */
public class ScreenResolution
{
	/**
	 * No instance is needed.
	 */
	private ScreenResolution()
	{

	}

	/**
	 * Get current screen resolution.
	 * 
	 * @return current screen resolution.
	 */
	public static Dimension getScreenResolution()
	{
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		return scrSize;
	}

	/**
	 * Get the x coordinate of the center of the screen. 
	 * 
	 * @return the x coordinate of the center of the screen. 
	 */
	public static int getXCenter()
	{
		return ScreenResolution.getScreenResolution().width / 2;
	}

	/**
	 * Get the x coordinate of the center of the screen. 
	 * 
	 * @return the x coordinate of the center of the screen. 
	 */
	public static int getYCenter()
	{
		return ScreenResolution.getScreenResolution().height / 2;
	}
	
	/**
	 * Center a windows.
	 * @param win {@link Window} object<br>
	 * Note: {@link JDialog} and {@link JFrame} are subclasses of 
	 * {@link Window}.
	 */
	public static void centerComponent(Window win)
	{
		int xCenter = getXCenter();
		int yCenter = getYCenter();
		int width = win.getWidth();
		int height = win.getHeight();
		int x = xCenter - width/2;
		int y = yCenter - height/2;
		win.setLocation(x, y);		
	}
	
	/**
	 * Put window {@code left} to the left of window {@code main},
	 * and change its height to height of {@code main}.
	 * @param main main window.
	 * @param left window to left align.
	 */
	public static void leftAlign(Window main, Window left)
	{		
		int mainX = main.getX();
		int mainY = main.getY();
		left.setSize(left.getWidth(), main.getHeight());
		left.setLocation(mainX - left.getWidth(), mainY);
	}
}
