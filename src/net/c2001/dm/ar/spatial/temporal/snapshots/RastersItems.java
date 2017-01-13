package net.c2001.dm.ar.spatial.temporal.snapshots;

import java.util.List;

import net.c2001.dm.ar.spatial.RasterItems;

import org.gdal.gdal.Dataset;

/**
 * Raster snapshots as items.
 * @author Lin Dong
 *
 */
public class RastersItems extends SnapshotsItems<Dataset, RasterSnapshots>{

	private static final long serialVersionUID = -5972141033017924889L;

	/**
	 * Create a {@link RasterItems}.
	 * @param items a list of {@link RasterSnapshots} as items.
	 */
	public RastersItems(List<RasterSnapshots> items) {
		super(items);		
	}
}
