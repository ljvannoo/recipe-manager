package com.blackmud.tools.RecipeManager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

public class DBMediator {
	private Connection dbConnection = null;
	private String error = null;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public DBMediator() {
	
	}
	
	public DBMediator(String serverName, String databaseName, String userName, String password) {
		connect(serverName, databaseName, userName, password);
	}

	public DBResult executeQuery(String query) {
		DBResult result = new DBResult();
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData metaData = null;
		Vector<Object> row = null;
		
		if(!connected()) {
			return null;
		}

		try {
			stmt = getDBConnection().createStatement();
			rs = stmt.executeQuery(query);

			metaData = rs.getMetaData();

			for(int i = 1; i <= metaData.getColumnCount(); i++) {
				result.addColumn(metaData.getColumnName(i));
			}

			while(rs.next()) {
				row = new Vector<Object>();
				for(int i = 1; i <= metaData.getColumnCount(); i++) {
					row.add(rs.getObject(i));
				}
				result.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rs = null;
			}

			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				stmt = null;
			}
		}

		return result;
	}
	
	public int executeUpdate(String query) {
		Statement stmt = null;

		int result = -1;
		
		if(!connected()) {
			return -1;
		}

		try {
			stmt = getDBConnection().createStatement();
			result = stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				stmt = null;
			}
		}

		return result;
	}
	
	public int executeUpdate(String preparedStatement, Vector values) {
		PreparedStatement prepStmt = null;

		int result = -1;
		
		if(!connected()) {
			return -1;
		}

		try {
			prepStmt = getDBConnection().prepareStatement(preparedStatement);
			for(int i = 0; i < values.size(); i++) {
				if(values.get(i) == null) {
					prepStmt.setNull(i+1, java.sql.Types.VARCHAR);
				} else if(values.get(i) instanceof Integer) {
					prepStmt.setInt(i+1, (Integer)values.get(i));
				} else if(values.get(i) instanceof Long) {
					prepStmt.setLong(i+1, (Long)values.get(i));
				} else if(values.get(i) instanceof String) {
					prepStmt.setString(i+1, (String)values.get(i));
				} else {
					System.out.println("UNEXPECTED TYPE: "+values.get(i).getClass().getName());
				}
			}
			result = prepStmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if(prepStmt != null) {
				try {
					prepStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				prepStmt = null;
			}
		}

		return result;
	}
	
	public int executeInsert(String preparedStatement, Vector values) {
		PreparedStatement prepStmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		int result = -1;
		
		if(!connected()) {
			return -1;
		}

		try {
			prepStmt = getDBConnection().prepareStatement(preparedStatement);
			for(int i = 0; i < values.size(); i++) {
				if(values.get(i) == null) {
					prepStmt.setNull(i+1, java.sql.Types.VARCHAR);
				} else if(values.get(i) instanceof Integer) {
					prepStmt.setInt(i+1, (Integer)values.get(i));
				} else if(values.get(i) instanceof Long) {
					prepStmt.setLong(i+1, (Long)values.get(i));
				} else if(values.get(i) instanceof String) {
					prepStmt.setString(i+1, (String)values.get(i));
				} else {
					System.out.println("UNEXPECTED TYPE: "+values.get(i).getClass().getName());
				}
			}
			result = prepStmt.executeUpdate();
			
			stmt = getDBConnection().createStatement();
			rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			if(rs != null && rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			setError(e.getMessage());
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rs = null;
			}
			
			if(prepStmt != null) {
				try {
					prepStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				prepStmt = null;
			}
		}

		return result;
	}

	public String connect(String serverName, String databaseName, String userName, String password) {
		if(dbConnection != null) {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbConnection = null;
		}

		try {
			System.out.println("Connecting to the database...");
			Properties props = new Properties();
			props.setProperty("useSSL", "true");
			dbConnection = DriverManager.getConnection("jdbc:mysql://"+serverName+"/"+databaseName+"?user="+userName+"&password="+password, props);
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			dbConnection = null;
			return ex.getMessage();
		}
		return null;
	}

	private Connection getDBConnection() {
		return dbConnection;
	}

	public void close() {
		try {
			if(connected()) {
				System.out.println("Closing DB connection...");
				getDBConnection().close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean connected() {
		return (dbConnection != null);
	}
}
