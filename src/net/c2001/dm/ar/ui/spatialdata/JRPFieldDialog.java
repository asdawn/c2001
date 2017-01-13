package net.c2001.dm.ar.ui.spatialdata;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JScrollPane;
import net.c2001.utils.ui.atom.JOKCancelPanel;
import net.c2001.utils.ui.ScreenResolution;
import net.c2001.utils.ui.atom.Checkable;

/**
 * Dialog for getting the aliases of raster/shape files. These aliases
 * will be used as item names.
 * @author Lin Dong
 *
 */
public class JRPFieldDialog extends JDialog implements Checkable
{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JRPFieldsPanel jRPFieldsPanel = null;
	private JOKCancelPanel JOKCancelPanel = null;
	private File[] files = null;
	protected static JRPFieldDialog me = null;
	protected boolean ok = false;
	private JScrollPane jScrollPane = null;

	/**
	 * Create a {@link JRPFieldDialog}.
	 * 
	 * @param owner
	 *            owner of this dialog.
	 */
	protected JRPFieldDialog(Frame owner, File[] files)
	{
		super(owner);
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
		this.setModal(true);
		this.setContentPane(getJContentPane());
		this.setTitle("raster/shape files as fields");
		this.pack();
		this.setPreferredSize(new Dimension(this.getWidth(), 500));
		ScreenResolution.centerComponent(this);
		
		if (checkInit())
		{
			JOKCancelPanel.getOKButton().addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (checkAll())
					{
						ok = true;
						setVisible(false);
					}
					else
					{
						JOptionPane.showMessageDialog(null, 
								"Please give an alias to each files",
								"Parameters not set",  
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			JOKCancelPanel.getCancelButton().addActionListener(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							ok = false;
							setVisible(false);
						}
					});
		}
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return {@link JPanel}
	 */
	private JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJOKCancelPanel(), BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	@Override
	public boolean checkInit()
	{
		if (JOKCancelPanel != null && JOKCancelPanel.checkInit()
				&& jRPFieldsPanel != null && jRPFieldsPanel.checkInit())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean checkAll()
	{
		if (checkInit() && JOKCancelPanel.checkAll()
				&& jRPFieldsPanel.checkAll())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * This method initializes jRPFieldsPanel
	 * 
	 * @return {@link JRPFieldsPanel}
	 */
	private JRPFieldsPanel getJRPFieldsPanel()
	{
		if (jRPFieldsPanel == null)
		{
			jRPFieldsPanel = new JRPFieldsPanel(files);
		}
		return jRPFieldsPanel;
	}

	/**
	 * This method initializes JOKCancelPanel
	 * @return   {@link JOKCancelPanel}  
	 * @uml.property  name="jOKCancelPanel"
	 */
	private JOKCancelPanel getJOKCancelPanel()
	{
		if (JOKCancelPanel == null)
		{
			JOKCancelPanel = new JOKCancelPanel();
		}
		return JOKCancelPanel;
	}
	
	@Override
	public void dispose()
	{
		if(checkInit())
			jRPFieldsPanel.dispose();
		super.dispose();
	}
	
	/**
	 * Reset the file list.
	 * @param files list of raster/shape files.
	 */
	protected void resetFiles(File[] files)
	{
		if(checkInit())
		{
			jRPFieldsPanel.setContentFiles(files);
		}
	}
	
	/**
	 * Get the {@link JRPFieldDialog} instance in current JVM.
	 * @param files list of raster/shape files.
	 * 
	 * @return {@link JRPFieldDialog}
	 */
	public static JRPFieldDialog getInstance(File[] files)
	{
		if (me == null)
		{
			me = new JRPFieldDialog(null, files);
		}
		else 
		{			
			me.resetFiles(files);
		}
		me.ok = false;
		me.pack();
		return me;
	}
	
	/**
	 * Show a {@link JRPFieldDialog} to get aliases.
	 * @param files list of shape files
	 * 
	 * @return aliases of shape files on success, {@code null} on failure.
	 * If the file list is empty or the OK button is not selected 
	 * {@code null} will be returned.
	 */
	public synchronized static String[] showRPFieldDialog(File[] files)
	{
		JRPFieldDialog dialog = JRPFieldDialog.getInstance(files);
		dialog.setVisible(true);
		String aliases[] = null;
		if (dialog.ok)
		{
			aliases = dialog.getAliases();
		}
		return aliases;
	}
		
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return {@link JScrollPane}	
	 */
	private JScrollPane getJScrollPane()
	{
		if (jScrollPane == null)
		{
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJRPFieldsPanel());
		}
		return jScrollPane;
	}	
	
	/**
	 * Get aliases of raster/shape files.
	 * @return aliases in {@link JRPFieldsPanel} on success, {@code null}
	 * on failure.
	 */
	public String[] getAliases()
	{
		if(checkAll())
		{
			return jRPFieldsPanel.getAlises();
		
		}
		else 
		{
			return null;
		}
	}
}
