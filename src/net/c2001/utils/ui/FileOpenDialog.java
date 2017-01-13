package net.c2001.utils.ui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Get path of input file or folder using file open dialogs. 
 * 
 * @author Lin Dong
 * 
 */
public final class FileOpenDialog
{
	private static String defaultPath = null;

	/**
	 * No instance is needed.
	 */
	private FileOpenDialog()
	{

	}

	/**
	 * Show a file open dialog for multi-file/folder selection.
	 * 
	 * @param filter
	 *            {@link FileFilter}.
	 * @param title
	 *            title of this dialog.
	 * @param type
	 *            folder or file, constants in {@link JFileChooser}.
	 * @return array of {@link File} on success, {@code null} on 
	 * empty selection, cancel, or failure. 
	 */
	public static File[] showMultiFileSelector(FileFilter filter, String title,
			int type)
	{
		JFileChooser fc = getJFileChooser();		
		fc.setDialogTitle(title);
		if (filter != null)
			fc.setFileFilter(filter);		
		fc.setFileSelectionMode(type);
		fc.setMultiSelectionEnabled(true);
		int retcode = fc.showOpenDialog(null);
		if (retcode != JFileChooser.APPROVE_OPTION)
			return null;
		else
		{
			File[] files = fc.getSelectedFiles();
			/**
			 * Default directory.
			 */
			try
			{
				if(files != null && files.length != 0)
				{
					if(files[0].isDirectory())
						defaultPath = files[0].getPath();
					else
						defaultPath = files[0].getParent();
				}
			}
			catch (Exception e)
			{				
			}
			return files;
		}
	}

	/**
	 * Show a file open dialog for file selection.
	 * 
	 * @param filter
	 *            {@link FileFilter}.
	 * @param title
	 *            title of this dialog.
	 * @return {@link File} on success, {@code null} on empty selection, 
	 * cancel, or failure. 
	 */
	public static File showFileSelector(FileFilter filter, String title)
	{
		JFileChooser fc = getJFileChooser();		
		fc.setDialogTitle(title);
		if (filter != null)
			fc.setFileFilter(filter);
		fc.setMultiSelectionEnabled(true);
		int retcode = fc.showOpenDialog(null);
		if (retcode != JFileChooser.APPROVE_OPTION)
			return null;
		else
		{
			File file = fc.getSelectedFile();
			/**
			 * Default directory.
			 */
			try
			{
				if(file != null)
				{			
					defaultPath = file.getParent();
				}
			}
			catch (Exception e)
			{				
			}
			return file;
		}

	}

	/**
	 * Show a file open dialog for folder selection.
	 * 
	 * @param title
	 *            title of this dialog.
	 * @return {@link File} on success, {@code null} on empty selection, 
	 * cancel, or failure. 
	 */
	public static File showFolderSelector(String title)
	{
		JFileChooser fc = getJFileChooser();		
		fc.setDialogTitle(title);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int retcode = fc.showOpenDialog(null);
		if (retcode != JFileChooser.APPROVE_OPTION)
			return null;
		else
		{
			File file = fc.getSelectedFile();
			/**
			 * Default directory.
			 */
			try
			{
				if(file != null)
				{			
					defaultPath = file.getPath();
				}
			}
			catch (Exception e)
			{				
			}
			return file;
		}

	}
	
	/**
	 * Create a {@link JFileChooser} instance using the given start path.
	 * @return {@link JFileChooser}.
	 */
	private static JFileChooser getJFileChooser()
	{
		JFileChooser fc = null;
		/**
		 * Set the default path.
		 */
		if(defaultPath != null)			
			fc = new JFileChooser(defaultPath);
		else
			fc = new JFileChooser();
		return fc;
	}

	/**
	 * Show a file save dialog.
	 * 
	 * @param title
	 *            title of this dialog.
	 * @return {@link File} on success, {@code null} on empty selection, 
	 * cancel, or failure. 
	 */
	public static File showFileSaveDialog(FileFilter filter, String title)
	{
		JFileChooser fc = getJFileChooser();		
		fc.setDialogTitle(title);
		if (filter != null)
			fc.setFileFilter(filter);
		fc.setMultiSelectionEnabled(true);
		int retcode = fc.showSaveDialog(null);
		if (retcode != JFileChooser.APPROVE_OPTION)
			return null;
		else
		{
			File file = fc.getSelectedFile();
			/**
			 * Set the default directory.
			 */
			try
			{
				if(file != null)
				{			
					defaultPath = file.getPath();
				}
			}
			catch (Exception e)
			{				
			}
			return file;
		}

	}

}
