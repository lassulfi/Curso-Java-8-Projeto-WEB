package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import enums.FormIDEnum;
import enums.FormTypeEnum;

/**
 * Servlet implementation class ServletController
 */
@WebServlet("/ServletController")
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletController() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Control variables
		FormIDEnum formID; // 1 - consulta, 2 - Cursos, 3 - Pagamentos
		FormTypeEnum formTypeID; // 1.1 - consultar todos...

		formID = FormIDEnum.fromInteger(Integer.parseInt(request.getParameter("idformulario")));
		formTypeID = FormTypeEnum.fromInteger(Integer.parseInt(request.getParameter("tipoformulario")));

		String cpfMask, name, email, url, dataInscricao;
		long cpf;
		int cdcurso;
		double price;

		if (formID.equals(FormIDEnum.Cliente)) {

			switch (formTypeID) {
			case ConsultarTodos:
				break;
			case ConsultarUm:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
//				if(cliente != null) cliente = null;
//				cliente = em.find(Cliente.class, cpf);
				out.println("<h2>Clientes => Consultar => " + cpf + "</h2>");
				break;
			case Cadastrar:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
				name = request.getParameter("nome").toString();
				email = request.getParameter("email").toString();
				out.println(
						"<h2>Clientes => Cadastrar => CPF: " + cpf + " nome: " + name + " email: " + email + "</h2>");
//				et.begin();
//				em.persist(new Cliente(cpf, name, email));
//				et.commit();
				break;
			case Alterar:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
				name = request.getParameter("nome").toString();
				email = request.getParameter("email").toString();
				out.println("<h2>Clientes => Alterar => CPF: " + cpf + " nome: " + name + " email: " + email + "</h2>");
//				et.begin();
//				em.merge(new Cliente(cpf, name, email));
//				et.commit();
				break;
			case Excluir:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
				out.println("<h2>Clientes => Excluir => " + cpf + "</h2>");
//				if(cliente != null) cliente = null;
//				cliente = em.find(Cliente.class, cpf);
//				et.begin();
//				em.remove(cliente);
//				et.commit();
				break;
			}
		} else if (formID.equals(FormIDEnum.Pagamento)) {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter dmf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			LocalDate date;
			
			switch (formTypeID) {
			case ConsultarUm:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
//					pagamento = em.find(Pagamento.class, new PagamentoId(cpf, course, subscription));
				out.println("<h2>Pagamento => Consultar => CPF: " + cpf + " código curso: " + cdcurso + "</h2>");
				break;
			case ConsultarTodos:
//					System.out.println("[4] - Consulta de todas as compras");
//					String sql = "select p from Pagamento p";
//					TypedQuery<Pagamento> query = em.createQuery(sql, Pagamento.class);
//					List<Pagamento> pagamentos = query.getResultList();
//					pagamentos.forEach(System.out::println);
//					System.out.println("Total de compras cadastradas: " + pagamentos.size());
				break;
			case Cadastrar:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
				dataInscricao = request.getParameter("datainscricao").toString();
				date = LocalDate.parse(dataInscricao, formatter);
//					cliente = em.find(Cliente.class, cpf);
//					curso = em.find(Curso.class, course);
//					et.begin();
//					em.persist(new Pagamento(new PagamentoId(cpf, course, date), cliente, curso));
//					et.commit();
				out.println("<h2>Pagamento => Cadastrar => CPF: " + cpf + " código curso: " + cdcurso
						+ "Data inscrição: " + dmf.format(date) + "</h2>");
				break;
			case Alterar:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
				dataInscricao = request.getParameter("datainscricao").toString();
				date = LocalDate.parse(dataInscricao, formatter);
//					cliente = em.find(Cliente.class, cpf);
//					curso = em.find(Curso.class, course);
//					et.begin();
//					em.merge(new Pagamento(new PagamentoId(cpf, course, subscription), cliente, curso));
//					et.commit();
				out.println("<h2>Pagamento => Alterar => CPF: " + cpf + " código curso: " + cdcurso
						+ "Data inscrição: " + dmf.format(date) + "</h2>");
				break;
			case Excluir:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
//					cliente = em.find(Cliente.class, cpf);
//					curso = em.find(Curso.class, course);
//					et.begin();
//					em.remove(new Pagamento(new PagamentoId(cpf, course, subscription), cliente, curso));
//					et.commit();
				out.println("<h2>Pagamento => Excluir => CPF: " + cpf + " código curso: " + cdcurso + "</h2>");
				break;
			}
		} else if (formID.equals(FormIDEnum.Curso)) {
			switch (formTypeID) {
			case ConsultarTodos:
//					TypedQuery<Curso> query = em.createQuery(sql, Curso.class);
//					List<Curso> cursos = query.getResultList();
				break;
			case ConsultarUm:
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
				out.println("<h2>Cursos => Consultar => cdcurso: " + cdcurso + "</h2>");
//					curso = em.find(Curso.class, cdcurso);
				break;
			case Cadastrar:
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
				name = request.getParameter("nome").toString();
				price = Double.parseDouble(request.getParameter("valor").toString());
				url = request.getParameter("url").toString();
				out.println("<h2>Cursos => Cadastrar => cdcurso: " + cdcurso + " nome: " + name + " valor: " + price
						+ " url: " + url + "</h2>");
//					et.begin();
//					em.persist(new Curso(cdcurso, name, valor, url));
//					et.commit();
				break;
			case Alterar:
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
				name = request.getParameter("nome").toString();
				price = Double.parseDouble(request.getParameter("valor").toString());
				url = request.getParameter("url").toString();
				out.println("<h2>Cursos => Alterar => cdcurso: " + cdcurso + " nome: " + name + " valor: " + price
						+ " url: " + url + "</h2>");
//					et.begin();
//					em.merge(new Curso(cdcurso, name, valor, url));
//					et.commit();
				break;
			case Excluir:
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
				out.println("<h2>Cursos => Excluir => cdcurso: " + cdcurso + "</h2>");
//					et.begin();
//					em.remove(curso);
//					et.commit();
				break;
			}
		}
	}

}
