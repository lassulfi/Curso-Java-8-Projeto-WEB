<jsp:directive.page import="java.sql.*"/>

<jsp:declaration>
	
	static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	static String username = "cursojava";
	static String password = "schema";
	
	static Connection connection;
	
	public void init() throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean login(long cpf, String password) throws SQLException {
		String sql = "select * from login where cpf=" + cpf +" and senha='" + password + "'";

		Statement st = connection.prepareStatement(sql);

		ResultSet rs = st.executeQuery(sql);
		
		return rs.next();
	}
	
</jsp:declaration>

<jsp:scriptlet>
	
	String cpf = request.getParameter("cpf");
	cpf = cpf.replaceAll("[.-]", "");
	long cpfValue = Long.parseLong(cpf);
	String password = request.getParameter("senha");

	try {
		if(login(cpfValue, password)) {
			session.setAttribute("mensagem", "Usuário autenticado");
			session.setAttribute("login", "true");
			response.sendRedirect("../index.jsp");
		} else {
			session.setAttribute("mensagem", "Usuário não autenticado");
			response.sendRedirect("../login.jsp");
			session.setAttribute("login", "false");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}

</jsp:scriptlet>