package edu.uic.ids517.model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.jsp.jstl.sql.Result;

public class DbSchemaBean {

	public String tableName;
	private String selectedQuery;
	private List<String> columnNames;
	private List tableNames = new ArrayList<>();
	private String tableNameValue;
	private List columnNameValue;
	private String errorMessage;
	private List tableColumns = new ArrayList<>();
	private List<String> columnNamesSelected;
	private boolean columnListRendered = false ;
	private boolean renderSet = false ;
	private Result result; 
	public List<String> columnHeaders; 
	private DbAccessBean dbAccessBean;
	private MessageBean messageBean;
	private UploadFileBean uploadBean;
	public String enteredSQL;
	private int rowCount;
	private List dataOperationsList = new ArrayList<>();
	private boolean renderMessage = false;
	private boolean renderTable;
	private boolean renderColumn;
	private boolean renderDataTable;
	private String message = "";
	private ArrayList<String> cmd;
	private boolean sourceDestSelection = false;
	private String sourceCol = "";
	private String destCol = "";
	
	private int nc;
	private int nr;
	private String executeQuery;
	private String processQuery;
	private String datasetLabel;
	private String Operation;
	private boolean renderErrorMessage;
	private String ticker;
	private String sortby;
	private String sortbydate;
	private String dateTo;
	private String dateFrom;
	private List<String> tickers;

	public DbSchemaBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map <String, Object> m = context.getExternalContext().getSessionMap();
		dbAccessBean = (DbAccessBean) m.get("dbAccessBean");
		messageBean = (MessageBean) m.get("messageBean");
		uploadBean = (UploadFileBean) m.get("uploadBean");
		// TODO Auto-generated constructor stub

