package net.c2001.dm.ar.spatial.raster;

import java.util.List;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;

import net.c2001.base.Global;
import net.c2001.dm.ar.spatial.AreaCalculator;
import net.c2001.utils.spatial.base.RasterOps;

/**
 * {@link AreaCalculator} for raster datasets.
 * @author Lin Dong
 *
 */
public class RasterAC extends AreaCalculator<Dataset> {

	private static final long serialVersionUID = 1027946232795219002L;
	private int i = 0;
	
	public RasterAC(List<Dataset> datasets) {
		super(datasets);
	}

	@Override
	protected Dataset getIntersection(List<Dataset> datasets) {
		String tempFolder = Global.getInstance().tempFolder;
		RasterOps.getBooleanAnd(datasets, tempFolder+"/ba"+i+".tif");
		i++;
		return gdal.Open(tempFolder+"/ba"+(i-1)+".tif");
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
