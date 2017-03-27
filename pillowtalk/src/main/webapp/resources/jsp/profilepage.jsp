<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


</head>
<body>
	<jsp:include page="link.jsp" />
	<hr>
	<br>
	<br>
	<center>
		<form:form method="POST" action="editpage">
			<table cellspacing="2" cellpadding="10">
			<tr>
					<td>Title :</td>
					<td>${title}</td>
				</tr>
				<tr>
					<td>First Name :</td>
					<td>${firstname}</td>
				</tr>
				<tr>
					<td>Surname :</td>
					<td>${surname}</td>
				</tr>
				<tr>
					<td>Mobile Number :</td>
					<td>${number}</td>
				</tr>
				<tr>
					<td>Email :</td>
					<td>${email}</td>
				</tr>
				

				<!-- <tr>    
         			<td colspan="2" align="center"><input type="submit" value="Edit" /></td>    
         		</tr>    -->

			</table>
		</form:form>
	</center>
</body>
</html>