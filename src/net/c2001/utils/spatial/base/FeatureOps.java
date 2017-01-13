package net.c2001.utils.spatial.base;

import java.util.List;
import java.util.Vector;

import org.gdal.ogr.DataSource;
import org.gdal.ogr.Driver;
import org.gdal.ogr.Feature;
import org.gdal.ogr.Geometry;
import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;
import org.gdal.ogr.ogrConstants;

/**
 * Basic operations over feature datasets.
 * 
 * @author Lin Dong
 * 
 */
public class FeatureOps {
	/**
	 * Get the area of given feature dataset.
	 * 
	 * @param layer
	 *            a feature layer.
	 * @return the area on success, a minus value on failure.
	 */
	public static double getArea(Layer layer) {
		double area = -1;
		if (layer != null) {
			double sum = 0;
			int featureCount = layer.GetFeatureCount();
			for (int i = 0; i < featureCount; i++) {
				Feature feature = layer.GetFeature(i);
				Geometry geometry = feature.GetGeometryRef();
				int type = geometry.GetGeometryType();
				if (type != ogrConstants.wkbPolygon
						&& type != ogrConstants.wkbMultiPolygon
						&& type != ogrConstants.wkbLinearRing
						&& type != ogrConstants.wkbGeometryCollection)
					continue;
				sum += geometry.Area();
			}
			area = sum;
		}
		return area;
	}

	/**
	 * Get the intersection of {@code a} and {@code b}.
	 * 
	 * @param a
	 *            a feature layer.
	 * @param b
	 *            another feature layer.
	 * @param result
	 *            layer to save the result.
	 * @param sync
	 *            synchronize the result layer to disk or not.
	 * @return the intersection of {@code a} and {@code b} on success,
	 *         {@code null} otherwise. The result should be a layer in memory,
	 *         remember to save it if necessary.<br>
	 *         Note: the intersection is <i>a.clip(b)</i> in fact.
	 */
	public static Layer getIntersection(Layer a, Layer b, Layer result,
			boolean sync) {
		try {
			Vector<String> v = new Vector<String>();
			v.add("PROMOTE_TO_MULTI=NO");
			v.add("SKIP_FAILURES=YES");
			a.Clip(b, result, v);
			if (sync)
				result.SyncToDisk();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get the intersection of {@code a} and {@code b}.
	 * 
	 * @param a
	 *            a feature layer.
	 * @param b
	 *            another feature layer.
	 * @param name
	 *            name of the output layer.
	 * @return the intersection of {@code a} and {@code b} on success,
	 *         {@code null} otherwise. The result should be a layer in memory,
	 *         remember to save it if necessary.<br>
	 *         Note: the intersection is <i>a.clip(b)</i> in fact.
	 */
	public static Layer getIntersection(Layer a, Layer b, String name) {
		try {
			Vector<String> v = new Vector<String>();
			v.add("PROMOTE_TO_MULTI=NO");
			v.add("SKIP_FAILURES=YES");
			DataSource dataSource = getTempMemDataSource();
			Layer result = dataSource.CreateLayer(name);
			a.Clip(b, result, v);
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get the intersection of feature classes.
	 * 
	 * @param layers
	 *            feature classes.
	 * 
	 * @param outName
	 *            the name of the result.
	 * @return the intersection.
	 */
	public static Layer getIntersection(List<Layer> layers, String outName) {
		if(layers == null)
			throw new NullPointerException();
		
		if (layers.size() == 0){
			return null;
		}
			else if(layers.size() == 1) {
				return layers.get(0);
		}
		else {
			DataSource dataSource = getTempMemDataSource();
			if (dataSource == null)
				return null;
				// Try with resource in java 7
			DataSource ds = getTempMemDataSource();	
			Layer result1 = ds.CreateLayer("result1", layers.get(0)
							.GetSpatialRef());
			Layer result2 = ds.CreateLayer("result2", layers.get(0)
							.GetSpatialRef());
			//System.out.println(layers.get(1));
			//System.out.println(layers.get(0));
			layers.get(1).Clip(layers.get(0), result1);
			boolean r1 = true; // 1 ok
			for (int i = 2; i < layers.size(); i++) {
				if (r1)
					layers.get(i).Clip(result1, result2);
				else
					layers.get(i).Clip(result2, result1);
				r1 = !r1;
			}
			return r1 ? result1 : result2;
		}
	}

	/**
	 * Get the intersection of feature classes.
	 * 
	 * @param layers
	 *            feature classes.
	 * @param outDS
	 *            output data source.
	 * @param outName
	 *            the name of the result.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public static boolean getIntersection(List<Layer> layers, DataSource outDS,
			String outName) {
		try {
			if (layers.size() == 1) {
				outDS.CopyLayer(layers.get(0), outName);
				outDS.SyncToDisk();
				return true;
			} else if (layers.size() == 2) {
				Layer result = outDS.CreateLayer(outName, layers.get(0)
						.GetSpatialRef());
				result = getIntersection(layers.get(0), layers.get(1), result,
						true);
				return (result != null);
			} else {
				DataSource mds = getMemoryDataSource("inttemp");
				if (mds == null)
					return false;
				// Try with resource in java 7
				try (TemporaryDataSource ds = new TemporaryDataSource(mds)) {
					Layer result1 = mds.CreateLayer("result1", layers.get(0)
							.GetSpatialRef());
					Layer result2 = mds.CreateLayer("result2", layers.get(0)
							.GetSpatialRef());
					layers.get(1).Clip(layers.get(0), result1);
					boolean r1 = true; // 1 ok
					for (int i = 2; i < layers.size(); i++) {
						if (r1)
							layers.get(i).Clip(result1, result2);
						else
							layers.get(i).Clip(result2, result1);
						r1 = !r1;
					}
					outDS.CopyLayer(r1 ? result1 : result2, outName);
				} catch (Exception e) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Get a temporary data source in memory.
	 * 
	 * @return a temporary data source in memory.
	 */
	private static DataSource getTempMemDataSource() {
		return getMemoryDataSource("TEMP");
	}

	/**
	 * Create a temporary {@link DataSource} in memory.
	 * 
	 * @return the data source on success, {@code null} on failure.
	 */
	public static DataSource getMemoryDataSource(String name) {
		//Driver driver = ogr.GetDriverByName("ESRI Shapefile");
		Driver driver = ogr.GetDriverByName("Memory");
		DataSource ds = driver.CreateDataSource(name);
		return ds;
	}

}
