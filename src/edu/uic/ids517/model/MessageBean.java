package edu.uic.ids517.model;

//Error Message handler
public class MessageBean {
	
	private String error;
	private boolean errorRender = false;
	private String processSql;
	private boolean processSqlRender;
	
	public MessageBean() {
		// TODO Auto-generated constructor stub
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isErrorRender() {
		return errorRender;
	}

	public void setErrorRender(boolean errorRender) {
		this.errorRender = errorRender;
	}
	

	public String getProcessSql() {
		return processSql;
	}

	public void setProcessSql(String processSql) {
		this.processSql = processSql;
	}

	public boolean isProcessSqlRender() {
		return processSqlRender;
	}

	public void setProcessSqlRender(boolean processSqlRender) {
		this.processSqlRender = processSqlRender;
	}

	
	

}
