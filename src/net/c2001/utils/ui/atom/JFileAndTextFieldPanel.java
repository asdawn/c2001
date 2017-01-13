package net.c2001.utils.ui.atom;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import net.c2001.utils.ui.BorderMaker;


import java.awt.BorderLayout;

/**
 * 带有文件输入栏和文本栏的{@link JPanel}
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
	 * 创建一个{@link JFileAndTextFieldPanel}实例
	 * @param fieldSize 路径输入框的初始化大小， 对于小于1的值将采用默认值20进行处理
	 * @param title 单击路径输入框出现的文件打开对话框的标题
	 * @param path 单击路径输入框出现的文件打开对话框的默认路径
	 * @param f 单击路径输入框出现的文件打开对话框的过滤器
	 * @param type 单击路径输入框出现的文件打开对话框的文件选择类型（文件或文件夹），
	 * JFileChooser中的常量
	 * @param tfSize 文本输入框的大小
	 * @param pfTitle 路径输入框的边框文字
	 * @param tfTitle 文本框的边框文字
	 * @param pfLabel 路径输入框的提示文本
	 * @param pfEnabled 路径输入框是否可用
	 * @param tfEnabled 文本框是否可用
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
	 * 设定文本框中显示的内容
	 * @param text 文本框要要显示的内容
	 */
	public void setText(String text)
	{
		if(checkInit())
			jTextFieldPanel.setText(text);
	}

	/**
	 * 设定路径输入框中显示的内容
	 * @param path 路径输入框要显示的内容
	 */
	public void setPath(String path)
	{
		if(checkInit())
			jPathPanel.setText(path);
	}
	
	/**
	 * 获取路径输的内容
	 * @return 路径输入框中的内容，如果发生错误或内容为空返回{@code null}<br>
	 * 注意：在路径框和文本框都填写完毕后该功能才能正常返回
	 */
	public String getPath()
	{
		if(checkAll())
			return jPathPanel.getText();
		else
			return null;
	}
	
	/**
	 * 获取文本框内容
	 * @return 文本框内容，如果发生错误或内容为空返回{@code null}<br>
	 * 注意：在路径框和文本框都填写完毕后该功能才能正常返回 
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
