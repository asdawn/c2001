package net.c2001.dm.ar.spatial;

/**
 * A spatial dataset.
 * @author Lin Dong
 *
 */
public interface SpatialDataset {
	/**
	 * The type of this dataset, feature, raster or TIN.
	 * @author Lin Dong
	 *
	 */
	public enum TYPE{
		Feature, Raster, TIN
	};
	
	/**
	 * Alias for datasets that can not get the real name.
	 */
	public final String NAME_UNKNOWN = "Unknown";
	
	/**
	 * Get the measure of this dataset. That is, area, length or count.
	 * @return the measure of this dataset.
	 */
	abstract public double getMeasure();
	
	/**
	 * Get the name of this dataset.
	 * @return the name of this dataset. If it is impossible,
	 * {@code NAME_UNKNOWN} will be returned.
	 */
	abstract public String getName();
	
}
