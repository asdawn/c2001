package net.c2001.utils.ui.atom;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.ListModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;

/**
 * 带有一个{@link JList}的{@link JPanel}
 * 
 * @author Lin Dong
 */
public class JListPanel extends JPanel implements Checkable
{
	
	private final String[] list;
	private static final long serialVersionUID = 1L;	
	private JList<String> jList = null;	
	private Integer index = null;	
	private JScrollPane jScrollPane = null;
	private JPanel jPanel = null;

	/**
	 * 根据指定字符串数组创建含有{@link JList}的{@link JPanel}
	 * 
	 * @param list
	 *            要添加到列表中的内容<br>
	 *            注意：以{@code null}作为参数会导致 {@link NullPointerException}
	 */
	public JListPanel(String[] list)
	{
		super();
		this.list = list;
		initialize();
	}

	/**
	 * 根据指定字符串数组创建含有{@link JList}的{@link JPanel}， 并设定第{@code index}项 为默认值。
	 * 
	 * @param list
	 *            要添加到列表中的内容
	 * @param index
	 *            列表中的默认项编号，下标从0开始<br>
	 *            注意：以{@code null} 作为参数会导致NullPointerException<br>
	 *            注意：如果{@code index}不正确则会抛出 {@link IndexOutOfBoundsException}
	 */
	public JListPanel(String[] list, int index)
	{
		super();
		this.list = list;
		if (index < 0 || index >= list.length)
			throw new IndexOutOfBoundsException("Invalid index: " + index);
		this.index = index;
		initialize();
	}

	/**
	 * 设定{@link JListPanel}上的{@link JList}当前选中的项目
	 * 
	 * @param index
	 *            要选定的项目的下标
	 */
	public void setSelectedIndex(int index)
	{
		if (index < 0 || index >= list.length)
			throw new IndexOutOfBoundsException("Invalid index: " + index);
		getJListPrivate().setSelectedIndex(index);
	}

	/**
	 * 获取{@link JListPanel}中的{@link JList}选中的项目
	 * 
	 * @return 选中的项目。如果{@link JList}未成功创建或选中内容为空返回 {@code null}
	 */
	public String getSelection()
	{
		if(checkAll())
			return (String) jList.getSelectedValue();
		else
			return null;
	}

	/**
	 * 获取{@link JListPanel}中的{@link JList}
	 * 
	 * @return {@link JListPanel}中的{@link JList}， 如果创建失败则返回{@code null}
	 */
	public JList<String> getJList()
	{
		if(checkInit())
			return jList;
		else
			return null;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(192, 179));
		this.add(getJScrollPane(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jList
	 * 
	 * @return {@link JList}
	 */
	private JList<String> getJListPrivate()
	{
		if (jList == null)
		{
			jList = new JList<String>(list);
			if (index != null)
				jList.setSelectedIndex(index);
		}
		return jList;
	}

	@Override
	public boolean checkAll()
	{
		if (checkInit() && jList.isSelectionEmpty() != true)
			return true;
		else
			return false;
	}

	@Override
	public boolean checkInit()
	{
		if (jList != null)
			return true;
		else
			return false;
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
	 * This method initializes jPanel	
	 * 	
	 * @return {@link JPanel}	
	 */
	private JPanel getJPanel()
	{
		if (jPanel == null)
		{
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJListPrivate(), BorderLayout.CENTER);
		}
		return jPanel;
	}
	
	/**
	 * 在列表中添加指定条目
	 * @param item 要添加的条目
	 */
	public void addItem(String item)
	{
		if(checkInit())
		{
			DefaultListModel<String> model = new DefaultListModel<String>();
			ListModel<String> m = jList.getModel();
			for (int i = 0; i < m.getSize() ; i++)
			{
				model.addElement(m.getElementAt(i));
			}
			model.addElement(item);
			jList.setModel(model);
		}
	}
	
	/**
	 * 返回列表中条目总数
	 * @return 列表中条目总数，如果发生错误返回0
	 */
	public int getItemCount()
	{
		if(checkInit())
		{
			ListModel<String> m = jList.getModel();	
			return m.getSize();
		}
		else
			return 0;
	}
	
	/**
	 * 获取列表中指定编号的条目
	 * @param index 条目的编号
	 * @return 指定的条目，如果发生错误返回{@code null}
	 */
	public String getItem(int index)
	{
		if(checkInit())
		{			
			ListModel<String> m = jList.getModel();			
			if(index >= 0 && index < m.getSize())
				return (String)m.getElementAt(index);
		}
		return null;
	}
	
	/**
	 * 在列表中删除指定条目
	 * @param item 要删除的条目
	 */
	public void removeItem(String item)
	{
		if(checkAll())
		{
			DefaultListModel<String> model = new DefaultListModel<String>();
			ListModel<String> m = jList.getModel();
			for (int i = 0; i < m.getSize() ; i++)
			{
				model.addElement(m.getElementAt(i));
			}
			model.removeElement(item);
			jList.setModel(model);
		}
	}
	
	/**
	 * 在列表中删除指定条目
	 * @param index 要删除的条目的下标
	 */
	public void removeItem(int index)
	{
		if(checkAll())
		{
			DefaultListModel<String> model = new DefaultListModel<String>();
			ListModel<String> m = jList.getModel();
			for (int i = 0; i < m.getSize() ; i++)
			{
				model.addElement(m.getElementAt(i));
			}
			model.removeElementAt(index);
			jList.setModel(model);
		}
	}
	
	/**
	 * 删除列表中所有条目
	 */
	public void removeAllElements()
	{
		if(checkInit())
		{
			DefaultListModel<String> model = new DefaultListModel<String>();
			jList.setModel(model);
		}
	}
	
	/**
	 * 获取列表的内容
	 * @return 列表内容，如果列表为空或初始化失败返回{@code null}
	 */	
	public synchronized String[] getContent()
	{
		if(checkInit())
		{
			ListModel<String> model = jList.getModel();
			int size = model.getSize();
			if(size > 0)
			{	
				String[] contents = new String[size];
				for(int i=0;i<size;i++)
					contents[i] = (String) model.getElementAt(i);
				return contents;
			}
		}
		return null;
	}

}
