package edu.uic.ids517.model;

public class DbInformationAccessBean	 {
	private String username;  
	private String password;  
	private String dbms;	  
	private String dbmsHost;
	private String dbSchema; 
	
	// default constructor of the bean 
	public DbInformationAccessBean(){
		
	}

	// setters and getters method of the variables 
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbms() {
		return dbms;
	}

	public void setDbms(String dbms) {
		this.dbms = dbms;
	}

	public String getDbmsHost() {
		return dbmsHost;
	}

	public void setDbmsHost(String dbmsHost) {
		this.dbmsHost = dbmsHost;
	}

	public String getDbSchema() {
		return dbSchema;
	}

	public void setDbSchema(String dbSchema) {
		this.dbSchema = dbSchema;
	};
	
}
