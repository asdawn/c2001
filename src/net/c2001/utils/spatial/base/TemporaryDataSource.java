package net.c2001.utils.spatial.base;


import org.gdal.ogr.DataSource;
/**
 * An temporary data source which support {@link AutoCloseable}.
 * @author Lin Dong
 *
 */
public class TemporaryDataSource implements AutoCloseable {
	
	DataSource ds = null;
	
	/**
	 * Create a temporary data source. It will be removed after use.
	 * @param dataSource a data source to bind.
	 */
	public TemporaryDataSource(DataSource dataSource){
		this.ds = dataSource;
	}

	@Override
	public void close() throws Exception {
		if(this.ds != null)
			this.ds.delete();
	}

}
