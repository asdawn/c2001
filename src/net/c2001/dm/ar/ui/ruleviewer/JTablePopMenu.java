package net.c2001.dm.ar.ui.ruleviewer;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import net.c2001.utils.ui.SysClipBoard;

/**
 * Pop menu of {@link JTable} which implements functions of copy and line copy,
 * etc.
 * 
 * @author Lin Dong
 */
public class JTablePopMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;

	/**
	 * Create a pop menu for the {@link JTable} in {@link UniViewer}.
	 * 
	 * @param tbl
	 *            the {@link JTable} in {@link UniViewer}.
	 */
	public JTablePopMenu(JTable tbl) {
		final JTable table = tbl;
		final JMenuItem copy = new JMenuItem("Copy", KeyEvent.VK_C);
		final JMenuItem copyinc = new JMenuItem("Copy(with field names)",
				KeyEvent.VK_S);
		final JMenuItem copyln = new JMenuItem("Copy line", KeyEvent.VK_E);
		final JMenuItem copylninc = new JMenuItem(
				"Copy line(with field names)", KeyEvent.VK_D);
		this.add(copy);
		this.add(copyinc);
		this.add(copyln);
		this.add(copylninc);
		MouseAdapter l = new listener(table);
		copy.addMouseListener(l);
		copyinc.addMouseListener(l);
		copyln.addMouseListener(l);
		copylninc.addMouseListener(l);
	}

	/**
	 * {@link MouseAdapter} for {@link JTablePopMenu}.
	 * 
	 * @author Lin Dong
	 * 
	 */
	private class listener extends MouseAdapter {
		private JTable table = null;

		/**
		 * Create a listener for given table.
		 * 
		 * @param tbl
		 *            Table to access.
		 */
		public listener(JTable tbl) {
			table = tbl;
		}

		public void mousePressed(MouseEvent e) {
			JMenuItem item = (JMenuItem) e.getSource();
			int i, j;
			StringBuffer toCopy = new StringBuffer();
			int[] rows = table.getSelectedRows();
			int[] cols = table.getSelectedColumns();
			/*
			 * Just use the common codes.
			 */
			if (item.getText().equals("Copy")) {
			}
			/*
			 * Copy with field names.
			 */
			else if ((item.getText().equals("Copy(with field names)"))) {
				for (i = 0; i < (cols.length - 1); i++) {
					toCopy.append(table.getColumnName(cols[i]));
					toCopy.append("\t");
				}
				toCopy.append(table.getColumnName(cols[i]));
				toCopy.append("\n");
			}
			/*
			 * Copy lines selected.
			 */
			else if ((item.getText().equals("Copy line"))) {
				cols = new int[table.getColumnCount()];
				for (i = 0; i < cols.length; i++) {
					cols[i] = i;
				}
			}
			/*
			 * Copy lines with field names.
			 */
			else if ((item.getText().equals("Copy line(with field names)"))) {
				cols = new int[table.getColumnCount()];
				for (i = 0; i < cols.length; i++) {
					cols[i] = i;
				}
				for (i = 0; i < (cols.length - 1); i++) {
					toCopy.append(table.getColumnName(cols[i]));
					toCopy.append("\t");
				}
				toCopy.append(table.getColumnName(cols[i]));
				toCopy.append("\n");
			}
			/*
			 * Add contents to clip board.
			 */
			for (i = 0; i < rows.length; i++) {
				for (j = 0; j < (cols.length - 1); j++) {
					toCopy.append(table.getValueAt(rows[i], cols[j]).toString());
					toCopy.append("\t");
				}
				toCopy.append(table.getValueAt(rows[i], cols[j]).toString());
				toCopy.append("\n");
			}
			SysClipBoard.writeClipBoard(toCopy.toString());
		}
	}

}