		tickers = new ArrayList<String>();		
	}
	
	public void enableSourceDest() {
		sourceDestSelection = true;
	}

	public List getTableNames() {
		return tableNames;
	}

	public void setTableNames(List tableNames) {
		this.tableNames = tableNames;
	}

	public String getTableNameValue() {
		return tableNameValue;
	}

	public void setTableNameValue(String tableNameValue) {
		this.tableNameValue = tableNameValue;
	}

	public List getColumnNameValue() {
		return columnNameValue;
	}

	public void setColumnNameValue(List columnNameValue) {
		this.columnNameValue = columnNameValue;
	}

	public boolean isRenderTable() {
		return renderTable;
	}

	public void setRenderTable(boolean renderTable) {
		this.renderTable = renderTable;
	}

	public boolean isRenderColumn() {
		return renderColumn;
	}

	public void setRenderColumn(boolean renderColumn) {
		this.renderColumn = renderColumn;
	}

	public boolean isRenderDataTable() {
		return renderDataTable;
	}

	public void setRenderDataTable(boolean renderDataTable) {
		this.renderDataTable = renderDataTable;
	}

	public List getDataOperationsList() {
		return dataOperationsList;
	}

	public void setDataOperationsList(List dataOperationsList) {
		this.dataOperationsList = dataOperationsList;
	}

	public int getNc() {
		return nc;
	}

	public void setNc(int nc) {
		this.nc = nc;
	}
	
	public String getsortbydate() {
		return sortbydate;
	}

	public void setsortbydate(String sortbydate) {
		this.sortbydate = sortbydate;
	}
	
	public String getdateTo() {
		return dateTo;
	}

	public void setdateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	
	public String getdateFrom() {
		return dateTo;
	}

	public void setdateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public String getExecuteQuery() {
		return executeQuery;
	}

	public void setExecuteQuery(String executeQuery) {
		this.executeQuery = executeQuery;
	}

	public String getProcessQuery() {
		return processQuery;
	}

	public void setProcessQuery(String processQuery) {
		this.processQuery = processQuery;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isRenderErrorMessage() {
		return renderErrorMessage;
	}

	public void setRenderErrorMessage(boolean renderErrorMessage) {
		this.renderErrorMessage = renderErrorMessage;
	}

	public List getTableColumns() {
		return tableColumns;
	}

	public void setTableColumns(List tableColumns) {
		this.tableColumns = tableColumns;
	}

	public boolean isSourceDestSelection() {
		return sourceDestSelection;
	}

	public void setSourceDestSelection(boolean sourceDestSelection) {
		this.sourceDestSelection = sourceDestSelection;
	}

	public String getSourceCol() {
		return sourceCol;
	}

	public void setSourceCol(String sourceCol) {
		this.sourceCol = sourceCol;
	}

	public String getDestCol() {
		return destCol;
	}

	public void setDestCol(String destCol) {
		this.destCol = destCol;
	}

	public boolean isRenderMessage() {
		return renderMessage;
	}

	public void setRenderMessage(boolean renderMessage) {
		this.renderMessage = renderMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDatasetLabel() {
		return datasetLabel;
	}

	public void setDatasetLabel(String datasetLabel) {
		this.datasetLabel = datasetLabel;
	}

	public String getOperation() {
		return Operation;
	}

	public void setOperation(String Operation) {
		this.Operation = Operation;
	}

	public DbAccessBean getDataAccess() {
		return dbAccessBean;
	}

	public void setDataAccess(DbAccessBean dbAccessBean) {
		this.dbAccessBean = dbAccessBean;
	}


	public String getSelectedQuery() {
		return selectedQuery;
	}

	public void setSelectedQuery(String selectedQuery) {
		this.selectedQuery = selectedQuery;
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public List<String> getColumnNames() {
		return columnNames;
	}


	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;	
	}



	public List<String> getColumnNamesSelected() {
		return columnNamesSelected;
	}


	public void setColumnNamesSelected(List<String> columnNamesSelected) {
		this.columnNamesSelected = columnNamesSelected;
	}


	public Result getResult() {
		return result;
	}


	public void setResult(Result result) {
		this.result = result;
	}


	public List<String> getColumnHeaders() {
		return columnHeaders;
	}


	public void setColumnHeaders(List<String> columnHeaders) {
		this.columnHeaders = columnHeaders;
	}


	public boolean isColumnListRendered() {
		return columnListRendered;
	}


	public void setColumnListRendered(boolean columnListRendered) {
		this.columnListRendered = columnListRendered;
	}



	public boolean isRenderSet() {
		return renderSet;
	}


	public void setRenderSet(boolean renderSet) {
		this.renderSet = renderSet;
	}
	
		
	public String getEnteredSQL() {
		return enteredSQL;
	}

	public void setEnteredSQL(String enteredSQL) {
		this.enteredSQL = enteredSQL;
	}
	
	
	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	public String getsortby() {
		return sortby;
	}

	public void setsortby(String sortby) {
		this.sortby = sortby;
	}
	
	public List<String> getTickers() {
		return tickers;
	}

	public void setTickers(List<String> tickers) {
		this.tickers = tickers;
	}

	public String showColumns(){
		setColumnNames(dbAccessBean.columnNames(tableName));
		columnListRendered = true;
		return "true";	
		
	}
	
	public void CreateTable() {
		dbAccessBean.CreateTable();		
	}	
	
	public void DropTable() {
		dbAccessBean.DropTable();		
	}
	
	public void reset() {
		dataOperationsList.clear();
		columnNames.clear();
		tableNames.clear();
		sourceDestSelection = false;
		renderMessage = false;
		renderDataTable = false;
		processQuery = "";
		executeQuery = "";
		nc=0;
		nr=0;
		renderErrorMessage = false;
		
	}
	
	public String tableResult(){
		String query = "Select * from " + tableName ;
		enteredSQL = query;
		columnHeaders= dbAccessBean.columnNames(tableName);
		showColumns();
		runSql();		
		return "true";
	}
	
	//method to process the entered sql
	public String runSql(){		
		
		try {
			if(!enteredSQL.trim().isEmpty()){
				if(enteredSQL.toUpperCase().startsWith("SELECT") ){
					// setting the column headers 
					int startIndex = enteredSQL.toUpperCase().indexOf("FROM ");
					String subStr = enteredSQL.substring(startIndex);
					String[] firstString = subStr.split(" ");
					tableName = firstString[1] ;
					columnHeaders= dbAccessBean.columnNames(tableName);
					//if(enteredSQL.contains("*")){
					//	columnHeaders= dbAccessBean.columnNames(tableName);
					//}
					//else{
					//	columnHeaders = Arrays.asList(col.split(","));	
					//}
					result = dbAccessBean.convertRs((dbAccessBean.executeAppQuery(enteredSQL, 1)));
					rowCount = result.getRowCount();
					messageBean.setProcessSql("No. of rows: " + rowCount);
					messageBean.setProcessSqlRender(true);
					showColumns();
					renderSet = true;		
				}
				else {
					
					dbAccessBean.executeAppQuery(enteredSQL, 2);
					renderSet = false;
					messageBean.setProcessSql("SQL Executed Successfully");
					messageBean.setProcessSqlRender(true);
				}
				
				return "true";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			messageBean.setProcessSql(e.getMessage());
			messageBean.setProcessSqlRender(true);
			e.printStackTrace();
		}	
		return "false";
	}
	
	// method to show the selected columns data 
	public String columnResult(){
		try {
			if (columnNamesSelected.size() == 0){
				return "false";
			}
			String query ="Select ";
			
			for(String column :columnNamesSelected ){
				query = query.concat(column);
				query = query.concat(",");
			}
			
			query = query.substring(0, query.length()-1);
			query = query.concat(" from ");
			query = query.concat(tableName);
			enteredSQL = query;
			columnHeaders = columnNamesSelected;
			showColumns();
			runSql();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return "true";
	}
	
	public String dropTable(){
		if(!tableName.trim().isEmpty()){
		enteredSQL = "Drop table " + tableName;
		return runSql();
		}
		return "false";
		
	}
	
	public String distinctTickers() {
		 try {	
			String sqlQuery;
			sqlQuery = "select distinct ticker from " + tableName;
			ResultSet resultSet = dbAccessBean.selectQueryProcessing(sqlQuery);
			tickers.clear();
			String ticknames;
			if (resultSet != null) {
				while(resultSet.next()) {
					ticknames= resultSet.getString("ticker");
					tickers.add(ticknames);
				}
				
			} else {
				message = "No tickers available"+getMessage();
				renderMessage = true;
			}
		 }
		 catch (Exception err) {
				err.printStackTrace();
				message = "An exception has occured. The details of the error are given below." + "\n" + err.getMessage();
				renderMessage = true;
				return "FAIL";
			}
		
		return "SUCCESS";
	}
	
	
	
	public String FilterDataTicker() {

		String query="";
		tableName = "gs308_stocktransaction";
		if(ticker!="") {
			if(dateFrom=="" || dateTo=="") {
				query = "Select * from " + tableName + " where ticker = '" + ticker + "'";
				enteredSQL = query;
				columnHeaders= dbAccessBean.columnNames(tableName);
				showColumns();
				runSql();	
			}
			else {
				query = "Select * from " + tableName + " where ticker = '" + ticker + "'" + " AND DATE_FORMAT(str_to_date(transactiondate, '%m/%d/%Y'),'%Y/%m/%d') BETWEEN '" + dateFrom + "' AND '" + dateTo +"';";
				enteredSQL = query;
				columnHeaders= dbAccessBean.columnNames(tableName);
				showColumns();
				runSql();	
			}
		}
		else {
			query = "select * from "+ tableName;
			enteredSQL = query;
			columnHeaders= dbAccessBean.columnNames(tableName);
			showColumns();
			runSql();
			} 
		
		return "SUCCESS";
		}
	
	public String FilterDataDate() {
		String query="";
		tableName = "gs308_stocktransaction";
		if(ticker=="") {
			query = "Select * from " + tableName + " where DATE_FORMAT(str_to_date(transactiondate, '%m/%d/%Y'),'%Y/%m/%d') BETWEEN '" + dateFrom + "' AND '" + dateTo +"';";
			enteredSQL = query;
			columnHeaders= dbAccessBean.columnNames(tableName);
			showColumns();
			runSql();
		}
		else {
			query = "Select * from " + tableName + " where ticker = '" + ticker + "' AND DATE_FORMAT(str_to_date(transactiondate, '%m/%d/%Y'),'%Y/%m/%d') BETWEEN '" + dateFrom + "' AND '" + dateTo +"';";
			enteredSQL = query;
			columnHeaders= dbAccessBean.columnNames(tableName);
			showColumns();
			runSql();
		
		}		
		
		return "SUCCESS";
	}	
	
	public String SortData() {
		
		String sortby_val = sortby;
		tableName = "gs308_stocktransaction";
		
			String query = "Select * from " + tableName + " order by " + "ticker" + " " + sortby_val;
			enteredSQL = query;
			columnHeaders= dbAccessBean.columnNames(tableName);
			showColumns();
			runSql();	
		
			
		return "true";		
	}
	
	public String SortDataDate() {
		
		String sortby_val = sortbydate;
		tableName = "gs308_stocktransaction";		
			
			String query="";
			if(ticker=="") {
				if(dateFrom=="" || dateTo=="") {
					query = "Select id, ticker, DATE_FORMAT(str_to_date(transactiondate,'%m/%d/%Y'),'%Y/%m/%d') as transactiondate, open, high, low, close, adjClose, volume, dividend, splitCoefficient, dailyReturn from " + tableName + " ORDER BY transactiondate "  + sortby_val;
					enteredSQL = query;
					columnHeaders= dbAccessBean.columnNames(tableName);
					showColumns();
					runSql();	
				}
				else {
					query = "Select id, ticker, DATE_FORMAT(str_to_date(transactiondate,'%m/%d/%Y'),'%Y/%m/%d') as transactiondate, open, high, low, close, adjClose, volume, dividend, splitCoefficient, dailyReturn from " + tableName + " + where DATE_FORMAT(str_to_date(transactiondate, '%m/%d/%Y'),'%Y/%m/%d') BETWEEN '" + dateFrom + "' AND '" + dateTo +"' ORDER BY transactiondate "  + sortby_val;
					enteredSQL = query;
					columnHeaders= dbAccessBean.columnNames(tableName);
					showColumns();
					runSql();	
				}
			}
			else {
				if(dateFrom =="" || dateTo=="") {
					query = "Select id, ticker, DATE_FORMAT(str_to_date(transactiondate,'%m/%d/%Y'),'%Y/%m/%d') as transactiondate, open, high, low, close, adjClose, volume, dividend, splitCoefficient, dailyReturn from " + tableName + " where ticker = '" + ticker + "' ORDER BY transactiondate "  + sortby_val;
					
					enteredSQL = query;
					columnHeaders= dbAccessBean.columnNames(tableName);
					showColumns();
					runSql();	
				}
				else {
					query = "Select id, ticker, DATE_FORMAT(str_to_date(transactiondate,'%m/%d/%Y'),'%Y/%m/%d') as transactiondate, open, high, low, close, adjClose, volume, dividend, splitCoefficient, dailyReturn from " + tableName + " + where ticker = '" + ticker + "' AND DATE_FORMAT(str_to_date(transactiondate, '%m/%d/%Y'),'%Y/%m/%d') BETWEEN '" + dateFrom + "' AND '" + dateTo +"' ORDER BY transactiondate "  + sortby_val;
					enteredSQL = query;
					columnHeaders= dbAccessBean.columnNames(tableName);
					showColumns();
					runSql();	
				}
			}
	
			
		return "true";		
	}
	
	
	public void ComputeReturn()
	{
		try {
		//if (tableNameValue.length() != 0) {
			setRenderErrorMessage(false);
			setRenderColumn(true);
			setRenderDataTable(false);

			executeQuery = null;
			nc = 0;
			nr = 0;

			processQuery = "SELECT * FROM gs308_stocktransaction WHERE ticker = '" + ticker + "';";
			
			if (processQuery.length()!=0) {
				setRenderErrorMessage(false);
				setRenderDataTable(false);
				setRenderColumn(true);

				executeQuery = null;

				nc = 0;
				nr = 0;

				if (!dataOperationsList.isEmpty()) {
					dataOperationsList.clear();
				}

				if (!tableColumns.isEmpty()) {
					tableColumns.clear();
				}
					dataOperationsList = dbAccessBean.executeComputeReturn(processQuery);
					int lastindex = dataOperationsList.size()-1;
					
					
					for (int i=0; i < dataOperationsList.size() ; i++)
					{
						
					if(i < lastindex)
					{
						//Code for ticker
						String t1 = dataOperationsList.get(i).toString();
						String [] result = t1.split(",");
						String ticker1 = result[1].replaceAll("}", "");
						String [] result1 = ticker1.split("=");
						String ticker = result1[1].replaceAll("}", "");
						//Code for transaction date
						String tDate1 = result[2].replaceAll("}", "");
						String [] result2 = tDate1.split("=");
						String transactionDate = result2[1].replaceAll("}", "");
						//Code for close1
						String c1 = result[7].replaceAll("}", "");
						String [] result3 = c1.split("=");
						String Day1close = result3[1].replaceAll("}", "");
						//Code for close2
						String c2 = dataOperationsList.get(i+1).toString();
						String [] result4 = c2.split(",");
						String close2 = result4[7].replaceAll("}", "");
						String [] result5 = close2.split("=");
						String Day2close = result5[1].replaceAll("}", "");									
						
						Double dailyReturn = Math.log(Double.parseDouble(Day2close) / Double.parseDouble(Day1close));	
						dbAccessBean.UpdateDB(ticker, transactionDate, dailyReturn);							
						tableResult();
					}
					else
					{
						//Code for ticker
						String t6 = dataOperationsList.get(lastindex).toString();
						String [] result6 = t6.split(",");
						String ticker2 = result6[1].replaceAll("}", "");
						String [] result7 = ticker2.split("=");
						String ticker = result7[1].replaceAll("}", "");
						//Code for transaction date
						String tDate2 = result6[2].replaceAll("}", "");
						String [] result8 = tDate2.split("=");
						String transactionDate = result8[1].replaceAll("}", "");
						//Code for close2
						String c3 = dataOperationsList.get(lastindex).toString();
						String [] result9 = c3.split(",");
						String close3 = result9[7].replaceAll("}", "");
						String [] result10 = close3.split("=");
						String Day2close = result10[1].replaceAll("}", ""); 
						Double dailyReturn = 0.0;
						dbAccessBean.UpdateDB(ticker, transactionDate, dailyReturn);
						tableResult();
					} 
						
						}
					}			
		
		}		
			catch (SQLException e) {
				if (null != errorMessage) {
					setErrorMessage(null);
				}
				setErrorMessage(e.getMessage());
				setRenderErrorMessage(true);
				setRenderTable(false);
				e.printStackTrace();
			}
			
	}

	
	public String runSelectedQuery() {
		if(selectedQuery!=null) {
			enteredSQL = selectedQuery;
			return runSql();		
		}
		return "false";
	}
	
}
