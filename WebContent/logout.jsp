<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Logout - Sistema de Gestão de Cursos</title>
</head>
<body>
<%
	session.invalidate();
	response.sendRedirect("login.jsp");
%>
</body>
</html>