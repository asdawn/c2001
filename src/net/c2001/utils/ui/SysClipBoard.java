package net.c2001.utils.ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * Access to the clip board of the OS.
 * 
 * @author Lin Dong
 */
public class SysClipBoard {
	/**
	 * No instance is needed.
	 */
	private SysClipBoard() {

	}

	/**
	 * Write the given text to the system clip board.
	 * 
	 * @param str
	 *            {@link String}
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public static boolean writeClipBoard(String str) {
		Transferable tText = new StringSelection(str);
		java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(tText, null);
		return true;
	}

	/**
	 * Read a string from the system clip board.
	 * @param caller the caller of this method.
	 * @return the content of the clip board, {code null} will be return if it
	 *         is empty or error occurred.
	 */
	public static Object readClipBoard(net.c2001.base.Object caller) {
		Transferable clipT = java.awt.Toolkit.getDefaultToolkit()
				.getSystemClipboard().getContents(null);
		if (clipT != null) {
			if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				try {
					return (String) clipT
							.getTransferData(DataFlavor.stringFlavor);
				} catch (Exception e) {
					caller.warning("Content type of clipboard not supported.");
				}
			}
		}
		return null;
	}

}
