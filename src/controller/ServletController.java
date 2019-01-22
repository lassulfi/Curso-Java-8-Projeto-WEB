package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import enums.FormIDEnum;
import enums.FormTypeEnum;
import model.Cliente;
import model.Curso;
import model.Pagamento;
import model.PagamentoId;

/**
 * Servlet implementation class ServletController
 */
@WebServlet("/ServletController")
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjetoWEB");
	EntityManager em = emf.createEntityManager();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletController() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		// Control variables
		FormIDEnum formID; // 1 - consulta, 2 - Cursos, 3 - Pagamentos
		FormTypeEnum formTypeID; // 1.1 - consultar todos...

		formID = FormIDEnum.fromInteger(Integer.parseInt(request.getParameter("idformulario")));
		formTypeID = FormTypeEnum.fromInteger(Integer.parseInt(request.getParameter("tipoformulario")));

		String cpfMask, name, email, url, dataInscricao;
		long cpf;
		int cdcurso;
		double price;

		EntityTransaction et = em.getTransaction();
		HttpSession session = request.getSession();

		if (formID.equals(FormIDEnum.Cliente)) {
			Cliente cliente = null;

			switch (formTypeID) {
			case ConsultarTodos:
				TypedQuery<Cliente> query = em.createQuery("Select c from Cliente c", Cliente.class);
				List<Cliente> clientes = query.getResultList();
				session.setAttribute("mensagem", "Total de clientes cadastrados: " + clientes.size());
				session.setAttribute("clientes", clientes);
				response.sendRedirect("clientes/consultarTodos.jsp");
				break;
			case ConsultarUm:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
				cliente = em.find(Cliente.class, cpf);
				if (cliente != null) {// cliente encontrado
					session.setAttribute("mensagem",
							"Cliente " + request.getParameter("cpf").toString() + " encontrado");
					session.setAttribute("cliente", cliente);
				} else {// Cliente não encontrado
					session.setAttribute("mensagem",
							"Cliente " + request.getParameter("cpf").toString() + " não encontrado");
					session.setAttribute("cliente", null);
				}
				response.sendRedirect("clientes/resultado.jsp");
				break;
			case Cadastrar:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
				name = request.getParameter("nome").toString();
				email = request.getParameter("email").toString();
				et.begin();
				cliente = new Cliente(cpf, name, email);
				em.persist(cliente);
				et.commit();
				session.setAttribute("mensagem", "Cliente " + request.getParameter("cpf").toString() + " cadastrado");
				session.setAttribute("cliente", cliente);
				response.sendRedirect("clientes/resultado.jsp");
				break;
			case Alterar:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
				name = request.getParameter("nome").toString();
				email = request.getParameter("email").toString();
				cliente = em.find(Cliente.class, cpf);
				if (cliente != null) {// cliente encontrado
					cliente.setNome(name);
					cliente.setEmail(email);
					session.setAttribute("mensagem",
							"Cliente " + request.getParameter("cpf").toString() + " atualizado");
					session.setAttribute("cliente", cliente);
					et.begin();
					em.merge(cliente);
					et.commit();
				} else {// Cliente não encontrado
					session.setAttribute("mensagem", "Cliente " + request.getParameter("cpf").toString()
							+ " não encontrado. Operação cancelada");
					session.setAttribute("cliente", null);
				}
				response.sendRedirect("clientes/resultado.jsp");
				break;
			case Excluir:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
				cliente = em.find(Cliente.class, cpf);
				if (cliente != null) {// cliente encontrado
					et.begin();
					em.remove(cliente);
					et.commit();
					session.setAttribute("mensagem", "Cliente " + request.getParameter("cpf").toString() + " excluído");
				} else {// Cliente não encontrado
					session.setAttribute("mensagem", "Cliente " + request.getParameter("cpf").toString()
							+ " não encontrado. Exclusão cancelada.");
					session.setAttribute("cliente", null);
				}
				response.sendRedirect("clientes/resultado.jsp");
				break;
			}
		} else if (formID.equals(FormIDEnum.Pagamento)) {
			Pagamento pagamento = null;
			PagamentoId id = null;

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			LocalDate date;

			switch (formTypeID) {
			case ConsultarUm:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
				dataInscricao = request.getParameter("datainscricao").toString();
				date = LocalDate.parse(dataInscricao, formatter);
				id = new PagamentoId(cpf, cdcurso, date.toString());
				pagamento = em.find(Pagamento.class, id);
				if (pagamento != null) {
					session.setAttribute("mensagem",
							"Pagamento do cliente CPF " + request.getParameter("cpf").toString()
									+ " referente ao curso código " + request.getParameter("cdcurso").toString()
									+ " encontrado");
					session.setAttribute("pagamento", pagamento);
				} else {
					session.setAttribute("mensagem",
							"Pagamento do cliente CPF " + request.getParameter("cpf").toString()
									+ " referente ao curso código " + request.getParameter("cdcurso").toString()
									+ " não encontrado");
					session.setAttribute("pagamento", null);
				}
				response.sendRedirect("pagamentos/resultado.jsp");
				break;
			case ConsultarTodos:
				String sql = "select p from Pagamento p";
				TypedQuery<Pagamento> query = em.createQuery(sql, Pagamento.class);
				List<Pagamento> pagamentos = query.getResultList();
				session.setAttribute("mensagem", "Total de pagamentos cadastrados: " + pagamentos.size());
				session.setAttribute("pagamentos", pagamentos);
				response.sendRedirect("pagamentos/consultarTodos.jsp");
				break;
			case Cadastrar:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
				dataInscricao = request.getParameter("datainscricao").toString();
				date = LocalDate.parse(dataInscricao, formatter);
				Cliente cliente = em.find(Cliente.class, cpf);
				Curso curso = em.find(Curso.class, cdcurso);
				if (cliente != null && curso != null) {
					pagamento = new Pagamento(new PagamentoId(cpf, cdcurso, date.toString()), cliente, curso);
					et.begin();
					em.persist(pagamento);
					et.commit();
					session.setAttribute("mensagem",
							"Pagamento do cliente CPF " + request.getParameter("cpf").toString()
									+ " referente ao curso código " + request.getParameter("cdcurso").toString()
									+ " cadastrado");
					session.setAttribute("pagamento", pagamento);
				} else {
					session.setAttribute("mensagem",
							"Cliente CPF " + request.getParameter("cpf").toString() + " e/ou curso código "
									+ request.getParameter("cdcurso").toString() + " não encontrado(s)");
					session.setAttribute("pagamento", null);
				}
				response.sendRedirect("pagamentos/resultado.jsp");
				break;
			case Alterar:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
				dataInscricao = request.getParameter("datainscricao").toString();
				String novaData = request.getParameter("novadata").toString();
				date = LocalDate.parse(dataInscricao, formatter);
				LocalDate newDate = LocalDate.parse(novaData, formatter);
				cliente = em.find(Cliente.class, cpf);
				curso = em.find(Curso.class, cdcurso);
				if (cliente != null && curso != null) {
					id = new PagamentoId(cpf, cdcurso, date.toString());
					pagamento = em.find(Pagamento.class, id);
					if (pagamento != null) {
						PagamentoId newId = new PagamentoId(cpf, cdcurso, newDate.toString());
						pagamento = new Pagamento(newId, cliente, curso);
						et.begin();
						em.merge(pagamento);
						et.commit();
						session.setAttribute("mensagem",
								"Pagamento do cliente CPF " + request.getParameter("cpf").toString()
										+ " referente ao curso código " + request.getParameter("cdcurso").toString()
										+ " atualizado");
						session.setAttribute("pagamento", pagamento);
					} else {
						session.setAttribute("mensagem",
								"Pagamento do cliente CPF " + request.getParameter("cpf").toString()
										+ " referente ao curso código " + request.getParameter("cdcurso").toString()
										+ " não encontrado");
						session.setAttribute("pagamento", null);
					}
				} else {
					session.setAttribute("mensagem",
							"Cliente CPF " + request.getParameter("cpf").toString() + " e/ou curso código "
									+ request.getParameter("cdcurso").toString() + " não encontrado(s)");
					session.setAttribute("pagamento", null);
				}
				response.sendRedirect("pagamentos/resultado.jsp");
				break;
			case Excluir:
				cpfMask = request.getParameter("cpf").toString();
				cpfMask = cpfMask.replaceAll("[.-]", "");
				cpf = Long.parseLong(cpfMask);
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
				dataInscricao = request.getParameter("datainscricao").toString();
				date = LocalDate.parse(dataInscricao, formatter);
				cliente = em.find(Cliente.class, cpf);
				curso = em.find(Curso.class, cdcurso);
				if (cliente != null && curso != null) {
					id = new PagamentoId(cpf, cdcurso, date.toString());
					pagamento = em.find(Pagamento.class, id);
					if (pagamento != null) {
						et.begin();
						em.remove(pagamento);
						et.commit();
						session.setAttribute("mensagem",
								"Pagamento do cliente CPF " + request.getParameter("cpf").toString()
										+ " referente ao curso código " + request.getParameter("cdcurso").toString()
										+ " excluído");
						session.setAttribute("pagamento", pagamento);
					} else {
						session.setAttribute("mensagem",
								"Pagamento do cliente CPF " + request.getParameter("cpf").toString()
										+ " referente ao curso código " + request.getParameter("cdcurso").toString()
										+ " não encontrado");
						session.setAttribute("pagamento", null);
					}
				} else {
					session.setAttribute("mensagem",
							"Cliente CPF " + request.getParameter("cpf").toString() + " e/ou curso código "
									+ request.getParameter("cdcurso").toString() + " não encontrado(s)");
					session.setAttribute("pagamento", null);
				}
				response.sendRedirect("pagamentos/resultado.jpg");
				break;
			}
		} else if (formID.equals(FormIDEnum.Curso)) {
			Curso curso = null;
			switch (formTypeID) {
			case ConsultarTodos:
				String sql = "Select c from Curso c";
				TypedQuery<Curso> query = em.createQuery(sql, Curso.class);
				List<Curso> cursos = query.getResultList();
				session.setAttribute("mensagem", "Total de cursos cadastrados: " + cursos.size());
				session.setAttribute("cursos", cursos);
				response.sendRedirect("cursos/consultarTodos.jsp");
				break;
			case ConsultarUm:
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
				curso = em.find(Curso.class, cdcurso);
				if (curso != null) {// cliente encontrado
					session.setAttribute("mensagem", "Curso " + request.getParameter("cdcurso") + " encontrado");
					session.setAttribute("curso", curso);
				} else {// Cliente não encontrado
					session.setAttribute("mensagem",
							"Curso " + request.getParameter("cdcurso").toString() + " não encontrado");
					session.setAttribute("curso", null);
				}
				response.sendRedirect("cursos/resultado.jsp");
				break;
			case Cadastrar:
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
				name = request.getParameter("nome").toString();
				price = Double.parseDouble(request.getParameter("valor").toString());
				url = request.getParameter("url").toString();
				curso = new Curso(cdcurso, name, price, url);
				et.begin();
				em.persist(curso);
				et.commit();
				session.setAttribute("mensagem", "Curso " + request.getParameter("cdcurso") + " encontrado");
				session.setAttribute("curso", curso);
				response.sendRedirect("cursos/resultado.jsp");
				break;
			case Alterar:
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
				name = request.getParameter("nome").toString();
				price = Double.parseDouble(request.getParameter("valor").toString());
				url = request.getParameter("url").toString();
				curso = em.find(Curso.class, cdcurso);
				if (curso != null) {
					et.begin();
					em.merge(curso);
					et.commit();
					session.setAttribute("mensagem", "Curso " + request.getParameter("cdcurso") + " atualizado");
					session.setAttribute("curso", curso);
				} else {
					session.setAttribute("mensagem", "Curso " + request.getParameter("cdcurso") + " não encontrado.");
					session.setAttribute("curso", null);
				}
				response.sendRedirect("cursos/resultado.jsp");
				break;
			case Excluir:
				cdcurso = Integer.parseInt(request.getParameter("cdcurso").toString());
				curso = em.find(Curso.class, cdcurso);
				if (curso != null) {
					et.begin();
					em.remove(curso);
					et.commit();
					session.setAttribute("mensagem", "Curso " + request.getParameter("cdcurso") + " excluido");
					session.setAttribute("curso", curso);
				} else {
					session.setAttribute("mensagem", "Curso " + request.getParameter("cdcurso") + " não encontrado.");
					session.setAttribute("curso", null);
				}
				response.sendRedirect("cursos/resultado.jsp");
				break;
			}
		}
	}

}
