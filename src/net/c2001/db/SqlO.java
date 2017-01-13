package net.c2001.db;

import java.sql.*;
import java.util.Vector;
import java.io.*;

/**
 * This class aims to accessing ODBC data sources. <br>
 * NOTE: SQL NULL will be returned as 0 or {@code false}, see also
 * {@link ResultSet}.
 * 
 * @author Lin Dong
 */
public class SqlO extends net.c2001.base.Object
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4007636892965651864L;
	/**
	 * Built-in {@link Connection} object. 
	 */
	protected Connection c = null;
	/**
	 * {@code true} for connected.	 
	 */
	protected boolean cf = false;
	/**
	 * Built-in {@link Statement} object. 
	 */
	private Statement s = null;
	/**
	 * {@code true} for created.	 
	 */
	protected boolean sf = false;
	/**
	 * Built-in {@link ResultSet} object. 
	 */
	private ResultSet r = null;
	/**
	 * {@code true} for created.	 
	 */
	private boolean rf = false;
	/**
	 * Built-in {@link PreparedStatement} object. 
	 */
	private PreparedStatement p = null;
	/**
	 * {@code true} for created.	 
	 */
	private boolean pf = false;
	/**
	 * SQL query string to use.
	 */
	protected String sqlstr = null;
	/**
	 * {@code true} for set.	 
	 */
	protected boolean strf = false;

	/**
	 * Create a {@link SqlO} and connect to the given ODBC data source.
	 * 
	 * @param dbname
	 *            name of ODBC data source.
	 */
	public SqlO(String dbname)
	{
		this.openDB(dbname);
	}

	/**
	 * Creat a {@link SqlO}.
	 */
	public SqlO()
	{
	}

	/**
	 * Set the query string to execute.
	 * 
	 * @param str
	 *            SQL query string.
	 */
	public void setSQLStr(String str)
	{
		sqlstr = str;
		strf = true;
	}

	private boolean checkCF()
	{
		if (cf == false)
		{
			warning(this, "Data source not connected.");
			return false;
		}
		else return true;
	}

	private boolean checkSF()
	{
		if (sf == false)
		{
			warning(this, "Statement not created.");
			return false;
		}
		else return true;
	}

	private boolean checkStrF()
	{
		if (strf == false)
		{
			warning(this, "Query string not set.");
			return false;
		}
		else return true;
	}

	private boolean checkPF()
	{
		if (pf == false)
		{
			warning(this, "PreparedStatment not created.");
			return false;
		}
		else return true;
	}

	private boolean checkRF()
	{
		if (rf == false)
		{
			warning(this, "ResultSet not get.");
			return false;
		}
		else return true;
	}

	/**
	 * Cancel current operation and roll back({@link Connection}.rollback()).
	 * 
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean abort()
	{
		if (checkCF() == false)
			return false;
		try
		{
			c.rollback();
		}
		catch (SQLException e)
		{
			exception(this, e, null);
			return false;
		}
		return true;
	}

	/**
	 * Submit current operation ({@link Connection}.flush()).
	 * 
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean flush()
	{
		if (checkCF() == false)
			return false;
		try
		{
			c.commit();
		}
		catch (SQLException e)
		{
			exception(this, e, null);
			return false;
		}
		return true;
	}

	/**
	 * Open given ODBC data source and create a built-in {@link Statement}.
	 * 
	 * @param dbname
	 *            name of ODBC data source to open.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean openDB(String dbname)
	{
		return openDB(dbname, "", "");
	}

	/**
	 * Open given ODBC data source and create a built-in {@link Statement}
	 * using given user name and password.
	 * 
	 * @param dbname
	 *            name of ODBC data source to open.
	 * @param user
	 *            user name of the data source.
	 * @param pass
	 *            password of the data source.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean openDB(String dbname, String user, String pass)
	{
		Connection dbc;
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}
		catch (ClassNotFoundException e)
		{
			exception(this, e, null);
			return false;
		}
		try
		{
			dbc =
					DriverManager.getConnection("jdbc:odbc:" + dbname, user,
							pass);
		}
		catch (SQLException e)
		{
			exception(this, e, "Fail to open jdbc:odbc:" + dbname + 
					"\n\tUser: " + user + "\n\tPassword: *******");
			return false;
		}
		this.c = dbc;
		cf = true;
		if (this.getStatement() == false)
			return false;
		return true;
	}

	/**
	 * Create a built-in {@link Statement} object for queries.
	 * 
	 * @return {@code true} on success, {@code false} on failure.
	 */
	protected boolean getStatement()
	{
		Statement stt = null;
		//Check if connection is built.
		if (checkCF() == false)
			return false;
		try
		{
			stt =
					c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
		}
		catch (SQLException e)
		{
			exception(this, e, "Failed to create Statement.");
			return false;
		}
		s = stt;
		sf = true;
		return true;
	}

	/**
	 * Create a built-in {@link PreparedStatement}.
	 * 
	 * @return {@code true} on success, {@code false} on failure.<br>
	 *         NOTE: set query string using the {@code setSQLStr} method
	 *         before create a {@link PreparedStatement}.
	 */
	public boolean prepareStatement()
	{
		PreparedStatement ps = null;
		//Check if connection is built.
		if (checkCF() == false)
			return false;
		//Check if query string is set.
		if (checkStrF() == false)
			return false;
		try
		{
			ps = c.prepareStatement(sqlstr);
		}
		catch (SQLException e)
		{
			exception(this, e, "Fail to create PreparedStatement.");
			return false;
		}
		p = ps;
		pf = true;
		return true;
	}

	/**
	 * Set values of parameters for the built-in {@link PreparedStatement}.
	 * See also {@link PreparedStatement} for details.
	 * 
	 * @param i
	 *            index of the parameter.
	 * @param para
	 *            the content of the parameter, a string.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean setPreparedStatementParas(int i, String para)
	{
		if (checkPF() == false)
			return false;
		try
		{
			p.setString(i, para);
		}
		catch (SQLException sqler)
		{
			warning(this, "Failed to set parameter "+i+
					" of PreparedStatement.");
			return false;
		}
		return true;
	}
	
	/**
	 * Set values of parameters for the built-in {@link PreparedStatement}.
	 * See also {@link PreparedStatement} for details.
	 * 
	 * @param i
	 *            index of the parameter.
	 * @param para
	 *            the content of the parameter, an {@link Object}.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean setPreparedStatementParas(int i, Object para)
	{
		if (checkPF() == false)
			return false;
		try
		{
			p.setObject(i, para);
		}
		catch (SQLException sqler)
		{
			warning(this, "Failed to set parameter "+i+
					" of PreparedStatement.");
			return false;
		}
		return true;
	}
	

	/**
	 * Set values of parameters for the built-in {@link PreparedStatement}.
	 * See also {@link PreparedStatement} for details.
	 * 
	 * @param i
	 *            index of the parameter.
	 * @param para
	 *            the content of the parameter, a integer.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean setPreparedStatementParas(int i, int para)
	{
		if (checkPF() == false)
			return false;
		try
		{
			p.setInt(i, para);
		}
		catch (SQLException sqler)
		{
			warning(this, "Failed to set parameter "+i+
					" of PreparedStatement.");
		}
		return true;

	}

	/**
	 * Set values of parameters for the built-in {@link PreparedStatement}.
	 * See also {@link PreparedStatement} for details.
	 * 
	 * @param i
	 *            index of the parameter.
	 * @param para
	 *            the content of the parameter, a long value.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean setPreparedStatementParas(int i, long para)
	{
		if (checkPF() == false)
			return false;
		try
		{
			p.setLong(i, para);
		}
		catch (SQLException sqler)
		{
			warning(this, "Failed to set parameter "+i+
					" of PreparedStatement.");
			return false;
		}
		return true;
	}

	/**
	 * Set values of parameters for the built-in {@link PreparedStatement}.
	 * See also {@link PreparedStatement} for details.
	 * 
	 * @param i
	 *            index of the parameter.
	 * @param para
	 *            the content of the parameter, a float value.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean setPreparedStatementParas(int i, float para)
	{
		if (checkPF() == false)
			return false;
		try
		{
			p.setFloat(i, para);
		}
		catch (SQLException sqler)
		{
			warning(this, "Failed to set parameter "+i+
					" of PreparedStatement.");
			return false;
		}
		return true;
	}

	/**
	 * Set values of parameters for the built-in {@link PreparedStatement}.
	 * See also {@link PreparedStatement} for details.
	 * 
	 * @param i
	 *            index of the parameter.
	 * @param para
	 *            the content of the parameter, a double value.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean setPreparedStatementParas(int i, double para)
	{
		if (checkPF() == false)
			return false;
		try
		{
			p.setDouble(i, para);
		}
		catch (SQLException sqler)
		{
			warning(this, "Failed to set parameter "+i+
					" of PreparedStatement.");
			return false;
		}
		return true;
	}

	/**
	 * Create a {@link PreparedStatement} and set the query string.
	 * 
	 * @param str
	 *            query string, see also {@link PreparedStatement}
	 *            for details.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean prepareStatement(String str)
	{
		this.setSQLStr(str);
		return this.prepareStatement();
	}

	/**
	 * Execute an SQL string and get the result.
	 * 
	 * @return {@link ResultSet} on success, {@code null} on failure.<br>
	 *         NOTE: use {@code runSQLStrS()} if you only want to
	 *         execute a query.
	 */
	public ResultSet getResultSetS()
	{
		// Check if statement is created.
		if (checkSF() == false)
			return null;
		// Check if query string is set.
		if (checkStrF() == false)
			return null;
		try
		{
			r = s.executeQuery(sqlstr);
		}
		catch (SQLException e)
		{
			warning(this, "Failed to execute query: \n"+sqlstr);
			r = null;
			rf = false;
			return null;
		}
		rf = true;
		return r;
	}

	/**
	 * Execute an SQL string.
	 * 
	 * @return {@code true} on success, {@code false} on failure.<br>
	 *         NOTE: use {@code getResultSetS()} if you only want to
	 *         use the result of query.
	 */
	public boolean runSQLStrS()
	{
		// Check if statement is created.
		if (checkSF() == false)
			return false;
		// Check if query string is set.
		if (checkStrF() == false)
			return false;
		try
		{
			if (false == s.execute(sqlstr))
				return false;
		}
		catch (SQLException e)
		{			
			warning(this, "Failed to execute query: \n"+sqlstr);
			return false;
		}
		return true;
	}

	/**
	 * Get the built-in {@link PreparedStatement} of current object.
	 * 
	 * @return the built-in {@link PreparedStatement} on success,
	 * {@code null} on failure.
	 */
	public PreparedStatement getPreparedStatement()
	{
		if (checkPF() == false)
			return null;
		else return p;

	}

	/**
	 * Load a file to {@link FileInputStream}, this method can be
	 * used when accessing BLOB fields.
	 * 
	 * @param file
	 *            path of the file to open.
	 * @return {@link FileInputStream} on success, {@code null} on failure.
	 */
	private FileInputStream loadFile(String file)
	{
		File f = new File(file);
		FileInputStream fis = null;
		if (f.exists() == false || f.isFile() == false)
		{
			warning(this, "File not exist: " + file);
			return null;
		}
		try
		{
			fis = new FileInputStream(f);
		}
		catch (IOException fiserr)
		{
			exception(this, fiserr, null);
			System.exit(0);
		}
		return fis;
	}

	/**
	 * Get the built-in {@link ResultSet} of current object.
	 * 
	 * @return the built-in {@link ResultSet} on success,
	 * {@code null} on failure.
	 */
	public ResultSet getSqlOResultSet()
	{
		if (checkRF() == false)
			return null;
		else return r;
	}
	
	/**
	 * Get the built-in {@link Statement} of current object.
	 * 
	 * @return the built-in {@link Statement} on success,
	 * {@code null} on failure.
	 */
	public Statement getSQlOStatement()
	{
		if (checkSF() == false)
			return null;
		else return s;
	}


	/**
	 * Save the value of the {@code n}th field (a BLOB field)
	 * to a file (no overwriting).
	 *
	 * 
	 * @param n
	 *            the number of a BLOB field.
	 * @param file
	 *            path of file to write to.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean saveFile(int n, String file)
	{
		return saveFile(n, file, false);
	}

	/**
	 * Save the value of the {@code n}th field (a BLOB field)
	 * to a file.
	 * 
	 * @param n
	 *            the number of a BLOB field.
	 * @param file
	 *            path of file to write to.
	 * @param replace
	 *            enable overwriting or not.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean saveFile(int n, String file, boolean replace)
	{
		if (checkRF() == false)
			return false;
		try
		{
			// Check if the file exists.
			File f = new File(file);
			if (f.exists() == true)
			{
				if (f.isDirectory())
				{
					return false;
				}
				else
				{
					if (replace == true)
					{
						f.delete();
					}
					else
					{
						return false;
					}
				}
			}
			f.createNewFile();
			InputStream ins = r.getBinaryStream(n);
			FileOutputStream fos = new FileOutputStream(f);
			// 512K cache.
			byte[] buf = new byte[1024 * 512];
			int len = 0;
			while ((len = ins.read(buf)) > 0)
				fos.write(buf, 0, len);
			ins.close();
			fos.close();
		}
		catch (Exception ersavef)
		{
			warning(this, "Failed to load file from BLOB field: "+n);
			return false;
		}
		return true;
	}

	/**
	 * Assign the content of given file to the number {@code pn} parameter
	 * in the built-in {@link PreparedStatement}. This method is for
	 * BLOB writing.
	 * 
	 * @param pn
	 *            number of parameter to assign.
	 * @param file
	 *            path of a file.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean prepareBLOB(int pn, String file)
	{
		if (checkPF() == false)
			return false;
		try
		{
			FileInputStream in = this.loadFile(file);
			p.setBinaryStream(pn, in, in.available());
		}
		catch (Exception errpblob)
		{
			warning(this, "Failed to update parameter " + pn +
					"in PreparedStatement.\n" +
					"File: "+file);
			return false;
		}
		return true;
	}

	/**
	 * Execute query using the built-in {@link PreparedStatement}, and 
	 * get the result.
	 * 
	 * @return {@link ResultSet} on success, {@code null} on failure.<br>
	 *         NOTE: if no result is needed please use{@code runSQLStrPS()}.
	 */
	public ResultSet getResultSetPS()
	{
		// Check if PreparedStatement is created.
		if (checkPF() == false)
			return null;
		try
		{
			r = p.executeQuery();
		}
		catch (SQLException sqlerp)
		{
			r = null;
			rf = false;
			warning(this, "Failed to execute query.");
			return null;
		}
		rf = true;
		return r;
	}

	/**
	 * Execute query using the built-in {@link PreparedStatement}.
	 * 
	 * @return {@code true} on success, {@code false} on failure.<br>
	 *         NOTE: if result is needed, please use{@code getResultSetPS()}.
	 */
	public boolean runSQLStrPS()
	{
		// Check if PreparedStatement is created.
		if (checkPF() == false)
			return false;
		try
		{
			p.execute();
		}
		catch (SQLException sqler)
		{
			warning(this, "Failed to execute query.");
			return false;
		}
		return true;
	}

	/**
	 * Close connection to data source. Built-in objects
	 * will be closed.
	 * 
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public Boolean closeDB()
	{
		try
		{
			if (rf == true)
			{
				r = null;
				rf = false;
			}
			if (sf == true)
			{
				s = null;
				sf = false;
			}
			if (pf == true)
			{
				p = null;
				pf = false;
			}
			if (cf == true)
			{
				if (c != null && c.isClosed() == false)
					c.close();
				cf = false;
			}
		}
		catch (SQLException e)
		{
			exception(this, e, "Error occurred while closing connection.");
			return false;
		}
		return true;
	}

	/**
	 * Get names of fields in the built-in {@link ResultSet}.
	 * 
	 * @return list of field names on success, {@code null} on failure.<br>
	 *         NOTE: this method returns the "NAME", see also
	 *         {@link ResultSetMetaData} for details.
	 */
	public Vector<String> getResultSetCol()
	{
		ResultSetMetaData m;
		Vector<String> v = new Vector<String>(10, 5);
		if (checkRF() == false)
			return null;
		try
		{
			m = r.getMetaData();
			int i, j;
			i = m.getColumnCount();
			text(this, "Column count: " + i);
			for (j = 1; j <= i; j++)
			{
				v.add(m.getCatalogName(j));
			}
		}
		catch (SQLException e)
		{
			exception(this, e, "Failed to get names of fields.");
			return null;
		}
		return v;
	}

	/**
	 * Get labels of fields in the built-in {@link ResultSet}.
	 * 
	 * @return list of field names on success, {@code null} on failure.<br>
	 *         NOTE: this method returns the "Label", see also
	 *         {@link ResultSetMetaData} for details.
	 */
	public Vector<String> getResultSetColL()
	{
		ResultSetMetaData m;
		Vector<String> v = new Vector<String>(10, 5);
		if (checkRF() == false)
			return null;
		try
		{
			m = r.getMetaData();
			int i, j;
			i = m.getColumnCount();
			for (j = 1; j <= i; j++)
				v.add(m.getColumnLabel(j));
		}
		catch (SQLException e)
		{
			exception(this, e, "Failed to get field labels.");
			return null;
		}
		return v;
	}


	/**
	 * Get column count in the built-in {@link ResultSet}.
	 * 
	 * @return column count on success, -1 on failure.
	 */
	public int getResultSetColCount()
	{
		int count;
		if (checkRF() == false)
			return -1;
		try
		{
			ResultSetMetaData m = r.getMetaData();
			count = m.getColumnCount();
		}
		catch (SQLException e)
		{
			exception(this, e, "Failed to get column count.");
			return -1;
		}
		return count;
	}

	/**
	 * Get record count in the built-in {@link ResultSet}.<br>
	 * Note: after calling this method the cursor will be moved
	 * to the the front of the [@link {@link ResultSet}, that is,
	 * a {@link ResultSet}.{@code beforeFirst()} will be called.
	 * @return record count in {@link ResultSet} on success, 
	 * -1 on failure.
	 */
	public long getCount()
	{
		long count;
		if (checkRF() == false)
			return -1;
		try
		{
			r.last();
			count = r.getRow();
			r.beforeFirst();
		}
		catch (SQLException e)
		{
			exception(this, e, "Failed to get record count.");
			return -1;
		}
		return count;
	}

	/**
	 * Get names of fields in {@link ResultSet}.
	 * 
	 * @param r
	 *            {@link ResultSet}.
	 * @return list of field names on success, {@code null} on failure.<br>
	 *         NOTE: this method returns the "NAME", see also
	 *         {@link ResultSetMetaData} for details.
	 */
	public static Vector<String> getSQLCol(ResultSet r)
	{
		ResultSetMetaData m;
		Vector<String> v = new Vector<String>(10, 5);
		try
		{
			m = r.getMetaData();
			int i, j;
			i = m.getColumnCount();
			for (j = 1; j <= i; j++)
			{
				v.add(m.getCatalogName(j));
			}
		}
		catch (SQLException merr)
		{
			return null;
		}
		return v;
	}

	/**
	 * Get labels of fields in {@link ResultSet}.
	 * 
	 * @param r
	 *            {@link ResultSet}.
	 * @return list of field names on success, {@code null} on failure.<br>
	 *         NOTE: this method returns the "Label", see also
	 *         {@link ResultSetMetaData} for details.
	 */
	public static Vector<String> getSQLColL(ResultSet r)
	{
		ResultSetMetaData m;
		Vector<String> v = new Vector<String>(10, 5);
		try
		{
			m = r.getMetaData();
			int i, j;
			i = m.getColumnCount();
			for (j = 1; j <= i; j++)
				v.add(m.getColumnLabel(j));
		}
		catch (SQLException e)
		{
			return null;
		}
		return v;
	}

	/**
	 * Get column count in given {@link ResultSet}.
	 * 
	 * @param r
	 *            {@link ResultSet}.
	 * @return column count on success, -1 on failure.
	 */
	public static int getColCount(ResultSet r)
	{
		int c;
		try
		{
			ResultSetMetaData m = r.getMetaData();
			c = m.getColumnCount();
		}
		catch (SQLException e)
		{
			return -1;
		}
		return c;
	}

	/**
	 * Get the record count in given {@link ResultSet}.<br>
	 * Note: after calling this method the cursor will be moved
	 * to the the front of the [@link {@link ResultSet}, that is,
	 * a {@link ResultSet}.{@code beforeFirst()} will be called.
	 * 
	 * @param r
	 *            {@link ResultSet}.
	 * @return record count in {@link ResultSet} on success, 
	 * -1 on failure.
	 *         
	 */
	public static long getCount(ResultSet r)
	{
		long c;
		try
		{
			r.last();
			c = r.getRow();
			r.beforeFirst();
		}
		catch (SQLException e)
		{
			return -1;
		}
		return c;
	}

	/**
	 * Read a interger value from current row from {@link ResultSet}
	 * 
	 * @param num
	 *            number of field to read.
	 * @return value of the filed, {@code null} on failure.
	 */
	public Integer getInt(int num)
	{
		int val = 0;
		if (checkRF() == false)
			return null;
		try
		{
			val = r.getInt(num);
		}
		catch (SQLException e)
		{
			exception(SqlO.class, e, "Failed to read value of field:"+num);
			return null;
		}
		return val;
	}

	/**
	 * Read a integer value from current row from {@link ResultSet}.
	 * 
	 * @param str
	 *            field to read.
	 * @return value of the filed, {@code null} on failure.
	 */
	public Integer getInt(String str)
	{
		Integer val = null;
		if (checkRF() == false)
			return null;
		try
		{
			val = r.getInt(str);
		}
		catch (SQLException e)
		{
			exception(SqlO.class, e, "Failed to read value of field:"+str);
			return null;
		}
		return val;
	}

	/**
	 * Read a long value from current row from {@link ResultSet}
	 * 
	 * @param num
	 *            number of field to read.
	 * @return value of the filed, {@code null} on failure.
	 */
	public Long getLong(int num)
	{
		Long val = null;
		if (checkRF() == false)
			return null;
		try
		{
			val = r.getLong(num);
		}
		catch (SQLException e)
		{
			exception(SqlO.class, e, "Failed to read value of field:"+num);
			return null;
		}
		return val;
	}

	/**
	 * Read a long value from current row from {@link ResultSet}.
	 * 
	 * @param str
	 *            field to read.
	 * @return value of the filed, {@code null} on failure.
	 */
	public Long getLong(String str)
	{
		Long val = null;
		if (checkRF() == false)
			return null;
		try
		{
			val = r.getLong(str);
		}
		catch (SQLException e)
		{
			exception(SqlO.class, e, "Failed to read value of field:"+str);
			return null;
		}
		return val;
	}

	/**
	 * Read a float value from current row from {@link ResultSet}
	 * 
	 * @param num
	 *            number of field to read.
	 * @return value of the filed, {@code null} on failure.
	 */
	public Float getFloat(int num)
	{
		Float val = null;
		if (checkRF() == false)
			return null;
		try
		{
			val = r.getFloat(num);
		}
		catch (SQLException e)
		{
			exception(SqlO.class, e, "Failed to read value of field:"+num);
			return null;
		}
		return val;
	}

	/**
	 * Read a float value from current row from {@link ResultSet}.
	 * 
	 * @param str
	 *            field to read.
	 * @return value of the filed, {@code null} on failure.
	 */
	public Float getFloat(String str)
	{
		Float val = null;
		if (checkRF() == false)
			return null;
		try
		{
			val = r.getFloat(str);
		}
		catch (SQLException e)
		{
			exception(SqlO.class, e, "Failed to read value of field:"+str);
			return null;
		}
		return val;
	}

	/**
	 * Read a double value from current row from {@link ResultSet}
	 * 
	 * @param num
	 *            number of field to read.
	 * @return value of the filed, {@code null} on failure.
	 */
	public Double getDouble(int num)
	{
		Double val = null;
		if (checkRF() == false)
			return null;
		try
		{
			val = r.getDouble(num);
		}
		catch (SQLException e)
		{
			exception(SqlO.class, e, "Failed to read value of field:"+num);
			return null;
		}
		return val;
	}

	/**
	 * Read a double value from current row from {@link ResultSet}.
	 * 
	 * @param str
	 *            field to read.
	 * @return value of the filed, {@code null} on failure.
	 */
	public Double getDouble(String str)
	{
		Double val = null;
		if (checkRF() == false)
			return null;
		try
		{
			val = r.getDouble(str);
		}
		catch (SQLException e)
		{
			exception(SqlO.class, e, "Failed to read value of field:"+str);
			return null;
		}
		return val;
	}

	/**
	 * Read a string from current row from {@link ResultSet}
	 * 
	 * @param num
	 *            number of field to read.
	 * @return value of the filed, {@code null} on failure.
	 */
	public String getString(int num)
	{
		String val = null;
		if (checkRF() == false)
			return null;
		try
		{
			val = r.getString(num);
		}
		catch (SQLException e)
		{
			exception(SqlO.class, e, "Failed to read value of field:"+num);
			return null;
		}
		return val;
	}

	/**
	 * Read a string from current row from {@link ResultSet}.
	 * 
	 * @param str
	 *            field to read.
	 * @return value of the filed, {@code null} on failure.
	 */
	public String getString(String str)
	{
		String val = null;
		if (checkRF() == false)
			return null;
		try
		{
			val = r.getString(str);
		}
		catch (SQLException e)
		{
			exception(SqlO.class, e, "Failed to read value of field:"+str);
			return null;
		}
		return val;
	}

	/**
	 * Read a bool value from current row from {@link ResultSet}
	 * 
	 * @param num
	 *            number of field to read.
	 * @return value of the filed, {@code null} on failure.
	 */
	public Boolean getBoolean(int num)
	{
		Boolean val = null;
		if (checkRF() == false)
			return null;
		try
		{
			val = r.getBoolean(num);
		}
		catch (SQLException e)
		{
			exception(SqlO.class, e, "Failed to read value of field:"+num);
			return null;
		}
		return val;
	}

	/**
	 * Read a bool value from current row from {@link ResultSet}.
	 * 
	 * @param str
	 *            field to read.
	 * @return value of the filed, {@code null} on failure.
	 */
	public Boolean getBoolean(String str)
	{
		Boolean val = null;
		if (checkRF() == false)
			return null;
		try
		{
			val = r.getBoolean(str);
		}
		catch (SQLException e)
		{
			exception(SqlO.class, e, "Failed to read value of field:"+str);
			return null;
		}
		return val;
	}

	/**
	 * Read a byte value from current row from {@link ResultSet}
	 * 
	 * @param num
	 *            number of field to read.
	 * @return value of the filed, {@code null} on failure.
	 */
	public Byte getByte(int num)
	{
		Byte val = null;
		if (checkRF() == false)
			return null;
		try
		{
			val = r.getByte(num);
		}
		catch (SQLException e)
		{
			exception(SqlO.class, e, "Failed to read value of field:"+num);
			return null;
		}
		return val;
	}

	/**
	 * Read a byte value from current row from {@link ResultSet}.
	 * 
	 * @param str
	 *            field to read.
	 * @return value of the filed, {@code null} on failure.
	 */
	public Byte getByte(String str)
	{
		Byte val = null;
		if (checkRF() == false)
			return null;
		try
		{
			val = r.getByte(str);
		}
		catch (SQLException e)
		{
			exception(SqlO.class, e, "Failed to read value of field:"+str);
			return null;
		}
		return val;
	}

	/**
	 * Call the {@code next()} method of the {@link ResultSet} in
	 * this object.
	 * 
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean next()
	{
		boolean nxt = false;
		if (checkRF() == false)
			return false;
		try
		{
			nxt = r.next();
		}
		catch (SQLException e)
		{
			exception(SqlO.class, e, "Failed to move the cursor to" +
					"next row. ");
			return false;
		}
		return nxt;
	}

	/**
	 * Create a view using given query. That is, 
	 * execute "create view {@code viewName} as {@code sqlStr}".
	 * 
	 * @param sqlStr
	 *            a query string.
	 * @param viewName
	 *            name of the view to create.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean createView(String sqlStr, String viewName)
	{
		this.setSQLStr("create view " + viewName + " as " + sqlStr);
		boolean okay = this.runSQLStrS();
		return okay;
	}

	/**
	 * Drop a table/view.
	 * 
	 * @param viewName
	 *            name of table/view to drop.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean dropView(String viewName)
	{
		this.setSQLStr("drop table " + viewName);
		boolean okay = this.runSQLStrS();
		return okay;
	}
}
