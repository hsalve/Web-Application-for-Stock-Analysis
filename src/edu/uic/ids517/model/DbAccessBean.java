package edu.uic.ids517.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

@ManagedBean(name = "dbAccessBean")
@SessionScoped

public class DbAccessBean {
	private String jdbcDriver;
	private String url;
	private String table;
	private String statemt;
	private static Connection connection = null;
	private Statement statement;
	private DatabaseMetaData databaseMetaData;
	private ResultSet resultSet, rs;
	private DbInformationAccessBean dbInformationAccessBean;
	
	private UploadFileBean uploadFileBean;
	private MessageBean messageBean;
	private String status = "false";
	private String query = "";
	private HttpSession session;

	private boolean queryTypeFlag = false;

	private boolean queryFlag = false;
	private String badRowNumbers = "";
	private boolean putInDBFlag = true;
	ResultSetMetaData metaData1;
	private String message;
	private String tableSelected;
	public String tableName;
	private boolean renderMessage;
	
	List<Map<String, Object>> resultData = new ArrayList<Map<String, Object>>();
	private List userColumns = new ArrayList<>();
	private static final String[] TABLE_TYPES = { "TABLE", "VIEW" };
	FacesContext context = FacesContext.getCurrentInstance();
	Map <String, Object> m = context.getExternalContext().getSessionMap();
	private StringBuffer actionScript=new StringBuffer();
	private List<String> headerList = null;
	private List<String[]> dataList = new ArrayList<String[]>();
	private List<String> tickers;
	private ArrayList<String> tables = null;
	private ArrayList<String> tableNames;

	// default constructor of the bean
	public DbAccessBean() {
	} 	
	
