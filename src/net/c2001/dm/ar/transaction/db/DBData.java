package net.c2001.dm.ar.transaction.db;

import net.c2001.db.SqlO;
import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.Data;
import net.c2001.dm.ar.garmf.DefaultApriori;
import net.c2001.dm.gmf.Algorithm;

/**
 * {@link Data} that use {@link SqlO} as data source.
 * @author Lin Dong
 *
 */
public abstract class DBData extends Data
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4265402914864279118L;
	protected String tableName;
	protected SqlO o;
	protected int fieldCount;
	protected double recordCount;
	
	/**
	 * Create a {@link DBData}.
	 * @param op {@link SqlO} object which is used to access database.
	 * @param tableName name of table/view or a SQL query string.
	 * @param isSQLString {@code true} means {@code tableName} is a 
	 * query string, otherwise table/view name.<br>
	 * Note: If tableName is a query string, a pare of it will be
	 * brackets will be added to it.
	 */
	public DBData(SqlO op, String tableName, boolean isSQLString)
	{
		super();
		miner = getMiner();
		/*
		 * Register miner as an element object, then it can share the
		 * message processor operations with this class.
		 * 
		 */
		this.addElementObject(miner);
		this.o = op;
		/*
		 * Register o as an element object. 
		 */
		this.addElementObject(this.o);
		if(isSQLString == true)
			this.tableName = "("+tableName+")";
		else
			this.tableName = tableName;
		initialize();
	}	
	/**
	 * Get a subclass of {@link DefaultApriori}. For different data format, different subclass
	 * should be selected or created.
	 * @return  a subclass of {@link DefaultApriori} for mining current {@link Data} object.
	 */
	abstract protected Algorithm<AssociationRules> getMiner();
		

	/**
	 * Initialization works to do.
	 */
	abstract protected void initialize();
}
