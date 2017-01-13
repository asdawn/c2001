package net.c2001.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.c2001.dm.ar.garmf.ItemSet;



/**
 * The super class of most important classes in this library. Atomic classes
 * such as {@link ItemSet} won't inherit this class for performance reasons.
 * The output methods with an {@link java.lang.Object} parameter are for
 * debug purpose, if debug output is disabled, these methods will also be
 * disabled. To save more time, subclasses may call the 
 * {@code getDebugOutputStatus} method before try to output debug information.
 * 
 * @author Lin Dong
 * 
 */
public abstract class Object  implements Serializable,MessageProvider {

	/**
	 * Message processors.
	 */
	private List<MessageProcessor> mps = null;
	
	/**
	 * Elements of Object type.
	 */
	private List<Object> elementObjects = null;
	/**
	 * Whether to show messages with object information.
	 */
	private boolean debugOutputStatus = false;
	private static final long serialVersionUID = -49331812379709273L;

	/**
	 * Load an object from file.
	 * @param file given file, a {@link NullPointerException} will be thrown
	 * if it is {@code null}.
	 * @return return the object on success, {@code null} otherwise.
	 */
	public java.lang.Object loadFromFile(File file) {
		if(file == null)
			throw new NullPointerException();
		//try with resource, since java 7.
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			java.lang.Object object = ois.readObject();
			return object;
		} catch (Exception e) {
			exception(this, e, null);
			warning("Failed to load object from "+file.getPath());
			return null;
		}
	}
	
	/**
	 * Save this object to file.
	 * @param file output file to use.
	 * @return {@code true} on success, {@code false} otherwise.
	 */
	public boolean saveToFile(File file) {
		//try with resource, since java 7.
		try(FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);)
		{
			oos.writeObject(this);
			return true;
		}
		catch (Exception e)
		{
			exception(this, e, null);
			warning("Failed to save object to "+file.getPath());
			return false;
		}	

	}

	/**
	 * Add an message processor to this object.
	 * 
	 * @param processor
	 *            message processor. If it is {@code null} nothing will happen.
	 */
	public void addMessageProcessor(MessageProcessor processor) {
		if (processor == null)
			return;
		if (this.mps == null) {
			this.mps = new ArrayList<>();
		}
		this.mps.add(processor);
		if(this.elementObjects!=null && this.elementObjects.isEmpty() == false) {
			for (Object object : this.elementObjects) {
				object.addMessageProcessor(processor);
			}
		}
	}

	/**
	 * Remove an message processor from this object (and its elements).
	 * 
	 * @param processor
	 *            message processor. If it is {@code null} this method will
	 *            return {@code false}.
	 * @return {@code true} if the given processor is in the list, {@code false}
	 *         otherwise.
	 */
	public boolean removeMessageProcessor(MessageProcessor processor) {
		if (processor == null || this.mps == null)
			return false;
		boolean exist = false;
		exist = this.mps.remove(processor);
		if(this.elementObjects!=null && this.elementObjects.isEmpty() == false) {
			for (Object object : this.elementObjects) {
				boolean result = object.removeMessageProcessor(processor);
				if(exist == false && result == true)
					exist = true;
			}
		}
		return exist;
	}

	/**
	 * Remove all message processors.
	 */
	public void clearMessageProcessors() {
		if (this.mps == null)
			return;
		else {
			this.mps.clear();
			if(this.elementObjects!=null && this.elementObjects.isEmpty() == false) {
				for (Object object : this.elementObjects) {
					object.clearMessageProcessors();
				}
			}
		}
	}
	
	/**
	 * Register an {@link Object} element to this object.
	 * 
	 * @param object
	 *            an element of {@link Object} type which should share the same
	 *            {@link MessageProcessor} with this class.
	 */
	public void addElementObject(Object object) {
		if (object == null)
			return;
		if (this.elementObjects == null) {
			this.elementObjects = new ArrayList<>();
		}
		this.elementObjects.add(object);		
	}

	/**
	 * Unregister an {@link Object} element to this object.
	 * 
	 @param object
	 *            an element of {@link Object} type which share the same
	 *            {@link MessageProcessor} with this class.
	 */
	public boolean removeElementObject(Object object) {
		if (object == null || this.elementObjects==null)
			return false;
		return this.elementObjects.remove(object);
	}

	/**
	 * Remove all registered {@link Object} elements.
	 */
	public void clearObjectElements() {
		if (this.elementObjects == null)
			return;
		else {
			this.elementObjects.clear();
		}
	}

	@Override
	public void text(String text) {
		if (mps != null) {
			for (MessageProcessor mp : mps) {
				mp.text(text);
			}
		}

	}

	@Override
	public void text(java.lang.Object object, String text) {
		if (mps != null && getDebugOutputStatus()) {
			for (MessageProcessor mp : mps) {
				mp.text(object, text);
			}
		}

	}

	@Override
	public void warning(String text) {
		if (mps != null) {
			for (MessageProcessor mp : mps) {
				mp.warning(text);
			}
		}

	}

	@Override
	public void warning(java.lang.Object object, String text) {
		if (mps != null && getDebugOutputStatus()) {
			for (MessageProcessor mp : mps) {
				mp.warning(object, text);
			}
		}
	}

	@Override
	public void error(String text) {
		if (mps != null) {
			for (MessageProcessor mp : mps) {
				mp.error(text);
			}
		}

	}

	@Override
	public void error(java.lang.Object object, String text) {
		if (mps != null && getDebugOutputStatus()) {
			for (MessageProcessor mp : mps) {
				mp.error(object, text);
			}
		}
	}

	@Override
	public void fatal(String text) {
		if (mps != null) {
			for (MessageProcessor mp : mps) {
				mp.fatal(text);
			}
		}
	}

	@Override
	public void fatal(java.lang.Object object, String text) {
		if (mps != null && getDebugOutputStatus()) {
			for (MessageProcessor mp : mps) {
				mp.fatal(object, text);
			}
		}
	}

	@Override
	public void exception(Exception e, String text) {
		if (mps != null) {
			for (MessageProcessor mp : mps) {
				mp.exception(e, text);
			}
		}
	}

	@Override
	public void exception(java.lang.Object object, Exception e, String text) {
		if (mps != null && getDebugOutputStatus()) {
			for (MessageProcessor mp : mps) {
				mp.exception(object, e, text);
			}
		}
	}

	@Override
	public void progress(double progress, String title, String text) {
		if (mps != null) {
			for (MessageProcessor mp : mps) {
				mp.progress(progress, title, text);
			}
		}
	}

	@Override
	public void progress(java.lang.Object object, double progress,
			String title, String text) {
		if (mps != null && getDebugOutputStatus()) {
			for (MessageProcessor mp : mps) {
				mp.progress(object, progress, title, text);
			}
		}
	}

	/**
	 * To know whether debug output is enabled.
	 * @return {@code true} for enabled, {@code false} otherwise.
	 */
	public boolean getDebugOutputStatus() {
		return debugOutputStatus;
	}

	/**
	 * Enable or disable debug output.
	 * @param status {@code true} to enable it, {@code false} otherwise.
	 */
	public void setDebugOutputStatus(boolean status) {
		this.debugOutputStatus = status;
		if(this.elementObjects!=null && this.elementObjects.isEmpty()==false) {
			for (Object object : this.elementObjects) {
				object.setDebugOutputStatus(status);
			}
		}
	}
}
