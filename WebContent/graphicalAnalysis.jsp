<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
<a href="dbAccess.jsp" >Database Access</a> &nbsp;&nbsp;&nbsp;&nbsp;
<a href="uploadFile.jsp" >File Upload</a> &nbsp;&nbsp;&nbsp;&nbsp;
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
						
							<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="Tables" action="#{statsBean.getTables}" styleClass="button" />
							<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="Columns for Stats" action="#{statsBean.getColumnNames}" styleClass="button" disabled="#{statsBean.renderColumnListbutton}" />
							<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="Descriptive Stats" action="#{statsBean.generateReport}" styleClass="button" disabled="#{statsBean.renderReport}" />
							<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="Columns for regression" action="#{statsBean.displayColumnsforRegression}" styleClass="button" />												
							<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="Regression Report" action="#{statsBean.generateRegressionReport}" styleClass="button" disabled="#{statsBean.renderRegressionButton}" />
							<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="Reset" action="#{statsBean.resetButton}" styleClass="button" />
							<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="GenerateGraph" action="#{statsBean.generateChart}"  disabled="#{statsBean.disableRegressionResult}"  styleClass="button" />
 							
					</h:panelGrid>
					</div>
						<div class="right-div">
					
						
						<br />
						
					<h:outputText value="#{statsBean.message}"
								rendered="#{statsBean.renderMessage}" style="color:red" />
					<h:outputText value="#{statsBean.tableSelected}" style="display:none"/> 
						<panelGrid columns="4">
							<br/><br/>	
								<h:selectOneListbox
								size="14" styleClass="selectOneListbox_mono"
								value="#{statsBean.tableSelected}">
								<f:selectItems value="#{statsBean.tableList}" />
								</h:selectOneListbox> 
								&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 
							<h:selectManyListbox id="selectcolumns"
								style="width:150px; height:100px"
								value="#{statsBean.columnSelected}"
								rendered="#{statsBean.columnRender}" size="5">
								<f:selectItems value="#{statsBean.columnsList}" />
							</h:selectManyListbox>
							<h:selectOneListbox value="#{statsBean.ticker}" size="1">
							<f:selectItem itemValue="CSCO" itemLabel="CSCO"/>
							<f:selectItem itemValue="ORCL" itemLabel="ORCL"/>
							</h:selectOneListbox>
							<h:selectOneListbox id="predictor"
								value="#{statsBean.predictorValue}"
								rendered="#{statsBean.renderRegressionColumn}" size="5">
								<f:selectItem itemValue="0" itemLabel="Select Predictor Value" />
								<f:selectItems value="#{statsBean.numericData}" />
							</h:selectOneListbox>
							&nbsp; &nbsp;&nbsp;&nbsp;
							<h:selectOneListbox id="response"
								value="#{statsBean.responseValue}"
								rendered="#{statsBean.renderRegressionColumn}" size="5">
								<f:selectItem itemValue="0" itemLabel="Select Response Value" />
								<f:selectItems value="#{statsBean.numericData}" />
							</h:selectOneListbox>

						</panelGrid>
					</div>
					<div class="bottom" style="background-color: white; padding-left: 20px; padding-right: 20px; padding-top: 20px;">
						<div
							style="background-attachment: scroll; overflow: auto; background-color:white; "
							align="center">
							<t:dataTable value="#{statsBean.statisticList}"
								var="rowNumber" rendered="#{statsBean.renderTabledata}"
								border="1" cellspacing="0" cellpadding="1"
								headerClass="headerWidth">
								<h:column>
									<f:facet name="header">
										<h:outputText value="Column Selected" />
									</f:facet>
									<h:outputText value="#{rowNumber.columnSelected}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Minimum Value" />
									</f:facet>
									<h:outputText value="#{rowNumber.minimumValue}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Maximum Value" />
									</f:facet>
									<h:outputText value="#{rowNumber.maximumValue}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Mean" />
									</f:facet>
									<h:outputText value="#{rowNumber.mean}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Variance" />
									</f:facet>
									<h:outputText value="#{rowNumber.variance}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Standard Deviation" />
									</f:facet>
									<h:outputText value="#{rowNumber.standardDeviation}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="quartileOne" />
									</f:facet>
									<h:outputText value="#{rowNumber.quartileOne}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="quartileThree" />
									</f:facet>
									<h:outputText value="#{rowNumber.quartileThree}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="Range" />
									</f:facet>
									<h:outputText value="#{rowNumber.range}" />
								</h:column>
								<h:column>
									<f:facet name="header">
										<h:outputText value="interquartileRange" />
									</f:facet>
									<h:outputText value="#{rowNumber.interquartileRange}" />
								</h:column>
							</t:dataTable>
						</div>
						<br />
					</div>
					<div class="bottom">
						<h:outputText value="Regression Statement: " rendered="#{statsBean.renderRegressionResult}">
						</h:outputText>
						&#160;
						<h:outputText value="#{mathManagedBean.regEquation}" rendered="#{statsBean.renderRegressionResult}">
						</h:outputText>
						<br /> <br />
						<h:outputText value="Regression Model" rendered="#{statsBean.renderRegressionResult}"></h:outputText>
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
					<div class="right-div2">
						
						<h:graphicImage value="#{statsBean.seriesPath}" width="600"
							height="600" rendered="#{statsBean.seriesChart}" />
						<h:graphicImage value="#{statsBean.seriesPath1}" width="600"
							height="600" rendered="#{statsBean.seriesChart}" />
						
						<br />
					</div>
				</h:form>
				
				<div class="footer">
					
				</div>
	
</f:view>
</body>
</html>