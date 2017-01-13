package net.c2001.utils.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * Export the content of a table.
 * 
 * @author Lin Dong
 */
public class TableExporter
{
	/**
	 * No instance is needed.
	 */
	private TableExporter()
	{
	}

	/**
	 * Export the content of a {@link JTable} to .csv file.
	 * 
	 * @param table
	 *            {@link JTable} to export.
	 * @return {@code true} on success, {@code false} on failure.<br>
	 *          This might be time consuming, referring to the size
	 *          of the contents.
	 *          
	 */
	public static boolean saveAs(JTable table)
	{
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("C:\\"));
		fc.setSelectedFile(new File("table.csv"));
		int retMethod = fc.showSaveDialog(fc);
		if (retMethod != JFileChooser.APPROVE_OPTION)
			return false;
		try
		{
			File csvFile = fc.getSelectedFile();
			FileWriter fw = new FileWriter(csvFile);
			int nRows = table.getRowCount();
			int nCols = table.getColumnCount();			
			StringBuffer s = new StringBuffer();
			TableModel model = table.getModel();
			for (int i = 0; i < (nCols - 1); i++)
			{
				s.append(model.getColumnName(i));
				s.append(",");
			}
			s.append(model.getColumnName(nCols - 1));
			s.append("\r\n");
			for (int i = 0; i < nRows; i++)
			{
				for (int j = 0; j < (nCols - 1); j++)
				{
					s.append(String.valueOf(model.getValueAt(i, j)));
					s.append(",");
				}
				s.append(String.valueOf(model.getValueAt(i, (nCols - 1))));
				s.append("\r\n");
				/*
				 * Flush when the size is larger than 64K.
				 */
				if(s.length() > 1024*64)
				{
					fw.write(s.toString());
					fw.flush();					
					s = new StringBuffer();
				}				
			}
			fw.write(s.toString());
			fw.flush();
			fw.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			return false;
		}
		return true;
	}

}
