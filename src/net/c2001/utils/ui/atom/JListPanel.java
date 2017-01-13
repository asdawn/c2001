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
 * ����һ��{@link JList}��{@link JPanel}
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
	 * ����ָ���ַ������鴴������{@link JList}��{@link JPanel}
	 * 
	 * @param list
	 *            Ҫ��ӵ��б��е�����<br>
	 *            ע�⣺��{@code null}��Ϊ�����ᵼ�� {@link NullPointerException}
	 */
	public JListPanel(String[] list)
	{
		super();
		this.list = list;
		initialize();
	}

	/**
	 * ����ָ���ַ������鴴������{@link JList}��{@link JPanel}�� ���趨��{@code index}�� ΪĬ��ֵ��
	 * 
	 * @param list
	 *            Ҫ��ӵ��б��е�����
	 * @param index
	 *            �б��е�Ĭ�����ţ��±��0��ʼ<br>
	 *            ע�⣺��{@code null} ��Ϊ�����ᵼ��NullPointerException<br>
	 *            ע�⣺���{@code index}����ȷ����׳� {@link IndexOutOfBoundsException}
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
	 * �趨{@link JListPanel}�ϵ�{@link JList}��ǰѡ�е���Ŀ
	 * 
	 * @param index
	 *            Ҫѡ������Ŀ���±�
	 */
	public void setSelectedIndex(int index)
	{
		if (index < 0 || index >= list.length)
			throw new IndexOutOfBoundsException("Invalid index: " + index);
		getJListPrivate().setSelectedIndex(index);
	}

	/**
	 * ��ȡ{@link JListPanel}�е�{@link JList}ѡ�е���Ŀ
	 * 
	 * @return ѡ�е���Ŀ�����{@link JList}δ�ɹ�������ѡ������Ϊ�շ��� {@code null}
	 */
	public String getSelection()
	{
		if(checkAll())
			return (String) jList.getSelectedValue();
		else
			return null;
	}

	/**
	 * ��ȡ{@link JListPanel}�е�{@link JList}
	 * 
	 * @return {@link JListPanel}�е�{@link JList}�� �������ʧ���򷵻�{@code null}
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
	 * ���б������ָ����Ŀ
	 * @param item Ҫ��ӵ���Ŀ
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
	 * �����б�����Ŀ����
	 * @return �б�����Ŀ����������������󷵻�0
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
	 * ��ȡ�б���ָ����ŵ���Ŀ
	 * @param index ��Ŀ�ı��
	 * @return ָ������Ŀ������������󷵻�{@code null}
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
	 * ���б���ɾ��ָ����Ŀ
	 * @param item Ҫɾ������Ŀ
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
	 * ���б���ɾ��ָ����Ŀ
	 * @param index Ҫɾ������Ŀ���±�
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
	 * ɾ���б���������Ŀ
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
	 * ��ȡ�б������
	 * @return �б����ݣ�����б�Ϊ�ջ��ʼ��ʧ�ܷ���{@code null}
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
