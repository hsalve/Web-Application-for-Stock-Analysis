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
<h3 align = "center">DATABASE ACCESS</h3> 
<br>

<center>
<a href="Main.jsp">Home</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="uploadFile.jsp" >File Upload</a> &nbsp;&nbsp;&nbsp;&nbsp;
<a href="analysis.jsp" >Descriptive Statistics</a> &nbsp;&nbsp;&nbsp;&nbsp;
<a href="graphs.jsp" >Graphs</a> &nbsp;&nbsp;&nbsp;&nbsp;
<a href="regression.jsp" >Regression Analysis</a> &nbsp;&nbsp;&nbsp;&nbsp;
<a href="index.jsp"style = "float:left">Logout</a>
<br>
</center>
<hr>
</div>

<h:form>

&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
<label><b>Table List</b></label>  &nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; 
<label><b>Column List</b></label> &nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; 
<label><b>SQL Query</b></label>

 
<br/><br/>	
&nbsp; &nbsp;&nbsp;
<h:selectOneListbox
size="14" styleClass="selectOneListbox_mono"
value="#{dbSchemaBean.tableName}">
<f:selectItems value="#{dbAccessBean.tableNames}" />
</h:selectOneListbox>
&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 

<h:selectManyListbox
size="14" styleClass="selectManyListbox"
value="#{dbSchemaBean.columnNamesSelected}"
rendered="#{dbSchemaBean.columnListRendered}"> 
<f:selectItems value="#{dbSchemaBean.columnNames}" />
</h:selectManyListbox> 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp;

<h:inputTextarea value= "#{dbSchemaBean.enteredSQL}" cols="100" rows="14" />
&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp;
&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp;
&nbsp;  


<br />
<br />
&nbsp; <h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" type="submit" value="List of Table" action="#{dbAccessBean.getAllTableNames}"/>
&nbsp; <h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" type="submit" value="List of Columns" action="#{dbSchemaBean.showColumns}"/>
&nbsp; <h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" type="submit" value="View Table" action="#{dbSchemaBean.tableResult}" />
&nbsp; <h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" type="submit" value="Display Selected Columns" action="#{dbSchemaBean.columnResult}" />
&nbsp; <h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" type="submit" value="Process SQL Query" action="#{dbSchemaBean.runSql}" />
&nbsp; <h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" type="submit" value="Get Tickers" action="#{dbSchemaBean.distinctTickers}" />
&nbsp; <h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" type="submit" value="Create Table" action="#{dbSchemaBean.CreateTable}" />
&nbsp; <h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" type="submit" value="Drop Table" action="#{dbSchemaBean.DropTable}" />
&nbsp; <h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" type="submit" value="Compute Return" action="#{dbSchemaBean.ComputeReturn}" />
<br/><br/>
<center>

<h:selectOneListbox
styleClass="selectOneListbox_mono"
value="#{dbSchemaBean.ticker}" size="1">
<f:selectItems value="#{dbSchemaBean.tickers}"/>
</h:selectOneListbox>
&nbsp; &nbsp; 

<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" type="submit" value="Filter Ticker" action="#{dbSchemaBean.FilterDataTicker}" />
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<label><b>Sort: </b></label>
&nbsp; &nbsp; 
<h:selectOneListbox value="#{dbSchemaBean.sortby}" size="1">
<f:selectItem itemValue="ASC" itemLabel="Ticker ascending"/>
<f:selectItem itemValue="DESC" itemLabel="Ticker descending"/>
</h:selectOneListbox>
&nbsp; &nbsp; 
<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" type="submit" value="Apply" action="#{dbSchemaBean.SortData}" />


&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<label><b>Sort by date: </b></label>
&nbsp; &nbsp; 
<h:selectOneListbox value="#{dbSchemaBean.sortbydate}" size="1">
<f:selectItem itemValue="ASC" itemLabel="Date ascending"/>
<f:selectItem itemValue="DESC" itemLabel="Date descending"/>
</h:selectOneListbox>

&nbsp; &nbsp; 
<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" type="submit" value="Apply" action="#{dbSchemaBean.SortDataDate}" />

&nbsp; &nbsp; 
<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" type="submit" value="Filter by Date" action="#{dbSchemaBean.FilterDataDate}" />

<script>

$(document).ready(
function() { 
$(".datepicker").datepicker({ 
showOn:"button",
dateFormat:'yy/mm/dd',
showOtherMonths: true, 
selectOtherMonths: true, 
changeYear: true, 
showButtonPanel: true,
buttonImageOnly: true 
});

// to display 'select date' title on icton of calender image (ie Calc.png)
$('.ui-datepicker-trigger').attr('alt', 'Select Date').attr('title', 'Select Date');
});
</script>
<br/><br/><br/>
<h:inputText id="dateFrom" value="#{dbSchemaBean.dateFrom}" styleClass="datepicker" />
<h:inputText id="dateTo" value="#{dbSchemaBean.dateTo}" styleClass="datepicker" />

</center>


&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<br /><br />

<br />
<br />

</h:form>
<div style="background-attachment: scroll; overflow:auto; height:380px; background-color: #F5F5F5; background-repeat: repeat">
<br> &nbsp; &nbsp; &nbsp;

<h:outputText value="#{messageBean.processSql}"
rendered = "#{messageBean.processSqlRender }" />
<br/><br/>
<center>
<div style="background: white;">
<t:dataTable
value="#{dbSchemaBean.result}"
var="row"
rendered="#{dbSchemaBean.renderSet}"
border="1" cellspacing="0" cellpadding="1"
columnClasses="columnClass1 border"
headerClass="headerClass"
footerClass="footerClass"
rowClasses="rowClass2"
styleClass="dataTableEx"
width="900">
<t:columns
var="col"
value="#{dbSchemaBean.columnHeaders}">
<f:facet name="header">
<t:outputText
styleClass="outputHeader"
value="#{col}" />
</f:facet>
<center>
<t:outputText
styleClass="outputText"
value="#{row[col]}" />
</center>
</t:columns>
</t:dataTable>
</div>
</center>
</div>

<br/>

</f:view>
</body>
</html>