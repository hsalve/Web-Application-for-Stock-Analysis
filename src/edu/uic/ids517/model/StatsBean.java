package edu.uic.ids517.model;

import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.time.Day;
import java.util.Calendar;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.date.MonthConstants;

import java.sql.DatabaseMetaData;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class StatsBean {
	
	private DbInformationAccessBean dbInformationAccessBean;
	private double quartileOne;
	private double quartileThree;
	private double medianOne;
	private List<StatsManagedBean> statisticList;
	private Result result;
	private ResultSet resultSet;
	private int columnCount;
	private int rowsAffected;
	private List<String> numericData;
	private List<String> worldTables = new ArrayList<String>();

	private List<String> categoricalData;
	private List<String> columnSelected;
	private List<String> columnsList;
	private List<String> tableList;
	private List<String> columns;
	private List<String> list;
	private XYSeries xySeries;
	private XYSeriesCollection xySeriesVars;
	private String message;
	private String tableSelected;
	private String predictorValue;
	private String responseValue;
	private String predictorTicker;
	private String responseTicker;
	private boolean columnRender;
	private boolean disableButton;
	private DbAccessBean dbAccessBean;
	private DatabaseMetaData metaData;
	private ResultSetMetaData resultSetMetaData;
	private StatsManagedBean statsManagedBean;
	private MathManagedBean mathManagedBean;
	private XYSeriesCollection xySeriesVar;
	private XYSeriesCollection xyTimeSeriesCol;
	private XYSeries predSeries;
	private XYSeries resSeries;
	private String errorMessage;
	private boolean renderMessage;
	private boolean renderReport;
	private boolean renderTabledata;
	private boolean disableTabledata;
	private boolean disableRegressionResult;
	private Export export;
	private boolean renderTablename;
	private boolean renderRegressionColumn;
	private boolean renderRegressionButton;
	private boolean renderColumnListbutton;
	private boolean renderRegressionResult;
	private boolean renderSchema;
	private boolean seriesChart;
	private boolean seriesChart1;
	private String seriesPath;
	private String seriesPath1;
	StringBuffer exportBuffer;
	private String ticker;
	private String graphType, graphColumn;
	private String seriesPath2;
	
	public StatsBean() 
	{
		
		renderRegressionButton = true;
		renderReport = false;
		tableList = new ArrayList<String>();
		list = new ArrayList<String>();
		xySeries = new XYSeries("Random");
		xySeriesVar = new XYSeriesCollection();		
//		renderTablename = true;		
		xyTimeSeriesCol = new XYSeriesCollection();
		predSeries = new XYSeries("Predictor");
		resSeries = new XYSeries("Response");
		columnSelected = new ArrayList<String>();
		columnsList = new ArrayList<String>();
		columns = new ArrayList<String>();
		renderTabledata = false;
		statisticList = new ArrayList<StatsManagedBean>();
		categoricalData = new ArrayList<String>();
		numericData = new ArrayList<String>();
		disableButton = false;
		getTables();
	}

	public String processLogout() 
	{
		try 
		{
			reset();
			dbAccessBean.connectionClose();
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.invalidateSession();
			return "LOGOUT";
		}
		catch (Exception error) 
		{
			message = error.getMessage();
			renderMessage = true;
			return "FAIL";
		}
	}

	public String getTables() 
	{
		try 
		{
			reset();
			tableList = new ArrayList<String>();
			String tableNames;
			ResultSet[] rs = dbAccessBean.fetchTables();
			
			if (rs != null) {
				while (rs[0].next()) {
					tableNames = rs[0].getString("TABLE_NAME");
					tableList.add(tableNames);
			}
				
				renderTableList();
			} 
			else 
			{
				message = dbAccessBean.getMessage();
				renderMessage = true;
			}
			return "SUCCESS";
		} 
		catch (Exception error) 
		{
			message = error.getMessage();
			renderMessage = true;
			return "FAIL";
		}
	}

	public void renderTableList() 
	{
		reset();
		if (tableList.isEmpty()) 
		{
			message = "No tables in selected schema.";
			renderTabledata = false;
			renderMessage = true;
			renderTablename = false;
			columnRender = true;
			renderMessage = true;
			columnRender = false;
			renderRegressionResult = false;
			columnRender = false;
			renderRegressionColumn = false;
			
		} 
		else
			renderTablename = true;
	}

	public String getRegressionColumnNames()
	{
		reset();
		if (tableList.isEmpty())
		{
			message = "No tables in selected schema.";
			renderMessage = true;
			return "FAIL";
		}
		if (tableSelected.isEmpty()) 
		{
			message = "Select a table.";
			renderMessage = true;
			return "FAIL";
		}
		if (generateRegressionColumns())
		{
			return "SUCCESS";
		} 
		else 
		{
			renderMessage = true;
			return "FAIL";
		}
	}

	public boolean generateRegressionColumns() 
	{
		try 
		{
			String sqlQuery="";
			
		    sqlQuery = "select * from "  + tableSelected;
			
			resultSet = dbAccessBean.fetchColumnData(sqlQuery);
			if (resultSet != null) 
			{
				columnsList.clear();
				categoricalData.clear();
				numericData.clear();
				ResultSetMetaData resultSetmd = (ResultSetMetaData) resultSet.getMetaData();
				int columnCount = resultSetmd.getColumnCount();
				for (int i = 1; i <= columnCount; i++) 
				{
					String name = resultSetmd.getColumnName(i);
					String datatype = resultSetmd.getColumnTypeName(i);
					if (datatype.equalsIgnoreCase("char") || datatype.equalsIgnoreCase("varchar")) 
					{
						categoricalData.add(name);
					}
					else
						numericData.add(name);
				}
				columnRender = true;
			} 
			else 
			{
				message = dbAccessBean.getMessage();
				renderMessage = true;
				return false;
			}
			return true;
		} 
		catch (Exception error) 
		{
			message = error.getMessage();
			renderMessage = true;
			return false;
		}
	}

	// Split Columns to be used for report generation generateReport method
	public String splitColumns() 
	{
		try 
		{
			reset();
			
			if (tableSelected != null && columnSelected != null) 
			{
				List<String> columnSeperated = new ArrayList<String>();
				for (int i = 0; i < columnSelected.size(); i++) 
				{
					String data = columnSelected.get(i);
					int index = data.indexOf(" ");
					String column = data.substring(0, index);
					String datatype = data.substring((index + 1), data.length());
					if (datatype.equalsIgnoreCase("CHAR") || datatype.equalsIgnoreCase("VARCHAR")) 
					{
						message = "Categorical values are not permited.";
						return "FAIL";
					} else
					{
						columnSeperated.add(column);
					}
				}
				columnSelected = new ArrayList<String>();
				columnSelected = columnSeperated;
				list.clear();
				list = columnSelected;
				columnSeperated = null;
				return "SUCCESS";
			} 
			else
			{
				message = "Select a table and a column.";
				return "FAIL";
			}
		} 
		catch (Exception error) 
		{
			message = error.getMessage();
			renderMessage = true;
			return "FAIL";
		}
	}

	public String generateReport()
	{
		reset();
		System.out.println(columnSelected);
		System.out.println(tableSelected);

		System.out.println(tableSelected);
		if (tableList.isEmpty()) 
		{
			message = "No tables in selected schema.";
			renderMessage = true;
			return "FAIL";
		}
		
		if (tableSelected.isEmpty())
		{
			message = "Select a table and a column.";
			renderMessage = true;
			return "FAIL";
		}
		if (columnSelected.isEmpty()) 
		{
			message = "Select a column.";
			renderMessage = true;
			return "FAIL";
		}
		if (splitColumns().equalsIgnoreCase("FAIL")) 
		{		System.out.println("FAIL 316");

			renderMessage = true;
			return "FAIL";
		} else 
		{

			if (calculateVariables().equals("FAIL"))
			{
				renderMessage = true;
				return "FAIL";
			} 
			else 
			{
				export.setCmd("descriptiveStats\n");
				export.addCmd();
				return "SUCCESS";
			}
		}
	}
	
public String getSeriesPath1() {
	return seriesPath1;
}

public void setSeriesPath1(String seriesPath1) {
	this.seriesPath1 = seriesPath1;
}

public boolean isSeriesChart() {
	return seriesChart;
}

public void setSeriesChart(boolean seriesChart) {
	this.seriesChart = seriesChart;
}

public boolean isSeriesChart1() {
	return seriesChart;
}

public void setSeriesChart1(boolean seriesChart1) {
	this.seriesChart1 = seriesChart1;
}

	public String calculateVariables() 
	{
		exportBuffer = new StringBuffer();
		try
		{
			for (int listCounter = 0; listCounter < list.size(); listCounter++)
			{
				String sqlQuery = "select " + list.get(listCounter) + " from " + tableSelected;
				resultSet = dbAccessBean.selectQueryProcessing(sqlQuery);
				if (resultSet == null) 
				{
					
					message = dbAccessBean.getMessage();
					renderMessage = true;
					return "FAIL";
				}
				resultSetMetaData = (ResultSetMetaData) resultSet.getMetaData();
				columnCount = resultSetMetaData.getColumnCount();
				String columnName;
				
				for (int columnCounter = 1; columnCounter < columnCount + 1; columnCounter++)
				{
					List<Double> values = new ArrayList<Double>();
					columnName = resultSetMetaData.getColumnName(columnCounter);
					String columnType = resultSetMetaData.getColumnTypeName(columnCounter);
					
					while (resultSet.next())
					{
						switch (columnType.toLowerCase()) 
						{
						case "int":
							values.add((double) resultSet.getInt(columnName));
							break;
						case "smallint":
							values.add((double) resultSet.getInt(columnName));
							break;
						case "float":
							values.add((double) resultSet.getFloat(columnName));
							break;
						case "double":
							values.add((double) resultSet.getDouble(columnName));
							break;
						case "long":
							values.add((double) resultSet.getLong(columnName));
							break;
						default:
							values.add((double) resultSet.getDouble(columnName));
							break;
						}
					}
					double[] valuesArray = new double[values.size()];
					for (int i = 0; i < values.size(); i++) {
						valuesArray[i] = (double) values.get(i);
					}
					
					double minimumValue = MathUtil.round(StatUtils.min(valuesArray), 100);
					
					double maximumValue = MathUtil.round(StatUtils.max(valuesArray), 100);
					double mean = MathUtil.round(StatUtils.mean(valuesArray), 100);
					double variance = StatUtils.variance(valuesArray, mean);
					double standardDeviation = Math.sqrt(variance);
					double median = MathUtil.round(StatUtils.percentile(valuesArray, 50.0), 100);
					double quartileOne = MathUtil.round(StatUtils.percentile(valuesArray, 25.0), 100);
					double quartileThree = MathUtil.round(StatUtils.percentile(valuesArray, 75.0), 100);
					double interquratileRange = quartileThree - quartileOne;
					double range = maximumValue - minimumValue;
					String columnNames [] = new String[]{"Column Selected","Minimum Value","Maximum Value","Mean","	Variance ","	Standard Deviation	","quartileOne","	quartileThree ","	Range ","	interquartileRange"};

					exportBuffer.append(list.get(listCounter)+","+minimumValue+","+maximumValue+","+mean+","+variance+","+standardDeviation +","+quartileOne +","+quartileThree+","+range+","+interquratileRange+",\n");
					statisticList.add(new StatsManagedBean(quartileOne, quartileThree, interquratileRange, range,
							columnName, minimumValue, maximumValue, mean, variance, standardDeviation, median));
					statsManagedBean.setVariables(quartileOne, quartileThree, median);
				}
				renderTabledata = true;
			}
			return "SUCCESS";
		} 
		catch (Exception error) 
		{
			error.printStackTrace();
			message = error.getMessage();
			renderMessage = true;
			return "FAIL";
		}
	}

	public String export(){
		try 
		{
			
				FacesContext facesCont = FacesContext.getCurrentInstance();
				ExternalContext externalCont = facesCont.getExternalContext();
				FileOutputStream fileOutputStream = null;
				String path = facesCont.getExternalContext().getRealPath("/temp");
				File dir = new File(path);
				
				if(!dir.exists())
					new File(path).mkdirs();
				externalCont.setResponseCharacterEncoding("UTF-8");
				String fileNameBase = tableSelected + ".csv";
				String fileName = path + "/" + "_" + fileNameBase;
				File f = new File(fileName);
				
				
			
				
					result = ResultSupport.toResult(resultSet);
					Object [][] sData = result.getRowsByIndex();
					String columnNames [] = new String[]{"Column Selected","Minimum Value","	Maximum Value","	Mean","	Variance ","	Standard Deviation	","quartileOne","	quartileThree ","	Range ","	interquartileRange"};
					StringBuffer stringBuff = new StringBuffer();
				
					try 
					{
						fileOutputStream = new FileOutputStream(fileName);
						for(int i=0; i<columnNames.length; i++) 
						{
							stringBuff.append(columnNames[i].toString() + ",");
						}
						stringBuff.append("\n");
						stringBuff.append(exportBuffer);
						fileOutputStream.write(stringBuff.toString().getBytes());
					
						
						fileOutputStream.flush();
						fileOutputStream.close();
					} 
					
					catch (FileNotFoundException error) 
					{
						message = error.getMessage();
						renderMessage = true; 
					} 
					
					catch (IOException io) 
					{
						message = io.getMessage();
						renderMessage = true;
					}
					String mimeType = externalCont.getMimeType(fileName);
					FileInputStream input = null;
					byte b;
					externalCont.responseReset();
					externalCont.setResponseContentType(mimeType);
					externalCont.setResponseContentLength((int) f.length());
					externalCont.setResponseHeader("Content-Disposition",
							"attachment; filename=\"" + fileNameBase + "\"");
					
					try 
					{
						input = new FileInputStream(f);
						OutputStream output = externalCont.getResponseOutputStream();
						
						while(true) 
						{
							b = (byte) input.read();
							if(b < 0)
								break;
							output.write(b);

						}
					} 
					
					catch (IOException error) 
					{
						message=error.getMessage();
						renderMessage=true;
					}
					
					finally
					{
					
					//Test message CSV
					System.out.println("Exporting CSV File: " + fileNameBase);
					///////////create log entry 
					
					
					//test accesslog
					//accessLogBean.logEntry("logged in");/////

					
					try 
						{ 
							input.close(); 
						} 
						
						catch (IOException error) 
						{
							message=error.getMessage();
							renderMessage=true;
						}
					}
					facesCont.responseComplete();
				
				
				
			

			return "SUCCESS";
		} 
		
		catch (Exception error) 
		{
			message = error.getMessage();
			renderMessage = true;
			return "FAIL";
		}
	}
	
	// Get names of columns
	public String getColumnNames() 
	{
		try 
		{
			reset();
			getTables();
			if (tableList.isEmpty())
			{
				message = "No tables in selected schema.";
				renderMessage = true;
				return "FAIL";
			}
			if (tableSelected.isEmpty())
			{
				message = "Select a table.";
				renderMessage = true;
				return "FAIL";
			} 
			else 
			{
				columnsList.clear();
				String sqlQuery = "";
				if(worldTables.contains(tableSelected))
				 sqlQuery = "select * from world."  + tableSelected;
				else sqlQuery = "select * from "+dbInformationAccessBean.getDbSchema()+"."  + tableSelected;

				ResultSet resultSet = dbAccessBean.fetchColumnData(sqlQuery);
				if (resultSet != null)
				{

					ResultSetMetaData resultSetmd = (ResultSetMetaData) resultSet.getMetaData();
					int columnCount = resultSetmd.getColumnCount();
					for (int i = 1; i <= columnCount; i++) {
						String name = resultSetmd.getColumnName(i);
						String datatype = resultSetmd.getColumnTypeName(i);
						columns.add(name);
						columnsList.add(name + " " + datatype);
					}
					columnRender = true;
				}
				else
				{
					message = dbAccessBean.getMessage();
					renderMessage = true;
				}
			}
			return "SUCCESS";
		} 
		catch (Exception error)
		{
			message = error.getMessage();
			renderMessage = true;
			return "FAIL";
		}
	}

	public String displayColumnsforRegression()
	{
		reset();
		if (tableList.isEmpty()) 
		{
			message = "No tables in selected schema.";
			renderMessage = true;
			renderColumnListbutton = true;
			renderReport = true;
			renderRegressionColumn = false;
			return "FAIL";
		}
		
		if (tableSelected == null)
		{
			message = "Select a table.";
			renderMessage = true;
			renderColumnListbutton = true;
			renderReport = true;
			return "FAIL";
		}
		String status = getRegressionColumnNames();
		if (status.equalsIgnoreCase("SUCCESS"))
		{
			columnRender = false;
			renderRegressionButton = false;
			renderRegressionColumn = true;
			renderColumnListbutton = true;
			renderReport = true;

			return "SUCCESS";
		} 
		else 
		{
			renderMessage = true;
			return "FAIL";
		}
	}

	public String generateRegressionReport() 
	{
		reset();
		if (tableList.isEmpty()) 
		{
			message = "No tables in selected schema.";
			renderMessage = true;
			renderColumnListbutton = true;
			renderReport = true;
			renderRegressionColumn = false;
			return "FAIL";
		}
		
		if (tableSelected == null) 
		{
			message = "Select a table.";
			renderMessage = true;
			return "FAIL";
		}
		if (predictorValue == null || responseValue == null || responseValue.equals("0") || predictorValue.equals("0")) 
		{
			message = "Select a predictor and a response variable.";
			renderMessage = true;
			return "FAIL";
		}

		if (calculateRegressionVariables())
		{
			return "SUCCESS";
		} 
		else
			return "FAIL";
	}
	
	public String getSeriesPath() {
		return seriesPath;
	}

	public void setSeriesPath(String seriesPath) {
		this.seriesPath = seriesPath;
	}	
	
	public String generateChart() 
	  {
		  reset();
		  try
		  {
			  if(tableSelected==null)
			  {
				  message="Select table";
				  renderMessage=true;
				  return "FAIL";
			  }
			 
			  FacesContext context = FacesContext.getCurrentInstance();
			  String path = context.getExternalContext().getRealPath("/ChartImages");
			  File dir = new File(path);
			  if(!dir.exists())
			  {
				  new File(path).mkdirs();
			  }
			  
			  if(tableSelected == null)
			  {
				  message = "Select a table.";
				  renderMessage=true;
				  return "FAIL";
			  }
			  
			  if(responseValue == null || predictorValue == null)
		  		{
		  			message="Select a response and a predictor values to generate the Chart";
		  			renderMessage=true;
		  			return "FAIL";
		  		}
			  	if(responseValue.equals("0") || predictorValue.equals("0"))
		  		{
		  			message="Select a response and a predictor values to generate the Chart";
		  			renderMessage=true;
		  			return "FAIL";
		  		}
		  		{
		  			
		  			//Scatter plot
		  			
		  			JFreeChart chart = ChartFactory.createScatterPlot(
		  					"Scatter Plot", predictorValue, responseValue,
		  					getXySeriesVar(), PlotOrientation.VERTICAL,
		  					true, true, false);
		  			File xy = new File(path+"/"+ dbAccessBean.getDbInformationAccessBean().getUsername() +"_scatterplot.png" + predictorValue + "_"+responseValue+".png");
		  			ChartUtilities.saveChartAsPNG(xy, chart, 400, 400);
		  			seriesPath = "/ChartImages/"+ dbAccessBean.getDbInformationAccessBean().getUsername() +"_scatterplot.png" + predictorValue + "_"+responseValue+".png";
		  			///////////////////////////////////////////////
		  			 HistogramDataset dataset = new HistogramDataset();
					 dataset.setType(HistogramType.RELATIVE_FREQUENCY);
					 double[] predictorArray;
					 
					seriesChart=true;
				    seriesChart1=true;
				    return "SUCCESS";
		  		}
		  		
		  } catch(IOException io) {
			  message=io.getMessage();
			  renderMessage=true;
			  return "fail";
		  } catch(Exception error) {
			  message=error.getMessage();
			  renderMessage=false;
			  return "fail";
		  }
	  }
	  
	public double[] barcol() throws SQLException
	{
		double[] predictorArray;
		String sqlQuery = "select " +graphColumn+ " from "+ tableSelected+ " where ticker='" + ticker + "';";
		resultSet = dbAccessBean.selectQueryProcessing(sqlQuery);
		if (resultSet != null) {
			List<Double> predictorList = new ArrayList<Double>();
			while (resultSet.next()) 
			{
					predictorList.add((double) resultSet.getDouble(1));
			}
			predictorArray = new double[predictorList.size()];
			for (int i = 0; i < predictorList.size(); i++)
			{
				predictorArray[i] = (double) predictorList.get(i);
			}
			return predictorArray;
		}
		else {
			return null;
		}
	}
	  
	public boolean calculateRegressionVariables() 
	{
		try 
		{
			resSeries.clear();
			predSeries.clear();
			xySeries.clear();
			xySeriesVar.removeAllSeries();
			xyTimeSeriesCol.removeAllSeries();
			if(responseValue!="" && predictorValue!="") {
				List<Double> predictorList = new ArrayList<Double>();
				List<Double> responseList = new ArrayList<Double>();
				String predQuery = "select " + predictorValue + " from " + tableSelected+" where ticker='" + predictorTicker +"';" ;	
				ResultSet rsPred = dbAccessBean.selectQueryProcessing(predQuery);
				if(rsPred!=null) {
					while(rsPred.next()) {
						predictorList.add((double) rsPred.getDouble(1));
					}
				}
				String resQuery = "select " + responseValue + " from " + tableSelected+" where ticker='" + responseTicker +"';" ;
				ResultSet rsRes = dbAccessBean.selectQueryProcessing(resQuery);
				if(rsRes!=null) {
					while(rsRes.next()) {
						responseList.add((double) rsRes.getDouble(1));
					}
				}
					
				double[] predictorArray = new double[predictorList.size()];
				for (int i = 0; i < predictorList.size(); i++)
				{
					predictorArray[i] = (double) predictorList.get(i);
					predSeries.add(i + 1, (double) predictorList.get(i));
				}
				double[] responseArray = new double[responseList.size()];
				for (int i = 0; i < responseList.size(); i++) 
				{
					responseArray[i] = (double) responseList.get(i);
					resSeries.add(i + 1, (double) responseList.get(i));
				}
				xyTimeSeriesCol.addSeries(predSeries);
				xyTimeSeriesCol.addSeries(resSeries);
//					xyTimeSeriesCol.addSeries(predSeries);
				SimpleRegression sr = new SimpleRegression();
				if (responseArray.length > predictorArray.length)
				{
					for (int i = 0; i < predictorArray.length; i++) 
					{
						sr.addData(predictorArray[i], responseArray[i]);
						xySeries.add(predictorArray[i], responseArray[i]);
						/*sr.addData(responseArray[i],predictorArray[i]);
						xySeries.add(responseArray[i],predictorArray[i]);*/
					}
				}
				else 
				{
					for (int i = 0; i < responseArray.length; i++) 
					{
						sr.addData(predictorArray[i], responseArray[i]);
						xySeries.add(predictorArray[i], responseArray[i]);
					}
				}
				xySeriesVar.addSeries(xySeries);
				int totalDF = responseArray.length - 1;
				TDistribution tDistribution = new TDistribution(totalDF);
				double intercept = sr.getIntercept();
				double interceptStandardError = sr.getInterceptStdErr();
				double tStatistic = 0;
				int predictorDF = 1;
				int residualErrorDF = totalDF - predictorDF;
				double rSquare = sr.getRSquare();
				double rSquareAdjusted = rSquare - (1 - rSquare) / (totalDF - predictorDF - 1);
				if (interceptStandardError != 0) 
				{
					tStatistic = (double) intercept / interceptStandardError;
				}
				double interceptPValue = (double) 2 * tDistribution.cumulativeProbability(-Math.abs(tStatistic));
				double slope = sr.getSlope();
				double slopeStandardError = sr.getSlopeStdErr();
				double tStatisticpredict = 0;
				if (slopeStandardError != 0) 
				{
					tStatisticpredict = (double) slope / slopeStandardError;
				}
				double pValuePredictor = (double) 2 * tDistribution.cumulativeProbability(-Math.abs(tStatisticpredict));
				double standardErrorModel = Math.sqrt(StatUtils.variance(responseArray))
						* (Math.sqrt(1 - rSquareAdjusted));
				double regressionSumSquares = sr.getRegressionSumSquares();
				double sumSquaredErrors = sr.getSumSquaredErrors();
				double totalSumSquares = sr.getTotalSumSquares();
				double meanSquare = 0;
				if (predictorDF != 0) {
					meanSquare = regressionSumSquares / predictorDF;
				}
				double meanSquareError = 0;
				if (residualErrorDF != 0)
				{
					meanSquareError = (double) (sumSquaredErrors / residualErrorDF);
				}
				double fValue = 0;
				if (meanSquareError != 0) {
					fValue = meanSquare / meanSquareError;
				}
				String regressionEquation = responseValue + " = " + intercept + " + (" + slope + ") " + predictorValue;
				FDistribution fDistribution = new FDistribution(predictorDF, residualErrorDF);
				double pValue = (double) (1 - fDistribution.cumulativeProbability(fValue));
				boolean regressionResultsStatus = mathManagedBean.setRegAnalysisVar(regressionEquation,
						intercept, interceptStandardError, tStatistic, interceptPValue, slope, slopeStandardError,
						tStatisticpredict, pValuePredictor, standardErrorModel, rSquare, rSquareAdjusted, predictorDF,
						residualErrorDF, totalDF, regressionSumSquares, sumSquaredErrors, totalSumSquares, meanSquare,
						meanSquareError, fValue, pValue);
				if (regressionResultsStatus)
				{
					renderRegressionResult = true;
					return true;
				} 
				else
				{
					message = mathManagedBean.getMessage();
					renderMessage = true;
					return false;
				}
			}
			else {
				return false;
			}
		} 
		catch (Exception error) 
		{
			message = error.getMessage();
			renderMessage = true;
			return false;
		}
	}	  

	public boolean onChartTypeChange()
	{
		if (getTables().equals("SUCCESS")) 
		{
			renderRegressionColumn = false;
			renderTablename = false;
			return true;
		} 
		else
		{
			errorMessage = message;
			return false;
		}
	}

	public boolean generateResultsforGraph() 
	{
		if (calculateVariables().equals("SUCCESS")) 
		{
			renderTabledata = false;
			return true;
		}
		renderTabledata = false;
		errorMessage = message;
		return false;
	}

	public boolean onTableChange()
	{
		if (generateRegressionColumns())
		{
			renderRegressionColumn = false;
			return true;
		}
		else
		{
			errorMessage = message;
			return false;
		}
	}

	public boolean generateRegressionResults()
	{
		xySeries.clear();
		xySeriesVar.removeAllSeries();
		if (calculateRegressionVariables())
		{
			renderRegressionResult = false;
			return true;
		}
		else
		{
			errorMessage = message;
			return false;
		}
	}

	public void statisticsSchemaChange()
	{
		
		columnRender = false;
		renderTabledata = false;
		renderRegressionResult = false;
		renderMessage = false;
		columnRender = false;
		columnsList = new ArrayList<String>();
		tableList = new ArrayList<String>();
		list = new ArrayList<String>();
		statisticList = new ArrayList<StatsManagedBean>();
		categoricalData = new ArrayList<String>();
		columnSelected = new ArrayList<String>();
		columnsList = new ArrayList<String>();
		columns = new ArrayList<String>();
		resetButton();
	}
	
	public String graphSelection() {
		reset();
		String status="";
		try
		  {
			
			if(tableSelected==null)
			  {
				  message="Select table";
				  renderMessage=true;
				  status="FAIL";
			  }
			else if(ticker=="0") {
				  message="Select ticker";
				  renderMessage=true;
				  status="FAIL";
			}
			else if(graphType=="0") {
				  message="Select Graph";
				  renderMessage=true;
				  status="FAIL";
			}
			else {
				if(graphType.equalsIgnoreCase("histogram")) {
					predictorValue = graphColumn;
					status=generateHistogram();
				}
				if(graphType.equalsIgnoreCase("timeSeries")) {
					status=generateTimeSeries();
				}
				
			}
		  } catch(Exception error) {
			  message=error.getMessage();
			  renderMessage=false;
			  return "fail";
		  }
		return status;
	}
	
	@SuppressWarnings("deprecation")
	public String generateTimeSeries() {
		reset();
		  try
		  {
			  if(tableSelected==null)
			  {
				  message="Select table";
				  renderMessage=true;
				  return "FAIL";
			  }
			 
			  FacesContext context = FacesContext.getCurrentInstance();
			  String path = context.getExternalContext().getRealPath("/ChartImages");
			  File dir = new File(path);
			  if(!dir.exists())
			  {
				  new File(path).mkdirs();
			  }
			  
			  if(columnSelected == null)
		  		{
		  			message="Select a column to generate the Chart";
		  			renderMessage=true;
		  			return "FAIL";
		  		}
			  if(ticker=="0"){
				  	message="Select ticker to generate the Chart";
		  			renderMessage=true;
		  			return "FAIL";
			  }
		  		{
		  			/* TimeSeries series = new TimeSeries("Random Data");
		  			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd");
		  			 Date date=null;
		  			 double data=0;
		  			 String query="Select DATE_FORMAT(str_to_date(transactiondate,'%m/%d/%Y'),'%Y/%m/%d') as transactiondate,"+graphColumn+" from gs308_stocktransaction where ticker ='"+ticker+"';";
		  			 ResultSet resultSet = dbAccessBean.selectQueryProcessing(query);
		  			 
		  			 if(resultSet!=null) {	
		  				 while(resultSet.next()) {
			  				 date= resultSet.getDate("transactiondate");
			  				 data=(double) resultSet.getDouble(2);
			  				 series.add(new Day(date.getDate(), date.getMonth(), date.getYear()), data);
		  				 }
		  			 }
		  			 XYDataset xyds = new TimeSeriesCollection(series);
		  			 JFreeChart chart = ChartFactory.createTimeSeriesChart( "Time Series Chart","Date",graphColumn, xyds, true, true, false);
				       File xy = new File(path+"/"+ dbAccessBean.getDbInformationAccessBean().getUsername() +"_timeSeries.png");
				       ChartUtilities.saveChartAsPNG(xy, chart, 600, 450);
				       seriesPath2 = "/ChartImages/"+ dbAccessBean.getDbInformationAccessBean().getUsername() +"_timeSeries.png";
					
				    seriesChart=true;
				  return "SUCCESS";
				     */ 
				    
		  			System.out.println(graphColumn);
		  			
				    TimeSeries series2 = new TimeSeries("Random Data");
				    	 Day current = new Day(1, MonthConstants.JANUARY, 2001);
				    	 for (int i = 0; i < 100; i++) {
				    	 series2.add(current, Math.random() * 100);
				    	 current = (Day) current.next();
				    	 }			    	 
				    	
				    	 XYDataset data2 = new TimeSeriesCollection(series2);
				    	 JFreeChart chart2 = ChartFactory.createTimeSeriesChart(
				    	 "Time Series Chart - " + ticker, "Date", graphColumn,
				    	 data2, true, true, false
				    	 );
				    	 File xy = new File(path+"/"+ dbAccessBean.getDbInformationAccessBean().getUsername() +"_timeSeries.png");
					       ChartUtilities.saveChartAsPNG(xy, chart2, 600, 450);
					       seriesPath2 = "/ChartImages/"+ dbAccessBean.getDbInformationAccessBean().getUsername() +"_timeSeries.png";
						
					    seriesChart=true;
					    return "SUCCESS";
					    
				    	 }
		  		
			  		
		} catch(IOException io) {
			  message=io.getMessage();
			  renderMessage=true;
			  return "fail";
		} catch(Exception error) {
			  message=error.getMessage();
			  System.out.println(message);
			  renderMessage=false;
			  return "fail";
		  }
	}
	
	public String generateHistogram() {
		 reset();
		  try
		  {
			  if(tableSelected==null)
			  {
				  message="Select table";
				  renderMessage=true;
				  return "FAIL";
			  }
			 
			  FacesContext context = FacesContext.getCurrentInstance();
			  String path = context.getExternalContext().getRealPath("/ChartImages");
			  File dir = new File(path);
			  if(!dir.exists())
			  {
				  new File(path).mkdirs();
			  }
			  
			  if(tableSelected == null)
			  {
				  message = "Select a table.";
				  renderMessage=true;
				  return "FAIL";
			  }
			  			 
		  		{ 			
		  		
		  			 HistogramDataset dataset = new HistogramDataset();
					 dataset.setType(HistogramType.RELATIVE_FREQUENCY);
					 double[] predictorArray;
					 
					 //Histogram
					 predictorArray= barcol();
				       dataset.addSeries("Histogram",predictorArray,10);
				       String plotTitle = "Histogram"; 
				       String xaxis = "number";
				       String yaxis = "Frequency"; 
				       PlotOrientation orientation = PlotOrientation.VERTICAL; 
				       JFreeChart chart1 = ChartFactory.createHistogram( plotTitle,predictorValue, yaxis, 
				                dataset, orientation, false, false, false);
				       File xy1 = new File(path+"/"+ dbAccessBean.getDbInformationAccessBean().getUsername() +"_histogram_" + predictorValue +".png");
				       ChartUtilities.saveChartAsPNG(xy1, chart1, 400, 400);
			  			seriesPath1 = "/ChartImages/"+ dbAccessBean.getDbInformationAccessBean().getUsername() +"_histogram_" + predictorValue +".png";
					
				    seriesChart=true;
				    return "SUCCESS";
		  		}
			  		
			  } catch(IOException io) {
				  message=io.getMessage();
				  renderMessage=true;
				  return "fail";
			  } catch(Exception error) {
				  message=error.getMessage();
				  renderMessage=false;
				  return "fail";
			  }
		  }
	
	
	

	// Reset fields
	public String resetButton() 
	{
		renderTabledata = false;
		
		renderReport = false;
		renderMessage = false;
		columnSelected.clear();
		tableList.clear();
		statisticList.clear();
		renderTablename = false;
		columnRender = false;
		renderRegressionButton = true;
		renderColumnListbutton = false;
		renderRegressionColumn = false;
		renderRegressionResult = false;
		System.out.println("Reset button has run");
		return "SUCCESS";

	}

	// Reset messages and table data and Regression results
	public void reset() {
		renderMessage = false;
		renderTabledata = false;
		renderRegressionResult = false;
	}
	
	public boolean isDisableRegressionResult() {
		return !renderRegressionResult;
	}

	public void setDisableRegressionResult(boolean disableRegressionResult) {
		this.disableRegressionResult = !renderRegressionResult;
	}

	public boolean isDisableTabledata() {
		return !renderTabledata;
	}

	public void setDisableTabledata(boolean disableTabledata) {
		this.disableTabledata = !renderTabledata;
	}
	

	// Getters and setters

	public String getTableSelected() {
		return tableSelected;
	}

	public void setTableSelected(String tableSelected) {
		this.tableSelected = tableSelected;
	}

	public List<String> getColumnSelected() {
		return columnSelected;
	}

	public void setColumnSelected(List<String> columnSelected) {
		this.columnSelected = columnSelected;
	}

	public List<String> getColumnsList() {
		return columnsList;
	}

	public void setColumnsList(List<String> columnsList) {
		this.columnsList = columnsList;
	}

	public boolean isColumnRender() {
		return columnRender;
	}

	public void setColumnRender(boolean columnRender) {
		this.columnRender = columnRender;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public boolean isRenderTablename() {
		return renderTablename;
	}

	public void setRenderTablename(boolean renderTablename) {
		this.renderTablename = renderTablename;
	}

	public ResultSetMetaData getResultSetMetaData() {
		return resultSetMetaData;
	}

	public void setResultSetMetaData(ResultSetMetaData resultSetMetaData) {
		this.resultSetMetaData = resultSetMetaData;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public List<String> getTableList() {
		return tableList;
	}

	public void setTableList(List<String> tableList) {
		this.tableList = tableList;
	}

	public DatabaseMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(DatabaseMetaData metaData) {
		this.metaData = metaData;
	}

	public boolean isRenderSchema() {
		return renderSchema;
	}

	public void setRenderSchema(boolean renderSchema) {
		this.renderSchema = renderSchema;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isRenderMessage() {
		return renderMessage;
	}

	public void setRenderMessage(boolean renderMessage) {
		this.renderMessage = renderMessage;
	}

	public DbAccessBean getDbAccessBean() {
		return dbAccessBean;
	}

	public void setDbAccessBean(DbAccessBean dbAccessBean) {
		this.dbAccessBean = dbAccessBean;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public int getRowsAffected() {
		return rowsAffected;
	}

	public void setRowsAffected(int rowsAffected) {
		this.rowsAffected = rowsAffected;
	}

	public List<StatsManagedBean> getStatisticList() {
		return statisticList;
	}

	public void setStatisticList(List<StatsManagedBean> statisticList) {
		this.statisticList = statisticList;
	}

	public boolean isRenderTabledata() {
		return renderTabledata;
	}

	public void setRenderTabledata(boolean renderTabledata) {
		this.renderTabledata = renderTabledata;
	}

	public StatsManagedBean getStatisticManagedBean() {
		return statsManagedBean;
	}

	public void setStatsManagedBean(StatsManagedBean statsManagedBean) {
		this.statsManagedBean = statsManagedBean;
	}

	public boolean isRenderRegressionColumn() {
		return renderRegressionColumn;
	}

	public void setRenderRegressionColumn(boolean renderRegressionColumn) {
		this.renderRegressionColumn = renderRegressionColumn;
	}

	public boolean isRenderColumnListbutton() {
		return renderColumnListbutton;
	}

	public void setRenderColumnListbutton(boolean renderColumnListbutton) {
		this.renderColumnListbutton = renderColumnListbutton;
	}

	public boolean isRenderRegressionButton() {
		return renderRegressionButton;
	}

	public void setRenderRegressionButton(boolean renderRegressionButton) {
		this.renderRegressionButton = renderRegressionButton;
	}

	public boolean isDisableButton() {
		return disableButton;
	}

	public void setDisableButton(boolean disableButton) {
		this.disableButton = disableButton;
	}

	public List<String> getCategoricalData() {
		return categoricalData;
	}

	public void setCategoricalData(List<String> categoricalData) {
		this.categoricalData = categoricalData;
	}

	public List<String> getNumericData() {
		return numericData;
	}

	public void setNumericData(List<String> numericData) {
		this.numericData = numericData;
	}

	public String getPredictorValue() {
		return predictorValue;
	}

	public void setPredictorValue(String predictorValue) {
		this.predictorValue = predictorValue;
	}

	public String getResponseValue() {
		return responseValue;
	}
	
	public void setPredictorTicker(String predictorTicker) {
		this.predictorTicker = predictorTicker;
	}

	public String getPredictorTicker() {
		return predictorTicker;
	}
	
	public void setResponseTicker(String responseTicker) {
		this.responseTicker = responseTicker;
	}

	public String getResponseTicker() {
		return responseTicker;
	}

	public void setResponseValue(String responseValue) {
		this.responseValue = responseValue;
	}

	public boolean isRenderReport() {
		return renderReport;
	}

	public void setRenderReport(boolean renderReport) {
		this.renderReport = renderReport;
	}

	public boolean isRenderRegressionResult() {
		return renderRegressionResult;
	}

	public void setRenderRegressionResult(boolean renderRegressionResult) {
		this.renderRegressionResult = renderRegressionResult;
	}

	public MathManagedBean getMathManagedBean() {
		return mathManagedBean;
	}

	public void setMathManagedBean(MathManagedBean mathManagedBean) {
		this.mathManagedBean = mathManagedBean;
	}

	public StatsManagedBean getStatsManagedBean() {
		return statsManagedBean;
	}

	public double getMedianOne() {
		return medianOne;
	}

	public void setMedianOne(double medianOne) {
		this.medianOne = medianOne;
	}

	public double getQuartileOne() {
		return quartileOne;
	}

	public void setQuartileOne(double quartileOne) {
		this.quartileOne = quartileOne;
	}

	public double getQuartileThree() {
		return quartileThree;
	}

	public void setQuartileThree(double quartileThree) {
		this.quartileThree = quartileThree;
	}

	public XYSeriesCollection getXySeriesVar() {
		return xySeriesVar;
	}

	public void setXySeriesVar(XYSeriesCollection xySeriesVar) {
		this.xySeriesVar = xySeriesVar;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public XYSeriesCollection getXyTimeSeriesCol() {
		return xyTimeSeriesCol;
	}

	public void setXyTimeSeriesCol(XYSeriesCollection xyTimeSeriesCol) {
		this.xyTimeSeriesCol = xyTimeSeriesCol;
	}

	public XYSeriesCollection getXySeriesVars() {
		return xySeriesVars;
	}

	public void setXySeriesVars(XYSeriesCollection xySeriesVars) {
		this.xySeriesVars = xySeriesVars;
	}

	public XYSeries getXySeries() {
		return xySeries;
	}

	public void setXySeries(XYSeries xySeries) {
		this.xySeries = xySeries;
	}

	public XYSeries getPredSeries() {
		return predSeries;
	}

	public void setPredSeries(XYSeries predSeries) {
		this.predSeries = predSeries;
	}

	public XYSeries getResSeries() {
		return resSeries;
	}

	public void setResSeries(XYSeries resSeries) {
		this.resSeries = resSeries;
	}

	public Export getExport() {
		return export;
	}

	public void setExport(Export export) {
		this.export = export;
	}
	
	public DbInformationAccessBean getDbInformationAccessBean() {
		return dbInformationAccessBean;
	}

	public void setDbInformationAccessBean(DbInformationAccessBean dbInformationAccessBean) {
		this.dbInformationAccessBean = dbInformationAccessBean;
	}
	
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	public String getGraphType() {
		return graphType;
	}

	public void setGraphType(String graphType) {
		this.graphType = graphType;
	}
	
	public String getGraphColumn() {
		return graphColumn;
	}

	public void setGraphColumn(String graphColumn) {
		this.graphColumn = graphColumn;
	}
	
	public String getSeriesPath2() {
		return seriesPath2;
	}

	public void setSeriesPath2(String seriesPath2) {
		this.seriesPath2 = seriesPath2;
	}

		
}
