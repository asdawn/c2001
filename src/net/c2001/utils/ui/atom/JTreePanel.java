package net.c2001.utils.ui.atom;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import java.awt.BorderLayout;

/**
 * {@link JPanel} with a {@link JTree}.
 * @author Lin Dong
 *
 */
public class JTreePanel extends JPanel implements Checkable
{	
	private static final long serialVersionUID = 1L;
	
	private JTree jTree = null;
	
	private JScrollPane jScrollPane = null;
	
	private TreeNode root = null;

	/**
	 * Ceeate a {@link JTreePanel}
	 * @param root root of the tree.
	 */
	public JTreePanel(TreeNode root) 
	{
		super();
		this.root = root;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(321, 270));
        this.add(getJScrollPane(), BorderLayout.NORTH);
			
	}
	
	/**
	 * Get the {@link JTree} in {@link JTreePanel}.
	 * @return {@link JTree}
	 */
	public JTree getTree()
	{
		return jTree;
	}

	/**
	 * This method initializes jTree	
	 * 	
	 * @return {@link JTree}	
	 */
	private JTree getJTree()
	{
		if (jTree == null)
		{			
			jTree = new JTree();			
			if(this.root != null)
			{
				TreeModel model = new DefaultTreeModel(root);
				jTree.setModel(model);
				jTree.setEditable(true);
				jTree.setCellEditor(new DefaultTreeCellEditor(jTree, new DefaultTreeCellRenderer()));
			}			
		}
		return jTree;
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
			jScrollPane.setViewportView(getJTree());
		}
		return jScrollPane;
	}

	@Override
	public boolean checkAll()
	{
		if(checkInit() && root != null)
			return true;
		else
			 return false;
	}

	@Override
	public boolean checkInit()
	{
		if(jScrollPane != null && jTree != null)
			return true;
		else
			return false;
	}

}
