package net.c2001.dm.ar.spatial;

import java.util.List;
import org.gdal.ogr.Layer;

/**
 * Feature layers as items. Each feature layer will be treated as an item.
 * 
 * 
 * @author Lin Dong
 * 
 */
public class FeatureItems extends SpatialItems<Layer> {

	private static final long serialVersionUID = -2973907021761041854L;

	/**
	 * Create a {@link FeatureItems}.
	 */
	public FeatureItems() {
	}

	/**
	 * Create a {@link FeatureItems} with given parameters.
	 * @param layers layers as items.
	 * @param names names of items.
	 */
	public FeatureItems(List<Layer> layers, List<String> names){
		super(layers, names);
	}
	
	
}
