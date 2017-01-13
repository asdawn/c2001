package net.c2001.utils.ui.atom;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
/**
 * {@link JPanel} with {@link JLabel}.
 * @author Lin Dong
 *
 */
public class JLabelPanel extends JPanel implements Checkable
{

	private static final long serialVersionUID = 1L;
	private JLabel jLabel = null;

	/**
	 * Create a {@link JLabelPanel}.
	 */
	public JLabelPanel()
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
		this.add(getJLabelPrivate(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jLabel	
	 * 	
	 * @return {@link JLabel}	
	 */
	private JLabel getJLabelPrivate()
	{
		if (jLabel == null)
		{
			jLabel = new JLabel();
			jLabel.setFont(new Font("simsong",Font.PLAIN, 12));
			jLabel.setAlignmentX(BOTTOM_ALIGNMENT);
		}
		return jLabel;
	}
	
	/**
	 * Set the text of {@link JLabel}.
	 * @param text the text of {@link JLabel}.
	 */
	public void setText(String text)
	{
		if(checkInit())
			jLabel.setText(text);
	}
	
	/**
	 * Get the {@link JLabel} on {@link JLabelPanel}.
	 * @return {@link JLabel} on success, {@code null} on failure.
	 */
	public JLabel getJLabel()
	{
		if(checkAll())
			return jLabel;
		else
			return null;
	}

	@Override
	public boolean checkAll()
	{
		return checkInit();
	}

	@Override
	public boolean checkInit()
	{
		if(jLabel != null)
			return true;
		else
			return false;
	}

}
