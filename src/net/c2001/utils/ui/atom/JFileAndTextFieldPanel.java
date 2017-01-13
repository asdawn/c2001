package net.c2001.utils.ui.atom;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import net.c2001.utils.ui.BorderMaker;


import java.awt.BorderLayout;

/**
 * �����ļ����������ı�����{@link JPanel}
 * @author Lin Dong
 *
 */
abstract public class JFileAndTextFieldPanel extends JPanel implements Checkable
{

	private static final long serialVersionUID = 1L;
	private JPathPanel jPathPanel = null;  
	private int fieldSize = 0;
	private String title = null;
	private String path = null;
	private FileFilter f = null;
	private int type = JFileChooser.FILES_ONLY;
	private JTextFieldPanel jTextFieldPanel = null;
	private int tfSize = 0;
	private String pfTitle = null;
	private String tfTitle = null;
	private String pfLabel = null;
	private boolean pfEnabled = false;
	private boolean tfEnabled = false;
	
	/**
	 * ����һ��{@link JFileAndTextFieldPanel}ʵ��
	 * @param fieldSize ·�������ĳ�ʼ����С�� ����С��1��ֵ������Ĭ��ֵ20���д���
	 * @param title ����·���������ֵ��ļ��򿪶Ի���ı���
	 * @param path ����·���������ֵ��ļ��򿪶Ի����Ĭ��·��
	 * @param f ����·���������ֵ��ļ��򿪶Ի���Ĺ�����
	 * @param type ����·���������ֵ��ļ��򿪶Ի�����ļ�ѡ�����ͣ��ļ����ļ��У���
	 * JFileChooser�еĳ���
	 * @param tfSize �ı������Ĵ�С
	 * @param pfTitle ·�������ı߿�����
	 * @param tfTitle �ı���ı߿�����
	 * @param pfLabel ·����������ʾ�ı�
	 * @param pfEnabled ·��������Ƿ����
	 * @param tfEnabled �ı����Ƿ����
	 */
	public JFileAndTextFieldPanel(int fieldSize, String title, String path,
			FileFilter f, int type, int tfSize, String pfTitle, String tfTitle,
			String pfLabel, boolean pfEnabled, boolean tfEnabled)
	{		
		super();
		this.fieldSize = fieldSize;
		this.title = title;
		this.path = path;
		this.f = f;
		this.type = type;
		this.tfSize = tfSize;
		this.pfTitle =pfTitle;
		this.tfTitle = tfTitle;	
		this.pfLabel = pfLabel;
		this.pfEnabled = pfEnabled;
		this.tfEnabled = tfEnabled;
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
		this.add(getJPathPanel(), BorderLayout.WEST);
		this.add(getJTextFieldPanel(), BorderLayout.EAST);
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
			jPathPanel = new JPathPanel(fieldSize, title, path, f, type);
			if(pfTitle != null)
				BorderMaker.addBorder(jPathPanel, pfTitle);
			if (pfLabel != null)
			{
				jPathPanel.setLabelText(pfLabel);
			}
			jPathPanel.setFieldEnabled(pfEnabled);
		}
		return jPathPanel;
	}

	/**
	 * This method initializes jTextFieldPanel	
	 * 	
	 * @return {@link JTextFieldPanel}	
	 */
	private JTextFieldPanel getJTextFieldPanel()
	{
		if (jTextFieldPanel == null)
		{
			jTextFieldPanel = new JTextFieldPanel(tfSize);
			if(tfTitle != null)
				BorderMaker.addBorder(jTextFieldPanel, tfTitle);
			jTextFieldPanel.setFieldEnabled(tfEnabled);
		}
		return jTextFieldPanel;
	}
	
	/**
	 * �趨�ı�������ʾ������
	 * @param text �ı���ҪҪ��ʾ������
	 */
	public void setText(String text)
	{
		if(checkInit())
			jTextFieldPanel.setText(text);
	}

	/**
	 * �趨·�����������ʾ������
	 * @param path ·�������Ҫ��ʾ������
	 */
	public void setPath(String path)
	{
		if(checkInit())
			jPathPanel.setText(path);
	}
	
	/**
	 * ��ȡ·���������
	 * @return ·��������е����ݣ�����������������Ϊ�շ���{@code null}<br>
	 * ע�⣺��·������ı�����д��Ϻ�ù��ܲ�����������
	 */
	public String getPath()
	{
		if(checkAll())
			return jPathPanel.getText();
		else
			return null;
	}
	
	/**
	 * ��ȡ�ı�������
	 * @return �ı������ݣ�����������������Ϊ�շ���{@code null}<br>
	 * ע�⣺��·������ı�����д��Ϻ�ù��ܲ����������� 
	 */
	public String getText()
	{
		if(checkAll())
			return jTextFieldPanel.getText();
		else
			return null;
	}
	
	
	@Override
	public boolean checkInit()
	{
		if(jPathPanel != null && jPathPanel.checkInit() &&
				jTextFieldPanel != null && jTextFieldPanel.checkInit())
		{
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean checkAll()
	{
		if(checkInit() && jPathPanel.checkAll() && jTextFieldPanel.checkAll())
		{
			return true;
		}
		else
			return false;
	}

}
