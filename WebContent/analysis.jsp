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
<h3 align = "center">DESCRIPTIVE STATISTICS</h3> 
<br>

<center>
<a href="Main.jsp">Home</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="dbAccess.jsp">Database Access</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="uploadFile.jsp" >File Upload</a> &nbsp;&nbsp;&nbsp;&nbsp;
<a href="graphs.jsp" >Graphs</a> &nbsp;&nbsp;&nbsp;&nbsp;
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
							<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="Descriptive Stats" action="#{statsBean.generateReport}" styleClass="button"  />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" value="Reset" action="#{statsBean.resetButton}" styleClass="button" />
							
					</h:panelGrid>
					<p align="center">
					<h:outputText value="#{statsBean.message}"
								rendered="#{statsBean.renderMessage}" style="color:red" />
					</p>
					</div>
						<div class="right-div">
					
						
						<br />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp;
						<panelGrid columns="4">
							<h:selectOneListbox id="selectOneCb" size="14" styleClass="selectOneListbox_mono"								
								value="#{statsBean.tableSelected}"
								rendered="#{statsBean.renderTablename}">
								<f:selectItems value="#{statsBean.tableList}" />
							</h:selectOneListbox>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<h:selectManyListbox id="selectcolumns"	size="14" styleClass="selectOneListbox_mono"							
								value="#{statsBean.columnSelected}"
								rendered="#{statsBean.columnRender}" >
								<f:selectItems value="#{statsBean.columnsList}" />
							</h:selectManyListbox>
							
						</panelGrid>
					</div>
					<br/>
					<br/>
					<div class="bottom">
						<div
							style="background: white;background-attachment: scroll; overflow: auto; background-repeat: repeat"
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
										<h:outputText value="Median" />
									</f:facet>
									<h:outputText value="#{rowNumber.median}" />
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
						
						<div class="right-div2" align="center">
																		
					</div>
					
				</h:form>
				
				<div class="footer">
					
				</div>
			
<br/>

</f:view>
</body>
</html>