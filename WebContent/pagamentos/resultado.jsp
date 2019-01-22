<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../util/topo.jsp" />
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="../lib/js/jquery.min.js"></script>
<script type="text/javascript" src="../lib/js/bootstrap.min.js"></script>
<link href="../lib/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<link href="../lib/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="../lib/css/padrao.css" rel="stylesheet" type="text/css">
<title>Sucesso - Sistema de Gerenciamento de Cursos</title>
</head>
<body>
	<div class="section section-danger text-justify">
		<div class="container">
			<div class="row text-center">
				<div class="col-md-12 text-center">
					<h1 class="text-center">Sistema de Gerenciamento de Cursos</h1>
				</div>
			</div>
		</div>
	</div>
	<div class="section section-danger text-justify">
		<h1 class="text-center">${mensagem}</h1>
		<div class="container">
			<div class="row text-center">
				<div class="col-md-6 text-center">
					<h2>Informações do cliente</h2>
					<c:if test="${pagamento != null}">
						<h3 class="text-center">CPF: ${pagamento.cliente.cpf}</h3>
						<h3 class="text-center">NOME: ${pagamento.cliente.nome}</h3>
						<h3 class="text-center">EMAIL: ${pagamento.cliente.email}</h3>
					</c:if>
				</div>
				<div class="col-md-6 text-center">
					<h2>Informações do curso</h2>
					<c:if test="${pagamento != null}">
						<h3 class="text-center">CÓDIGO DO CURSO: ${pagamento.curso.cdcurso}</h3>
						<h3 class="text-center">NOME: ${pagamento.curso.nome}</h3>
						<h3 class="text-center">VALOR: ${pagamento.curso.valor}</h3>
						<h3 class="text-center">URL: ${pagamento.curso.url}</h3>
					</c:if>
				</div>
			</div>
			<c:if test="${pagamento != null}">
				<h3>Data de efetuação do pagamento: ${pagamento.id.datainscricao}</h3>
			</c:if>
		</div>
	</div>
	<div class="section">
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<a class="btn btn-default" href="index.jsp">Retornar ao menu de
						clientes</a>
				</div>
			</div>
		</div>
	</div>
	<footer>
		<div class="navbar navbar-fixed-bottom bgred">
			<div class="container">
				<div class="row">
					<div class="col-sm-12 text-center" style="top: 13px; color: #fff;">Â©
						ABCTreinamentos - Curso de Java 8 para Web</div>
				</div>
			</div>
		</div>
	</footer>


</body>
</html>