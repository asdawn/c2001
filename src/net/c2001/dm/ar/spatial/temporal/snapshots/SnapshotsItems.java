package net.c2001.dm.ar.spatial.temporal.snapshots;

import java.util.ArrayList;
import java.util.List;
import net.c2001.base.Object;
import net.c2001.dm.ar.garmf.ItemNames;

/**
 * Snapshots as items.
 * @author Lin Dong
 *
 * @param <T> layer type
 */
public abstract class SnapshotsItems<E,T extends SnapShots<E>> extends Object{

	private static final long serialVersionUID = -4472519543938642098L;

	/**
	 * Names of items.
	 */
	protected ItemNames names = null;
	
	/**
	 * list of {@link SnapShots} as items.
	 */
	protected List<T> items = null;
	
	/**
	 * Get names of items.
	 * @return {@link ItemNames}.
	 */
	public ItemNames getItemNames(){
		if(names == null){			
			updateNames();
		}
		return names;
	}

	/**
	 * Get the {@code i}th item (a {@link SnapShots} object).
	 * @param i the index of the item to fetch.
	 * @return the {@code i}th item on success, {@code null} on failure.
	 */
	public T getSnapshots(int i){
		if(items == null){
			this.exception(this, new NullPointerException("Snapshots not initialized."),"Snapshots not initialized.");
			return null;
		}
		else if(i<0 || i >= this.size()){
			this.exception(this, new ArrayIndexOutOfBoundsException(),"Invalid index.");
			return null;
		}
		return items.get(i);
	}
	
	/**
	 * Returns layer count.
	 * @return the number of layers.
	 */
	public int size(){
		if(items == null){
			this.warning(this, "Items not initialized.");		
			return 0;
		}
		else
			return items.size();
	}

	private void updateNames() {
		int i = 0;
		if(this.items != null){
			List<String> ns = new ArrayList<>();
			for (T item : this.items) {
				String n = item.getName();
				if(n == null){
					ns.add("Unknown"+i);
					i++;
					}
				else
					ns.add(n);
			}
			names = new ItemNames(ns);
		}
		
	}
	
	/**
	 * Create a {@link SnapshotsItems}.
	 * @param items a list of {@link SnapShots} as items.
	 */
	public SnapshotsItems(List<T> items){
		if(items == null || items.size()==0)
			throw new NullPointerException("No items in the list");
		this.items = items;
	}
}
