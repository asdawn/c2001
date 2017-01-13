package net.c2001.dm.ar.spatial.polygon;

import java.util.List;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.Layer;
import net.c2001.dm.ar.spatial.HashedAreaCalculator;
import net.c2001.utils.spatial.base.FeatureOps;

/**
 * {@link HashedAreaCalculator} for feature layers.
 * @author Lin Dong
 *
 */
public class HashedFeatureAC extends HashedAreaCalculator<Layer> {

	private static final long serialVersionUID = 2878433537226718146L;
	private int i = 0;
	protected DataSource ds = null;
	
	/**
	 * Create a {@link HashedFeatureAC}.
	 * @param layers feature layers as items.
	 * @param ds a temporary workspace, all feature layers in it might
	 * be removed.
	 */
	public HashedFeatureAC(List<Layer> layers, DataSource ds) {
		super(layers);
		this.doClean = false;
		this.doCleanEveryIntersection = false;
		if(ds == null){
			this.ds = FeatureOps.getMemoryDataSource("TEMP");
		}
		else
			this.ds = ds;
	}

	@Override
	protected Layer getIntersection(List<Layer> layers) {
		i++;
		if(false ==FeatureOps.getIntersection(layers, this.ds, "hfa"+(i-1))){
			return null;
		}
		return ds.GetLayer("hfa"+(i-1));
	}

	@Override
	protected double getArea(Layer layer) {		
		return FeatureOps.getArea(layer);
	}

	@Override
	protected void doClean(Layer layer) {
		int count = layer.GetRefCount();
		this.ds.DeleteLayer(count);		
	}

}
