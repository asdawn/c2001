package net.c2001.dm.ar.spatial.raster;

import java.util.List;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;
import net.c2001.base.Global;
import net.c2001.dm.ar.spatial.HashedAreaCalculator;
import net.c2001.utils.spatial.base.RasterOps;

/**
 * {@link HashedAreaCalculator} for raster datasets.
 * @author Lin Dong
 *
 */
public class HashedRasterAC extends HashedAreaCalculator<Dataset> {

	private static final long serialVersionUID = 9041659677777530704L;
	private int i = 0;
	
	/**
	 * Create a {@link HashedRasterAC}.
	 * @param datasets raster datasets as items.
	 */
	public HashedRasterAC(List<Dataset> datasets) {
		super(datasets);
	}

	@Override
	protected Dataset getIntersection(List<Dataset> datasets) {
		String tempFolder = Global.getInstance().tempFolder;
		RasterOps.getBooleanAnd(datasets, tempFolder+"/hba"+i+".tif");
		i++;
		return gdal.Open(tempFolder+"/hba"+(i-1)+".tif");
	}

	@Override
	protected double getArea(Dataset dataset) {
		return RasterOps.getArea(dataset, (byte) 1);
	}

	@Override
	protected void doClean(Dataset dataset) {
		Driver driver = gdal.GetDriverByName("Gtiff");
		String path = dataset.GetDescription();
		dataset.delete();
		driver.Delete(path);		
	}

}
