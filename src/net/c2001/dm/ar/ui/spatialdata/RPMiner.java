package net.c2001.dm.ar.ui.spatialdata;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.border.Border;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.BoxLayout;
import net.c2001.utils.ui.BorderMaker;
import net.c2001.utils.ui.FileFilters;
import net.c2001.utils.ui.FileOpenDialog;
import net.c2001.utils.ui.ScreenResolution;
import net.c2001.utils.ui.atom.JPathPanel;

/**
 * Dialog to get input data and parameters for rule mining.
 * @author Lin Dong
 *
 */
public class RPMiner extends JDialog
{
	/*
	 * Please use Visual Editor or other editor. UI codes are
	 * always long and boring.
	 */
	private static final long	serialVersionUID	= 1L;
	private JPanel				jContentPane		= null;
	private JPanel rpFilesPanel = null;
	private JTextArea rpFilesTextArea = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane = null;
	private JLabel jLabel = null;
	private JPanel jPanel1 = null;
	private JButton cancelButton = null;
	private JButton okButton = null;
	private int retcode = JOptionPane.CANCEL_OPTION;
	private JPanel jPanel2 = null;
	private JTextField supTextField = null;
	private JPanel jPanel3 = null;
	private JTextField confTextField = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;
	private JPathPanel jPathPanel = null;
	private String[] aliases = null;
	private File[] files = null;
	private double area = -1;
	
	/**
	 * Test whether parameters are set correctly and the OK button is pressed.
	 * @return  {@link JOptionPane}.{@code  OK_OPTION} for yes, 
	 * {@link JOptionPane}.{@code  CANCEL_OPTION} for no.  
	 */
	public synchronized int getRetcode()
	{
		return retcode;
	}
	
	/**
	 * Get the minimum support threshold set on the {@link RPMiner} dialog.
	 * @return minimum support threshold.
	 */
	public double getSup()
	{
		return Double.parseDouble(getSupTextField().getText());
	}
	
	/**
	 * Get the minimum confidence threshold set on the {@link RPMiner} dialog.
	 * @return minimum confidence threshold.
	 */
	public double getConf()
	{
		return Double.parseDouble(getConfTextField().getText());
	}
	
	/**
	 * Get the area of research region set on the {@link RPMiner} dialog.
	 * @return  area of research region.
	 */
	public double getArea()
	{
		return  area;
	}
	
	/**
	 * Get raster/shape files to mine.
	 * @return raster/shape files to mine.
	 */
	public File[] getRPFiles()
	{
		return files;
	}
	
	/**
	 * Get aliases of raster/shape files. These aliases will be treated as
	 * names of items.
	 * @return  aliases of raster/shape files.
	 */
	public String[] getAliases()
	{
		return aliases;
	}

	/**
	 * Set descriptions, will be show in the bottom of this dialog.
	 * @param ins descriptions.
	 */
	public void setInstruction(String ins)
	{
		getJLabel().setText(ins);
	}
	
