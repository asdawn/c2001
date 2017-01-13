package net.c2001.utils.spatial.base;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;

/**
 * Basic operations over raster datasets.
 * 
 * @author Lin Dong
 * 
 */
public class RasterOps {
	/**
	 * Get the area of given raster dataset.
	 * 
	 * @param dataset
	 *            the raster dataset to get area.
	 * @param value
	 *            the pixel value stands for {@code true}.
	 * @return the area on success, a minus value on failure.
	 */
	public static double getArea(Dataset dataset, byte value) {
		try {
			double[] trans = dataset.GetGeoTransform();
			// north up image
			double width = Math.abs(trans[1]);
			double height = Math.abs(trans[5]);
			int x = dataset.getRasterXSize();
			int y = dataset.getRasterYSize();
			Band band = dataset.GetRasterBand(1);
			byte[] values = new byte[x * y];
			band.ReadRaster(0, 0, x, y, x, y, gdalconstConstants.GDT_Byte,
					values);
			int count = 0;
			for (byte b : values) {
				if (b == value)
					count++;
			}
			return count * width * height;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Get the boolean and of {@code a} and {@code b}.
	 * 
	 * @param a
	 *            a raster dataset.
	 * @param b
	 *            another raster dataset.
	 * @param outPath
	 *            the path for output TIFF file.
	 * @return the intersection of {@code a} and {@code b} on success,
	 *         {@code null} otherwise.
	 */
	public static boolean getBooleanAnd(Dataset a, Dataset b, String outPath) {
		int x = a.getRasterXSize();
		int y = a.getRasterYSize();
		byte[] av = new byte[x * y];
		byte[] bv = new byte[x * y];
		byte[] output = new byte[x * y];
		a.GetRasterBand(1).ReadRaster(0, 0, x, y, x, y,
				gdalconstConstants.GDT_Byte, av);
		b.GetRasterBand(1).ReadRaster(0, 0, x, y, x, y,
				gdalconstConstants.GDT_Byte, bv);
		for (int i = 0; i < x * y; i++) {
			output[i] = (byte) (av[i] & bv[i]);
		}
		Driver tiffDriver = gdal.GetDriverByName("Gtiff");
		Dataset out = tiffDriver.Create(outPath, a.getRasterXSize(),
				a.getRasterYSize());
		// If there are no data value, copy it.
		out.SetMetadata(a.GetMetadata_Dict());
		out.SetProjection(a.GetProjectionRef());
		out.SetGeoTransform(a.GetGeoTransform());
		out.GetRasterBand(1).WriteRaster(0, 0, x, y, x, y,
				gdalconstConstants.GDT_Byte, output);
		out.FlushCache();
		return true;
	}

	/**
	 * Get the boolean and of a group of raster datasets.
	 * 
	 * @param datasets
	 *            raster datasets.
	 * @param outPath
	 *            the path for output TIFF file.
	 * @return the intersection on success, {@code null} otherwise.
	 */
	public static boolean getBooleanAnd(List<Dataset> datasets, String outPath) {
		if (datasets.size() < 2)
			throw new InvalidParameterException();
		int x = datasets.get(0).getRasterXSize();
		int y = datasets.get(0).getRasterYSize();
		byte[] v = new byte[x * y];
		byte[] output = new byte[x * y];
		Arrays.fill(output, (byte) 1);
		for (Dataset dataset : datasets) {
			dataset.GetRasterBand(1).ReadRaster(0, 0, x, y, x, y,
					gdalconstConstants.GDT_Byte, v);
			for (int i = 0; i < x * y; i++) {
				if (v[i] == 1 && output[i] == 1)
					output[i] = 1;
				else
					output[i] = 0;
			}
		}
		Driver tiffDriver = gdal.GetDriverByName("Gtiff");
		Dataset out = tiffDriver.Create(outPath, datasets.get(0)
				.getRasterXSize(), datasets.get(0).getRasterYSize());
		// If there are no data value, copy it.
		out.SetMetadata(datasets.get(0).GetMetadata_Dict());
		out.SetProjection(datasets.get(0).GetProjectionRef());
		out.SetGeoTransform(datasets.get(0).GetGeoTransform());
		out.GetRasterBand(1).WriteRaster(0, 0, x, y, x, y,
				gdalconstConstants.GDT_Byte, output);
		out.FlushCache();
		return true;
	}
}
