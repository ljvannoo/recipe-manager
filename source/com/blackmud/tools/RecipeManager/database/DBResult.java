package com.blackmud.tools.RecipeManager.database;

import java.util.Hashtable;
import java.util.Vector;

public class DBResult {
	private Hashtable<String, Integer> columnNames = null;
	private Vector<Vector<Object>> rows = null;
	private int columnCount = 0;
	
	public DBResult() {
	
	}
	
	public void addColumn(String columnName) {
		getColumnNames().put(columnName, columnCount);
		columnCount++;
	}
	
	public void addRow(Vector<Object> row) {
//		System.out.println("Adding row: "+row);
		getRows().add(row);
	}
	
	private Hashtable<String, Integer> getColumnNames() {
		if(columnNames == null) {
			columnNames = new Hashtable<String, Integer>();
		}
		return columnNames;
	}
	
	private Vector<Vector<Object>> getRows() {
		if(rows == null) {
			rows = new Vector<Vector<Object>>();
		}
		return rows;
	}
	
	public Object getObject(int row, int column) {
		return getRows().get(row).get(column);
	}
	
	public Object getObject(int row, String columnName) {
		return getRows().get(row).get(getColumnNames().get(columnName));
	}
//	
	public int getInt(int row, int column) {
		return Integer.parseInt(getString(row, column));
	}
	
	public int getInt(int row, String columnName) {
		return Integer.parseInt(getString(row, columnName));
	}
	
	public long getLong(int row, int column) {
		return Long.parseLong(getString(row, column));
	}
	
	public long getLong(int row, String columnName) {
		return Long.parseLong(getString(row, columnName));
	}
	
	public double getDouble(int row, int column) {
		return Double.parseDouble(getString(row, column));
	}
	
	public double getDouble(int row, String columnName) {
		return Double.parseDouble(getString(row, columnName));
	}
	
	public boolean getBool(int row, int column) {
		return Boolean.parseBoolean(getString(row, column));
	}
	
	public boolean getBool(int row, String columnName) {
		return Boolean.parseBoolean(getString(row, columnName));
	}
	
	public String getString(int row, int column) {
		return ""+getRows().get(row).get(column);
	}
	
	public String getString(int row, String columnName) {
		if(getColumnNames().containsKey(columnName))
			return ""+getRows().get(row).get(getColumnNames().get(columnName));
		else {
			System.out.println("Unknown column '"+columnName+"'");
			return null;
		}
	}
	
	public Vector<Object> getRow(int row) {
		return getRows().get(row);
	}
	
	public int size() {
		return getRows().size();
	}
}
