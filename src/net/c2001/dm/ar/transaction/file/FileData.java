package net.c2001.dm.ar.transaction.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.transaction.TransactionData;

/**
 * File transaction data. Subclasses  
 * @author Lin Dong
 *
 */
public abstract class FileData extends TransactionData{

	private static final long serialVersionUID = -3764564583634043029L;
	
	/**
	 * Data file.
	 */
	protected File dataFile = null;

	/**
	 * Read the next transaction in current file.
	 * @return an {@link ItemSet} stands for a transaction on success, {@code null} if reached the end of file.  
	 * @throws IOException 
	 */
	abstract public ItemSet readTransaction(BufferedReader reader) throws IOException;
}
