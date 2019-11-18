<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"
xmlns:h="http://java.sun.com/jsf/html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload File</title>

<link rel="stylesheet" href="style.css">
</head>
<body>
<f:view>
<h:form enctype="multipart/form-data">
<div id="DBHeading">
<br/>
<h3 align = "center">FILE &nbsp;UPLOAD</h3> 
<br>

<center>
<a href="Main.jsp">Home</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="dbAccess.jsp">Database Access</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="analysis.jsp" >Descriptive Statistics</a> &nbsp;&nbsp;&nbsp;&nbsp;
<a href="graphs.jsp" >Graphs</a> &nbsp;&nbsp;&nbsp;&nbsp;
<a href="regression.jsp" >Regression Analysis</a> &nbsp;&nbsp;&nbsp;&nbsp;
<a href="index.jsp"style = "float:left">Logout</a>
<br>
</center><br />
</div>
<br /><br /><br /><br />
<center>

			<h:panelGrid columns="2">

				<h:outputLabel value="Upload File:" />
				<t:inputFileUpload id="fileUpload" label="File to upload"
					storage="default" value="#{uploadBean.uploadedFile}"
					required="true" size="60" requiredMessage="Please select a file" />


					<h:outputText value="File type:"></h:outputText>
					<h:selectOneMenu value="#{uploadBean.fileFormat}">
						<f:selectItems value="#{uploadBean.fileFormatList}" />
					</h:selectOneMenu>


				<center>
				<br /><br />
				<h:commandButton style="border-radius:4px;margin-top:15px;border:none;color:white;padding:15px;font-weight:bold;text-align:center;background-color:black;" 
				id="Import" action="#{uploadBean.processFileImport}" value="Upload" />
				</center>
			</h:panelGrid>
		
			
			<div align="center" style="background-color:black;font-weight:bolder;color:white;">
				<br />
				<h:outputText style="color:cyan;" value="#{uploadBean.errorMessage}"
					rendered="#{uploadBean.messageRendered}" />
				<br /><br />
				<div>
				<h:outputLabel value="File Details" />
				<br />
				<h:message for="fileUpload" style="color:green; font-weight:bold;"
					errorClass="errorMessage" rendered="#{uploadBean.messageRendered}" />
				<br />
				
				<br />
				</div>
			</div>		
	
</h:form>

<div id="container3" align="center" style="background-color:black;color:white;font-weight:bolder;">
		<h:form enctype="multipart/form-data">
			<h:panelGrid>

				<h:outputLabel value="File Name:" />
				<h:outputText value="#{uploadBean.fileName}"
					rendered="#{uploadBean.messageRendered}" />
				<br><br/>
				<h:outputLabel value="File size" />
				<h:outputText value="#{uploadBean.fileSize}"
					rendered="#{uploadBean.messageRendered}" />
				<br><br/>
				<h:outputLabel value="File Content Type:" />
				<h:outputText value="#{uploadBean.fileContentType}"
					rendered="#{uploadBean.messageRendered}" />
				<br><br/>
				<h:outputLabel value="Temp File Path:" />
				<h:outputText value="#{uploadBean.filePath}"
					rendered="#{uploadBean.messageRendered}" />
				<br><br/>	
				<h:outputLabel value="Temp File Name:" />
				<h:outputText value="#{uploadBean.tempFileName}"
					rendered="#{uploadBean.messageRendered}" />
				<br><br/>
				<h:outputLabel value="Faces Context:" />
				<h:outputText value="#{uploadBean.facesContext}"
					rendered="#{uploadBean.messageRendered}" />
				<br><br/>				

			</h:panelGrid>					
		
		</h:form>
		</div>

</f:view>
</body>
</html>