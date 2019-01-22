<%
	if(session.getAttribute("login") != "true"){
		session.setAttribute("mensagem", "Acesso proibido. Favor se autenticar");
%>
<jsp:forward page="/login.jsp"/>
<%
	}
%>