package net.c2001.base.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.c2001.base.MessageProcessor;
import net.c2001.dm.ar.ui.ruleviewer.JTablePopMenu;

import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * A panel to show debug informations.
 * 
 * @author Lin Dong
 * 
 */
public class DebugPanel extends JPanel implements MessageProcessor {


	private static final long serialVersionUID = 235078581475264120L;
	private JTable table;
	private boolean stop = false;
	private boolean[] filters;
	
	/**
	 * Decide which messages are to be shown in this panel.
	 * @param filters an array of {@code booleans}, stand for
	 * text, debug text, warning, debug warning, error, debug error, fatal,
	 * debug fatal, exception, debug exception, progress, debug progress successively.
	 * If the value is {@code true}, then corresponding type of messages will be shown,
	 * {@code false} otherwise. The default value is true. 
	 */
	public synchronized void setFilters(boolean[] filters){
		if(filters != null && filters.length== this.filters.length)
			this.filters = filters;
	}
	
		
	/**
	 * Stop receiving messages.
	 */
	public synchronized void stop() {
		stop = true;
	}

	/**
	 * Star to receive messages.
	 */
	public synchronized void start() {
		stop = false;
	}

	/**
	 * Create the panel.
	 */
	public DebugPanel() {
		super();
		filters = new boolean[12];
		for(int i=0;i<filters.length;i++){
			filters[i] = true;
		}
		setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();		
		this.add(scrollPane, BorderLayout.CENTER);
		table = new JTable(getModel()) {
			private static final long serialVersionUID = -3002984943882035566L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		addMenu(table);
		table.getColumnModel().getColumn(0).setMaxWidth(200);
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(2).setMaxWidth(400);
		scrollPane.setViewportView(table);		
	}

	private void addMenu(final JTable table) {
		// add Menu
		final JTablePopMenu m = new JTablePopMenu(table);
		table.add(m);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				checkTrigger(e);
			}

			public void mouseReleased(MouseEvent e) {
				checkTrigger(e);
			}

			private void checkTrigger(MouseEvent e) {
				if (table.getSelectedColumnCount() == 0)
					return;
				if (e.isPopupTrigger()) {
					m.show(table, e.getX(), e.getY());
				}
			}
		});

	}

	private TableModel getModel() {
		Object[] names = { "Time", "Type", "Object", "Message" };
		DefaultTableModel model = new DefaultTableModel(names, 0);
		return model;
	}

	private void append(String type, String color, Object object, String message) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Date date = new Date();
		Object[] row = { date.toString(),
				"<html><font color=" + color + ">" + type + "</color></html>",
				object == null ? "" : object, message };
		model.addRow(row);
	}

	@Override
	public void text(String text) {
		if(!filters[0])
			return;
		_text(null, text);
	}

	@Override
	public void text(Object object, String text) {
		if(!filters[1])
			return;
		_text(object, text);
	}
	
	private void _text(Object object, String text) {
		if (stop)
			return;
		append("Text", "BLACK", object, text);
	}

	@Override
	public void warning(String text) {
		if(!filters[2])
			return;
		_warning(null, text);
	}

	@Override
	public void warning(Object object, String text) {
		if(!filters[3])
			return;
		_warning(object, text);
	}
	
	private void _warning(Object object, String text) {
		if (stop)
			return;
		append("Warning", "ORANGE", object, text);
	}

	@Override
	public void error(String text) {
		if(!filters[4])
			return;
		_error(null, text);
	}

	@Override
	public void error(Object object, String text) {
		if(!filters[5])
			return;
		_error(object,text);
	}
	
	private void _error(Object object, String text) {
		if (stop)
			return;
		append("Error", "RED", object, text);
	}

	@Override
	public void fatal(String text) {
		if(!filters[6])
			return;
		_fatal(null, text);
	}
	
	@Override
	public void fatal(Object object, String text) {
		if(!filters[7])
			return;
		_fatal(object, text);
	}

	private void _fatal(Object object, String text) {
		if (stop)
			return;
		append("<B>FATAL</B>", "RED", object, "<HTML><FONT color=RED>" + text
				+ "</Font></HTML>");

	}

	@Override
	public void exception(Exception e, String text) {
		if(!filters[8])
			return;
		_exception(null, e, text);
	}

	@Override
	public void exception(Object object, Exception e, String text) {
		if(!filters[9])
			return;
		_exception(object, e, text);
	}
	
	private void _exception(Object object, Exception e, String text) {
		if (stop)
			return;
		append("Exception", "RED", object, text + ">>>" + e);
	}

	@Override
	public void progress(double progress, String title, String text) {
		if(!filters[10])
			return;
		_progress(null, progress, title, text);
	}

	@Override
	public void progress(Object object, double progress, String title,
			String text) {		
		if(!filters[11])
			return;
		_progress(object, progress, title, text);
	}
	
	
	private void _progress(Object object, double progress, String title,
			String text) {
		if (stop)			
			return;
		StringBuilder builder = new StringBuilder(title);
		builder.append('(');
		if (progress < 0)
			builder.append("unknown");
		else
			builder.append(progress);
		builder.append("%): ");
		builder.append(text);
		append("Progress", "GREEN", object, builder.toString());
	}
}
