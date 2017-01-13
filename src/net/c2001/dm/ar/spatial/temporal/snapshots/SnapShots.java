package net.c2001.dm.ar.spatial.temporal.snapshots;

import java.util.List;

import net.c2001.base.Object;

/**
 * A group of snapshots as an item.
 * @author Lin Dong
 *
 * @param <T> type of content layers.
 */
public abstract class SnapShots<T> extends Object {
	private static final long serialVersionUID = 7298582188086806156L;
	/**
	 * layers as snapshots.
	 */
	protected List<T> layers = null;
	/**
	 * Name of this snapshots.
	 */
	protected String name = null;
	
	/**
	 * Returns layer count.
	 * @return the number of layers.
	 */
	public int size(){
		if(layers == null){
			this.warning(this, "Layers not initialized.");		
			return 0;
		}
		else
			return layers.size();
	}
	
	/**
	 * Get the {@code i}th layer.
	 * @param i the index of the layer to fetch.
	 * @return the {@code i}th snapshot on success, {@code null} on failure.
	 */
	public T getLayer(int i){
		if(layers == null){
			this.exception(this, new NullPointerException("Layers not initialized."),"Layers not initialized.");
			return null;
		}
		else if(i<0 || i >= this.size()){
			this.exception(this, new ArrayIndexOutOfBoundsException(),"Invalid index.");
			return null;
		}
		return layers.get(i);
	}
	
	/**
	 * Create an group of snapshots as an item.
	 */
	public SnapShots(List<T> list, String name){
		if(list == null || list.size()==0)
			throw new NullPointerException("No layer in the list");
		this.layers = list;
		this.name = name;
	}
	
	/**
	 * Returns the name of this item.
	 * @return name of this item.
	 */
	public String getName(){
		return name;
	}
	
}
