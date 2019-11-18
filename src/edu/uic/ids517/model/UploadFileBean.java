package edu.uic.ids517.model;
import javax.faces.model.SelectItem;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import edu.uic.ids517.model.DbAccessBean;

@ManagedBean(name = "uploadBean")
@SessionScoped
public class UploadFileBean {

	private UploadedFile uploadedFile;
	private List tableNames = new ArrayList<>();
	private String tableNameValue;
	private String ticker;
	private String transactionDate;
	private Double open,close,high,low,adjClose,splitCoefficient,dividend;
	Long volume;
		
	private List fileFormatList = new ArrayList();
	
	private boolean rendercolumns;
	public boolean isRendercolumns() {
		return rendercolumns;
	}

	public void setRendercolumns(boolean rendercolumns) {
		this.rendercolumns = rendercolumns;
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

	public List getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List columnNames) {
		this.columnNames = columnNames;
	}

	private List columnNameValue;
	private List columnNames = new ArrayList<>();
	public List getTableNames() {
		return tableNames;
	}

	public void setTableNames(List tableNames) {
		this.tableNames = tableNames;
	}

	//private String fileLabel;
	private String fileName;
	private long fileSize;
	private String fileContentType;
	private int numberRows;
	private int numberColumns;
	private String uploadedFileContents;
	private boolean fileImport;
	private boolean fileImportError;
	private String filePath;
	private String tempFileName;
	private FacesContext facesContext;
	private String datasetLabel;
	private String fileType;
	private String fileFormat;
	private String fileheaderRowFormat;
	private String relativeURL;
	private String contentsUploadedFile;
	private boolean renderRegression;
	private DbSchemaBean dbSchemaBean;
	private DbAccessBean dbAccessBean;
	

	public DbAccessBean getDataAccess() {
		return dataAccess;
	}

	public void setDataAccess(DbAccessBean dataAccess) {
		this.dataAccess = dataAccess;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public File getFileContents() {
		return fileContents;
	}

	public void setFileContents(File fileContents) {
		this.fileContents = fileContents;
	}

	public List getFileFormatList() {
		return fileFormatList;
	}

	public void setFileFormatList(List fileFormatList) {
		this.fileFormatList = fileFormatList;
	}
	
	private String errorMessage;
	private boolean messageRendered;
	private DbAccessBean dataAccess;
    DbAccessBean dbaccess = new DbAccessBean();
	
	String status = "";
	File fileContents = null;
	
	public UploadFileBean()
	{
		fileFormatList.clear();
		fileFormatList.add(new SelectItem("0", "Select"));
		fileFormatList.add(new SelectItem("1", "csv"));
		fileFormatList.add(new SelectItem("2", "Excel"));
		fileFormatList.add(new SelectItem("3", "tsv"));
	}

	@PostConstruct
	public void init() {
		Map<String, Object> m = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		dataAccess = (DbAccessBean) m.get("dataAccess");
	}
	
	public String processFileImport() {
		
		if(uploadedFile== null){
			return "FAIL";
		}
		
		errorMessage="";
		uploadedFileContents = null;
		facesContext = FacesContext.getCurrentInstance();
		filePath = facesContext.getExternalContext().getRealPath("/temp");
		FileOutputStream fos = null;
		
		fileImport = false;
		fileImportError = true;
		try {
			
			fileName = uploadedFile.getName();
			fileSize = uploadedFile.getSize();
			fileContentType = uploadedFile.getContentType();
			tempFileName = filePath + "/" + fileName;				
	
			if ("1".equalsIgnoreCase(fileFormat)) {
			fileName = uploadedFile.getName();
			fileSize = uploadedFile.getSize();
			fileContentType = uploadedFile.getContentType();
			tempFileName = filePath + "/" + fileName;
			fileContents = new File(tempFileName);
			fos = new FileOutputStream(fileContents);
			fos.write(uploadedFile.getBytes());
			fos.close();
			
			Scanner s = new Scanner(fileContents);
			String input;
			int result_len;
			String [] result;			

			BufferedReader brTest = new BufferedReader(new FileReader(tempFileName));
			String text = brTest.readLine();
			// Stop. text is the first line.
			System.out.println(text);
			String[] strArray = text.split(",");
			
			while(s.hasNext())
			{									
						//if headers exist
						if(strArray[0].contains("[a-zA-Z]+") == true && text.length() > 2) {
							s.nextLine();
							input = s.nextLine();				
							result = input.split(",");
							result_len = result.length;	
						}
						//no headers
						else {
							input = s.nextLine();				
							result = input.split(",");
							result_len = result.length;								
						}
							if(result_len == 10) {
								ticker = result[0];
								transactionDate = result[1];
								open = Double.parseDouble(result[2]);
								high = Double.parseDouble(result[3]);
								low = Double.parseDouble(result[4]);
								close = Double.parseDouble(result[5]);
								adjClose = Double.parseDouble(result[6]);
								volume = Long.parseLong(result[7]);
								dividend = Double.parseDouble(result[8]);
								splitCoefficient = Double.parseDouble(result[9]);
									
								dbaccess.InsertintoDB(ticker, transactionDate, open, high, low, close, adjClose, volume, dividend,splitCoefficient);						
								errorMessage = "Your file has been uploaded successfully. Please check the updated table.";
								messageRendered = true;
								
							}
							else {
								errorMessage = "Insufficient data! Please try another file.";
								messageRendered = true;
							}
							
											
						}

						brTest.close();						
					
			 }
			 
			 if ("2".equalsIgnoreCase(fileFormat)) {
				 
				 fileName = uploadedFile.getName();
					fileSize = uploadedFile.getSize();
					fileContentType = uploadedFile.getContentType();
					tempFileName = filePath + "/" + fileName;
					fileContents = new File(tempFileName);
					fos = new FileOutputStream(fileContents);
					fos.write(uploadedFile.getBytes());
					fos.close();
					
					Reader in = new FileReader(tempFileName);
					Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
					for (CSVRecord record : records) {
					   
						ticker = record.get(0);
				        transactionDate = record.get(1);
				        open = Double.parseDouble(record.get(2));
				        high = Double.parseDouble(record.get(3));
				        low = Double.parseDouble(record.get(4));
				        close = Double.parseDouble(record.get(5));
				        adjClose = Double.parseDouble(record.get(6));
				        volume = Long.parseLong(record.get(7));
				        dividend = Double.parseDouble(record.get(8));
					    splitCoefficient = Double.parseDouble(record.get(9));
					    dbaccess.InsertintoDB(ticker, transactionDate, open, high, low, close, adjClose, volume, dividend,splitCoefficient);
					    errorMessage = "Your file has been uploaded successfully. Please check the updated table.";
						messageRendered = true;
						
					} 			 
			 }
			 
				if ("3".equalsIgnoreCase(fileFormat)) {
					fileName = uploadedFile.getName();
					fileSize = uploadedFile.getSize();
					fileContentType = uploadedFile.getContentType();
					tempFileName = filePath + "/" + fileName;
					fileContents = new File(tempFileName);
					fos = new FileOutputStream(fileContents);
					fos.write(uploadedFile.getBytes());
					fos.close();
					
					Scanner s = new Scanner(fileContents);
					String input;
					int result_len;
					String [] result;			

					BufferedReader brTest = new BufferedReader(new FileReader(tempFileName));
					String text = brTest.readLine();
					// Stop. text is the first line.
					System.out.println(text);
					String[] strArray = text.split(",");
					
					while(s.hasNext())
					{									
								//if headers exist
								if(strArray[0].contains("[a-zA-Z]+") == true && text.length() > 2) {
									s.nextLine();
									input = s.nextLine();			
									result = input.split("\\t");
									result_len = result.length;	
								}
								//no headers
								else {
									input = s.nextLine();				
									result = input.split("\\t");
									result_len = result.length;								
								}
									if(result_len == 10) {
										ticker = result[0];
										transactionDate = result[1];
										open = Double.parseDouble(result[2]);
										high = Double.parseDouble(result[3]);
										low = Double.parseDouble(result[4]);
										close = Double.parseDouble(result[5]);
										adjClose = Double.parseDouble(result[6]);
										volume = Long.parseLong(result[7]);
										dividend = Double.parseDouble(result[8]);
										splitCoefficient = Double.parseDouble(result[9]);
											
										dbaccess.InsertintoDB(ticker, transactionDate, open, high, low, close, adjClose, volume, dividend,splitCoefficient);						
										errorMessage = "Your file has been uploaded successfully. Please check the updated table.";
										messageRendered = true;
										
									}
									else {
										errorMessage = "Insufficient data! Please try another file.";
										messageRendered = true;
									}
									
													
								}

								brTest.close();	
		 }
			
		} catch (IOException e) { 
			errorMessage = "Upload failed.";
			messageRendered = true;
			
			e.printStackTrace();
			return "FAIL";
		}
		return "SUCCESS"; 
	}
	
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}


	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public long getFileSize() {
		return fileSize;
	}

