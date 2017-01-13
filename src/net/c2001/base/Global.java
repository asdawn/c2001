package net.c2001.base;


import net.c2001.utils.CommonOps;

/**
 * Global variants. Remember to reset the values when you are
 * using a non-Windows system.
 * @author Lin Dong
 *
 */
public class Global {
	private static Global instance = null;
	
	/**
	 * A temporary folder in case the system variable "temp" does not exist.
	 */
	public final String  tempFolderInCase = "c:/temp";
	
	/**
	 * Default temporary folder to use. The default value is the temporary
	 * folder of the operating system.
	 */
	public String tempFolder = null;
	
	private Global(){
		setUpDefaultValues();
	}
	
	private void setUpDefaultValues() {
		String folder = CommonOps.getSystemProperty("temp");
		if(folder != null)
			this.tempFolder = folder;
		else
			this.tempFolder = tempFolderInCase; 
		
	}

	/**
	 * Get the unique instance of {@link Global}.
	 * @return a {@link Global}.
	 */
	public static Global getInstance() {
		if(instance == null){
			instance = new Global();
		}
		return instance;
	}
	
	
	
}
