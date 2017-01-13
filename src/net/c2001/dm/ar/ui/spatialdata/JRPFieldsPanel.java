package net.c2001.dm.ar.ui.spatialdata;

import javax.swing.JPanel;
import java.io.File;
import javax.swing.BoxLayout;

import net.c2001.utils.ui.atom.Checkable;


/**
 * {@link JPanel} to get the aliases for raster/shape files. These
 * aliases are used as item names.
 * 
 * @author Lin Dong
 * 
 */
public class JRPFieldsPanel extends JPanel implements Checkable
{

	private static final long serialVersionUID = 1L;
	private File[] files = null;
	private JRPFieldPanel[] panels = null;

	/**
	 * Create a {@link JRPFieldsPanel}.
	 * 
	 * @param files
	 *            list of raster/shape file.
	 */
	public JRPFieldsPanel(File[] files)
	{
		super();
		this.files = files;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(300, 200);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setContentFiles(files);
	}

	/**
	 * Clear current contents and reset the file list.
	 * 
	 * @param files
	 *            list of raster/shape file.
	 */
	public void setContentFiles(File[] files)
	{
		if (files != null)
		{
			this.files = files;
			this.removeAll();
			panels = null;
			int count = files.length;
			panels = new JRPFieldPanel[count];
			for (int i = 0; i < count; i++)
			{
				panels[i] = new JRPFieldPanel(i + 1);
				panels[i].setPath(files[i].getPath());
				this.add(panels[i]);
			}
		}
	}

	/**
	 * Release resources.
	 */
	public void dispose()
	{
		this.removeAll();
		panels = null;
		files = null;
	}

	@Override
	public boolean checkInit()
	{
		if (panels != null)
		{
			for (JRPFieldPanel panel : panels)
			{
				if (panel.checkInit() == false)
					return false;
			}
		}
		return true;
	}

	@Override
	public boolean checkAll()
	{
		if (panels != null && checkInit())
		{
			for (JRPFieldPanel panel : panels)
			{
				if (panel.checkAll() == false)
					return false;
			}
		}
		return true;
	}

	/**
	 * Get the aliases of raster/shape files as item names.
	 * @return aliases in {@link JRPFieldsPanel} on success,
	 * {@code null} on failure.
	 */
	public String[] getAlises()
	{
		String[] aliases = null;
		if (checkAll())
		{
			
			try
			{
				int count = panels.length;
				aliases = new String[count];
				for (int i = 0; i < count; i++)
				{
					JRPFieldPanel panel =
							(JRPFieldPanel) this.getComponent(i);
					aliases[i] = panel.getText();
				}
				return aliases;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				aliases = null;
			}
		}
		return aliases;		
	}
}
