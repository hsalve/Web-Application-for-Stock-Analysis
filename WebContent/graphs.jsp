<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src=https://code.jquery.com/jquery-1.12.4.js></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DATABASE MENU</title>
<link rel="stylesheet" href="style.css">
</head>
<body id="dbaccess">
<f:view>
<div id="DBHeading">
<br/>
<h3 align = "center">GRAPHS</h3> 
<br>

<center>
<a href="Main.jsp">Home</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="dbAccess.jsp">Database Access</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="uploadFile.jsp" >File Upload</a> &nbsp;&nbsp;&nbsp;&nbsp;
<a href="analysis.jsp" >Descriptive Statistics</a> &nbsp;&nbsp;&nbsp;&nbsp;
<a href="regression.jsp" >Regression Analysis</a> &nbsp;&nbsp;&nbsp;&nbsp;
<a href="index.jsp"style = "float:left">Logout</a>
<br>
</center>
<hr>
</div>
		<h:form>
				<br />
				<br />
				<div class="left-div">
					<h:panelGrid columns="8">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp;
						<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="Tables" action="#{statsBean.getTables}" styleClass="button" />
						&nbsp;&nbsp;&nbsp;&nbsp;
						<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="Columns for Stats" action="#{statsBean.getColumnNames}" styleClass="button"  />
						&nbsp;&nbsp;&nbsp;&nbsp;
						<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="Reset" action="#{statsBean.resetButton}" styleClass="button" />
						&nbsp;&nbsp;&nbsp;&nbsp;
						<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="GenerateGraph" action="#{statsBean.graphSelection}"  styleClass="button" />
				</h:panelGrid>
				<p align="center">
				<h:outputText value="#{statsBean.message}"
							rendered="#{statsBean.renderMessage}" style="color:red" />
				</p>
				</div>
					<div class="right-div">
				
					
					<br />
					 
					<panelGrid columns="4">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp;
						<h:selectOneListbox id="selectOneCb" size="14" styleClass="selectOneListbox_mono"								
								value="#{statsBean.tableSelected}"
								rendered="#{statsBean.renderTablename}">
								<f:selectItems value="#{statsBean.tableList}" />
							</h:selectOneListbox>
							&nbsp;&nbsp;&nbsp;&nbsp;
						<h:selectOneListbox id="graphType"
							value="#{statsBean.graphType}" 
							rendered="#{statsBean.columnRender}" size="5">
							<f:selectItem itemValue="0" itemLabel="Select Graph" />
							<f:selectItem itemValue="histogram" itemLabel="Histogram" />
							<f:selectItem itemValue="timeSeries" itemLabel="Time Series" />
						</h:selectOneListbox>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<h:selectOneListbox id="selectcolumnsgraph" size="14" styleClass="selectOneListbox_mono"
							value="#{statsBean.graphColumn}"
							rendered="#{statsBean.columnRender}">
							<f:selectItems value="#{statsBean.columns}" />
						</h:selectOneListbox>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<h:selectOneListbox id="ticker"
							value="#{statsBean.ticker}" 
							rendered="#{statsBean.columnRender}" size="5">
							<f:selectItem itemValue="0" itemLabel="Select Ticker" />
							<f:selectItems value="#{dbSchemaBean.tickers}" />
						</h:selectOneListbox>
					</panelGrid>
				</div>
				<br/>
				<br/>
					<div class="right-div2" align="center">
					<h:graphicImage value="#{statsBean.seriesPath1}" width="600"
						height="600" rendered="#{statsBean.seriesChart}" />
					
					<h:graphicImage value="#{statsBean.seriesPath2}" width="600"
						height="600" rendered="#{statsBean.seriesChart}" />
					
					<br />
					</div>
				</h:form>
			
<br/>

</f:view>
</body>
</html>