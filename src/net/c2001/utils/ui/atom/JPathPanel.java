package net.c2001.utils.ui.atom;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;

/**
 * {@link JPanel} to get file path.
 * 
 * @author Lin Dong
 * 
 */
public class JPathPanel extends JPanel implements Checkable
{

	private static final long serialVersionUID = 1L;
	
	protected JTextField jTextField = null;
	
	private JLabel jLabel = null;
	
	private int defaultSize = 20;
	
	private JPanel jPanel = null;
	
	private String title = null;
	
	private String path = null;
	
	private FileFilter filter = null;
	
	private int type = JFileChooser.FILES_ONLY;

	/**
	 * Create a {@link JPathPanel} instance.
	 * 
	 * @param fieldSize
	 *            size of {@link JTextField} on this panel. 20 will be used 
	 *            if the values is less than 1.
	 * @param title
	 *            title of the file open dialog.
	 * @param path
	 *            default path of the file open dialog.
	 * @param f
	 *            filter of the file open dialog.
	 * @param type
	 *            title of the file open dialog, folder or file, constants of 
	 *            {@link JFileChooser}.
	 */
	public JPathPanel(int fieldSize, String title, String path, FileFilter f,
			int type)
	{
		super();
		this.title = title;
		this.path = path;
		this.type = type;
		this.filter = f;
		if (fieldSize > 0)
			defaultSize = fieldSize;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(173, 50));
		this.add(getJPanel(), BorderLayout.EAST);
		this.add(getJLabel(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return {@link JTextField}
	 */
	private JTextField getJTextField()
	{
		if (jTextField == null)
		{
			jTextField = new JTextField(defaultSize);
			jTextField.addMouseListener(new MouseListener()
			{

				@Override
				public void mouseReleased(MouseEvent arg0)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent arg0)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent arg0)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent arg0)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent arg0)
				{
					JFileChooser fc = new JFileChooser(path);
					fc.setDialogTitle(title);
					fc.setFileFilter(filter);
					fc.setFileSelectionMode(type);
					int retCode = fc.showOpenDialog(jLabel);
					if (retCode == JFileChooser.APPROVE_OPTION)
					{
						jTextField.setText(fc.getSelectedFile().getPath());
					}
				}
			});
		}
		return jTextField;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return {@link JLabel}
	 */
	private JLabel getJLabel()
	{
		if (jLabel == null)
		{
			jLabel = new JLabel();
			jLabel.setText("JLabel");
		}
		return jLabel;
	}

	/**
	 * Set text in {@link JLabel} on the {@link JPathPanel}.
	 * 
	 * @param text
	 *            text in {@link JLabel} on the {@link JPathPanel}.
	 */
	public void setLabelText(String text)
	{
		if (checkInit())
			jLabel.setText(text);
	}

	/**
	 * Set texts in the text box on the {@link JPathPanel}.
	 * 
	 * @param text
	 *            texts in the text box.
	 */
	public void setText(String text)
	{
		if (checkInit())
			jTextField.setText(text);
	}

	/**
	 * Get the text in the text box on this {@link JPathPanel}.
	 * 
	 * @return text in the text box on success, {@code null} on failure.
	 */
	public String getText()
	{
		if (checkInit())
			return jTextField.getText();
		else
			return null;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return {@link JPanel}
	 */
	private JPanel getJPanel()
	{
		if (jPanel == null)
		{
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJTextField(), BorderLayout.CENTER);
		}
		return jPanel;
	}
	
	/**
	 * Enable or disable the text field. If it is disabled, listeners bonded 
	 * to this {@link JTextField} will be automatically removed.
	 * @param enable {@code true} for enable, {@code false} for disable.
	 * 
	 */
	public void setFieldEnabled(boolean enable)
	{
		if(checkInit())
		{
			jTextField.setEnabled(enable);
			if(enable == false)
			{
				MouseListener[] listeners = jTextField.getListeners(MouseListener.class);
				for (MouseListener listener : listeners)
				{
					jTextField.removeMouseListener(listener);
				}
			}
		}
	}

	@Override
	public boolean checkAll()
	{
		if (checkInit() && jTextField.getText().trim().isEmpty() == false)
			return true;
		else
			return false;
	}

	@Override
	public boolean checkInit()
	{
		if (jPanel != null && jTextField != null && jLabel != null)
			return true;
		else
			return false;
	}
}
