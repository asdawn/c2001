package net.c2001.dm.ar.spatial.temporal.snapshots;

import java.util.List;
import net.c2001.base.Global;
import net.c2001.dm.ar.spatial.AreaCalculator;
import net.c2001.utils.spatial.base.RasterOps;

import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;

/**
 * Measurer for {@link RasterSnapshots}. It is an {@link AreaCalculator},
 * however here we should use the word "measure" instead of "area".
 * 
 * @author Lin Dong
 * 
 */
public class RasterSM extends SnapshotsCalc<Dataset, RasterSnapshots> {

	private static int cc = 0;

	private static final long serialVersionUID = 9066276081938649881L;

	/**
	 * Create a {@link RasterSM}.
	 * 
	 * @param ds
	 *            list of {@link RasterSnapshots}.
	 */
	public RasterSM(List<RasterSnapshots> ds) {
		super(ds);
		this.doCleanEveryIntersection = false;
	}

	@Override
	protected RasterSnapshots createSnapshots(List<Dataset> layers) {
		return new RasterSnapshots(layers, "intersection");
	}

	@Override
	protected Dataset getLayerIntersection(List<Dataset> layers) {
		cc++;
		String temp = Global.getInstance().tempFolder;		
		boolean ok = RasterOps.getBooleanAnd(layers, temp + "\\t" + cc + ".tif");
		if (!ok)
			return null;
		else {
			return gdal.Open(temp + "\\t" + cc + ".tif");
		}

	}

	@Override
	protected double measure(Dataset layer) {
		return RasterOps.getArea(layer, (byte) 1);
	}

	@Override
	protected void doClean(RasterSnapshots dataset) {
	}
}
