package net.c2001.utils.ui.atom;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;

/**
 * {@link JPanel} with a {@link JTable} in it.
 * 
 * @author Lin Dong
 * 
 */
public class JTablePanel extends JPanel implements Checkable
{
	//Visual Editor is suggested.
	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;

	/**
	 * Create a {@link JTablePanel}.
	 */
	public JTablePanel()
	{
		super();
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
		this.setLayout(new BorderLayout());
		this.add(getJScrollPane(), BorderLayout.NORTH);
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
			jScrollPane.setViewportView(getJTablePrivate());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return {@link JTable}
	 */
	private JTable getJTablePrivate()
	{
		if (jTable == null)
		{
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * Get the {@link JTable} in {@link JTablePanel}.
	 * 
	 * @return the {@link JTable} in {@link JTablePanel} on success,
	 * {@code null} otherwise.
	 */
	public JTable getJTable()
	{
		if(checkInit())
			return jTable;
		else
		return null;
	}

	@Override
	public boolean checkAll()
	{
		if(checkInit() && jTable.getColumnCount() > 0)
			return true;
		else 
			return false;
	}

	@Override
	public boolean checkInit()
	{
		if(jScrollPane != null && jTable != null)
			return true;
		else
			return false;
	
	}

}
