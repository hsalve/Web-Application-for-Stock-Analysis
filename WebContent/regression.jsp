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
<h3 align = "center">REGRESSION ANALYSIS</h3> 
<br>

<center>
<a href="Main.jsp">Home</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="dbAccess.jsp">Database Access</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="uploadFile.jsp" >File Upload</a> &nbsp;&nbsp;&nbsp;&nbsp;
<a href="analysis.jsp" >Descriptive Statistics</a> &nbsp;&nbsp;&nbsp;&nbsp;
<a href="graphs.jsp" >Graphs</a> &nbsp;&nbsp;&nbsp;&nbsp;
<a href="index.jsp"style = "float:left">Logout</a>
<br>
</center>
<hr>
</div>

			<h:form>
					<br />
					<br />
					<div class="center-div" align="center">
						<h:panelGrid columns="8">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp;
							<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="Tables" action="#{statsBean.getTables}" styleClass="button" />
							<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="Columns for regression" action="#{statsBean.displayColumnsforRegression}" styleClass="button" />												
							<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="Regression Report" action="#{statsBean.generateRegressionReport}" styleClass="button" disabled="#{statsBean.renderRegressionButton}" />
							<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="Reset" action="#{statsBean.resetButton}" styleClass="button" />
							<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="GenerateGraph" action="#{statsBean.generateChart}"  disabled="#{statsBean.disableRegressionResult}"  styleClass="button" />
					</h:panelGrid>
					<p align="center">
					<h:outputText value="#{statsBean.message}"
								rendered="#{statsBean.renderMessage}" style="color:red" />
					</p>
					</div>
						<div class="right-div" align="center">
						<br />
						<panelGrid columns="4">
							<h:selectOneListbox id="selectOneCb" size="14" styleClass="selectOneListbox_mono"								
							value="#{statsBean.tableSelected}"
								rendered="#{statsBean.renderTablename}">
								<f:selectItems value="#{statsBean.tableList}" />
							</h:selectOneListbox>
							&nbsp; &nbsp;&nbsp;&nbsp;
							<h:selectOneListbox id="predictor" size="14" styleClass="selectOneListbox_mono"
								value="#{statsBean.predictorValue}"
								rendered="#{statsBean.renderRegressionColumn}">
								<f:selectItem itemValue="0" itemLabel="Select Predictor Value" />
								<f:selectItems value="#{statsBean.numericData}" />
							</h:selectOneListbox>
							
							&nbsp; &nbsp;&nbsp;&nbsp;
							<h:selectOneListbox id="response" size="14" styleClass="selectOneListbox_mono"
								value="#{statsBean.responseValue}"
								rendered="#{statsBean.renderRegressionColumn}">
								<f:selectItem itemValue="0" itemLabel="Select Response Value" />
								<f:selectItems value="#{statsBean.numericData}" />
							</h:selectOneListbox>
							&nbsp; &nbsp;&nbsp;&nbsp;
							
							<h:selectOneListbox id="predictorTicker" size="14" styleClass="selectOneListbox_mono"
								value="#{statsBean.predictorTicker}"
								rendered="#{statsBean.renderRegressionColumn}">
								<f:selectItem itemValue="0" itemLabel="Select Predictor Ticker" />
								<f:selectItems value="#{dbSchemaBean.tickers}" />
							</h:selectOneListbox>
							&nbsp; &nbsp;&nbsp;&nbsp;
							<h:selectOneListbox id="responseTicker"
								value="#{statsBean.responseTicker}"
								rendered="#{statsBean.renderRegressionColumn}" size="5">
								<f:selectItem itemValue="0" itemLabel="Select Response Ticker" />
								<f:selectItems value="#{dbSchemaBean.tickers}" />
							</h:selectOneListbox>

						</panelGrid>
					</div>
					<br/>
					<br/>
					<div class="bottom" align="center" style="background: white;">
						
						<h:panelGrid columns="5" rendered="#{statsBean.renderRegressionResult}" border="1">
						
							<h:outputText value="Predictor" />
							
							
							<h:outputText value="Co-efficient" />
							
							<h:outputText value="Standard Error Co-efficient" />
							
							<h:outputText value="T-Statistic" />
						
							<h:outputText value="P-Value" />
						
							<h:outputText value="Constant" />
							
							<h:outputText value="#{mathManagedBean.intercept}" />
							<h:outputText value="#{mathManagedBean.interceptStdErr}" />
							<h:outputText value="#{mathManagedBean.tStatistic }" />
							<h:outputText value="#{mathManagedBean.interceptPValue }" />
							<h:outputText value="#{statsBean.predictorValue}" />
							<h:outputText value="#{mathManagedBean.slope}" />
							<h:outputText value="#{mathManagedBean.slopeStdErr}" />
							<h:outputText value="#{mathManagedBean.tStatPred }" />
							<h:outputText value="#{mathManagedBean.pValuePred }" />
						</h:panelGrid>
						<br /> <br />
						<h:panelGrid columns="2" rendered="#{statsBean.renderRegressionResult}" border="1">
							<h:outputText value="Model of Standard Error" />
							<h:outputText value="#{mathManagedBean.stdErrorM}" />
							<h:outputText value="R Square" />
							<h:outputText value="#{mathManagedBean.rSquare}" />
							<h:outputText value=" Adjusted R Square" />
							<h:outputText value="#{mathManagedBean.rSquareAdj}" />
						</h:panelGrid>
						<br /> <br />
						<h:outputText value="Analysis of Variance" rendered="#{statsBean.renderRegressionResult}" />
						<br />
						<h:panelGrid columns="6" rendered="#{statsBean.renderRegressionResult}" border="1">
							<h:outputText value="Source" />
							<h:outputText value="Degrees of Freedom" />
							<h:outputText value="Sum of Squares" />
							<h:outputText value="Mean of Squares" />
							<h:outputText value="F-Statistic" />
							<h:outputText value="P-Value" />
							<h:outputText value="Regression" />
							<h:outputText value="#{mathManagedBean.predictorDegreesFreedom}" />
							<h:outputText value="#{mathManagedBean.regSumSquares}" />
							<h:outputText value="#{mathManagedBean.meanSquare }" />
							<h:outputText value="#{mathManagedBean.fValue }" />
							<h:outputText value="#{mathManagedBean.pValue}" />
							<h:outputText value="Residual Error" />
							<h:outputText value="#{mathManagedBean.residualErrorDegreesFreedom}" />
							<h:outputText value="#{mathManagedBean.sumSquaredErr }" />
							<h:outputText value="#{mathManagedBean.meanSquareErr }" />
							<h:outputText value="" />
							<h:outputText value="" />
							<h:outputText value="Total" />
							<h:outputText value="#{mathManagedBean.totalDegreesFreedom}" />
							<h:outputText value="#{mathManagedBean.totalSumSquares}" />
							
						</h:panelGrid>
						
					</div>
					<br/><br/>
					<div class="right-div2" align="center">
						
						<h:graphicImage value="#{statsBean.seriesPath}" width="600"
							height="600" rendered="#{statsBean.seriesChart}" />
						
						<br />
					</div>
				</h:form>
					
</f:view>
</body>
</html>