	public String getRelativeURL() {
		return relativeURL;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}


	public String getFileContentType() {
		return fileContentType;
	}


	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}


	public int getNumberRows() {
		return numberRows;
	}


	public void setNumberRows(int numberRows) {
		this.numberRows = numberRows;
	}


	public int getNumberColumns() {
		return numberColumns;
	}


	public void setNumberColumns(int numberColumns) {
		this.numberColumns = numberColumns;
	}


	public String getUploadedFileContents() {
		return uploadedFileContents;
	}


	public void setUploadedFileContents(String uploadedFileContents) {
		this.uploadedFileContents = uploadedFileContents;
	}


	public boolean isFileImport() {
		return fileImport;
	}


	public void setFileImport(boolean fileImport) {
		this.fileImport = fileImport;
	}

	public void setRelativeURL(String relativeURL) {
		this.relativeURL = relativeURL;
	}

	public boolean isFileImportError() {
		return fileImportError;
	}


	public void setFileImportError(boolean fileImportError) {
		this.fileImportError = fileImportError;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getTempFileName() {
		return tempFileName;
	}


	public void setTempFileName(String tempFileName) {
		this.tempFileName = tempFileName;
	}


	public FacesContext getFacesContext() {
		return facesContext;
	}


	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}


	public String getDatasetLabel() {
		return datasetLabel;
	}


	public void setDatasetLabel(String datasetLabel) {
		this.datasetLabel = datasetLabel;
	}


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public String getFileFormat() {
		return fileFormat;
	}


	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}


	public String getFileheaderRowFormat() {
		return fileheaderRowFormat;
	}


	public void setFileheaderRowFormat(String fileheaderRowFormat) {
		this.fileheaderRowFormat = fileheaderRowFormat;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isMessageRendered() {
		return messageRendered;
	}

	public void setMessageRendered(boolean messageRendered) {
		this.messageRendered = messageRendered;
	}	

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}
	
}

