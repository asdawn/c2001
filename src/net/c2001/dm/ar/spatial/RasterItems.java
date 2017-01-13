package net.c2001.dm.ar.spatial;

import java.util.List;
import org.gdal.gdal.Dataset;

/**
 * Raster datasets as items. Each raster dataset will be treated as an item.
 * 
 * 
 * @author Lin Dong
 * 
 */
public class RasterItems extends SpatialItems<Dataset> {

	private static final long serialVersionUID = -271937722831557413L;

	/**
	 * Create a {@link RasterItems}.
	 */
	public RasterItems() {
	}

	/**
	 * Create a {@link RasterItems} with given parameters.
	 * @param datasets datasets as items.
	 * @param names names of items.
	 */
	public RasterItems(List<Dataset> datasets, List<String> names){
		super(datasets, names);
	}
	
	
}
