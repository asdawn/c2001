package net.c2001.dm.ar.spatial.polygon;

import java.util.List;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.Layer;
import net.c2001.dm.ar.spatial.AreaCalculator;
import net.c2001.utils.spatial.base.FeatureOps;

/**
 * {@link AreaCalculator} for feature layers.
 * 
 * @author Lin Dong
 * 
 */
public class FeatureAC extends AreaCalculator<Layer> {

	private static final long serialVersionUID = -4275386767678105741L;
	private int i = 0;
	private DataSource out = null;

	/**
	 * Create a {@link FeatureAC}.
	 * 
	 * @param datasets
	 *            the input datasets as items.
	 * @param out
	 *            the place to put outputs. If it is {@code null} a memory data
	 *            source will be created, and the temporary results will be
	 *            inaccessible. <Not implemented!>
	 */
	public FeatureAC(List<Layer> datasets, DataSource out) {
		super(datasets);
		if (out != null) {
			this.out = out;
		}
	}

	@Override
	protected Layer getIntersection(List<Layer> datasets) {
		this.i++;
		if (this.out == null) {
			return FeatureOps.getIntersection(datasets, "int" + this.i);
		}
		else{
			boolean ok = FeatureOps.getIntersection(datasets, this.out, "int" + this.i);
			if(ok)
				return this.out.GetLayer("int" + this.i);
			else
				return null;
		}
	}

	@Override
	protected double getArea(Layer layer) {
		return FeatureOps.getArea(layer);
	}

	@Override
	protected void doClean(Layer dataset) {
		// TODO Auto-generated method stub
	}
}