	// method to connect to the database according to the type of db entered by
	// the user
	public String getConnection() {		
		String error;
		try {
			dbInformationAccessBean = (DbInformationAccessBean) m.get("dbInformationAccessBean");
			messageBean = (MessageBean) m.get("messageBean");
			
			switch (dbInformationAccessBean.getDbms().toUpperCase()) {
			// 1 = MySQL
			case "MYSQL":
				jdbcDriver = "com.mysql.jdbc.Driver";
				url = "jdbc:mysql://" + dbInformationAccessBean.getDbmsHost() + ":3306/" + dbInformationAccessBean.getDbSchema();
				break;
			// 2 = Oracle
			case "ORACLE":
				jdbcDriver = "oracle.jdbc.driver.OracleDriver";
				url = "jdbc:oracle:thin:@" + dbInformationAccessBean.getDbmsHost() + ":1521:" + dbInformationAccessBean.getDbSchema();
				break;
			// 3= DB2
			case "DB2":
				jdbcDriver = "com.ibm.db2.jcc.DB2Driver";
				url = "jdbc:db2://" + dbInformationAccessBean.getDbmsHost() + ":50000/" + dbInformationAccessBean.getDbSchema();
				break;

			default:
				return status;
				
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			
			Class.forName(jdbcDriver);
			connection = DriverManager.getConnection(url, dbInformationAccessBean.getUsername(),
					dbInformationAccessBean.getPassword());
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			databaseMetaData = connection.getMetaData();
			// getting all the tables in the current schema in an array list 
			ArrayList<String> existingTables = getAllTableNames();
			
			// method to check if all the tables are available for the application to function;
			// if not then create all the required tables 
		
			status = "true";
			messageBean.setError("");
			messageBean.setErrorRender(false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			error = e.getMessage();
			messageBean.setError(error);
			messageBean.setErrorRender(true);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	// method to execute the query and return the result set in case of select statement
	public ResultSet executeAppQuery(String query, int type) {

		rs = null;

		if (connection != null && statement != null) {
			// select query
			if (type == 1) {
				try {
					rs = statement.executeQuery(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// non select query
			else {
				try {
					statement.executeUpdate(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
		}
		
		return rs;		
	}
		
	public ResultSet selectQueryProcessing(String query)
	{
		try {
			resultSet = statement.executeQuery(query);
			return resultSet;
		} catch (SQLException se) {
			try {
				
				resultSet = statement.executeQuery(query);
			} catch (SQLException e) {
				se.printStackTrace();
				return resultSet;
			}
			return resultSet;

			
		} 
		catch (Exception e) {
			e.printStackTrace();
			//message = "Ooops, the application encountered an exception: " + e.getMessage();
			e.printStackTrace();
			return resultSet;
		}
	}
	
	public ResultSet[] fetchTables()
	{
		ResultSet[] rs = new ResultSet[2];
		try {
			DatabaseMetaData meta = (DatabaseMetaData) connection.getMetaData();
			ResultSet rSet = meta.getTables(dbInformationAccessBean.getDbSchema(), null, null, new String[] {"TABLE"});
			rs[0]=rSet;
			return rs;
		} 
		catch (SQLException se) 
		{
			se.printStackTrace();
			message = "SQL Exception has occured while fetching the tables, find the error details below" + "\n" + "SQL State: " + se.getSQLState() + "\n" +  "SQL Error Code: " + se.getErrorCode() + "\n" +
					"Message :" + se.getMessage() + "\n";
			
			return rs;
		} 
		catch (Exception e) {
			e.printStackTrace();
			message = "Ooops, the application encountered an exception: " + e.getMessage();
			return rs;
		}
	}
	
	public ResultSet fetchColumnData(String query)
	{
		try {
			ResultSet resultSet ;
			/*if(query.contains("world"))
				 resultSet = worldStatement.executeQuery(query);
			else*/
			 resultSet = statement.executeQuery(query);
			return resultSet;
		} catch (SQLException se) {
			message = "Application encountered an SQL Exception while fetching the column data requested." + "\n" + "SQL Error Code: " + se.getErrorCode() + "\n"+ "SQL State: " + se.getSQLState() + "\n" +
					"Message :" + se.getMessage() + "\n";
			return resultSet = null;
		} catch (Exception e) {e.printStackTrace();
			message = "Exception:  " + e.getMessage();
			return resultSet = null;
		}
	}
		
	
	public void InsertintoDB(String ticker,String transactionDate , Double open , Double high, Double low, Double close ,Double adjClose , Long volume , Double splitCoefficient , Double dividend) {
		query = "insert into gs308_stocktransaction(ticker,transactionDate,open,high,low,close,adjClose,volume,dividend,splitCoefficient) VALUES (?,?,?,?,?,?,?,?,?,?);";

		table = null;
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1,ticker);
			stmt.setString(2,transactionDate);
			stmt.setString(3,Double.toString(open));
			stmt.setString(4, Double.toString(high));
			stmt.setString(5, Double.toString(low));
			stmt.setString(6, Double.toString(close));
			stmt.setString(7, Double.toString(adjClose));
			stmt.setString(8, Long.toString(volume));
			stmt.setString(9, Double.toString(dividend));
			stmt.setString(10, Double.toString(splitCoefficient));			
			
			stmt.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void UpdateDB(String ticker , String transactionDate, Double dailyReturn ) {
		query = "update gs308_stocktransaction SET dailyReturn = ? WHERE ticker = ? AND transactionDate = ?;";

		table = null;
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1,Double.toString(dailyReturn));
			stmt.setString(2,ticker);
			stmt.setString(3,transactionDate);		
			
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void CreateTable() {
		query = "CREATE TABLE gs308_stocktransaction (id int(11) NOT NULL AUTO_INCREMENT,"
				+ "ticker VARCHAR(45),"
				+ "transactiondate VARCHAR(45),"
				+ "open DOUBLE,"
				+ "high DOUBLE,"
				+ "low DOUBLE,"
				+ "close DOUBLE,"
				+ "adjClose DOUBLE,"
				+ "volume BIGINT,"
				+ "dividend DOUBLE,"
				+ "splitCoefficient DOUBLE,"
				+ "dailyReturn DOUBLE,"
				+ "PRIMARY KEY(id))";

		table = null;
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.executeUpdate();
			getAllTableNames();

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
	
	public void DropTable() {
		query = "DROP TABLE gs308_stocktransaction";

		table = null;
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.executeUpdate();
			getAllTableNames();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List executeComputeReturn(String processQuery) throws SQLException {
		setQueryTypeFlag(false);
		resultData.clear();
		userColumns.clear();

		char lastChar = processQuery.charAt(processQuery.length() - 1);
		if (lastChar != ';') {

			query = processQuery + ";";
		} else {
			query = processQuery;
		}

		PreparedStatement statemt = connection.prepareStatement(query);

		System.out.println(statemt);
		setStatemt(statemt.toString());

		System.out.println("After setting Statement" + statemt);

		if (query.toUpperCase().contains("SELECT")) {

			setQueryTypeFlag(true);

			resultSet = statemt.executeQuery();
			
			setQueryFlag(true);

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			for (int i = 1; i <= columnCount; i++) {
				String name = metaData.getColumnName(i);
				userColumns.add(name);
			}

			while (resultSet.next()) {
				Map<String, Object> columns = new LinkedHashMap<String, Object>();

				for (int i = 1; i <= columnCount; i++) {
					
					columns.put(metaData.getColumnLabel(i), resultSet.getObject(i));
				}			
				
				resultData.add(columns);
			}
			resultSet.beforeFirst();
			
			return resultData;

		} else {
			statemt.executeUpdate();
			setQueryTypeFlag(false);
			return resultData;

		}

	}	
	
	public Result convertRs(ResultSet rs){
		// converting result set into result object 
		Result result = ResultSupport.toResult(rs);
		return result;

	}

	// method to return all the columns in a particular table
	public ArrayList<String> columnNames(String table) {
		ArrayList<String> columns = new ArrayList<String>();
		

		try {
			resultSet = databaseMetaData.getColumns(null, "%", table, null);

			String columnName = "";
			if (resultSet != null) {
				while (resultSet.next()) {
					columnName = resultSet.getString("COLUMN_NAME");
					columns.add(columnName);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return columns;
	}
	
// method to get all the table names in the schema in an array list
// and returning the array list 	
	//public ArrayList<String> tableNames(DatabaseInformationAccessBean dbInformationAccessBean) {
	public ArrayList<String> getAllTableNames() {
		try {
			resultSet = databaseMetaData.getTables(null, dbInformationAccessBean.getDbSchema(), null, TABLE_TYPES);
			resultSet.last();
			int rows = resultSet.getRow();
			tables = new ArrayList<String>(rows);
			resultSet.beforeFirst();

			String tableName = "";
			if (resultSet != null) {
				while (resultSet.next()) {
					tableName = resultSet.getString("TABLE_NAME");
					// check if the dbms is not oracle , 2=oracle
					if (dbInformationAccessBean.getDbms().toUpperCase() != "ORACLE"|| tableName.length() < 4) {
						tables.add(tableName);
					} else if (tableName.substring(0, 4).equalsIgnoreCase("BIN$")) {
						tables.add(tableName);
					}
				}
			}
			tableNames = tables;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return tables;

	}	
	
	
	
	
	// method to close  the open connections
	public String connectionClose() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "closed";
	}


		public Statement getStatement() {
			return statement;
		}


		public void setStatement(Statement statement) {
			this.statement = statement;
		}	

		public DbInformationAccessBean getDbInformationAccessBean() {
			return dbInformationAccessBean;
		}

		public void setDbBean(DbInformationAccessBean dbInformationAccessBean) {
			this.dbInformationAccessBean = dbInformationAccessBean;
		}
		
		public String getJdbcDriver() {
			return jdbcDriver;
		}

		public void setJdbcDriver(String jdbcDriver) {
			this.jdbcDriver = jdbcDriver;
		}

		public String getQuery() {
			return query;
		}

		public void setQuery(String query) {
			this.query = query;
		}

		public String getStatemt() {
			return statemt;
		}

		public void setStatemt(String statemt) {
			this.statemt = statemt;
		}

		public List getUserColumns() {
			return userColumns;
		}

		public void setUserColumns(List userColumns) {
			this.userColumns = userColumns;
		}

		public List<String> getHeaderList() {
			return headerList;
		}

		public void setHeaderList(List<String> headerList) {
			this.headerList = headerList;
		}

		public List<String[]> getDataList() {
			return dataList;
		}

		public void setDataList(List<String[]> dataList) {
			this.dataList = dataList;
		}

		public ResultSet getResultSet() {
			return resultSet;
		}

		public void setResultSet(ResultSet resultSet) {
			this.resultSet = resultSet;
		}

		public boolean isQueryTypeFlag() {
			return queryTypeFlag;
		}

		public void setQueryTypeFlag(boolean queryTypeFlag) {
			this.queryTypeFlag = queryTypeFlag;
		}

		public boolean isQueryFlag() {
			return queryFlag;
		}

		public void setQueryFlag(boolean queryFlag) {
			this.queryFlag = queryFlag;
		}

	public ResultSetMetaData getMetaData1() {
			return metaData1;
		}

		public void setMetaData1(ResultSetMetaData metaData1) {
			this.metaData1 = metaData1;
		}

		public static Connection getConn() {
			return connection;
		}

		public static void setConn(Connection conn) {
			connection = conn;
		}

		public String getTable() {
			return table;
		}

		public void setTable(String table) {
			this.table = table;
		}

		public List<Map<String, Object>> getResultData() {
			return resultData;
		}

		public void setResultData(List<Map<String, Object>> resultData) {
			this.resultData = resultData;
		}

		public String getBadRowNumbers() {
			return badRowNumbers;
		}

		public void setBadRowNumbers(String badRowNumbers) {
			this.badRowNumbers = badRowNumbers;
		}

		public boolean isPutInDBFlag() {
			return putInDBFlag;
		}

		public void setPutInDBFlag(boolean putInDBFlag) {
			this.putInDBFlag = putInDBFlag;
		}

		public StringBuffer getActionScript() {
			return actionScript;
		}

		public void setActionScript(StringBuffer actionScript) {
			this.actionScript = actionScript;
		}
		
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
	
		
		public List getTableNames() {
			return tableNames;
		}

		public void setTableNames(ArrayList<String> tableNames) {
			this.tableNames = tableNames;
		}
		
		public boolean isRenderMessage() {
			return renderMessage;
		}

		public void setRenderMessage(boolean renderMessage) {
			this.renderMessage = renderMessage;
		}
		
		public String logoutUser()
		{		
			uploadFileBean = new UploadFileBean();
			messageBean = new MessageBean();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("messageBean",messageBean);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fileUploadBean",uploadFileBean);
			return "logout";
		}
	
}


