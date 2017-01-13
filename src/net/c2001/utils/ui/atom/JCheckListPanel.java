package net.c2001.utils.ui.atom;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JPopupMenu;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;

/**
 * ����ָ���ַ����������ɴ���һ��{@link JCheckBox}��{@link JPanel}
 */
public class JCheckListPanel extends JPanel implements Checkable
{

	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane = null;
	protected JCheckBox[] CheckBoxes = null;
	private String[] list = null;
	private JPanel jPanel = null;
	protected boolean[] defaultSels = null;
	private JPopupMenu jPopupMenu = null;
	private JMenuItem clearAllMenuItem = null;
	private JMenuItem selectAllMenuItem = null;
	private JMenuItem defaultMenuItem = null;

	/**
	 * ����ָ�����ַ������鴴�����ж��{@link JCheckBox}��{@link JPanel}�� {@link JCheckBox}��������ַ���һһ��Ӧ��������ʾ������
	 * 
	 * @param list
	 *            ָ��JCheckBox��ʾ����
	 */
	public JCheckListPanel(String[] list)
	{
		super();
		this.list = list;
		initialize();
	}

	/**
	 * �趨{@link JCheckListPanel}��{@link JCheckBox}�����ѡȡ״��
	 * 
	 * @param sels
	 *            �������飬Ϊ{@code true}���ʾ��Ӧ�±�� {@link JCheckBox} Ϊѡ��״̬��Ϊ{@code false} ���ʾΪ��ѡ��״̬
	 */
	public void setSelectedChecks(boolean[] sels)
	{
		if ((CheckBoxes == null) || (CheckBoxes.length != sels.length) || (sels.length == 0))
		{
			return;
		}
		else
		{
			for (int i = 0; i < sels.length; i++)
			{
				if (sels[i] == true)
				{
					CheckBoxes[i].setSelected(true);
				}
				else
				{
					CheckBoxes[i].setSelected(false);
				}
			}
		}
	}

	/**
	 * �趨{@link JCheckListPanel}������{@link JCheckBox}ΪĬ��״̬�� Ĭ��״̬������{@code defaultSels}ָ���� {@code
	 * defaultSels}��Ҫ ͨ��{@code Override}�趨�����СӦ����{@link JCheckBox}����
	 */
	public void setDefaultChecks()
	{
		if (defaultSels != null)
		{
			setSelectedChecks(defaultSels);
		}
	}

	/**
	 * �趨{@link JCheckListPanel}������{@link JCheckBox}Ϊѡ��״̬
	 */
	public void selectAllChecks()
	{
		if (checkInit())
		{
			for (JCheckBox check : CheckBoxes)
			{
				check.setSelected(true);
			}
		}
	}

