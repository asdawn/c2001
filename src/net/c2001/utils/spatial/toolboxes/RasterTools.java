package net.c2001.utils.spatial.toolboxes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.c2001.base.DefaultMessageProcessor;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;

/**
 * Tools for manipulating raster data.
 * 
 * @author Lin Dong
 */
public final class RasterTools extends net.c2001.base.Object {

	private static final long serialVersionUID = 2169744472483972842L;

	/**
	 * Create a series of raster datasets as divisions of the input one. These
	 * output datasets are binary ones, where 1s stand for the division area.
	 * 
	 * @param input
	 *            the raster dataset to divide.
	 * @param outfolder
	 *            the folder to place the divisions.
	 * @param prefix
	 *            the prefix of output TIFF file. This will be followed with the
	 *            pixel value and a ".TIF" extension as the file name.
	 * @return {@code true} on success, {@code false} otherwise.
	 */
	public boolean divideByValue(String input, String outfolder, String prefix) {
		try {
			gdal.AllRegister();
			Dataset ds = gdal.Open(input);
			if (ds == null) {
				this.warning("Can not open file: " + input);
				return false;
			}
			List<Byte> bytes = new ArrayList<>();
			Band band = ds.GetRasterBand(1);
			int x = band.getXSize();
			int y = band.getYSize();
			byte[] values = new byte[x * y];
			band.ReadRaster(0, 0, x, y, x, y, gdalconstConstants.GDT_Byte,
					values);
			for (byte value : values) {
				if (bytes.contains(value) == false)
					bytes.add(value);
			}
			Double[] ndv = new Double[1];
			band.GetNoDataValue(ndv);
			if (ndv[0] != null) {
				double v = ndv[0];
				byte vs = (byte) v;
				int i = bytes.indexOf(vs);
				if (i != -1)
					bytes.remove(i);
			}
			Driver driver = gdal.GetDriverByName("Gtiff");
			for (byte value : bytes) {
				String out = outfolder + "/" + prefix + value + ".tif";
				byte[] pv = new byte[x * y];
				for (int i = 0; i < pv.length; i++) {
					if (values[i] == value)
						pv[i] = 1;
				}
				Dataset outDataset = driver.Create(out, x, y);
				// b.AddBand(gdalconstConstants.GDT_Byte);
				outDataset.GetRasterBand(1).SetNoDataValue(0);
				outDataset.SetMetadata(ds.GetMetadata_Dict());
				outDataset.SetProjection(ds.GetProjectionRef());
				outDataset.SetGeoTransform(ds.GetGeoTransform());
				outDataset.GetRasterBand(1).WriteRaster(0, 0, x, y, x, y,
						gdalconstConstants.GDT_Byte, pv, 1, 0);
				outDataset.FlushCache();
				outDataset.delete();
			}
			return true;
		} catch (Exception e) {
			this.exception(e, "Failed to divide raster.");
			return false;
		}
	}

	public static void main(String[] args) {
		
		gdal.AllRegister();
		RasterTools tools = new RasterTools();
		tools.addMessageProcessor(new DefaultMessageProcessor());
		for (int i = 1984; i < 2012; i += 2) {
			tools.divideByValue("e:/c/alameda"+i+".tif", "e:/d", i+"");
		}
		
		String[] type = {"D","G","P","S","U","W","X"};
		for(int i = 1984;i<2012;i+=2){
			for(int j=0;j<type.length;j++)
				new File("e:/d/"+i+""+j+".tif").renameTo(new File("e:/d/"+i+type[j]+".tif"));
		}
		
		

	}

}
