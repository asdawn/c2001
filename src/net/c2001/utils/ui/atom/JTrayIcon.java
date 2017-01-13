package net.c2001.utils.ui.atom;

import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.TrayIcon;

/**
 * Tray icon.
 * @author Lin Dong
 *
 */
public class JTrayIcon extends TrayIcon implements Checkable{

	private boolean inited = false;
	

	/**
	 * Creates a {@link JTrayIcon} with the specified image, 
	 * tooltip and popup menu.
	 * @param image the {@link Image} to be used.
	 * @param tooltip the string to be used as tooltip text; 
	 * 		if the value is {@code null} no tooltip is shown.
	 * @param popup the menu to be used for the tray icon's popup menu; 
	 * if the value is {@code null} no popup menu is shown.
	 */
	public JTrayIcon(Image image, String tooltip, PopupMenu popup) {
		super(image, tooltip, popup);
		inited = true;
	}

	/**
	 * Creates a {@link JTrayIcon} with the specified image 
	 * and tooltip.
	 * @param image the {@link Image} to be used.
	 * @param tooltip the string to be used as tooltip text; 
	 * 		if the value is {@code null} no tooltip is shown.
	 */
	public JTrayIcon(Image image, String tooltip) {
		super(image, tooltip);
		inited = true;
	}

	/**
	 * Creates a {@link JTrayIcon} with the specified image.
	 * @param image the {@link Image} to be used.
	 */
	public JTrayIcon(Image image) {
		super(image);
		inited = true;
	}

	@Override
	public boolean checkInit() {
		return inited;
	}

	@Override
	public boolean checkAll() {
		return checkInit();
	}

	


}
