package edu.uic.ids517.model;

import java.sql.ResultSet;

import javax.servlet.jsp.jstl.sql.Result;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.date.MonthConstants;

import java.sql.ResultSetMetaData;

public class Reports {
	   private DbInformationAccessBean dbInformationAccessBean;
	   private JFreeChart pieChart;
	   private Result result;
	   private DbAccessBean dbAccessBean;
	   private ResultSet resultSet;
	   private DefaultPieDataset pieModel;
	   private ResultSetMetaData resultSetMetaData;
	   private String errorMessage;
	   private boolean renderErrorMessage;
	   private boolean renderPieChart;
	   private XYDataset data ;
	   private DefaultCategoryDataset dataset ;
	   private StatsManagedBean statsManagedBean;
	 
	   public Reports()
	   {
		   pieModel=new DefaultPieDataset();
		   dataset = new DefaultCategoryDataset();
	   }	  
		
		public DbAccessBean getDbAccessBean() {
			return dbAccessBean;
		}

		public void setDbAccessBean(DbAccessBean dbAccessBean) {
			this.dbAccessBean = dbAccessBean;
		}

		public DefaultPieDataset getPieModel() {
			return pieModel;
		}

		public void setPieModel(DefaultPieDataset pieModel) {
			this.pieModel = pieModel;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public ResultSet getResultSet() {
			return resultSet;
		}

		public void setResultSet(ResultSet resultSet) {
			this.resultSet = resultSet;
		}
		
		public JFreeChart getPieChart() {
			return pieChart;
		}

		public void setPieChart(JFreeChart pieChart) {
			this.pieChart = pieChart;
		}
		
		public ResultSetMetaData getResultSetMetaData() {
			return resultSetMetaData;
		}

		public void setResultSetMetaData(ResultSetMetaData resultSetMetaData) {
			this.resultSetMetaData = resultSetMetaData;
		}

		public boolean isRenderErrorMessage() {
			return renderErrorMessage;
		}

		public void setRenderErrorMessage(boolean renderErrorMessage) {
			this.renderErrorMessage = renderErrorMessage;
		}

		public boolean isRenderPieChart() {
			return renderPieChart;
		}

		public void setRenderPieChart(boolean renderPieChart) {
			this.renderPieChart = renderPieChart;
		}
		
		public XYDataset getData() {
			return data;
		}

		public void setData(XYDataset data) {
			this.data = data;
		}
		 
		public DefaultCategoryDataset getDataset() {
				return dataset;
		}

		public void setDataset(DefaultCategoryDataset dataset) {
			this.dataset = dataset;
		}

		public StatsManagedBean getStatsManagedBean() {
			return statsManagedBean;
		}

		public void setStatsManagedBean(StatsManagedBean statsManagedBean) {
			this.statsManagedBean = statsManagedBean;
		}

		public Result getResult() {
			return result;
		}

		public void setResult(Result result) {
			this.result = result;
		}

		public DbInformationAccessBean getDbInformationAccessBean() {
			return dbInformationAccessBean;
		}

		public void setDbInformationAccessBean(DbInformationAccessBean dbInformationAccessBean) {
			this.dbInformationAccessBean = dbInformationAccessBean;
		}

}

