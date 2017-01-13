package net.c2001.dm.ar.spatial.temporal.snapshots;

import net.c2001.dm.ar.garmf.Data;
import net.c2001.dm.ar.spatial.AreaCalculator;
import net.c2001.dm.ar.spatial.RasterItems;

import org.gdal.gdal.Dataset;
/**
 * Raster snapshots as {@link Data}.
 * @author Lin Dong
 *
 */
public class RasterSnapshotData extends SnapshotData<Dataset, RasterSnapshots, RastersItems>{


	private static final long serialVersionUID = 8819414997467061254L;

	/**
	 * Create a {@link RasterSnapshotData}.
	 * @param items snapshots as items, in a {@link RasterItems} object.
	 * @param measure total measure of this data.
	 * @param calc measure calculator. 
	 */
	public RasterSnapshotData(RastersItems items, double measure,
			AreaCalculator<RasterSnapshots> calc) {
		super(items, measure, calc);		
	}

	

}
