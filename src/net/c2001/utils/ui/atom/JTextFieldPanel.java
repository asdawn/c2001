package net.c2001.utils.ui.atom;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import javax.swing.JTextField;

/**
 * Create a {@link JPanel} which contains a {@link JTextField}.
 * @author Lin Dong
 *
 */
public class JTextFieldPanel extends JPanel implements Checkable
{

	private static final long serialVersionUID = 1L;
	
	private JTextField jTextField = null;
	
	private int len = 0;
	
	/**
	 * Create a {@link JTextFieldPanel}.
	 */
	public JTextFieldPanel()
	{
		super();
		initialize();
	}
	
	/**
	 * Create a {@link JTextFieldPanel}.
	 * @param len width of {@link JTextField} in this {@link JTextFieldPanel}.
	 */
	public JTextFieldPanel(int len)
	{
		super();
		this.len = len;
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
		this.add(getJTextFieldPrivate(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return {@link JTextField}
	 */
	private JTextField getJTextFieldPrivate()
	{
		if (jTextField == null)
		{		
			if(len > 0)
				jTextField = new JTextField(len);
			else
				jTextField = new JTextField();
		}
		return jTextField;
	}
	
	/**
	 * Get the text in {@link JTextField} on this {@link JTextFieldPanel}.
	 * @return text in {@link JTextField} on success, {@code null} on failure.
	 */
	public String getText()
	{
		if(checkAll())
		{			
			return  jTextField.getText();
		}
		return null;
	}
	
	/**
	 * Get the text in {@link JTextField} on this {@link JTextFieldPanel}.
	 * @param text test to display in the {@link JTextField}.
	 */
	public void setText(String text)
	{
		if(checkInit())
		{			
			jTextField.setText(text);
		}
	}

	/**
	 * Get the {@link JTextField} object on this {@link JTextFieldPanel}.
	 * 
	 * @return the {@link JTextField} object on this {@link JTextFieldPanel}
	 * on success, {@code null} on failure.
	 */
	public JTextField getJTextFiled()
	{
		if (checkInit())
			return jTextField;
		else
			return null;
	}
	
	/**
	 * Set whether the {@link JTextField} is enabled. If it is disabled, the
	 * listener bonded to it will be removed automatically.
	 * @param enable {@code true} for enable, {@code false} for disable.
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
		if(checkInit() && jTextField.getText().trim().isEmpty() != true)
			return true;
		else
			return false;		
	}

	@Override
	public boolean checkInit()
	{
		if (jTextField != null)
			return true;
		else
			return false;
	}

}
