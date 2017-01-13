package net.c2001.db;

import java.io.File;
import java.sql.*;

/**
 * This class aims to accessing the embedded Derby database. <br>
 * NOTE: SQL NULL will be returned as 0 or {@code false}, see also
 * {@link ResultSet}.
 * 
 * @author Lin Dong
 */
public class DerbyO extends SqlO implements AutoCloseable {

	private static final long serialVersionUID = -4882966915242648435L;

	/**
	 * Create a {@link DerbyO} and connect to database.
	 * 
	 * @param path
	 *            database path to open with derby.
	 */
	public DerbyO(File path) {
		this.openDB(path);
	}

	/**
	 * Create a {@link DerbyO}.
	 */
	public DerbyO() {
	}

	/**
	 * Open given folder as the database, and create a built-in
	 * {@link Statement}. This method uses derby as an embedded database.
	 * 
	 * @param path
	 *            directory of the database.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean openDB(File path) {
		Connection dbc;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (ClassNotFoundException e) {
			exception(this, e, null);
			return false;
		}
		try {
			dbc = DriverManager.getConnection("jdbc:derby:" + path.getPath()+";create=true");
		} catch (SQLException e) {
			exception(this, e, "Fail to open derby database in " + path);
			return false;
		}
		this.c = dbc;
		cf = true;
		if (this.getStatement() == false)
			return false;
		return true;

	}

	/**
	 * Open database and create a built-in {@link Statement} using given user
	 * name and password. Note here the username and password are ignored.
	 * 
	 * @param path
	 *            path of derby database to open.
	 * @param user
	 *            user name of the data source.
	 * @param pass
	 *            password of the data source.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean openDB(String path, String user, String pass) {
		File folder = new File(path);
		return openDB(folder);
	}
	
	@Override
	public boolean openDB(String path) {
		File folder = new File(path);
		return openDB(folder);
	}

	@Override
	public void close() throws Exception {
		closeDB();
	}

	@Override
	public Boolean closeDB() {
		shutdownDerby();
		return true;
	}

	/**
	 * Shutdown the embedded derby database.
	 */
	public void shutdownDerby() {
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException e) {
			this.text(this, e.getMessage());
		}
	}
	


}
