<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Database Login</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
<f:view>

<div id="DBHeading">
<br/>
<h3 align = "center">IDS Group - 308</h3> 
<br>

<center>
<a href="index.jsp">Home</a>&nbsp;&nbsp;&nbsp;&nbsp;
<br>
</center>
<hr>
</div>  

<br />
<center> <a href="index.jsp">Home</a> </center>
<br />  <br />
<center>
<h:form>
<br><br>
<h:panelGrid columns="2">
<h:outputText value="User Name -" />
<h:inputText id="userName" value="#{dbInformationAccessBean.username}" />
<h:outputText value="Password -" />
<h:inputSecret id="password" value="#{dbInformationAccessBean.password}" />
<h:outputText value="Host -" />
<h:selectOneListbox value="#{dbInformationAccessBean.dbmsHost}" size="1">
<f:selectItem itemValue="localhost"/>
<f:selectItem itemValue="131.193.209.68" itemLabel="131.193.209.68"/>
<f:selectItem itemValue="131.193.209.69" itemLabel="131.193.209.69"/>
</h:selectOneListbox>
<h:outputText value="DBMS -" />
<h:selectOneListbox value="#{dbInformationAccessBean.dbms}" size="1">
<f:selectItem itemValue="MySQL"/>
<f:selectItem itemValue="Oracle"/>
<f:selectItem itemValue="DB2"/>
</h:selectOneListbox>
<h:outputText value="Database Schema -" />
<h:inputText id="databaseSchema" value="#{dbInformationAccessBean.dbSchema}" />

<br />
<br />
<br/> <br /> <br/>
<h:commandButton style="border-radius:4px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" type="submit" value="Login" action="#{dbAccessBean.getConnection}" />

</h:panelGrid>
</h:form>
</center>
<h:outputText value="#{messageBean.error }"
rendered = "#{messageBean.errorRender }" />

</f:view>
</body>
</html>