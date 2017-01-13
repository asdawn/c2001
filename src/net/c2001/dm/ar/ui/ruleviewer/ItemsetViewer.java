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

/**
 * Viewer of itemsets, with support of copy and export.
 * 
 * @author Lin Dong
 */
public class ItemsetViewer extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	private JPanel jContentPane = null;
	
	private JPanel jPanel = null;
	
	private JButton btnExport = null;
	
	private JButton btnClose = null;
	
	private JScrollPane jScrollPane = null;
	
	private JTable jTable = null;
	
	private AssociationRules rule = null;

	/**
	 * Get the {@link JTable} in {@link ItemsetViewer}.
	 * 
	 * @return the {@link JTable} in {@link ItemsetViewer}.
	 */
	public JTable getContentTable()
	{
		return jTable;
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
	 * @return   {@link JButton}  
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
	 * @return   {@link JButton}  
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
	 * @return {@link JScrollPane}
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
	 * @return {@link JTable}
	 */
	private JTable getJTable()
	{
		if (jTable == null)
		{
			String[] colNames = new String[] { "no", "itemset", "support" };
			DefaultTableModel dataModal = new DefaultTableModel(colNames, 0);
			jTable = new JTable(dataModal);
			// set the max and min size of cell
			jTable.getColumnModel().getColumn(0).setMinWidth(20);
			jTable.getColumnModel().getColumn(0).setMaxWidth(40);
			jTable.getColumnModel().getColumn(1).setMinWidth(120);
			jTable.getColumnModel().getColumn(1).setMaxWidth(500);
			jTable.getColumnModel().getColumn(2).setMinWidth(40);
			jTable.getColumnModel().getColumn(2).setMaxWidth(80);
			// add rules
			int i, count;;
			RuleResolver resolver = new RuleResolver();
			count = rule.getFrequentItemset().length;
			for (i = 0; i <count; i++)
			{
				dataModal.addRow(resolver.resolveItemsetObj(rule, i));
			}
			// set number column color to gray
			Component cellr = null;
			cellr = new DefaultTableCellRenderer();
			cellr.setBackground(new Color(0xef, 0xef, 0xef));
			jTable.getColumnModel().getColumn(0)
			        .setCellRenderer((TableCellRenderer) cellr);
			jTable.setCellSelectionEnabled(true);
			// add Menu
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
	 * Create an {@link ItemsetViewer}.
	 * 
	 * @param owner
	 *            the owner of this dialog.
	 * @param r
	 *            {@link AssociationRules}.
	 */
	public ItemsetViewer(Frame owner, AssociationRules r)
	{
		super(owner, true);
		rule = r;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(370, 473);
		this.setTitle("View itemsets:");
		this.setContentPane(getJContentPane());
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
			jContentPane.add(getJPanel(), BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * Append a row to the table in {@link ItemsetViewer}.
	 * 
	 * @param rd
	 *            content of the new row, arry of {@link Object}.
	 *            Each {@link Object} stores the values of a filed.
	 */
	public void addRow(Object[] rd)
	{
		DefaultTableModel m = (DefaultTableModel) (getJTable().getModel());
		m.addRow(rd);
	}
}