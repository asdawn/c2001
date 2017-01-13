package net.c2001.utils.ui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Create borders for panels.
 * @author Lin Dong
 */
public final class BorderMaker
{
	/**
	 * No instance is needed.
	 */
	private BorderMaker()
	{

	}

	/**
	 * Create a {@link TitledBorder}.
	 * 
	 * @param title
	 *            title of the {@link TitledBorder}.
	 * @return {@link TitledBorder}.
	 */
	public static TitledBorder createTitledBorder(String title)
	{
		TitledBorder border = BorderFactory.createTitledBorder(title);
		return border;
	}

	/**
	 * Add border to  {@code panel} with given title.
	 * 
	 * @param panel
	 *            {@link JPanel}.
	 * @param title
	 *            title of border.
	 */
	public static void addBorder(JPanel panel, String title)
	{
		panel.setBorder(createTitledBorder(title));
	}

}
