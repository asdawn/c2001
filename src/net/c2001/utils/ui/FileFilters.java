package net.c2001.utils.ui;

import java.io.ObjectOutputStream;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Common file filters.
 * @author Lin Dong
 *
 */
public final class FileFilters
{
	private static FileNameExtensionFilter 
		RasterFilter = null;
	private static FileNameExtensionFilter ShapeFilter = null;
	private static FileNameExtensionFilter TableFilter = null;
	private static FileNameExtensionFilter SdeFilter = null;
	private static FileNameExtensionFilter CsvFilter = null;
	private static FileNameExtensionFilter ImgFilter = null;
	private static FileNameExtensionFilter GeoFilter = null;
	private static FileNameExtensionFilter MatlabFilter = null;
	private static FileNameExtensionFilter ObjectFilter = null;
	private static FileNameExtensionFilter KMLFilter = null;
	private static FileNameExtensionFilter FileDataFilter = null;
	private static final String raster =
			"Imagine and GeoTIFF Images(*.IMG, *.TIF)";
	private static final String shape = "Shape file(*.SHP)";
	private static final String DB = "Database table(*.CSV, *.DBF)";
	private static final String SDE = "SDE connection file(*.SDE)";
	private static final String csv = "CSV file(*.CSV)";
	private static final String img = "Image raster(*.IMG)";
	private static final String geo ="Geodata(*.SHP, *.IMG, *.TIF)";
	private static final String mat = "Matlab data file (*.mat)";
	private static final String obj = "Java object file (*.obj)";
	private static final String kml = "Google earth KML (*.kml, *.xml)";
	private static final String fileData = "File data (*.csv, *.txt, *.tab, *.trans)";
	
	/**
	 * Returns file filter which accepts files of Google Earth.
	 * 
	 * @return {@link FileNameExtensionFilter} that accepts .KML and .XML extensions.
	 */
	public static FileFilter getGoogleEarthFilter()
	{

		if (KMLFilter == null)
		{
			KMLFilter = new FileNameExtensionFilter(kml, "KML", "XML");
		}
		return KMLFilter;
	}
	
	/**
	 * Returns file filter which accepts files of Google Earth.
	 * 
	 * @return {@link FileNameExtensionFilter} that accepts .KML and .XML extensions.
	 */
	public static FileFilter getFileDataFilter()
	{

		if (FileDataFilter == null)
		{
			FileDataFilter = new FileNameExtensionFilter(fileData, "CSV", "TXT","TAB","TRANS");
			
		}
		return FileDataFilter;
	}
	
	
	/**
	 * Returns file filter which accepts raster files.
	 * 
	 * @return {@link FileNameExtensionFilter} that accepts .IMG and .TIF extensions.
	 */
	public static FileFilter getRasterFilter()
	{

		if (RasterFilter == null)
		{
			RasterFilter = new FileNameExtensionFilter(raster, "IMG", "TIF");
		}
		return RasterFilter;
	}

	/**
	 * Returns file filter which accepts shape files.
	 * 
	 * @return {@link FileNameExtensionFilter} that accepts .SHP extension.
	 */
	public static FileFilter getShapeFilter()
	{

		if (ShapeFilter == null)
		{
			ShapeFilter = new FileNameExtensionFilter(shape, "SHP");
		}
		return ShapeFilter;
	}

	/**
	 * Returns file filter which accepts data tables.
	 * 
	 * @return {@link FileNameExtensionFilter} that accepts .CSV and .DBF extensions.
	 */
	public static FileFilter getDBFileFilter()
	{

		if (TableFilter == null)
		{
			ShapeFilter = new FileNameExtensionFilter(DB, "CSV", "DBF");
		}
		return ShapeFilter;
	}

	/**
	 * Returns file filter which accepts SDE connection files.
	 * 
	 * @return {@link FileNameExtensionFilter} that accepts .SDE extension.
	 */
	public static FileFilter getSDEFilter()
	{

		if (SdeFilter == null)
		{
			SdeFilter = new FileNameExtensionFilter(SDE, "SDE");
		}
		return SdeFilter;
	}
	
	/**
	 * Returns file filter which accepts CSV files.
	 * 
	 * @return {@link FileNameExtensionFilter} that accepts .CSV extension.
	 */
	public static FileFilter getCsvTableFilter()
	{

		if (CsvFilter == null)
		{
			CsvFilter = new FileNameExtensionFilter(csv, "CSV");
		}
		return CsvFilter;
	}
	
	/**
	 * Returns file filter which accepts java object files (written using 
	 * {@link ObjectOutputStream}).
	 * 
	 * @return {@link FileNameExtensionFilter} that accepts .OBJ extension.
	 */
	public static FileFilter getJavaObjectFilter()
	{

		if (ObjectFilter == null)
		{
			ObjectFilter = new FileNameExtensionFilter(obj, "OBJ");
		}
		return ObjectFilter;
	}
	
	/**
	 * Returns file filter which accepts imagine raster files.
	 * 
	 * @return {@link FileNameExtensionFilter} that accepts .IMG extension.
	 */
	public static FileFilter getImgRasterFilter()
	{

		if (ImgFilter == null)
		{
			ImgFilter = new FileNameExtensionFilter(img, "IMG");
		}
		return ImgFilter;
	}
	
	/**
	 * Returns file filter which accepts raster and shape files.
	 * 
	 * @return {@link FileNameExtensionFilter} that accepts .IMG, .TIF and SHP extensions.
	 */
	public static FileFilter getGeoFilter()
	{
		if (GeoFilter == null)
		{
			GeoFilter = new FileNameExtensionFilter(geo, "IMG","TIF","SHP");
		}
		return GeoFilter;
	}

	/**
	 * Returns file filter which accepts Matlab files.
	 * 
	 * @return {@link FileNameExtensionFilter} that accepts .MAT extension.
	 */
	public static FileFilter getMatlabFileFilter()
	{
		if (MatlabFilter == null)
		{
			MatlabFilter = new FileNameExtensionFilter(mat, "MAT");
		}
		return MatlabFilter;
	}
	
	/**
	 * No instance is needed.
	 */
	private FileFilters()
	{
	}

}