	/**
	 * Create a {@link RPMiner} dialog.
	 */
	public RPMiner()
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
		this.setModal(true);		
		this.setContentPane(getJContentPane());
		this.setSize(450,400);
		ScreenResolution.centerComponent(this);
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
			jContentPane.add(getJPanel(), BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), BorderLayout.SOUTH);
			jContentPane.add(getJPanel5(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes rpFilesPanel	
	 * @return   {@link JPanel}  
	 */
	private JPanel getRPFilesPanel()
	{
		if (rpFilesPanel == null)
		{
			rpFilesPanel = new JPanel();
			rpFilesPanel.setLayout(new BorderLayout());
			Border bdr = BorderFactory.createTitledBorder(
					"raster/shape files for mining");
	    	rpFilesPanel.setBorder(bdr);
	    	rpFilesPanel.add(getJScrollPane(), BorderLayout.SOUTH);
			
		}
		return rpFilesPanel;
	}

	/**
	 * This method initializes rpFilesTextArea	
	 * @return   {@link JTextArea}  
	 */
	private JTextArea getRPFilesTextArea()
	{
		if (rpFilesTextArea == null)
		{
			rpFilesTextArea = new JTextArea();
			rpFilesTextArea.setRows(4);
			rpFilesTextArea.setEditable(false);
			rpFilesTextArea.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(MouseEvent e)
				{
					File[] rpFiles = FileOpenDialog.showMultiFileSelector(
							FileFilters.getGeoFilter(), 
							"Select shape files(polygon) and raster files", 
							JFileChooser.FILES_ONLY);
					if(rpFiles != null)
					{
						String[] rpAliases = JRPFieldDialog.showRPFieldDialog(rpFiles);
						if(rpAliases != null)
						{
							files = rpFiles;
							aliases = rpAliases;
							for (int i = 0; i< aliases.length; i++)
							{
								rpFilesTextArea.setText("");
								rpFilesTextArea.append(aliases[i]);
								rpFilesTextArea.append(": ");
								rpFilesTextArea.append(files[i].getPath());
							}
						}
					}					
				}
			});
		}
		return rpFilesTextArea;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel()
	{
		if (jPanel == null)
		{
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			Border bdr = BorderFactory.createTitledBorder("");
	    	jPanel.setBorder(bdr);
			jPanel.add(getRPFilesPanel(), BorderLayout.CENTER);
			jPanel.add(getJPathPanel(), BorderLayout.NORTH);
		}
		return jPanel;
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
			jScrollPane.setViewportView(getRPFilesTextArea());
		}
		return jScrollPane;
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
			Border bdr = BorderFactory.createTitledBorder("Instructions£º");
			Font font = new Font("default",Font.PLAIN,12);
			jLabel.setFont(font);
	    	jLabel.setBorder(bdr);			
		}
		return jLabel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return {@link JPanel}	
	 */
	private JPanel getJPanel1()
	{
		if (jPanel1 == null)
		{
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel1 = new JPanel();
			jPanel1.setLayout(flowLayout);
			jPanel1.add(getOkButton(), null);
			jPanel1.add(getCancelButton(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes cancelButton	
	 * @return   {@link JButton}  
	 */
	private JButton getCancelButton()
	{
		if (cancelButton == null)
		{
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					retcode = JOptionPane.CANCEL_OPTION;
					setVisible(false);
				}
			});
		}
		return cancelButton;
	}

	/**
	 * This method initializes okButton	
	 * @return   {@link JButton}  
	 */
	private JButton getOkButton()
	{
		if (okButton == null)
		{
			okButton = new JButton();
			okButton.setText("OK");
			okButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					if(jPathPanel.checkAll() != true || files == null)
					{
						JOptionPane.showMessageDialog(null, 
								"Please select raster/shape files" +
								"(map of research region, other polygon " +
								"shape files and raster files to mine).",
										"Input files not set",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					boolean supNull = false;
					boolean confNull = false;
					if(getSupTextField().getText().trim().equals(""))
					{
						getSupTextField().setBackground(Color.GREEN);
						supNull = true;
						//return;
					}
					if(getConfTextField().getText().trim().equals(""))
					{
						getConfTextField().setBackground(Color.GREEN);
						confNull = true;
						//return;
					}
					if(supNull || confNull)
					{
						String message = "";						
						if(supNull)
						{
							message += "\nMin SUP(a number between 0 and 1)";
						}
						if(confNull)
						{
							message += "\nMin CONF(a number between 0 and 1)";
						}
						JOptionPane.showMessageDialog(
								null, 
								message, 
								"Please fill all required fields:", 
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}					
					try
					{
						double sup = Double.parseDouble(getSupTextField().getText());
						if(sup >1 || sup <0)
							throw new Exception();
					}
					catch (Exception esup) 
					{
						getSupTextField().setBackground(Color.RED);
						JOptionPane.showMessageDialog(
								null, 
								"Min SUP should be a number between 0 and 1.");
						return;
					}
					try
					{
						double conf = Double.parseDouble(getConfTextField().getText());
						if(conf >1 || conf<0)
							throw new Exception();
					}
					catch (Exception esup) 
					{
						getConfTextField().setBackground(Color.RED);
						JOptionPane.showMessageDialog(
								null, 
								"Min CONF should be a number between 0 and 1.");
						return;
					}
					File regionFile = new File(jPathPanel.getText());
					area = getArea(regionFile);					
					if(area == -1)
					{
						JOptionPane.showMessageDialog(null, 
								"Failed to calculation the area of research region,\n" +
								"can not continue to mine rules. Please try again.", 
								"Failed to mine rules", 
								JOptionPane.INFORMATION_MESSAGE);						
						return;
					}					
					retcode = JOptionPane.OK_OPTION;					
					setVisible(false);					
				}
			});
		}
		return okButton;
	}

	protected double getArea(File regionFile) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Reset the content of this dialog.
	 */
	protected void reset()
	{
		retcode = JOptionPane.CANCEL_OPTION;
		files = null;
		aliases = null;
		jPathPanel.setText("");
		rpFilesTextArea.setText("");
		supTextField.setText("");
		confTextField.setText("");
	}
	
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return {@link JPanel}	
	 */
	private JPanel getJPanel2()
	{
		if (jPanel2 == null)
		{
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			Border bdr = BorderFactory.createTitledBorder("Min SUP£º");
	    	jPanel2.setBorder(bdr);
			jPanel2.add(getSupTextField(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes supTextField	
	 * @return   {@link JTextField}  
	 */
	private JTextField getSupTextField()
	{
		if (supTextField == null)
		{
			supTextField = new JTextField();
			supTextField.setText("");
			supTextField.addKeyListener(new java.awt.event.KeyAdapter()
			{
				public void keyTyped(java.awt.event.KeyEvent e)
				{
					supTextField.setBackground(Color.WHITE);
				}
			});
		}
		return supTextField;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return {@link JPanel}	
	 */
	private JPanel getJPanel3()
	{
		if (jPanel3 == null)
		{
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			Border bdr = BorderFactory.createTitledBorder("Min CONF£º");
	    	jPanel3.setBorder(bdr);
			jPanel3.add(getConfTextField(), BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes confTextField	
	 * @return   {@link JTextField}  
	 */
	private JTextField getConfTextField()
	{
		if (confTextField == null)
		{
			confTextField = new JTextField();
			confTextField.addKeyListener(new java.awt.event.KeyAdapter()
			{
				public void keyTyped(java.awt.event.KeyEvent e)
				{
					confTextField.setBackground(Color.WHITE);
				}
			});
		}
		return confTextField;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return {@link JPanel}	
	 */
	private JPanel getJPanel4()
	{
		if (jPanel4 == null)
		{
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BoxLayout(getJPanel4(), BoxLayout.X_AXIS));
			jPanel4.add(getJPanel2(), null);
			jPanel4.add(getJPanel3(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return {@link JPanel}	
	 */
	private JPanel getJPanel5()
	{
		if (jPanel5 == null)
		{
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJPanel4(), BorderLayout.NORTH);
			jPanel5.add(getJLabel(), BorderLayout.CENTER);
		}
		return jPanel5;
	}
	
	/**
	 * This method initializes jPathPanel	
	 * 	
	 * @return {@link JPathPanel}	
	 */
	private JPathPanel getJPathPanel()
	{
		if (jPathPanel == null)
		{
			jPathPanel = new JPathPanel(30, 
					"Select shape file", 
					null, 
					FileFilters.getShapeFilter(),
					JFileChooser.FILES_ONLY);
			jPathPanel.setLabelText("Study region ");
			BorderMaker.addBorder(jPathPanel, "Map of study region");
		}
		return jPathPanel;
	}

}
