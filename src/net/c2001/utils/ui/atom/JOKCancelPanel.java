package net.c2001.utils.ui.atom;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;

/**
 * {@link JPanel} with OK and Cancel buttons.
 * @author Lin Dong
 *
 */
public class JOKCancelPanel extends JPanel implements Checkable
{

	private static final long serialVersionUID = 1L;
	private JButton ok = null;
	private JButton cancel = null;
	/**
	 * Create a {@link JOKCancelPanel}.
	 */
	public JOKCancelPanel()
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
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
		this.setLayout(flowLayout);
		this.setSize(270, 49);
		this.add(getOkButtonPrivate(), null);
		this.add(getCancelButtonPrivate(), null);
	}

	/**
     * Get the OK button in {@link JOKCancelPanel}.
     * 	
     * @return the OK button on success, {@code null} on failure. 	
     */
	public JButton getOKButton()
	{
		if(checkInit())
			return ok;
		else
			return null;
	}
	
	/**
     * This method initializes ok	
     * 	
     * @return {@link JButton}	
     */
    private JButton getOkButtonPrivate()
    {
    	if (ok == null)
    	{
    		ok = new JButton();
    		ok.setText("OK");
    	}
    	return ok;
    }

    /**
     * Get the Cancel button in {@link JOKCancelPanel}.
     * 	
     * @return the Cancel button on success, {@code null} on failure. 	
     */
    public JButton getCancelButton()
	{
		if(checkInit())
			return cancel;
		else
			return null;
	}
    
    
    /**
     * This method initializes cancel	
     * 	
     * @return {@link JButton}	
     */
    private JButton getCancelButtonPrivate()
    {
    	if (cancel == null)
    	{
    		cancel = new JButton();
    		cancel.setText("Cancel");
    	}
    	return cancel;
    }

	@Override
	public boolean checkAll()
	{
		return checkInit();
	}

	@Override
	public boolean checkInit()
	{
		if(ok != null && cancel != null)
			return true;
		else
			return false;
	}

} 
