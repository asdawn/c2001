package net.c2001.dm.ar.ui.spatialdata;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import net.c2001.utils.ui.FileFilters;
import net.c2001.utils.ui.atom.JFileAndTextFieldPanel;


/**
 * {@link JPanel} to select raster/shape file as item and set its alias.
 * 
 * @author Lin Dong
 * 
 */
public class JRPFieldPanel extends JFileAndTextFieldPanel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Create a {@link JRPFieldPanel}.
	 * 
	 * @param i
	 *            number of file, will be shown in the left
	 *            of the text field.
	 */
	public JRPFieldPanel(int i)
	{
		super(15, "Select raster/shape file", null, FileFilters.getShapeFilter(),
				JFileChooser.FILES_ONLY, 10, "File", "Alias",
				"File " + i + " ", false, true);

	}
}