	/**
	 * �趨{@link JCheckListPanel}������{@link JCheckBox}Ϊ��ѡ��״̬
	 */
	public void clearAllChecks()
	{
		if (checkInit())
		{
			for (JCheckBox check : CheckBoxes)
			{
				check.setSelected(false);
			}
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setLayout(new BorderLayout());
		this.add(getJScrollPane(), BorderLayout.CENTER);
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
			jScrollPane.setViewportView(getJPanel());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes CheckBoxes
	 * 
	 * @return {@link JCheckBox}����
	 */
	private JCheckBox[] getCheckBoxesPrivate()
	{
		if (CheckBoxes == null)
		{
			if (list != null)
				if (list.length > 0)
				{
					CheckBoxes = new JCheckBox[list.length];
					for (int i = 0; i < list.length; i++)
					{
						CheckBoxes[i] = new JCheckBox(list[i]);
					}
				}
		}
		return CheckBoxes;
	}

	/**
	 * ��ȡ {@link JCheckListPanel} �е� {@link JCheckBox} �б�
	 * @return   {@link JCheckBox}  �б����δ�ܳɹ���ʼ���򷵻� {@code  null}  
	 */
	public JCheckBox[] getCheckBoxes()
	{
		if(checkInit())
			return CheckBoxes;
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
			jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.Y_AXIS));
			jPanel.addMouseListener(new java.awt.event.MouseAdapter()
			{
				@Override
				public void mousePressed(MouseEvent e)
				{
					if (e.isPopupTrigger())
					{
						getJPopupMenu().show(getJPanel(), e.getX(), e.getY());
					}
				}

				@Override
				public void mouseReleased(MouseEvent e)
				{
					if (e.isPopupTrigger())
					{
						getJPopupMenu().show(getJPanel(), e.getX(), e.getY());
					}
				}
			});

			JCheckBox[] checks = getCheckBoxesPrivate();
			if (checks != null)
			{
				for (JCheckBox jCheckBox : checks)
				{
					jPanel.add(jCheckBox);
				}
			}
		}
		return jPanel;
	}

	/**
	 * ��ȡ�趨{@link JCheckListPanel}�б�ѡ���ĸ�ѡ���б�
	 * 
	 * @return ѡ�еĸ�ѡ������ݣ��洢��{@link String}�����С� ��δѡ���κ�һ���{@code null}
	 */
	public String[] getSelectedItems()
	{
		if (CheckBoxes != null)
		{
			if (CheckBoxes.length != 0)
			{
				int count = 0;
				for (JCheckBox check : CheckBoxes)
				{
					if (check.isSelected())
						count++;
				}
				if (count != 0)
				{
					String[] selects = new String[count];
					count = 0;
					for (JCheckBox check : CheckBoxes)
					{
						if (check.isSelected())
						{
							selects[count] = check.getText();
							count++;
						}
					}
					return selects;
				}
			}
		}
		return null;
	}

	/**
	 * ��ȡ�趨{@link JCheckListPanel}�б�ѡ���ĸ�ѡ���б�
	 * 
	 * @return ����ѡ�еĸ�ѡ��ı�ţ���δѡ���κ�һ���{@code null}
	 */
	public int[] getSelectedIndexes()
	{
		if (CheckBoxes != null)
		{
			if (CheckBoxes.length != 0)
			{
				int count = 0;
				for (JCheckBox check : CheckBoxes)
				{
					if (check.isSelected())
						count++;
				}
				if (count != 0)
				{
					int[] selects = new int[count];
					count = 0;
					for (int i = 0; i < CheckBoxes.length; i++)
					{
						if (CheckBoxes[i].isSelected())
						{
							selects[count] = i;
							count++;
						}
					}
					return selects;
				}
			}
		}
		return null;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return {@link JPopupMenu}
	 */
	private JPopupMenu getJPopupMenu()
	{
		if (jPopupMenu == null)
		{
			jPopupMenu = new JPopupMenu();
			jPopupMenu.setEnabled(true);
			jPopupMenu.add(getSelectAllMenuItem());
			jPopupMenu.addSeparator();
			jPopupMenu.add(getClearAllMenuItem());
			jPopupMenu.addSeparator();
			jPopupMenu.add(getDefaultMenuItem());
		}
		return jPopupMenu;
	}

	/**
	 * This method initializes clearAllMenuItem
	 * @return   {@link JMenuItem}  
	 * @uml.property  name="clearAllMenuItem"
	 */
	private JMenuItem getClearAllMenuItem()
	{
		if (clearAllMenuItem == null)
		{
			clearAllMenuItem = new JMenuItem();
			clearAllMenuItem.setText("�������");
			clearAllMenuItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					getJPopupMenu().setVisible(false);
					clearAllChecks();
				}
			});
		}
		return clearAllMenuItem;
	}

	/**
	 * This method initializes selectAllMenuItem
	 * @return   {@link JMenuItem}  
	 * @uml.property  name="selectAllMenuItem"
	 */
	private JMenuItem getSelectAllMenuItem()
	{
		if (selectAllMenuItem == null)
		{
			selectAllMenuItem = new JMenuItem();
			selectAllMenuItem.setText("ѡȡ����");
			selectAllMenuItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					getJPopupMenu().setVisible(false);
					selectAllChecks();
				}
			});
		}
		return selectAllMenuItem;
	}

	/**
	 * This method initializes defaultMenuItem
	 * @return   {@link JMenuItem}  
	 * @uml.property  name="defaultMenuItem"
	 */
	private JMenuItem getDefaultMenuItem()
	{
		if (defaultMenuItem == null)
		{
			defaultMenuItem = new JMenuItem();
			defaultMenuItem.setText("Ĭ��ֵ");
			defaultMenuItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					getJPopupMenu().setVisible(false);
					setDefaultChecks();
				}
			});
		}
		return defaultMenuItem;
	}

	@Override
	public boolean checkAll()
	{
		if (checkInit())
		{
			for (JCheckBox check : CheckBoxes)
			{
				if (check == null)
					return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean checkInit()
	{
		if (jScrollPane != null && jPanel != null && list != null && list.length > 0
				&& CheckBoxes != null)
			return true;
		else
			return false;
	}
}
