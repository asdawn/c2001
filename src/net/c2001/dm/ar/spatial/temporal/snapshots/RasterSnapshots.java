package net.c2001.dm.ar.spatial.temporal.snapshots;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;
/**
 * A group of raster snapshots as an item.
 * @author Lin Dong
 *
 */
public class RasterSnapshots extends SnapShots<Dataset>{

	private static final long serialVersionUID = -438942270269634892L;

	/**
	 * Create a {@link RasterSnapshots}.
	 * @param list snapshots layers.
	 * @param name name of this item.
	 */
	public RasterSnapshots(List<Dataset> list, String name) {
		super(list, name);		
	}

	/**
	 * A tool for creating snapshots.
	 * @param dir the folder which stores the snapshots.
	 * @param name name of this item.
	 * @param names names of the snapshot layers. They must be in {@code dir).
	 * @return {@link RasterSnapshots} on success, {@code null} on failure.
	 */
	public static RasterSnapshots openSnapshots(String dir, String name, String... names){
		if(dir == null || names == null || names.length == 0){
			throw new NullPointerException("No path or file name.");
		}
		List<Dataset> datasets = new ArrayList<>();
		if((!dir.endsWith("/")) && !dir.endsWith("\\")){
			dir+="/";
		}
		for (String n : names) {
			Dataset temp = gdal.Open(dir+n);
			if(temp == null){
				throw new InvalidParameterException("Can not open "+n);
			}
			datasets.add(temp);	
		}
		return new RasterSnapshots(datasets, name);
	}

}
