package net.c2001.dm.ar.ui.ruleviewer;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.BorderLayout;

import javax.swing.JDialog;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.utils.ui.TableExporter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.InvalidParameterException;

/**
 * Viewer of association rules and evaluation indicators. Super class
 * of {@link RuleViewer}.
 * 
 * @author Lin Dong
 */
public class UniViewer extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	private JPanel jContentPane = null;
	
	private JPanel jPanel = null;
	
	private JButton btnExport = null;
	
	private JButton btnClose = null;
	
	private JScrollPane jScrollPane = null;
	
	private JTable jTable = null;
	
	protected AssociationRules rule = null;
	
	protected ValueAndName[] vn = null;

	/**
	 * Get the {@link JTable} in {@link UniViewer}. 
	 * @return the {@link JTable} in {@link UniViewer}.
	 */
	public JTable getContentTable()
	{
		return jTable;
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
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
			flowLayout.setHgap(15);
			flowLayout.setVgap(5);
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout);
			jPanel.add(getBtnExport(), null);
			jPanel.add(getBtnClose(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnExport
	 * @return  javax.swing.JButton
	 */
	private JButton getBtnExport()
	{
		if (btnExport == null)
		{
			btnExport = new JButton();
			btnExport.setText("Export");
			btnExport.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
					TableExporter.saveAs(getJTable());
				}
			});
		}
		return btnExport;
	}

	/**
	 * This method initializes btnClose
	 * @return  javax.swing.JButton
	 */
	private JButton getBtnClose()
	{
		if (btnClose == null)
		{
			btnClose = new JButton();
			btnClose.setText("Close");
			btnClose.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
					setVisible(false);
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane()
	{
		if (jScrollPane == null)
		{
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable()
	{
		if (jTable == null)
		{
			jTable = createJTable();
			// Set number column color to gray
			Component cellr = null;
			cellr = new DefaultTableCellRenderer();
			cellr.setBackground(new Color(0xef, 0xef, 0xef));
			jTable.getColumnModel().getColumn(0)
			        .setCellRenderer((TableCellRenderer) cellr);
			jTable.setCellSelectionEnabled(true);
			// Add Menu
			final JTablePopMenu m = new JTablePopMenu(jTable);
			jTable.add(m);
			jTable.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
					checkTrigger(e);
				}

				public void mouseReleased(MouseEvent e)
				{
					checkTrigger(e);
				}

				private void checkTrigger(MouseEvent e)
				{
					if (jTable.getSelectedColumnCount() == 0)
						return;
					if (e.isPopupTrigger())
					{
						m.show(jTable, e.getX(), e.getY());
					}
				}
			});
		}
		return jTable;
	}

	/**
	 * Create the {@link JTable} in {@link UniViewer}.
	 * @return {@link JTable}
	 */
	protected JTable createJTable()
	{
		JTable jTable;
		int vcount = 0;
		if(vn != null)
			vcount = vn.length;
		String[] colNames = new String[vcount + 5];
		String[] names = { "no", "antecedent", "consequent", "support", "confidence" };
		for(int i = 0; i< colNames.length; i++) {
			if(i <5)
				colNames[i] = names[i];
			else
				colNames[i] = vn[i-5].name;
		}
		DefaultTableModel dataModal = new DefaultTableModel(colNames, 0);
		jTable = new JTable(dataModal);
		// set the max and min size of cell
		jTable.getColumnModel().getColumn(0).setMinWidth(20);
		jTable.getColumnModel().getColumn(0).setMaxWidth(40);
		jTable.getColumnModel().getColumn(1).setMinWidth(120);
		jTable.getColumnModel().getColumn(1).setMaxWidth(500);
		jTable.getColumnModel().getColumn(2).setMinWidth(60);
		jTable.getColumnModel().getColumn(2).setMaxWidth(120);
		for(int i = 3; i < colNames.length; i++) {
			jTable.getColumnModel().getColumn(i).setMinWidth(40);
			jTable.getColumnModel().getColumn(i).setMaxWidth(80);
		}
		// add rules
		int i;
		RuleResolver resolver = new RuleResolver();
		for (i = 0; i < rule.getRules().size(); i++)
		{
			dataModal.addRow(resolver.resolveRuleObj(rule, vn, i));
		}
		return jTable;
	}


	
	/**
	 * Create a {@link UniViewer}.
	 * 
	 * @param owner
	 *            owner of this dialog.
	 * @param r
	 *            {@link AssociationRules} to view.
	 * @param vns names and values of evaluation indicators.
	 */
	public UniViewer(Frame owner, AssociationRules r, ValueAndName ... vns)
	{
		super(owner, true);		
		rule = r;
		vn = vns;
		//check vns
		if(vn != null) {
			for (ValueAndName v : vn) {
				if(v.value.length != r.getRules().size()) {
					throw new InvalidParameterException("Rule count does not equal to value count:" + 
							v.name);
				}
			}
		}
		initialize();
	}
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{	
		int width = 500+60*vn.length;
		if(width > 1024)
			width = 1024;
		this.setSize(493, width);
		this.setTitle("View rules:");
		this.setContentPane(getJContentPane());
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * Append a row to the table in {@link UniViewer}.
	 * 
	 * @param rd
	 *            content of the new row, array of {@link Object}, each 
	 *            {@link Object} stores the value of an attribute.
	 */
	public void addRow(Object[] rd)
	{
		DefaultTableModel m = (DefaultTableModel) (getJTable().getModel());
		m.addRow(rd);
	}
	
	/**
	 * Update the association rules. The count of columns can not be changed.
	 * @param r {@link AssociationRules} to view.
	 * @param vns names and values of evaluation indicators.
	 */
	public void setRules(AssociationRules r, ValueAndName ... vns)
	{
		if(r == null)
			throw new NullPointerException("Rule should not be null.");
		if((vns== null && this.vn != null) ||
				(vns != null && this.vn == null) ||
				vns.length != this.vn.length)
			throw new InvalidParameterException("Invalid ValueAndName");
		//check vns
		for (ValueAndName vn : vns) {
			if(vn.value.length != r.getRules().size()) {
				throw new InvalidParameterException("Rule count does not equal to value count:" + 
						vn.name);
			}
		}
		rule = r;
		vn = vns;
		DefaultTableModel m = (DefaultTableModel) (getJTable().getModel());
		int count = m.getRowCount();
		for(int i = count -1; i>=0; i--) {
			m.removeRow(i);
		}
		int i;
		RuleResolver resolver = new RuleResolver();
		for (i = 0; i < rule.getRules().size(); i++)
		{
			m.addRow(resolver.resolveRuleObj(rule, vn, i));
		}
		this.repaint();
	}
	
}