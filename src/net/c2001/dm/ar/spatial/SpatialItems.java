package net.c2001.dm.ar.spatial;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import net.c2001.base.Object;

/**
 * Stores information about spatial datasets as items. Each dataset will be
 * treated as an item, and name of this item is the alias of it.
 * 
 * @author Lin Dong
 * @param <E>
 *            the dataset class.
 */
public abstract class SpatialItems<E> extends Object {
	/**
	 * Datasets as items.
	 */
	protected List<E> items = null;
	/**
	 * Names of items.
	 */
	protected List<String> names = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a {@link SpatialItems}.
	 */
	public SpatialItems() {
		this.items = new ArrayList<>();
		this.names = new ArrayList<>();
	}

	/**
	 * Create a {@link SpatialItems} with given parameters.
	 * 
	 * @param datasets
	 *            spatial datasets to use. Here aliases are needed. 
	 * @param names
	 *            names of these items.
	 */
	public SpatialItems(List<E> datasets, List<String> names) {
		this.items = new ArrayList<>();
		this.names = new ArrayList<>();
		addItems(datasets, names);
	}

	/**
	 * Add spatial datasets as items.
	 * 
	 * @param datasets
	 *            spatial datasets as items.
	 * @param names
	 *            names of these items.
	 */
	public void addItems(List<E> datasets, List<String> names){
		if(datasets == null || names == null)
			throw new NullPointerException();
		if(names.size() != datasets.size())
			throw new InvalidParameterException();
		this.items.addAll(datasets);
		this.names.addAll(names);
	}

	/**
	 * Add a spatial dataset to {@link SpatialItems}.
	 * 
	 * @param dataset
	 *            spatial dataset to add.
	 *             @param name
	 *            names of these items.
	 */
	 public void addItem(E dataset,String name){
		if(dataset == null || name == null)
			throw new InvalidParameterException();
		this.items.add(dataset);
		this.names.add(name);
	}

	/**
	 * Remove the specified dataset from current {@link SpatialItems}.
	 * 
	 * @param dataset
	 *            some dataset as an item.
	 */
	public void removeDataset(E dataset){
		int index = this.items.indexOf(dataset);
		if(index == -1)
			return;
		this.items.remove(index);
		this.names.remove(index);
	}
	/**
	 * Remove all items.
	 */
	public void clear() {
		this.items.clear();
		this.names.clear();
	}

	/**
	 * Get the datasets in current {@link SpatialItems}.
	 * 
	 * @return a list of datasets. It may be {@code null}.
	 */
	public List<E> getDatasets(){
		return this.items;
	}

	/**
	 * Get aliases of datasets.
	 * 
	 * @return aliases of feature layers. If there are no feature layer
	 *         {@code null} will be returned.
	 */
	public List<String> getNames(){
		return this.names;
	}
}
